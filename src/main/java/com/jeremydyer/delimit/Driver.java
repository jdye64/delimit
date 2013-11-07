package com.jeremydyer.delimit;

import com.jeremydyer.delimit.context.ContextCreation;
import com.jeremydyer.delimit.context.ContextPersistence;
import com.jeremydyer.delimit.pojo.GenericContext;
import com.jeremydyer.delimit.service.GenericContextIKonWrapsVolusionSEOService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Entry point for the application. Hopefully one day I will make this much more robust with many more
 * run options instead of user having to manually change a little bit of code if they want a different runmode.
 *
 * User: Jeremy Dyer
 * Date: 11/1/13
 * Time: 1:47 PM
 */
public class Driver {

    private static final Logger logger = Logger.getLogger(Driver.class);

    private static int childrenNotAdded = 0;

    /**
     * Application entry method.
     *
     * @param args
     *      Commandline arguments.
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        System.out.println("----- BEGIN DELIMIT UTILITY -----");
        if (args != null && args.length > 0) {
            System.out.println("In your dreams this utility has run modes ... fire up VI and dig into "
                    + Driver.class.getName() + " if you want to change the runtime logic.");
            System.exit(-1);
        }

        //Creates the file references.
        //TODO: You will want to place the references to the actual files you want to work with on your machine here.
        //I'll upgrade this later when I have more time.
        File productsFile = new File("/home/jeremy/Downloads/Products_Joined_EC4HB5EM99.txt");
        File productsSEOFile = new File(productsFile.getParent() + File.separator + "Products_SEO.tsv");
        File categoriesFile = new File(productsFile.getParent() + File.separator + "Categories_YWP3WQY6UU.txt");
        File categoriesSEOFile = new File(productsFile.getParent() + File.separator + "Categories_SEO.tsv");

        ContextCreation contextCreation = new ContextCreation();

        GenericContext products = contextCreation.createGenericContext(productsFile, "\t");
        GenericContext categories = contextCreation.createGenericContext(categoriesFile, "\t");

        GenericContextIKonWrapsVolusionSEOService seoService = new GenericContextIKonWrapsVolusionSEOService();
        GenericContext seoOptimizedProducts = seoService.ikonWrapsSEOOptimizeProducts(products, categories);
        GenericContext seoOptimizedCategories = seoService.ikonWrapsSEOOptimizeCategories(categories, products);

        System.out.println("Done performing SEO");

        //Writes the SEO optimized contexts back out to file.
        ContextPersistence contextPersistence = new ContextPersistence();
        contextPersistence.saveGenericContextToTSVFile(seoOptimizedProducts, productsSEOFile);
        contextPersistence.saveGenericContextToTSVFile(seoOptimizedCategories, categoriesSEOFile);

        System.out.println("----- END DELIMIT UTILITY -----");
    }


}
