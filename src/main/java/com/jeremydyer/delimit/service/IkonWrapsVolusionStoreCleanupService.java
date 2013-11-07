package com.jeremydyer.delimit.service;


import com.jeremydyer.delimit.pojo.Product;
import com.jeremydyer.delimit.pojo.ProductContext;
import com.jeremydyer.delimit.pojo.SKU;
import com.jeremydyer.delimit.pojo.SKUContext;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Service for cleaning up the data from the Ikon Wraps Volusion Store.
 *
 * User: Jeremy Dyer
 * Date: 11/7/13
 * Time: 10:17 AM
 */
public class IkonWrapsVolusionStoreCleanupService {

    private static final Logger logger = Logger.getLogger(IkonWrapsVolusionStoreCleanupService.class);

    //To prevent Java deep object copy problems I do a serialize and then deserialize on an object. This has 2 advantages.
    //1) I don't have to write more code for every type of object I want to deep copy
    //2) No risk is not getting the deep copy for fudging up the context which has some lockedlinkedlists
    //Although it is not as efficient whatever this is just a utility.
    private XStream xStream = new XStream();

    /**
     * Takes a SKUContext and then replaces the values in the ProductContext with those SKU number values.
     *
     * @param productContext
     *      ProductContext object.
     *
     * @param skuContext
     *      SKUContext object.
     *
     * @return
     *      Updated ProductContext.
     */
    private ProductContext fixProductSkuNumbersWithOriginalSKUSheet(ProductContext productContext, SKUContext skuContext) {
        ArrayList<Product> updatedProducts = new ArrayList<Product>();
        for (Product product : productContext.getProductList()) {
            String productCode = product.getProductFieldValue("productcode");
            if (productCode.contains("-")) {
                productCode = skuContext.getSKUBasedOnParentCodeAndProductPrice(product.getProductFieldValue("productcode"),
                        product.getProductFieldValue("ischildofproductcode"), product.getProductFieldValue("productprice"));
                System.out.println("New product code - '" + productCode + "'");
            }
            product.setProductFieldValue("productcode", productCode);
            updatedProducts.add(product);
        }
        productContext.setProductList(updatedProducts);
        return productContext;
    }


    private SKUContext enrichSKUContextWithOptionsInformation(SKUContext skuContext) {
        System.out.println("Enriches the SKU Context");

        for (SKU sku : skuContext.getSkuList()) {
            //Updates the parentcode from the child_product_id value.
            String childProductId = sku.getValue("child_product_id");
            //System.out.println("\t" + childProductId);
            if (childProductId.contains(".")) {
                String[] parts = StringUtils.splitPreserveAllTokens(childProductId, ".");
                sku.setValue("parent_productcode", (parts[0] + "." + parts[1]));
            } else {
                sku.setValue("parent_productcode", childProductId);
            }
        }

        return skuContext;
    }


    /**
     * Adds the child products from the SKU to the list of Products.
     *
     * @param productContext
     *
     * @param skuContext
     *
     * @return
     */
    private ProductContext addChildProductsToList(ProductContext productContext, SKUContext skuContext) {

        ArrayList<Product> childrenProducts = new ArrayList<Product>();

        //Loops through all of the files.
        for (Product product : productContext.getProductList()) {
            ArrayList<SKU> children = skuContext.getSKUForParentCode(product.getProductFieldValue("productcode"));
            //System.out.println(product.getProductFieldValue("productcode") + " has " + children.size() + " children");
            for (SKU sku : children) {
                Product childProductCopy = this.deepCopyProduct(product);

                //Now that we have a copy of the parent we need to overwrite just a few fields to make sure its updated.
                childProductCopy.makeProductChildOfProduct(product);

                //System.out.println("SKU Value - " + sku.getValue("option_name").replace("\"\"", "\""));
                childProductCopy.setProductFieldValue("productcode", sku.getValue("child_product_id"));
                String secondName = sku.getValue("option_name").replace("\"\"", "\"");
                if (secondName.charAt(0) == '\"') {
                    secondName = secondName.substring(1);
                }
                if (secondName.charAt(secondName.length() - 1) == '\"') {
                    secondName = secondName.substring(0, secondName.length() - 1);
                }
                childProductCopy.setProductFieldValue("productname", (childProductCopy.getProductFieldValue("productname").replace("\"\"", "\"") + " - " + secondName));
                childProductCopy.setProductFieldValue("productprice", sku.getValue("list_price"));
                childProductCopy.setProductFieldValue("categoryids", "");

                //http://pehmd.bwsgr.servertrust.com/ProductDetails.asp?ProductCode=3M1080.BR120
                String productUrl = childProductCopy.getProductFieldValue("producturl");
                productUrl = productUrl.split("ProductCode=")[0];
                productUrl = productUrl + "ProductCode=" + sku.getValue("child_product_id");
                childProductCopy.setProductFieldValue("producturl", productUrl);

                //http://pehmd.bwsgr.servertrust.com/v/vspfiles/photos/3M1080.BR120-1.jpg
                String photoUrl = childProductCopy.getProductFieldValue("photourl");
                photoUrl = StringUtils.remove(photoUrl, (childProductCopy.getProductFieldValue("ischildofproductcode") + "-1.jpg"));
                photoUrl = photoUrl + childProductCopy.getProductFieldValue("productcode") + "-1.jpg";
                childProductCopy.setProductFieldValue("photourl", photoUrl);

                product.setProductFieldValue("enableoptions_inventorycontrol", "Y");
                childProductCopy.setProductFieldValue("enablemultichildaddtocart", "");
                childProductCopy.setProductFieldValue("enableoptions_inventorycontrol", "");

                //Then add the product to the context.
                //productContext.addProductToContext(childProductCopy);
                childrenProducts.add(childProductCopy);
            }
        }

        //Well as un optimal as it is we need to order these fields so lets do that now ....
        ArrayList<Product> originalProducts = productContext.getProductList();

        //Temporarily sets the children into the context so that they can be queried for useful information.
        productContext.setProductList(childrenProducts);
        ArrayList<Product> orderedProducts = new ArrayList<Product>();
        for (Product product : originalProducts) {
            orderedProducts.add(product);

            //Gets all of the children products for this Product.
            ArrayList<Product> childProducts = productContext.getChildProducts(product);
            for (Product childProduct : childProducts) {
                orderedProducts.add(childProduct);
            }
        }

        productContext.setProductList(orderedProducts);
        return productContext;
    }


    /**
     * Makes a deep although in-efficient copy of a Product object.
     *
     * @param product
     * @return
     */
    private Product deepCopyProduct(Product product) {
        return (Product) xStream.fromXML(xStream.toXML(product));
    }

}
