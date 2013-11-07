package com.jeremydyer.delimit.context;

import com.jeremydyer.delimit.pojo.GenericContext;
import com.jeremydyer.delimit.pojo.GenericRow;
import com.jeremydyer.delimit.pojo.Product;
import com.jeremydyer.delimit.pojo.ProductContext;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Handles persisting a GenericContext to a persisted location.
 *
 * User: Jeremy Dyer
 * Date: 11/6/13
 * Time: 1:50 PM
 */
public class ContextPersistence {

    private static final Logger logger = Logger.getLogger(ContextPersistence.class);

    /**
     * Takes a GenericContext object and writes it to the OutputFile specified.
     *
     * @param genericContext
     *      The GenericContext object that we want to persist.
     *
     * @param outputFile
     *      File that the GenericContext should be written to.
     */
    public void saveGenericContextToTSVFile(GenericContext genericContext, File outputFile) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

        //First write the column headers to the file.
        bw.write(genericContext.createColumnHeaderStringWithDelim("\t"));
        bw.write("\n");

        //Loops through and writes all of the products back out to a file.
        for (GenericRow row : genericContext.getRows()) {
            bw.write(row.createDelimitedLineWithDelimiter("\t"));
            bw.write("\n");
        }

        bw.close();
    }


    /**
     * Takes the products that have been cleaned and writes them back out to another csv file.
     *
     * @param productContext
     *      ProductContext object.
     */
    private void writeProductsToTSV(File outputFile, ProductContext productContext) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

        //First write the column headers to the file.
        bw.write(productContext.createColumnHeaderStringWithDelim("\t"));
        bw.write("\n");

        //Loops through and writes all of the products back out to a file.
        for (Product product : productContext.getProductList()) {
            bw.write(product.createDelimitedLineWithDelimiter("\t"));
            bw.write("\n");
        }

        bw.close();
    }
}
