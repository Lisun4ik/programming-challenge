package de.exxcellent.challenge.source;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.exxcellent.challenge.App;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This is the utils class to read Data of a file or a web service.
 * The source-type should be known to use this class.
 */
public class SourceReaderUtils {
    private final static Logger logger = LogManager.getLogger(App.class);

    /**
     * Chooses the right solution for the given source and reads the data.
     *
     * @param source the source to be read
     * @param type   the type of source
     * @return the records of the given source
     * @throws Exception Exception, if occurs
     */
    public static Object readData(String source, SourceType type) throws Exception {
        switch (type) {
            case CSV:
                return readCsvFile(source);
            case JSON:
                return readJsonFile(source);
            case WEB:
                return readFromWeb(source);
            default:
                throw new IllegalArgumentException("unsupported type: " + type);
        }
    }

    /**
     * Reads the data from a SCV-file and returns the content.
     *
     * @param filePath the path of the file to be read
     * @return the records of the given file
     * @throws IOException IOException, if occurs
     */
    private static List<Map<String, String>> readCsvFile(String filePath) throws IOException {
        logger.info("readCsvFile - START");
        List<Map<String, String>> result = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Path.of(filePath))) {
            // todo: this method is deprecated, find an other solution
            Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord csvRecord : csvRecords) {
                result.add(csvRecord.toMap());
            }
        }
        logger.info("readCsvFile - END");
        return result;
    }

    /**
     * Reads the data from a JSON-file and returns the content.
     *
     * @param filePath the path of the file to be read
     * @return the records of the given file
     * @throws IOException IOException, if occurs
     */
    private static List<Map<String, String>> readJsonFile(String filePath) throws IOException {
        logger.info("readJsonFile - START");
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, String>> result = objectMapper.readValue(new File(filePath), new TypeReference<>() {});
        logger.info("readJsonFile - END");
        return result;
    }

    /**
     * Reads the data from a web service and returns the content
     *
     * @param url the url of the web service to be read
     * @return the records of the given web service
     * @throws IOException IOException, if occurs
     */
    private static Object readFromWeb(String url) throws IOException {
        logger.info("readFromWeb - START");
        Object result = Request.get(URI.create(url)).execute().returnContent().asString();
        logger.info("readFromWeb - END");
        return result;
    }

}
