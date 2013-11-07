package com.jeremydyer.delimit.pojo;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

/**
 * Use the GenericContext now since this is deprecated.
 *
 * Holds Products in a context and wraps them with service calls that can be
 * made to perform CRUD operations on the Products.
 *
 * User: Jeremy Dyer
 * Date: 11/1/13
 * Time: 2:21 PM
 */
@Deprecated
public class ProductContext {

    private String[] columns = null;
    private ArrayList<Product> productList = null;

    public static final String[] fieldsToRemove = new String[]{"photoseed",
            "numproductssharingstock", "categorytree", "photourl", "producturl"};

    public ProductContext(String[] columns, ArrayList<Product> productList) {
        this.columns = columns;
        this.productList = productList;
    }

    public void addProductToContext(Product newProduct) {
        this.productList.add(newProduct);
    }

    public String createColumnHeaderStringWithDelim(String delimiter) {
        ArrayList<String> values = new ArrayList<String>();
        for (String val : this.columns) {
            if (!omitField(val)) {
                values.add(val);
            }
        }
        return StringUtils.join(values, delimiter);
    }

    private boolean omitField(String field) {
        for (String val : fieldsToRemove) {
            if (field.equalsIgnoreCase(val)) {
                System.out.println("Omitting field " + val);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a Product object based on its product code.
     *
     * @param productCode
     *      Product code for the object to retrieve.
     *
     * @return
     *      Product object found in the list.
     */
    public Product getProductByCode(String productCode) {
        for (Product product : productList) {
            if (product.getProductFieldValueMap().get("productcode").equalsIgnoreCase(productCode)) {
                return product;
            }
        }
        return null;
    }

    public ArrayList<Product> getChildProducts(Product parentProduct) {
        ArrayList<Product> childProducts = new ArrayList<Product>();
        for (Product product : this.productList) {
            if (product.getProductFieldValueMap().get("ischildofproductcode").equalsIgnoreCase(parentProduct.getProductFieldValue("productcode"))) {
                childProducts.add(product);
            }
        }
        return childProducts;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }
}
