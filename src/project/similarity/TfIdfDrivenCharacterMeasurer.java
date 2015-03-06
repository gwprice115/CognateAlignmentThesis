package project.similarity;

import java.util.HashMap;

public class TfIdfDrivenCharacterMeasurer extends SimilarityMeasurer {
	TfIdfMeasurer goodAlignmentGenerator;
	
	String inputFile1;
	String inputFile2;
	
	public TfIdfDrivenCharacterMeasurer(TfIdfMeasurer goodAlignmentGenerator, String inputFile1, String inputFile2) {
		this.inputFile1 = inputFile1;
		this.inputFile2 = inputFile2;
		this.goodAlignmentGenerator = goodAlignmentGenerator;
	}
	
	public TfIdfDrivenCharacterMeasurer(String tfIdfStoplist, String tfIdfSentences, String inputFile1, String inputFile2) {
		this.inputFile1 = inputFile1;
		this.inputFile2 = inputFile2;
		goodAlignmentGenerator = new TfIdfMeasurer(tfIdfStoplist, tfIdfSentences, inputFile1, inputFile2);
	}
	
	
	@Override
	double getWordSimilarityValue(String word1, String word2) {
		HashMap<String, HashMap<String,Double>> goodAlignments = goodAlignmentGenerator.calculateLuyiaSimilarities(inputFile1, inputFile2, 1);
		for(String j : goodAlignments.keySet()) {
			for(String k : goodAlignments.get(j).keySet()) {
				System.out.println(k + " -- " + j);
			}
		}
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
