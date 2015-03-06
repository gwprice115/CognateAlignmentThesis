package project.similarity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract class SimilarityMeasurer {

	abstract double getWordSimilarityValue(String word1, String word2);

	String isolateLex(String line) {
		return line.split("\t\t")[0];
	}

	/**
	 * @param inputFile1 the first Luyia-English dictionary file
	 * @param inputFile2 the second Luyia-English dictionary file
	 * @param threshold the score above which a word pair has to be for it to be included
	 * @return a HashMap of HashMaps of doubles representing word alignments from input1 to input2 and then a score
	 * @throws Exception 
	 */
	HashMap<String, HashMap<String,Double>> calculateLuyiaSimilarities(
			String inputFile1,
			String inputFile2,
			double threshold) {

		HashMap<String, HashMap<String, Double>> alignmentTable = new HashMap<String, HashMap<String,Double>>();

		ArrayList<String> file1lines = new ArrayList<String>();
		ArrayList<String> file2lines = new ArrayList<String>();

		try {
			BufferedReader br1 = new BufferedReader(new FileReader(inputFile1));
			BufferedReader br2 = new BufferedReader(new FileReader(inputFile2));
			String line = "";
			while((line = br1.readLine()) != null){
				file1lines.add(line);
			}
			while((line = br2.readLine()) != null){
				file2lines.add(line);
			}
			br1.close();
			br2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String line1 : file1lines) {
			for(String line2 : file2lines) {
				double sim = getWordSimilarityValue(line1,line2);
				if(sim >= threshold) {
					try {
						logValue(alignmentTable,isolateLex(line1),isolateLex(line2),sim);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		return alignmentTable;
	}

	static void logValue(HashMap<String, HashMap<String,Double>> alignmentTable, String one, String two, double sim) throws Exception {
		HashMap<String,Double> level2;
		if(alignmentTable.containsKey(one)) {
			level2 = alignmentTable.get(one);
		}
		else {
			level2 = new HashMap<String,Double>();
			alignmentTable.put(one, level2);
		}
		if(level2.containsKey(two)) {
			//	throw new Exception("Duplicate values in a dictionary: " + one + " ," + two);
		}
		level2.put(two, sim);
	}
	
	/**
	 * @param allVars the union of these two sets (DO NOT CALL DIRECTLY: THIS STRUCTURE IS REQUIRED TO NOT BREAK tfidfMeasurer)
	 * @param vec1
	 * @param vec2
	 * @return the cosine of the angle between these two vectors
	 */
	static double vectorCosine(HashSet<String> allVars,
			HashMap<String, Double> vec1, HashMap<String, Double> vec2) {
		double square1 = 0;
		double square2 = 0;
		double product = 0;

		for(String var : allVars){
			Double hashCount =  vec1.get(var);
			double count1 = hashCount == null ? 0 : hashCount;
			hashCount =  vec2.get(var);
			double count2 = hashCount == null ? 0 : hashCount;

			square1 += count1 * count1;
			square2 += count2 * count2;
			product += count1 * count2;
		}
		return product/(Math.sqrt(square1)*Math.sqrt(square2));
	}
	
	static double vectorCosine(HashMap<String, Double> vec1, HashMap<String, Double> vec2) {
		return vectorCosine(vectorUnion(vec1,vec2),vec1,vec2);
	}
	
	static HashSet<String> vectorUnion(HashMap<String, Double> vec1, HashMap<String, Double> vec2) {
		HashSet<String> union = new HashSet<String>();
		union.addAll(vec1.keySet());
		union.addAll(vec2.keySet());
		return union;
	}
	

}
