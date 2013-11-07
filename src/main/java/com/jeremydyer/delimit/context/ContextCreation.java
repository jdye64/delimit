package com.jeremydyer.delimit.context;

import com.jeremydyer.delimit.pojo.*;
import com.jeremydyer.delimit.service.ExtensionAwarenessService;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Service for creating the in memory representation of the delimited data set that you are trying to manipulate
 * That dataset is broken down into "rows" based on newlines and those "rows" are all stored in a Context* object.
 * Those Context* objects are created by this service.
 *
 * User: Jeremy Dyer
 * Date: 11/1/13
 * Time: 3:54 PM
 */
public class ContextCreation {

    private ExtensionAwarenessService extensionAwarenessService = new ExtensionAwarenessService();

    /**
     * Creates the GenericContext from a file reference.
     *
     * @param genericFile
     *      Location of the file to create the list of GenericRows from.
     *
     * @return
     *      GenericContext
     *
     * @throws java.io.IOException
     */
    public GenericContext createGenericContext(File genericFile, String delimiter) throws IOException {

        System.out.println("Creating GenericContext from file -> '" + genericFile.getAbsolutePath() + "'");

        ArrayList<GenericRow> rows = new ArrayList<GenericRow>();
        BufferedReader br = new BufferedReader(new FileReader(genericFile));
        String headerLine = br.readLine();
        String[] columns = StringUtils.splitPreserveAllTokens(headerLine, delimiter);
        System.out.println("# Columns " + columns.length);

        String dataLine = null;
        int lines = 0;
        while ((dataLine = br.readLine()) != null) {
            lines++;
            rows.add(new GenericRow(columns, dataLine, delimiter));
        }

        System.out.println(rows.size() + " rows loaded");
        br.close();

        GenericContext gc = new GenericContext(genericFile, delimiter, columns, rows);
        return gc;
    }

    /**
     * Creates the list of Products from the csv file.
     *
     * @param productFile
     *      Location of the file to create the list of Products from.
     *
     * @return
     *      List of Product items.
     *
     * @throws java.io.IOException
     */
    public ProductContext createProductsList(File productFile) throws IOException {

        String delimiter = this.extensionAwarenessService.determineContentDelimFromFile(productFile);
        ArrayList<Product> products = new ArrayList<Product>();
        BufferedReader br = new BufferedReader(new FileReader(productFile));
        String headerLine = br.readLine();
        String[] columns = StringUtils.splitPreserveAllTokens(headerLine, delimiter);
        System.out.println("# Columns " + columns.length);

        String dataLine = null;
        int lines = 0;
        while ((dataLine = br.readLine()) != null) {
            lines++;
            products.add(new Product(columns, dataLine, delimiter));
        }

        System.out.println(products.size() + " products loaded");
        br.close();

        ProductContext proc = new ProductContext(columns, products);
        return proc;
    }

    //Jsut try to use the GenericContext from now on. I promise it will work for you.
    @Deprecated
    public SKUContext createSkuContext(File skuFile) throws IOException {
        String delimiter = this.extensionAwarenessService.determineContentDelimFromFile(skuFile);
        ArrayList<SKU> skuList = new ArrayList<SKU>();
        BufferedReader br = new BufferedReader(new FileReader(skuFile));
        String headerLine = br.readLine();
        String[] columns = StringUtils.splitPreserveAllTokens(headerLine, delimiter);
        System.out.println("# Columns " + columns.length);

        String dataLine = null;
        int lines = 0;
        while ((dataLine = br.readLine()) != null) {
            lines++;
            skuList.add(new SKU(columns, dataLine, delimiter));
        }

        System.out.println(skuList.size() + " products loaded");
        br.close();

        SKUContext proc = new SKUContext(columns, skuList);
        return proc;
    }

    //Jsut try to use the GenericContext from now on. I promise it will work for you.
    @Deprecated
    public OptionContext createOptionContext(File optionFile) throws IOException {
        String delimiter = this.extensionAwarenessService.determineContentDelimFromFile(optionFile);
        ArrayList<Option> skuList = new ArrayList<Option>();
        BufferedReader br = new BufferedReader(new FileReader(optionFile));
        String headerLine = br.readLine();
        String[] columns = StringUtils.splitPreserveAllTokens(headerLine, delimiter);
        System.out.println("# Columns " + columns.length);

        String dataLine = null;
        int lines = 0;
        while ((dataLine = br.readLine()) != null) {
            lines++;
            skuList.add(new Option(columns, dataLine, delimiter));
        }

        System.out.println(skuList.size() + " products loaded");
        br.close();

        OptionContext proc = new OptionContext(columns, skuList);
        return proc;
    }
}
