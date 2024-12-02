package de.exxcellent.challenge;

import de.exxcellent.challenge.source.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The entry class for your solution. This class is only aimed as starting point and not intended as baseline for your software
 * design. Read: create your own classes and packages as appropriate.
 *
 * @author Benjamin Schmid <benjamin.schmid@exxcellent.de>
 */
public final class App {
    private final static Logger logger = LogManager.getLogger(App.class);

    /**
     * This is the main entry method of your program.
     *
     * @param args The CLI arguments passed
     */
    public static void main(String... args) {
        logger.info("*** APPLICATION STARTED ***");

        ProcessData.processDataOfFile(Constants.weatherFileName, SourceType.CSV, Constants.weatherIdCaption, Constants.weatherVal1Caption, Constants.weatherVal2Caption);
        ProcessData.processDataOfFile(Constants.footballFileName, SourceType.CSV, Constants.footballIdCaption, Constants.footballVal1Caption, Constants.footballVal2Caption);
    }
}
