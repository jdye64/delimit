package com.jeremydyer.delimit.pojo;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * Creates a generic context container that holds Generic rows that have been
 * loaded from a delimited file.
 *
 * User: Jeremy Dyer
 * Date: 11/6/13
 * Time: 1:12 PM
 */
public class GenericContext {

    private File sourceFile = null;
    private String delimiter = null;
    private String[] columns = null;
    private ArrayList<GenericRow> rows = null;

    public GenericContext(File sourceFile, String delimiter, String[] columns, ArrayList<GenericRow> rows) {
        this.sourceFile = sourceFile;
        this.delimiter = delimiter;
        this.columns = columns;
        this.rows = rows;
    }

    public String createColumnHeaderStringWithDelim(String delimiter) {
        ArrayList<String> values = new ArrayList<String>();
        for (String val : this.columns) {
            values.add(val);
        }
        return StringUtils.join(values, delimiter);
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public ArrayList<GenericRow> getRows() {
        return rows;
    }

    public void setRows(ArrayList<GenericRow> rows) {
        this.rows = rows;
    }
}
