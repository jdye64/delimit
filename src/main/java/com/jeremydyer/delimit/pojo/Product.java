package com.jeremydyer.delimit.pojo;

import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Use the GenericRow object as this class has been deprecated.
 *
 * Bean for a Volusion Product
 *
 * User: Jeremy Dyer
 * Date: 11/1/13
 * Time: 1:39 PM
 */
@Deprecated
public class Product {

    private Map<String, String> productFieldValueMap = null;

    public Product(String[] columns, String csvLine, String delimiter) {
        this.productFieldValueMap = new LinkedHashMap<String, String>();

        String[] values = StringUtils.splitPreserveAllTokens(csvLine, delimiter);
        if (values.length != columns.length) {
            System.out.println("Value length - " + values.length + " Columns length " + columns.length);
            System.err.println("Something is massively screwed up and the number of columns does not match the number of values! Exiting the applcation");
            System.exit(-1);
        }
        for (int i = 0; i < values.length; i++) {
            this.productFieldValueMap.put(columns[i], values[i]);
        }
    }

    /**
     * Takes this product and makes it a child of the parent Product specified.
     *
     * 1. set hideproduct = Y
     * 2. set stockstatus = 999
     * 3. photos_cloned_from = {parent.productcode}
     *
     * @param parent
     *      Parent Product to make this one a child of.
     */
    public void makeProductChildOfProduct(Product parent) {
        this.getProductFieldValueMap().put("hideproduct", "Y");
        this.getProductFieldValueMap().put("stockstatus", "999");
        this.getProductFieldValueMap().put("photos_cloned_from", parent.getProductFieldValue("productcode"));
        this.getProductFieldValueMap().put("ischildofproductcode", parent.getProductFieldValue("productcode"));
    }

    public String createDelimitedLineWithDelimiter(String delimiter) {
        for (int i = 0; i < ProductContext.fieldsToRemove.length; i++) {
            this.productFieldValueMap.remove(ProductContext.fieldsToRemove[i]);
        }
        return StringUtils.join(this.getProductFieldValueMap().values(), delimiter);
    }

    public String getProductFieldValue(String field) {
        return this.getProductFieldValueMap().get(field);
    }

    public void setProductFieldValue(String field, String value) {
        this.getProductFieldValueMap().put(field, value);
    }

    public boolean isProductAChildProduct() {
        if (this.productFieldValueMap.get("ischildofproductcode") != null) {
            if (!this.productFieldValueMap.get("ischildofproductcode").equalsIgnoreCase("")) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Map<String, String> getProductFieldValueMap() {
        return productFieldValueMap;
    }

    public void setProductFieldValueMap(Map<String, String> productFieldValueMap) {
        this.productFieldValueMap = productFieldValueMap;
    }
}
