package com.jeremydyer.delimit.service;

import com.jeremydyer.delimit.pojo.GenericContext;
import com.jeremydyer.delimit.pojo.GenericRow;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Service used perform SEO on a GenericContext for the IKonWraps Volusion Website.
 *
 * User: Jeremy Dyer
 * Date: 11/6/13
 * Time: 1:21 PM
 */
public class GenericContextIKonWrapsVolusionSEOService {

    private static final Logger logger = Logger.getLogger(GenericContextIKonWrapsVolusionSEOService.class);

    private static final String METATAG_TITLE_TEMPLATE = " | Ikon Wraps - Vinyl Wrap, Film and Tools";
    private static final String METATAG_DESCRIPTION_TEMPLATE1 = "Ikon Wraps is your source for ";
    private static final String METATAG_DESCRIPTION_TEMPLATE2 = ". We have the highest quality carbon fiber, matte, chrome, satin & brushed. Low prices, guaranteed.";
    private static final String METATAG_KEYWORDS_TEMPLATE = "ikon, ikon wrap, ikon wrap solutions, avs, auto vinyl solutions, vehicle wrap, vinyl solutions";

    private static ArrayList<String> KEYWORDS_SKIP_WORDS = null;

    //Creates the list of keyword skip words.
    static {
        KEYWORDS_SKIP_WORDS = new ArrayList<String>();
        KEYWORDS_SKIP_WORDS.add("-");
    }

    /**
     *
     * @param contextToFix
     * @param referenceContext
     * @return
     */
    public GenericContext ikonWrapsSEOOptimizeProducts(GenericContext contextToFix, GenericContext referenceContext) {

        for (GenericRow row : contextToFix.getRows()) {
            String productName = row.getValueForField("productname");
            System.out.println("ProductName -> '" + productName + "'");

            //Creates the meta title
            String metatagTitle = productName + METATAG_TITLE_TEMPLATE;
            System.out.println("\tMetaTagTitle -> '" + metatagTitle + "'");

            //Creates the meta description.
            String metaDescription = METATAG_DESCRIPTION_TEMPLATE1 + productName + METATAG_DESCRIPTION_TEMPLATE2;
            System.out.println("\tMetaDescription -> '" + metaDescription + "'");

            //Creates the meta keywords
            ArrayList<String> keywords = new ArrayList<String>();
            keywords.add(METATAG_KEYWORDS_TEMPLATE);
            String[] parts = StringUtils.splitPreserveAllTokens(productName, " ");
            if (parts.length > 1) {
                for (int i = 0; i < parts.length; i++) {
                    if (!KEYWORDS_SKIP_WORDS.contains(parts[i])) {
                        for (int j = 0; j < parts.length; j++) {
                            if (!KEYWORDS_SKIP_WORDS.contains(parts[j])
                                    && !parts[i].equals(parts[j])) {
                                keywords.add(" " + parts[i] + " " + parts[j]);
                            }
                        }
                    }
                }
            } else {
                if (parts.length > 0) {
                    keywords.add(" " + parts[0]);
                }
            }

            String metaKeywords = StringUtils.join(keywords, ",");
            System.out.println("\tKeywords -> '" + metaKeywords + "'");

            //Sets the values for the fields for the row.
            row.setValueForField("metatag_title", metatagTitle);
            row.setValueForField("metatag_description", metaDescription);
            row.setValueForField("metatag_keywords", metaKeywords);
            row.setValueForField("productkeywords", metaKeywords);
        }

        return contextToFix;
    }

    public GenericContext ikonWrapsSEOOptimizeCategories(GenericContext contextToFix, GenericContext referenceContext) {

        for (GenericRow row : contextToFix.getRows()) {
            String categoryName = row.getValueForField("categoryname");
            System.out.println("CategoryName -> '" + categoryName + "'");

            //Creates the meta title
            String metaTitle = categoryName + METATAG_TITLE_TEMPLATE;
            System.out.println("\tMetaTitle -> '" + metaTitle + "'");

            //Creates the meta description.
            String metaDescription = METATAG_DESCRIPTION_TEMPLATE1 + categoryName + METATAG_DESCRIPTION_TEMPLATE2;
            System.out.println("\tMetaDescription -> '" + metaDescription + "'");

            //Creates the meta keywords
            ArrayList<String> keywords = new ArrayList<String>();
            keywords.add(METATAG_KEYWORDS_TEMPLATE);
            String[] parts = StringUtils.splitPreserveAllTokens(categoryName, " ");
            if (parts.length > 1) {
                for (int i = 0; i < parts.length; i++) {
                    if (!KEYWORDS_SKIP_WORDS.contains(parts[i])) {
                        for (int j = 0; j < parts.length; j++) {
                            if (!KEYWORDS_SKIP_WORDS.contains(parts[j])
                                    && !parts[i].equals(parts[j])) {
                                keywords.add(" " + parts[i] + " " + parts[j]);
                            }
                        }
                    }
                }
            } else {
                if (parts.length > 0) {
                    keywords.add(" " + parts[0]);
                }
            }
            String metaKeywords = StringUtils.join(keywords, ",");
            System.out.println("\tKeywords -> '" + metaKeywords + "'");

            //Sets the values for the fields for the row.
            row.setValueForField("metatag_title", metaTitle);
            row.setValueForField("metatag_description", metaDescription);
            row.setValueForField("metatag_keywords", metaKeywords);
        }

        return contextToFix;
    }
}
