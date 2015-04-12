package project.similarity.measurers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import project.similarity.utilities.UtilityInterface;

public class EMAlignedCharNgramMeasurer extends SimilarityMeasurer {
	private static final double ABS_LOG_PROB_LENGTH_NORMALIZED_MIN = 15;
	private static final double LOG_PROB_LENGTH_NORMALIZED_SCALE_FACTOR = 13;
	
	HashMap<String,HashMap<String,Double>> probLookup = new HashMap<String,HashMap<String,Double>>();
	int n;
	int s;
	
//	public static void main(String[] args) {
//		EMAlignedCharNgramMeasurer three = new EMAlignedCharNgramMeasurer(3);
//	}
	
	/**
	 * DON'T FORGET THE A <-> A, B <-> B ALIGNMENT STUFF. wait but might not do anything cus these are already being assigned highest prob it looks like? wait no sometimes they're not. probably try both
	 */
	public EMAlignedCharNgramMeasurer(int n, int s) {
		
		this.n = n;
		this.s = s;
		
		try {
			//REMEMBER TO REMOVE THE WORD "FAKE" FROM THE FILE PATH
			BufferedReader br = new BufferedReader(new FileReader("/Users/gwprice/Desktop/project_data/berkeley_lookup/section_" + s + "ii-sl"+n+"gram.txt"));
			String line = "";
			int count = 0;
			while((line = br.readLine()) != null){
				count++;
				//would it be better to take advantage of the fact that we're gonna ask for these in order? memory is expensive
				String[] params = line.split(UtilityInterface.T_T);
				if(params.length == 3) {
					put(isolateLex(params[0]),isolateLex(params[1]),new Double(params[2]));
				}
				else {
					throw new IOException("A line without 3 parameters in the reference file:\n"+line);
				}
				if(count % 151003 == 0) {
					System.out.println(this + ": " + (count/151003) + "/150");
				}
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void put(String word1, String word2, Double logProb) {
		
		HashMap<String,Double> map1 = probLookup.get(word1);
		if(map1 == null) {
			map1 = new HashMap<String,Double>();
			probLookup.put(word1, map1);
		}
		map1.put(word2, logProb);
	}
	
	@Override
	double getWordSimilarityValue(String line1, String line2) {
		String word1 = isolateLex(line1);
		String word2 = isolateLex(line2);
		try {
			return Math.pow(10,getProb(word1,word2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	private double getProb(String word1, String word2) throws IOException {
		HashMap<String,Double> map1 = probLookup.get(word1);
		if(map1 == null) {
			throw new IOException(word1 + " does not appear in dictionary 1");
		}
		Double val = probLookup.get(word1).get(word2);
		if(val == null) {
			throw new IOException(word2 + " does not appear in dictionary 2");
		}
		double avgLength = .5 * ((double) word1.length()) + ((double) word2.length());
		double length_normalized = val / avgLength;
		double scaled = (length_normalized + ABS_LOG_PROB_LENGTH_NORMALIZED_MIN) / LOG_PROB_LENGTH_NORMALIZED_SCALE_FACTOR;
		if(scaled <= 0) {
			return 0;
		}
		if(scaled >= 1) {
			return 1;
		}
		return scaled;
	}
	
	@Override
	String getDescription() {
		return s+"-basedEMAlignedChar"+n+"gramMeasurer";
	}

}
