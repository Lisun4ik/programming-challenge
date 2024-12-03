package de.exxcellent.challenge;

import de.exxcellent.challenge.analysis.DataAnalysis;
import de.exxcellent.challenge.analysis.IDataAnalysis;
import de.exxcellent.challenge.helper.DataConverter;
import de.exxcellent.challenge.helper.SourceStringUtils;
import de.exxcellent.challenge.source.Constants;
import de.exxcellent.challenge.source.SourceReaderUtils;
import de.exxcellent.challenge.source.SourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Example JUnit 5 test case.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
class AppTest {

    private String successLabel = "not successful";

    @BeforeEach
    void setUp() {
        successLabel = "successful";
    }

    @Test
    void aPointlessTest() {
        assertEquals("successful", successLabel, "My expectations were not met");
    }


    @Test
    void runSourceString() {
        String actualPath = Constants.filePath + Constants.weatherFileName + "." + SourceType.CSV;
        String pathToCompare = SourceStringUtils.getSourceString(Constants.weatherFileName, SourceType.CSV);
        assertEquals(actualPath, pathToCompare);
    }

    @Test
    void runMinDiffEntryOfData() {
        List<Map<String, String>> testList = new ArrayList<>();

        Map<String, String> map1 = new HashMap<>();
        map1.put("Title", "First id");
        map1.put("Value1", "50");
        map1.put("Value2", "120");

        Map<String, String> map2 = new HashMap<>();
        map2.put("Title", "Second id");
        map2.put("Value1", "110");
        map2.put("Value2", "20");

        Map<String, String> map3 = new HashMap<>();
        map3.put("Title", "Third id");
        map3.put("Value1", "5");
        map3.put("Value2", "28");

        testList.add(map1);
        testList.add(map2);
        testList.add(map3);

        IDataAnalysis<List<Map<String, String>>> dataAnalysis = new DataAnalysis();
        assertEquals(dataAnalysis.getMinDiffEntryOfData(testList, "Title", "Value1", "Value2"), "Third id");
    }

    @Test
    void runReadCsvFile() {
        try {
            List<Map<String, String>> data = (List<Map<String, String>>) SourceReaderUtils.readData(SourceStringUtils.getSourceString(Constants.weatherFileName, SourceType.CSV), SourceType.CSV);
            assertTrue(data != null && !data.isEmpty());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void runDataToJsonFile() {
        try {
            List<Map<String, String>> data = (List<Map<String, String>>) SourceReaderUtils.readData(SourceStringUtils.getSourceString(Constants.weatherFileName, SourceType.CSV), SourceType.CSV);
            DataConverter.writeDataToJsonFile(data, Constants.weatherFileName);
            String path = SourceStringUtils.getSourceString(Constants.weatherFileName, SourceType.JSON);
            File f = new File(path);
            assertTrue(f.exists() && !f.isDirectory());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void runReadJsonFile() {
        try {
            List<Map<String, String>> data = (List<Map<String, String>>) SourceReaderUtils.readData(SourceStringUtils.getSourceString(Constants.weatherFileName, SourceType.JSON), SourceType.JSON);
            assertTrue(data != null && !data.isEmpty());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * todo: the server must be started before executing this method!
     * important information: the server must be running successfully before the test is executed.
     */
    @Test
    void runReadFromWeb() {
        // todo: comment out for testing
        /*
        try {
            List<Map<String, String>> data = (List<Map<String, String>>) SourceReaderUtils.readData(Constants.weatherWebService, SourceType.WEB);
            assertTrue(data != null && !data.isEmpty());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
         */
    }

    @Test
    void runWeatherCSV() {
        String minVal = ProcessData.processDataOfFile(Constants.weatherFileName, SourceType.CSV, Constants.weatherIdCaption, Constants.weatherVal1Caption, Constants.weatherVal2Caption);
        assertEquals(minVal, "14");
    }

    @Test
    void runWeatherJSON() {
        String minVal = ProcessData.processDataOfFile(Constants.weatherFileName, SourceType.JSON, Constants.weatherIdCaption, Constants.weatherVal1Caption, Constants.weatherVal2Caption);
        assertEquals(minVal, "14");
    }

    /**
     * todo: the server must be started before executing this method!
     * important information: the server must be running successfully before the test is executed.
     */
    @Test
    void runWeatherWeb() {
        // todo: comment out for testing
        /*
        String minVal = ProcessData.processWeatherDataOfWebSource(Constants.weatherWebService);
        assertEquals(minVal, "14");
         */
    }

    @Test
    void runFootballCSV() {
        String minVal = ProcessData.processDataOfFile(Constants.footballFileName, SourceType.CSV, Constants.footballIdCaption, Constants.footballVal1Caption, Constants.footballVal2Caption);
        assertEquals(minVal, "Aston_Villa");
    }

    @Test
    void runFootballJSON() {
        String minVal = ProcessData.processDataOfFile(Constants.footballFileName, SourceType.JSON, Constants.footballIdCaption, Constants.footballVal1Caption, Constants.footballVal2Caption);
        assertEquals(minVal, "Aston_Villa");
    }

}