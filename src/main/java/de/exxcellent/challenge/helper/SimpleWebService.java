package de.exxcellent.challenge.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpServer;
import de.exxcellent.challenge.App;
import de.exxcellent.challenge.source.Constants;
import de.exxcellent.challenge.source.SourceReaderUtils;
import de.exxcellent.challenge.source.SourceType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

/**
 * a simple web sevice which should be started before testing the source type 'web'
 */
public class SimpleWebService {
    private final static Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        logger.info("**** simple web service started! ****");
        try {
            // prepare data
            List<Map<String, String>> weatherData = (List<Map<String, String>>) SourceReaderUtils.readData(SourceStringUtils.getSourceString(Constants.weatherFileName, SourceType.CSV), SourceType.CSV);
            startWebService(weatherData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * starts a small web service
     *
     * @param list list of entries
     * @throws IOException
     */
    public static void startWebService(List<Map<String, String>> list) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        server.createContext("/test", exchange -> {
            // JSON-String erzeugen
            String jsonResponse = processInput(list);

            // Header setzen und JSON-Response senden
            exchange.getResponseHeaders().add("Content-Type", "application/json");
            if (jsonResponse != null) {
                exchange.sendResponseHeaders(200, jsonResponse.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(jsonResponse.getBytes());
                os.close();
            }
        });


        server.start();
        logger.info("server started at http://localhost:8080/test");

        // keep the main thread active
        try {
            Thread.currentThread().join(); // blocks the main thread
        } catch (InterruptedException e) {
            logger.error("server interrupted: " + e.getMessage());
        }
    }

    /**
     * processes a List of entries and returns a JSON response
     *
     * @param inputData list of entries
     * @return JSON string
     */
    private static String processInput(List<Map<String, String>> inputData) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(inputData);
        } catch (IOException e) {
            logger.error("failed to process input: " + e.getMessage());
            return null;
        }
    }
}
