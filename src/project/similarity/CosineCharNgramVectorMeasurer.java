package project.similarity;

import java.util.HashMap;

public class CosineCharNgramVectorMeasurer extends SimilarityMeasurer {
	int n;
	HashMap<String, Double> ngramWeights;

	//I think this metric is pretty bad
	public CosineCharNgramVectorMeasurer(int n) {
		this.n = n;
		ngramWeights = new HashMap<String, Double>();
	}
	
	/**
	 * @param n
	 * @param ngramWeights HashMap<String, Double> of ngram weights. Anything not in this HashMap will be assigned a weight of 1.
	 */
	public CosineCharNgramVectorMeasurer(int n, HashMap<String, Double> ngramWeights) {
		this.n = n;
		this.ngramWeights = ngramWeights;
	}
	
	double getWordSimilarityValue(String line1, String line2) {
		
		HashMap<String, Double> vector1 = new  HashMap<String, Double>();
		HashMap<String, Double> vector2 = new  HashMap<String, Double>();
		
		String one = isolateLex(line1).toLowerCase();
		String two = isolateLex(line2).toLowerCase();
		if(n < 1 || n > one.length() || n > two.length()) {
//			System.out.println(n + " is not a valid n-gram size");
			return -1;
		}
		for(int i = n-1; i < one.length(); i++) {
			String tri1 = one.substring(i-(n-1), i+1);
			incrementOccurrenceCount(vector1, tri1);
		}
		
		for(int j = n-1; j < two.length(); j++) {
			String tri2 = two.substring(j-(n-1), j+1);
			incrementOccurrenceCount(vector2, tri2);
		}
		return vectorCosine(vector1,vector2);
	}
	
	private double getWeight(String ngram) {
		Double hashedVal = ngramWeights.get(ngram);
		return hashedVal == null ? 1 : hashedVal;
	}
	
	/**
	 * @param vector the target vector for incrementing
	 * @param ngram the ngram to be incremented
	 * @return the new weighted count for ngram in vector
	 */
	private double incrementOccurrenceCount(HashMap<String, Double> vector, String ngram) {
		Double hashedWeightedCount = vector.get(ngram);
		double weightedCount = 0;
		if(hashedWeightedCount != null) {
			weightedCount = hashedWeightedCount;
		}
		vector.put(ngram, weightedCount + getWeight(ngram));
		return vector.get(ngram);
	}
}
