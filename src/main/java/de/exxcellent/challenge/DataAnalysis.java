package de.exxcellent.challenge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataAnalysis {
    private static Logger logger = LogManager.getLogger(App.class);

    /**
     * Returns the entry with the minimum absolute difference from a list of entries.
     *
     * @param sourceData    the source data
     * @param captionOfId   the caption of identifier
     * @param captionOfVal1 the caption of the first value to compare
     * @param captionOfVal2 the caption of the second value to compare
     * @return the identifier with minimum difference
     */
    public static String getMinDiffEntryOfData(List<Map<String, String>> sourceData, String captionOfId, String captionOfVal1, String captionOfVal2) {
        logger.info("getMinDiffEntryOfData - START");
        Map<String, Integer> diffList = new HashMap<>();
        for (Map<String, String> data : sourceData) {
            int val1 = 0;
            int val2 = 0;
            String id = "";
            for (Map.Entry<String, String> entry : data.entrySet()) {
                // the identifier
                if (captionOfId.equals(entry.getKey())) id = entry.getValue();
                // first value to compare
                if (captionOfVal1.equals(entry.getKey())) val1 = Integer.parseInt(entry.getValue());
                // second value to compare
                if (captionOfVal2.equals(entry.getKey())) val2 = Integer.parseInt(entry.getValue());
            }
            // absolute difference
            int diff = Math.abs(val1 - val2);
            diffList.put(id, diff);
        }
        String result = diffList.entrySet().stream()
                .min(Map.Entry.comparingByValue()) // which values
                .map(Map.Entry::getKey)           // get the key
                .orElse(null);

        logger.info("getMinDiffEntryOfData - End");

        return result;

    }
}
