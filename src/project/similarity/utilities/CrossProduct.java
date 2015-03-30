package project.similarity.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import project.similarity.measurers.SimilarityMeasurer;

public class CrossProduct implements UtilityInterface {

	public static void main(String[] args) {
//		for(int i = 2; i <= 3; i++) {
//			outputCrossProductForN(i);
//		}
		outputCrossProductForN(0);
	}
	
	public static void outputCrossProductForN(int n) {

		String iiPath = DICTIONARY_FILES[1] + TXT;
		String slPath = DICTIONARY_FILES[0] + TXT;

		try {
			PrintWriter out = new PrintWriter(new FileWriter("/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho_X_Samia-Lugwe_"+n+"gram.txt"));
			BufferedReader iiReader = new BufferedReader(new FileReader(iiPath));
			String iiLine = "";
			int i = 0;
			while((iiLine = iiReader.readLine()) != null){
				System.out.println(n + "grams: " + i+"/2049");
				BufferedReader slReader = new BufferedReader(new FileReader(slPath));
				String slLine = "";
				while((slLine = slReader.readLine()) != null){
					out.println(ngramString(iiLine, slLine, n));
				}
				slReader.close();
				i++;
			}

			out.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param iiLine
	 * @param slLine
	 * @return
	 */
	public static String ngramString(String iiLine, String slLine, int n) {
		if(n == 0) {
			return SimilarityMeasurer.isolateLex(iiLine) + DOUBLE_TAB + SimilarityMeasurer.isolateLex(slLine);
		}
		return splitWordIntoNgrams(SimilarityMeasurer.isolateLex(iiLine),n) + DOUBLE_TAB + splitWordIntoNgrams(SimilarityMeasurer.isolateLex(slLine),n);
	}

	/**
	 * copied directly from splitHandAligned.py
	 * 
	 * @param word
	 * @param n
	 * @return
	 */
	public static String splitWordIntoNgrams(String word, int n) {
		if(word.length() <= n) {
			return word + " "; //+ " \n";
		}
		ArrayList<String> allNgrams = new ArrayList<String>();
		char[] chars = word.toCharArray();
		for(int a = 0; a < chars.length - n + 1; a++) {
			StringBuilder ngram = new StringBuilder();
			int z = a + n;
			for(int c = a; c < z; c++) {
				char character = chars[c];
				ngram.append(character);
			}
			allNgrams.add(ngram.toString());
		}
		StringBuilder returnString = new StringBuilder();
		for(String gram : allNgrams) {
			returnString.append(gram);
			returnString.append(LITERAL_SPACE);
		}
//		returnString.append("\n");
		return returnString.toString();
	}

	public static String splitWordIntoNgrams(String word) {
		return splitWordIntoNgrams(word,1);
	}


}
