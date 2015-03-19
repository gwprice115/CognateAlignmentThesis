package project.similarity;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * You pass in a SimilarityMeasurer and a HashMap of authority alignments and
 * get a weight that maximizes precision, recall, or F1. It will calculate all
 * when you construct it and you can use some getters to retrieve those ideal
 * weights
 * 
 * @author gwprice
 *
 */
public class ThresholdTuner {

	SimilarityMeasurer measurer;
	Set<SingleAlignment> correctAlignments;
	String inputFile1;
	String inputFile2;
	double maxPrecision_threshold;
	double maxRecall_threshold;
	double maxF1_threshold;

	// holds all data for alignments above the threshold (key). I don't think I
	// need this anymore.
	HashMap<Double, Double> thresholdMap;

	/**
	 * @param measurer
	 * @param correctAlignments
	 * @param lowerBound
	 * @param upperBound
	 * @param increment
	 * @param inputFile1
	 * @param inputFile2
	 */
	public ThresholdTuner(SimilarityMeasurer measurer,
			Set<SingleAlignment> correctAlignments, double lowerBound,
			double upperBound, double increment, String inputFile1,
			String inputFile2) {
		this.measurer = measurer;
		this.correctAlignments = correctAlignments;
		this.inputFile1 = inputFile1;
		this.inputFile2 = inputFile2;

		HashMap<String, HashMap<String, Double>> table = measurer
				.calculateLuyiaSimilarities(inputFile1, inputFile2, 0);
		
		if(table.get("Lukaka").keySet().contains("Engaka")) {
			System.out.println("WE MADE IT");
		}

		ArrayList<SingleAlignment> sortedAlignments = new ArrayList<SingleAlignment>();
		for (String k : table.keySet()) {
			for (String v : table.get(k).keySet()) {
//				if(k.equals("Lukaka") && v.equals("Engaka")) {
//					System.out.println("GooD MORNING");
//				}
				double prob = table.get(k).get(v);
				sortedAlignments.add(new SingleAlignment(prob, k, v));
			}
		}
		Collections.sort(sortedAlignments);

		double maxPrecision = Double.NEGATIVE_INFINITY;
		double maxRecall = Double.NEGATIVE_INFINITY;
		double maxF1 = Double.NEGATIVE_INFINITY;

		for (double trialThreshold = lowerBound; trialThreshold < upperBound; trialThreshold += increment) {
			// thresholdMap.put(trialWeight,new HashMap<String,String>());
			int index = Collections.binarySearch(sortedAlignments,
					new SingleAlignment(trialThreshold));
			index = index < 0 ? (-index - 1) : index; // required to handle if
														// the item wasn't found

			// For reference: allPositives = truePositives + falsePositives
			// For reference: relevantItems = truePositives + falseNegatives

			double allPositives = sortedAlignments.size() - index; // off-by-one
																	// error??
			double truePositives = 0;
			double relevantItems = correctAlignments.size();
			for (int i = index; i < sortedAlignments.size(); i++) {
				SingleAlignment currentAlignment = sortedAlignments.get(i);
//				if (currentAlignment.equals(new SingleAlignment("Lukaka","Engaka"))) {
//					System.out.println("HERE");
//				}
				if (correctAlignments.contains(currentAlignment)) { //THIS TEST IS FAILING. WHY?????
					truePositives++;
				}
			}

			double recall = truePositives / relevantItems;
			double precision = truePositives / allPositives;
			double f1 = (2 * precision * recall) / (precision + recall);
			System.out.println("trialThreshold: " + trialThreshold);
			 System.out.println("precision: " + precision);
			 System.out.println("recall: " + recall);
			 System.out.println("f1: " + f1);

			if (precision > maxPrecision) {
				maxPrecision = precision;
				maxPrecision_threshold = trialThreshold;
			}
			if (recall > maxRecall) {
				maxRecall = recall;
				maxRecall_threshold = trialThreshold;
			}
			if (f1 > maxF1) {
				maxF1 = f1;
				maxF1_threshold = trialThreshold;
			}
		}

	}

	// THIS IS JUST A SORTING PROBLEM OH MY GOD
	// 1. Sort all alignments by weight in a huge list
	// 2. When asking a question about the group above a particular threshold,
	// just binary search to find that threshold and then perform the analysis
	// on anything above that point in the list

	public double getThresholdForPrecision() {
		return maxPrecision_threshold;
	}

	public double getThresholdForRecall() {
		return maxRecall_threshold;
	}

	public double getThresholdForF1() {
		return maxF1_threshold;
	}

}
