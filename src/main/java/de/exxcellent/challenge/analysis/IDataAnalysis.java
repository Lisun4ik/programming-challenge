package de.exxcellent.challenge.analysis;

/**
 * Interface for data analysis
 */
public interface IDataAnalysis<T> {
   String getMinDiffEntryOfData(T sourceData, String captionOfId, String captionOfVal1, String captionOfVal2);
}
