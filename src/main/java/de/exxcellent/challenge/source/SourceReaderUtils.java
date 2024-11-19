package de.exxcellent.challenge.source;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.hc.client5.http.fluent.Request;

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

    /**
     * Chooses the right solution for the given source and reads the data.
     *
     * @param source
     * @param type
     * @return
     * @throws Exception
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
                throw new IllegalArgumentException("Unsupported type: " + type);
        }
    }

    /**
     * Reads the data from a SCV-file and returns the content.
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    private static Object readCsvFile(String filePath) throws IOException {
        List<Map<String, String>> records = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Path.of(filePath))) {
            Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            for (CSVRecord csvRecord : csvRecords) {
                records.add(csvRecord.toMap());
            }
        }
        return records;
    }

    /**
     * Reads the data from a JSON-file and returns the content.
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    private static Object readJsonFile(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(filePath), Map.class);
    }

    /**
     * Reads the data from a web service and returns the content
     *
     * @param url
     * @return
     * @throws IOException
     */
    private static Object readFromWeb(String url) throws IOException {
        return Request.get(URI.create(url)).execute().returnContent().asString();
    }

}
