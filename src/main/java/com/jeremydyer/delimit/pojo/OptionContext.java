package com.jeremydyer.delimit.pojo;

import java.util.ArrayList;

/**
 * Use the GenericContext now since this is deprecated.
 *
 * User: Jeremy Dyer
 * Date: 11/1/13
 * Time: 3:10 PM
 */
@Deprecated
public class OptionContext {

    private String[] columns = null;
    private ArrayList<Option> optionList = null;

    public OptionContext(String[] columns, ArrayList<Option> optionList) {
        this.columns = columns;
        this.optionList = optionList;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public ArrayList<Option> optionList() {
        return optionList;
    }

    public void setProductList(ArrayList<Option> optionList) {
        this.optionList = optionList;
    }
}
