package project.similarity.measurers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import project.similarity.SingleAlignment;

public class TfIdfDrivenCharacterMeasurer extends SimilarityMeasurer {
	TfIdfMeasurer goodAlignmentGenerator;
	
	String inputFile1;
	String inputFile2;
	HashMap<String, HashMap<String,Double>> goodAlignments;
	
	public TfIdfDrivenCharacterMeasurer(TfIdfMeasurer goodAlignmentGenerator, String inputFile1, String inputFile2) {
		this.inputFile1 = inputFile1;
		this.inputFile2 = inputFile2;
		this.goodAlignmentGenerator = goodAlignmentGenerator;
		goodAlignments = goodAlignmentGenerator.calculateLuyiaSimilarities(inputFile1, inputFile2, .5); //arbirtary threshold just to generate 100 good pairs
		ArrayList<SingleAlignment> confidentPairs = getConfidentPairs();
	}
	
	public TfIdfDrivenCharacterMeasurer(String tfIdfStoplist, String tfIdfSentences, String inputFile1, String inputFile2) {
		this(new TfIdfMeasurer(tfIdfStoplist, tfIdfSentences, inputFile1, inputFile2), inputFile1, inputFile2);
	}
	
	ArrayList<SingleAlignment> getConfidentPairs() {
		ArrayList<SingleAlignment> sortedAlignments = new ArrayList<SingleAlignment>();
		for (String k : goodAlignments.keySet()) {
			for (String v : goodAlignments.get(k).keySet()) {
				//				if(k.equals("Lukaka") && v.equals("Engaka")) {
				//					System.out.println("GooD MORNING");
				//				}
				double prob = goodAlignments.get(k).get(v);
				sortedAlignments.add(new SingleAlignment(prob, k, v));
			}
		}
		Collections.sort(sortedAlignments);
		ArrayList<SingleAlignment> topHundred = new ArrayList<SingleAlignment>();
		for(int j = 0; j < 100; j++) {
			int i = sortedAlignments.size() - 1 - j;
			topHundred.add(sortedAlignments.get(i));
			System.out.println(sortedAlignments.get(i).getWord1() + " -- " + sortedAlignments.get(i).getWord2() + " -- " + sortedAlignments.get(i).getKey());
		}
		return topHundred;
	}
	
	
	@Override
	double getWordSimilarityValue(String line1, String line2) {
		return 0;
	}
	
	/**
	 * Keeps the orchestrator from running the TfIdf constructor too many times
	 * @return
	 */
	public TfIdfMeasurer getInternalTfIdf() {
		return goodAlignmentGenerator;
	}

}
