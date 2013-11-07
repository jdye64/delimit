package com.jeremydyer.delimit.service;


import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * This is a stupid and simple service that just tries to make assumptions about what sort of delimiter
 * the file is using based on the extension of the file. The only reason I placed this logic in its own
 * class was in hopes of future optimizations.
 *
 * User: Jeremy Dyer
 * Date: 11/7/13
 * Time: 9:54 AM
 */
public class ExtensionAwarenessService {

    private static final Logger logger = Logger.getLogger(ExtensionAwarenessService.class);
    private static final String DEFAULT_DELIMITER = ",";
    private static Map<String, String> extToDelimMap = null;

    static {
        extToDelimMap = new HashMap<String, String>();
        extToDelimMap.put("csv", ",");
        extToDelimMap.put("tsv", "\t");
    }

    public ExtensionAwarenessService() {}


    /**
     * Determines the content delimiter from the file that the data was read from.
     *
     * @param inputFile
     *      Input file that the content was loaded from.
     *
     * @return
     *      Most likely delimiter candidate for the file content.
     */
    public String determineContentDelimFromFile(File inputFile) {
        if (inputFile != null) {
            if (extToDelimMap.get(FilenameUtils.getExtension(inputFile.getName()).toLowerCase()) != null) {
                return extToDelimMap.get(FilenameUtils.getExtension(inputFile.getName()).toLowerCase());
            } else {
                return DEFAULT_DELIMITER;
            }
        } else {
            return DEFAULT_DELIMITER;
        }
    }
}
