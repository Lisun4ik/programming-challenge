package de.exxcellent.challenge.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.exxcellent.challenge.App;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * conversion of given data to provide test files
 */
public class DataConverter {
    private final static Logger logger = LogManager.getLogger(App.class);

    /**
     * writes data from a list of entries to a JSON file
     *
     * @param list     list of entries
     * @param fileName name of new JSON file
     */
    public static void writeCSVDataToJsonFile(List<Map<String, String>> list, String fileName) {
        logger.info("writeCSVDataToJsonFile - START");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // JSON in Datei schreiben
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), list);
            logger.info("JSON was successfully written to the file: " + fileName);
        } catch (IOException e) {
            logger.error("an error occurred by writing JSON file: " + e.getMessage());
        }
        logger.info("writeCSVDataToJsonFile - END");
    }

}
