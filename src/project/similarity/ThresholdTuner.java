package project.similarity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import project.similarity.measurers.CosineCharNgramVectorMeasurer;
import project.similarity.measurers.JaccardCharNgramsInCommonMeasurer;
import project.similarity.measurers.LinearCombinationMeasurer;
import project.similarity.measurers.SimilarityMeasurer;
import project.similarity.measurers.TfIdfMeasurer;
import project.similarity.utilities.UtilityInterface;

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
	HashSet<SingleAlignment> correctAlignments;
	String inputFile1;
	String inputFile2;
	String inputFile;
	double maxPrecision_threshold;
	double maxRecall_threshold;
	double maxF1_threshold;
	double maxPrecision;
	double maxRecall;
	double maxF1;

	// holds all data for alignments above the threshold (key). I don't think I
	// need this anymore.
	//	HashMap<Double, Double> thresholdMap;

	//	public ThresholdTuner(SimilarityMeasurer measurer,
	//			HashSet<SingleAlignment> correctAlignments, double lowerBound,
	//			double upperBound, double increment, String inputFile1,
	//			String inputFile2) {
	//		this(measurer, correctAlignments, lowerBound, upperBound, increment, inputFile1, inputFile2, false);
	//	}


	public ThresholdTuner(SimilarityMeasurer measurer, double increment, int set0_9) {
		this(
				measurer,
				Orchestrator.getCorrectAlignments(
						"/Users/gwprice/Desktop/project_data/hand_aligned/IsukhaIdakho_SamiaLugwe"+set0_9+"_90/"+ "/data.langOneRaw",
						"/Users/gwprice/Desktop/project_data/hand_aligned/IsukhaIdakho_SamiaLugwe"+set0_9+"_90/" + "/data.langTwoRaw"
						),
						0,
						1,
						increment,
						"/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho_X_Samia-Lugwe.txt"
				);
	}
	

	public ThresholdTuner(SimilarityMeasurer measurer,
			HashSet<SingleAlignment> correctAlignments, double lowerBound,
			double upperBound, double increment, String inputFile) {
		this(measurer, correctAlignments, lowerBound, upperBound, increment, inputFile, false);
	}

	/**
	 * @param measurer
	 * @param correctAlignments
	 * @param lowerBound
	 * @param upperBound
	 * @param increment
	 * @param inputFile1
	 * @param inputFile2
	 */
	//	public ThresholdTuner(SimilarityMeasurer measurer,
	//			HashSet<SingleAlignment> correctAlignments, double lowerBound,
	//			double upperBound, double increment, String inputFile1,
	//			String inputFile2, boolean verbose) {
	//		this.measurer = measurer;
	//		this.correctAlignments = correctAlignments;
	//		this.inputFile1 = inputFile1;
	//		this.inputFile2 = inputFile2;
	//
	////		HashMap<String, HashMap<String, Double>> table = measurer
	////				.calculateLuyiaSimilarities(inputFile1, inputFile2, 0);
	//
	//		//		if(table.get("lukaka").keySet().contains("engaka")) {
	//		//			System.out.println("WE MADE IT");
	//		//		}
	//
	//		ArrayList<SingleAlignment> sortedAlignments = measurer
	//				.calculateLuyiaSimilarities(inputFile1, inputFile2, 0);//new ArrayList<SingleAlignment>();
	////		for (String k : table.keySet()) {
	////			for (String v : table.get(k).keySet()) {
	////				//				if(k.equals("Lukaka") && v.equals("Engaka")) {
	////				//					System.out.println("GooD MORNING");
	////				//				}
	////				double prob = table.get(k).get(v);
	////				sortedAlignments.add(new SingleAlignment(prob, k, v));
	////			}
	////		}
	//		Collections.sort(sortedAlignments);
	//
	//		maxPrecision = Double.NEGATIVE_INFINITY;
	//		maxRecall = Double.NEGATIVE_INFINITY;
	//		maxF1 = Double.NEGATIVE_INFINITY;
	//
	//		for (double trialThreshold = lowerBound; trialThreshold < upperBound; trialThreshold += increment) {
	//			// thresholdMap.put(trialWeight,new HashMap<String,String>());
	//			int index = Collections.binarySearch(sortedAlignments,
	//					new SingleAlignment(trialThreshold));
	//			index = index < 0 ? (-index - 1) : index; // required to handle if
	//			// the item wasn't found
	//
	//			// For reference: allPositives = truePositives + falsePositives
	//			// For reference: relevantItems = truePositives + falseNegatives
	//
	//			double allPositives = sortedAlignments.size() - index; // off-by-one
	//			// error??
	//			double truePositives = 0;
	//			double relevantItems = correctAlignments.size();
	//			for (int i = index; i < sortedAlignments.size(); i++) {
	//				SingleAlignment currentAlignment = sortedAlignments.get(i);
	//				//				if (currentAlignment.equals(new SingleAlignment("lukaka","engaka"))) {
	//				//					System.out.println("HERE");
	//				//				}
	//				if (correctAlignments.contains(currentAlignment)) {
	//					truePositives++;
	//				}
	//			}
	//
	//			double recall = truePositives / relevantItems;
	//			double precision = truePositives / allPositives;
	//			double f1 = (2 * precision * recall) / (precision + recall);
	//			
	//			if(verbose) {
	//				System.out.println();
	//				System.out.println("trialThreshold: " + trialThreshold);
	//				System.out.println("precision: " + precision);
	//				System.out.println("recall: " + recall);
	//				System.out.println("f1: " + f1);
	//			}
	//
	//			if (precision > maxPrecision) {
	//				maxPrecision = precision;
	//				maxPrecision_threshold = trialThreshold;
	//			}
	//			if (recall > maxRecall) {
	//				maxRecall = recall;
	//				maxRecall_threshold = trialThreshold;
	//			}
	//			if (f1 > maxF1) {
	//				maxF1 = f1;
	//				maxF1_threshold = trialThreshold;
	//			}
	//		}
	//
	//	}

	public ThresholdTuner(SimilarityMeasurer measurer,
			HashSet<SingleAlignment> correctAlignments, double lowerBound,
			double upperBound, double increment, String inputFile, boolean verbose) {
		this.measurer = measurer;
		this.correctAlignments = correctAlignments;
		this.inputFile = inputFile;

		//		HashMap<String, HashMap<String, Double>> table = measurer
		//				.calculateLuyiaSimilarities(inputFile1, inputFile2, 0);

		//		if(table.get("lukaka").keySet().contains("engaka")) {
		//			System.out.println("WE MADE IT");
		//		}

		ArrayList<SingleAlignment> sortedAlignments = measurer
				.calculateLuyiaSimilarities(inputFile, 0);//new ArrayList<SingleAlignment>();
		//		for (String k : table.keySet()) {
		//			for (String v : table.get(k).keySet()) {
		//				//				if(k.equals("Lukaka") && v.equals("Engaka")) {
		//				//					System.out.println("GooD MORNING");
		//				//				}
		//				double prob = table.get(k).get(v);
		//				sortedAlignments.add(new SingleAlignment(prob, k, v));
		//			}
		//		}
		Collections.sort(sortedAlignments);

		maxPrecision = Double.NEGATIVE_INFINITY;
		maxRecall = Double.NEGATIVE_INFINITY;
		maxF1 = Double.NEGATIVE_INFINITY;

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
				//								if (currentAlignment.equals(new SingleAlignment("linyinya","enyinya"))) {
				//									System.out.println("HERE");
				//								}
				if (correctAlignments.contains(currentAlignment)) {
					truePositives++;
				}
			}

			double recall = truePositives / relevantItems;
			double precision = truePositives / allPositives;
			double f1 = (2 * precision * recall) / (precision + recall);

			if(verbose) {
				System.out.println();
				System.out.println("trialThreshold: " + trialThreshold);
				System.out.println("precision: " + precision);
				System.out.println("recall: " + recall);
				System.out.println("f1: " + f1);
			}

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

	public double getThresholdForMaxPrecision() {
		return maxPrecision_threshold;
	}

	public double getThresholdForMaxRecall() {
		return maxRecall_threshold;
	}

	public double getThresholdForMaxF1() {
		return maxF1_threshold;
	}

	public double getMaxF1() {
		return maxF1;
	}
	
	public static void main(String[] args) throws ParseException {
		double[] weights;
		double threshold;
		String inputFile = "/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho_X_Samia-Lugwe.txt";
		SimilarityMeasurer[] measurerSet = {
			new JaccardCharNgramsInCommonMeasurer(4),
			new CosineCharNgramVectorMeasurer(4),
			new TfIdfMeasurer()
		};
		
//		double[] weights1 = {0.1,0.3,0.6};
//		LinearCombinationMeasurer measurer1 = new LinearCombinationMeasurer(measurerSet,weights1);
//		ArrayList<SingleAlignment> sortedAlignments1 = measurer1
//				.calculateLuyiaSimilarities(inputFile, 0.6);//new ArrayList<SingleAlignment>();
//		Collections.sort(sortedAlignments1);
//		
//		try {
//			PrintWriter out1 = new PrintWriter(new FileWriter("/Users/gwprice/Desktop/project_data/test1.txt"));
//			for(SingleAlignment sa : sortedAlignments1) {
//				out1.println(sa.getWord1() + "		" + sa.getWord2() + "		" + sa.getKey());
//			}
//			out1.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		double[] weights2 = {0.03,0.44,0.53};
		LinearCombinationMeasurer measurer2 = new LinearCombinationMeasurer(measurerSet,weights2);
		ArrayList<SingleAlignment> sortedAlignments2 = measurer2
				.calculateLuyiaSimilarities(inputFile, 0.55);//new ArrayList<SingleAlignment>();
		Collections.sort(sortedAlignments2);
		
		try {
			PrintWriter out2 = new PrintWriter(new FileWriter("/Users/gwprice/Desktop/project_data/testb.txt"));
			for(SingleAlignment sa : sortedAlignments2) {
				out2.println(sa.getWord1() + "		" + sa.getWord2() + "		" + sa.getKey());
			}
			out2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		ThresholdTuner myTt = new ThresholdTuner(measurer, 0.05, 3);
		for(int set0_9 = 0; set0_9 < 10; set0_9++) {
			ArrayList<SingleAlignment> sortedAlignments;
//			if(set0_9 == 2 || set0_9 == 3 || set0_9 == 4) {
//				threshold = 0.6;
//				sortedAlignments = sortedAlignments1;
//			}
//			else {
//				threshold = 0.55;
//				sortedAlignments = sortedAlignments2;
//			}
			threshold = .565;
			sortedAlignments = sortedAlignments2;
			
			double[] myVals = ThresholdTuner.getFinalPrecisionRecallAndF1(sortedAlignments, inputFile, threshold, set0_9);
			double precision = myVals[0];
			double recall = myVals[1];
			double f1 = myVals[2];
			System.out.println("Set "+set0_9 + ": Precision: " + precision + ", Recall: " + recall + ", F1: " + f1);
		}
	}
	
	public static double[] getFinalPrecisionRecallAndF1(ArrayList<SingleAlignment> sortedAlignments, String inputFile, double threshold, int set0_9) {
		HashSet<SingleAlignment> correctAlignments = Orchestrator.getCorrectAlignments(
				"/Users/gwprice/Desktop/project_data/hand_aligned/IsukhaIdakho_SamiaLugwe"+set0_9+"_10/"+ "/data.langOneRaw",
				"/Users/gwprice/Desktop/project_data/hand_aligned/IsukhaIdakho_SamiaLugwe"+set0_9+"_10/" + "/data.langTwoRaw"
				);
//		int index = Collections.binarySearch(sortedAlignments,
//				new SingleAlignment(threshold));
//		index = index < 0 ? (-index - 1) : index; // required to handle if
		// the item wasn't found

		// For reference: allPositives = truePositives + falsePositives
		// For reference: relevantItems = truePositives + falseNegatives

//		double allPositives = sortedAlignments.size() - index; // off-by-one
		double allPositives = sortedAlignments.size();
		// error??
		double truePositives = 0;
		double relevantItems = correctAlignments.size();
//		for (int i = index; i < sortedAlignments.size(); i++) {
		for (int i = 0; i < sortedAlignments.size(); i++) {
			SingleAlignment currentAlignment = sortedAlignments.get(i);
			//								if (currentAlignment.equals(new SingleAlignment("linyinya","enyinya"))) {
			//									System.out.println("HERE");
			//								}
			if (correctAlignments.contains(currentAlignment)) {
				truePositives++;
			}
		}

		double precision = truePositives / allPositives;
		double recall = truePositives / relevantItems;
		double f1 = (2 * precision * recall) / (precision + recall);
		double[] toReturn = {precision,recall,f1};
		return toReturn;
	}

}
