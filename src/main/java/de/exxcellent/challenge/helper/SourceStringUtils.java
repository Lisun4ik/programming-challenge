package de.exxcellent.challenge.helper;

import de.exxcellent.challenge.source.Constants;
import de.exxcellent.challenge.source.SourceType;

public class SourceStringUtils {

    /**
     * build the right path for the source
     *
     * @param fileName name of file to be evaluated
     * @param type     type of file to be evaluated
     * @return full path of file
     */
    public static String getSourceString(String fileName, SourceType type) {
        if (SourceType.CSV.equals(type) || SourceType.JSON.equals(type)) {
            return Constants.filePath.concat(fileName).concat(".").concat(type.name());
        }
        return fileName;
    }
}
