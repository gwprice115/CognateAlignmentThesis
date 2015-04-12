/**
 * 
 */
package project.similarity.utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

import project.similarity.SingleAlignment;
import project.similarity.measurers.CosineCharNgramVectorMeasurer;
import project.similarity.measurers.JaccardCharNgramsInCommonMeasurer;
import project.similarity.measurers.LinearCombinationMeasurer;
import project.similarity.measurers.SimilarityMeasurer;
import project.similarity.measurers.TfIdfMeasurer;

/**
 * @author gwprice
 *
 */
public class QuestionnaireGenerator implements UtilityInterface {

	private static final String QUESTIONNAIRE_PATH = "/Users/gwprice/Desktop/project_data/questionnaires/";
	private static final String QUESTIONNAIRE_SUFFIX = "questionnaire.html";
	SimilarityMeasurer measurer;
	double threshold;
	private ArrayList<SingleAlignment> pairSet;
	String language1;
	String language2;


	/**
	 * 
	 */
	public QuestionnaireGenerator(SimilarityMeasurer m, double threshold, String inputFile1, String inputFile2, String language1, String language2) {
		measurer = m;
		this.threshold = threshold;
		this.language1 = language1;
		this.language2 = language2;
		pairSet = measurer.calculateLuyiaSimilarities(inputFile1, inputFile2, threshold, true);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 * @param outFilePath
	 * @return the number of questions in the questionnaire
	 */
	public int print(String outFilePath) {
		try {
			PrintWriter out = new PrintWriter(new FileWriter(outFilePath));
			out.println(generateHeader(language1, language2));
			for(SingleAlignment a : pairSet) {
				out.println(generateQuestion(a));
			}
			out.println(generateClose());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return pairSet.size();
	}

	public int print() {
		return print(QUESTIONNAIRE_PATH + language1+"_"+language2+"_"+QUESTIONNAIRE_SUFFIX);
	}

	private String generateHeader(String language1, String language2) {
		// TODO Auto-generated method stub
		return "<i>This document contains alignments proposed by George Price's CognateAlignment program for "+language1+" and "+language2+" words. Please circle YES or NO to indicate whether you think the two words are cognates.</i>"
		+ "\n<pre style=\"font-family:georgia\">\nNAME:_______________________________________________";
	}

	private String generateQuestion(SingleAlignment a) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n<pre style=\"font-family:times\">\n<b>");
		sb.append(a.getWord1());
		sb.append("</b>:\n");
		sb.append(a.getDefinition1());
		sb.append("\n<b>");
		sb.append(a.getWord2());
		sb.append("</b>:\n");
		sb.append(a.getDefinition2());
		sb.append("\n\n<b>YES</b>		<b>NO</b>\n</pre>");
		return sb.toString();
	}

	private String generateClose() {
		return "\n<pre style=\"font-family:times\">\nTHANK YOU SOOO MUCH!!!!!!!!\n</pre>";
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("QUESTIONNAIRE_GENERATOR");
		ArrayList<String> outputStrings = new ArrayList<String>();
		JaccardCharNgramsInCommonMeasurer jaccardCharNgramsInCommonMeasurer4 = new JaccardCharNgramsInCommonMeasurer(4);
		CosineCharNgramVectorMeasurer cosineCharNgramVectorMeasurer4 = new CosineCharNgramVectorMeasurer(4);
		for(String[] languagePair : getAllPairs()) {
			String l1 = languagePair[1];
			String l2 = languagePair[0];
			System.out.println(l1 + " and " + l2);
			double[] linearWeights = {0.1,0.3,0.6};
			try {
				SimilarityMeasurer[] combinedMeasurers = {jaccardCharNgramsInCommonMeasurer4, cosineCharNgramVectorMeasurer4, new TfIdfMeasurer(getDictPath(l1),getDictPath(l2))};
				LinearCombinationMeasurer m = new LinearCombinationMeasurer(combinedMeasurers, linearWeights);
				QuestionnaireGenerator qg = new QuestionnaireGenerator(
						m,
						0.6,
						getDictPath(l1),
						getDictPath(l2),
						l1,
						l2
						);
				String output = qg.print() + " questions for " + l1 + " and " + l2;
				outputStrings.add(output);
			}
			catch(ParseException e) {
				e.printStackTrace();
			}
		}
		for(String s: outputStrings) {
			System.out.println(s);
		}

	}

	public static ArrayList<String[]> getAllPairs() {
		ArrayList<String[]> pairs = new ArrayList<String[]>();
		String[] pair = new String[2];
//		pair[0] = "Bukusu";
//		pair[1] = "Isukha-Idakho";
//		pairs.add(Arrays.copyOf(pair,2));
//		pair[0] = "Bukusu";
//		pair[1] = "Samia-Lugwe";
//		pairs.add(Arrays.copyOf(pair,2));
		pair[0] = "Bukusu";
		pair[1] = "Tiriki";
//		pairs.add(Arrays.copyOf(pair,2));
//		pair[0] = "Bukusu";
//		pair[1] = "Wanga";
//		pairs.add(Arrays.copyOf(pair,2));
//		pair[0] = "Isukha-Idakho";
//		pair[1] = "Samia-Lugwe";
		pairs.add(Arrays.copyOf(pair,2));
		pair[0] = "Isukha-Idakho";
		pair[1] = "Tiriki";
//		pairs.add(Arrays.copyOf(pair,2));
//		pair[0] = "Isukha-Idakho";
//		pair[1] = "Wanga";
		pairs.add(Arrays.copyOf(pair,2));
		pair[0] = "Samia-Lugwe";
		pair[1] = "Tiriki";
		pairs.add(Arrays.copyOf(pair,2));
//		pair[0] = "Samia-Lugwe";
//		pair[1] = "Wanga";
//		pairs.add(Arrays.copyOf(pair,2));
		pair[0] = "Tiriki";
		pair[1] = "Wanga";
		pairs.add(Arrays.copyOf(pair,2));
		return pairs;
	}

	public static String getDictPath(String language) {
		return "/Users/gwprice/Desktop/project_data/final_data/" + language + TXT;
	}

}
