package de.exxcellent.challenge;

import de.exxcellent.challenge.analysis.DataAnalysis;
import de.exxcellent.challenge.analysis.IDataAnalysis;
import de.exxcellent.challenge.helper.SourceStringUtils;
import de.exxcellent.challenge.source.Constants;
import de.exxcellent.challenge.source.SourceReaderUtils;
import de.exxcellent.challenge.source.SourceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * processes given use cases for data analysis
 */
public class ProcessData {
    private final static Logger logger = LogManager.getLogger(App.class);

    /**
     * processes data analysis for a CSV, or a JSON file
     *
     * @param source name of file to analyse
     * @param type   type of file
     * @param id     id of dataset
     * @param val1   first value to compare (the order is irrelevant)
     * @param val2   second value to compare (the order is irrelevant)
     */
    public static String processDataOfFile(String source, SourceType type, String id, String val1, String val2) {
        logger.info("processWeatherDataCsv - START");
        if (SourceType.WEB.equals(type)) {
            logger.info("wrong source type");
            return null;
        }
        try {
            List<Map<String, String>> data = (List<Map<String, String>>) SourceReaderUtils.readData(SourceStringUtils.getSourceString(source, type), type);
            String minVal = process(data, id, val1, val2);
            logger.info("processWeatherDataCsv - END");
            return minVal;
        } catch (Exception e) {
            logger.error("an error accured by reading the data from file " + Constants.weatherIdCaption, e.getMessage());
            return null;
        }
    }

    /**
     * todo: the server must be started before executing this method!
     * important information: the server must be running successfully before the method is executed.
     * processes data analysis for a given web service.
     *
     * @param url web server url
     */
    public static String processWeatherDataOfWebSource(String url) {
        logger.info("processWeatherDataOfWebSource - START");
        try {
            List<Map<String, String>> weatherData = (List<Map<String, String>>) SourceReaderUtils.readData(url, SourceType.WEB);
            String minVal = process(weatherData, Constants.weatherIdCaption, Constants.weatherVal1Caption, Constants.weatherVal2Caption);
            logger.info("processWeatherDataOfWebSource - END");
            return minVal;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void processFootballDataWebSource() {
        // todo impl: it is necessary to create a web service first
        throw new UnsupportedOperationException("this method has not been implemented yet.");
    }

    /**
     * processes the data analysis
     *
     * @param data data as a list of maps
     * @param id   id of dataset
     * @param val1 first value to compare (the order is irrelevant)
     * @param val2 second value to compare (the order is irrelevant)
     */
    private static String process(List<Map<String, String>> data, String id, String val1, String val2) {
        if (data != null && !data.isEmpty()) {
            IDataAnalysis<List<Map<String, String>>> dataAnalysis = new DataAnalysis();
            String dayWithSmallestTempSpread = dataAnalysis.getMinDiffEntryOfData(data, id, val1, val2);
            System.out.printf("Day with smallest temperature spread : %s%n", dayWithSmallestTempSpread);
            return dayWithSmallestTempSpread;
        } else {
            logger.info("no data found in source: " + Constants.weatherFileName);
            return null;
        }
    }
}
