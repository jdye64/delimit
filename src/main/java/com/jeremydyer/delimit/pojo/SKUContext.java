package com.jeremydyer.delimit.pojo;

import java.util.ArrayList;

/**
 * Use the GenericContext now since this is deprecated.
 *
 * User: Jeremy Dyer
 * Date: 11/1/13
 * Time: 2:50 PM
 */
@Deprecated
public class SKUContext {

    private String[] columns = null;
    private ArrayList<SKU> skuList = null;

    public SKUContext(String[] columns, ArrayList<SKU> skuList) {
        this.columns = columns;
        this.skuList = skuList;
    }

    public ArrayList<SKU> getSKUForParentCode(String parentCode) {
        ArrayList<SKU> children = new ArrayList<SKU>();

        for (SKU sku : this.skuList) {
            if (sku.getValue("parent_productcode").equalsIgnoreCase(parentCode)) {
                children.add(sku);
            }
        }

        return children;
    }


    public String getSKUBasedOnParentCodeAndProductPrice(String original, String parentProductCode, String productPrice) {
        String skuNum = original;
        for (SKU sku : this.skuList) {
            String skuParentProductCode = sku.getValue("parent_productcode");
            String skuListPrice = sku.getValue("list_price");
            //System.out.println("SKU Code - '" + skuParentProductCode + "' Parent Code -'" + parentProductCode + "' SKU Price - '" + skuListPrice + "' Parent Price - " + productPrice + "'");
            if (skuParentProductCode.equalsIgnoreCase(parentProductCode) && skuListPrice.equalsIgnoreCase(productPrice)) {
                //System.out.println("Match found!!!");
                return sku.getValue("child_product_id");
            }
        }
        return skuNum;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public ArrayList<SKU> getSkuList() {
        return skuList;
    }

    public void setSkuList(ArrayList<SKU> skuList) {
        this.skuList = skuList;
    }
}
