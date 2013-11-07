package com.jeremydyer.delimit.pojo;

import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Use the GenericRow object as this class has been deprecated.
 *
 * User: Jeremy Dyer
 * Date: 11/1/13
 * Time: 3:09 PM
 */
@Deprecated
public class Option {

    private Map<String, String> optionValuesMap = null;

    public Option(String[] columns, String csvLine, String delimiter) {
        this.optionValuesMap = new LinkedHashMap<String, String>();

        String[] values = StringUtils.splitPreserveAllTokens(csvLine, delimiter);
        if (values.length != columns.length) {
            System.out.println("Value length - " + values.length + " Columns length " + columns.length);
            System.err.println("Something is massively screwed up and the number of columns does not match the number of values! Exiting the applcation");
            System.exit(-1);
        }
        for (int i = 0; i < values.length; i++) {
            this.optionValuesMap.put(columns[i], values[i]);
        }
    }

    public Map<String, String> getOptionValuesMap() {
        return optionValuesMap;
    }

    public void setOptionValuesMap(Map<String, String> optionValuesMap) {
        this.optionValuesMap = optionValuesMap;
    }
}
