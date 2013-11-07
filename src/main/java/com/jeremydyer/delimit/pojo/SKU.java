package com.jeremydyer.delimit.pojo;

import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Use the GenericRow object as this class has been deprecated.
 *
 * User: Jeremy Dyer
 * Date: 11/1/13
 * Time: 2:49 PM
 */
@Deprecated
public class SKU {

    private Map<String, String> skuFieldValueMap = null;

    public SKU(String[] columns, String csvLine, String delimiter) {
        this.skuFieldValueMap = new LinkedHashMap<String, String>();

        String[] values = StringUtils.splitPreserveAllTokens(csvLine, delimiter);
        if (values.length != columns.length) {
            System.out.println("Value length - " + values.length + " Columns length " + columns.length);
            System.err.println("Something is massively screwed up and the number of columns does not match the number of values! Exiting the applcation");
            System.exit(-1);
        }
        for (int i = 0; i < values.length; i++) {
            this.skuFieldValueMap.put(columns[i], values[i]);
        }
    }

    public void setValue(String field, String value) {
        this.skuFieldValueMap.put(field, value);
    }

    public String getValue(String field) {
        return this.skuFieldValueMap.get(field);
    }

    public Map<String, String> getSkuFieldValueMap() {
        return skuFieldValueMap;
    }

    public void setSkuFieldValueMap(Map<String, String> skuFieldValueMap) {
        this.skuFieldValueMap = skuFieldValueMap;
    }
}
