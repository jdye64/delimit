package com.jeremydyer.delimit.pojo;

import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: Jeremy Dyer
 * Date: 11/6/13
 * Time: 1:12 PM
 */
public class GenericRow {

    private Map<String, String> genericObjectFieldValueMap = null;

    public GenericRow(String[] columns, String csvLine, String delimiter) {
        this.genericObjectFieldValueMap = new LinkedHashMap<String, String>();

        String[] values = StringUtils.splitPreserveAllTokens(csvLine, delimiter);
        if (values.length != columns.length) {
            System.out.println("Value length - " + values.length + " Columns length " + columns.length);
            System.err.println("Something is massively screwed up and the number of columns does not match the number of values! Exiting the applcation");
            System.exit(-1);
        }
        for (int i = 0; i < values.length; i++) {
            this.genericObjectFieldValueMap.put(columns[i], values[i]);
        }
    }

    public String createDelimitedLineWithDelimiter(String delimiter) {
        return StringUtils.join(this.genericObjectFieldValueMap.values(), delimiter);
    }

    public String getValueForField(String columnName) {
        return this.genericObjectFieldValueMap.get(columnName);
    }

    public void setValueForField(String columnName, String columnValue) {
        this.genericObjectFieldValueMap.put(columnName, columnValue);
    }

    public Map<String, String> getGenericObjectFieldValueMap() {
        return genericObjectFieldValueMap;
    }

    public void setGenericObjectFieldValueMap(Map<String, String> genericObjectFieldValueMap) {
        this.genericObjectFieldValueMap = genericObjectFieldValueMap;
    }
}
