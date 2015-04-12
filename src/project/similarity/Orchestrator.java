package project.similarity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import project.similarity.measurers.CosineCharNgramVectorMeasurer;
import project.similarity.measurers.EMAlignedCharNgramMeasurer;
import project.similarity.measurers.JaccardCharNgramsInCommonMeasurer;
import project.similarity.measurers.JaccardConsonantNgramsInCommonMeasurer;
import project.similarity.measurers.SameDefinitionMeasurer;
import project.similarity.measurers.SimilarityMeasurer;
import project.similarity.measurers.TfIdfDrivenCharacterMeasurer;
import project.similarity.measurers.TfIdfMeasurer;
import project.similarity.utilities.UtilityInterface;

public class Orchestrator {

	static String stoplist = "data/stoplist";
	static String sentences = "data/sentences";
	static String inputFile1 = "/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho.txt";
	static String inputFile2 = "/Users/gwprice/Desktop/project_data/final_data/Samia-Lugwe.txt";
	static String inputFile = "/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho_X_Samia-Lugwe.txt";

	//	public static void main(String[] args) throws Exception {
	//
	//		//		testThresholdTuner();
	//		//3 args
	//		//stoplist, sample english sentences, inputfile1, inputfile2
	//		//		if(args.length < 4){
	//		//			throw new RuntimeException("Less than the required four arguments.");
	//		//		}
	//		//		String stoplist = args[0];
	//		//		String sentences = args[1];
	//		//		String inputFile1 = args[2];
	//		//		String inputFile2 = args[3];
	//
	//		String stoplist = "data/stoplist";
	//		String sentences = "data/sentences";
	//		String inputFile1 = "/Users/gwprice/Desktop/project_data/final_data/Samia-Lugwe.txt";
	//		String inputFile2 = "/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho.txt";
	//
	//
	//		//		TfIdfMeasurer tfIdf = new TfIdfMeasurer(stoplist, sentences, inputFile1, inputFile2);
	//		//		HashMap<String, HashMap<String,Double>> table = tfIdf.calculateLuyiaSimilarities(inputFile1, inputFile2, 0.9);
	//
	//		//		NumCharNgramsInCommonMeasurer lexical = new NumCharNgramsInCommonMeasurer(3);
	//		//		HashMap<String, HashMap<String,Double>> table = lexical.calculateLuyiaSimilarities(inputFile1, inputFile2, 3);
	//
	//		//		TfIdfDrivenCharacterMeasurer tdcm = new TfIdfDrivenCharacterMeasurer(tfIdf,inputFile1,inputFile2);
	//		//		HashMap<String, HashMap<String,Double>> table = tdcm.calculateLuyiaSimilarities(inputFile1, inputFile2, 0);
	//
	//		//		CosineCharNgramVectorMeasurer charCosine = new CosineCharNgramVectorMeasurer(1);
	//		//		HashMap<String, HashMap<String,Double>> table = charCosine.calculateLuyiaSimilarities(inputFile1, inputFile2, 0.8); 
	//
	//		SameDefinitionMeasurer sameDef = new SameDefinitionMeasurer(inputFile1, inputFile2);
	//		HashMap<String, HashMap<String,Double>> table = sameDef.calculateLuyiaSimilarities(inputFile1, inputFile2, 0.9);
	//
	//		//Thought: rather than calling calculateLuyiaSimilarities, we should just take every word pairwise and run that pair through many different
	//		//metrics whose values we would then linearly combine. Is this possible with the current architecture? Yes. Just call constructor and then a modified calculateLuyiaSimilarities that returns the value for every pair according to each metric
	//
	//		for(String one : table.keySet()) {
	//			for(String two : table.get(one).keySet()) {
	//				if(!one.equals(two)) {
	//					double sim = table.get(one).get(two);
	//					System.out.println(one + " - " + two + ": " + sim);
	//				}
	//			}
	//		}
	//	}

	public static void main(String[] args) {
		String outFilePath = "/Users/gwprice/Desktop/project_data/linear_combination_data.txt";

		SimilarityMeasurer[] measurers = {new JaccardCharNgramsInCommonMeasurer(4), new CosineCharNgramVectorMeasurer(4), new TfIdfMeasurer()};
		int precision = 10;
		for(int s = 4; s < 10; s++) {
			String titleString = "EVALUATING ON SECTION " + s;
			try {
				PrintWriter out = new PrintWriter(new FileWriter(outFilePath,true));
				System.out.println(titleString);
				out.println(titleString);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			CoefficientTuner tuner = new CoefficientTuner(measurers,precision,s);
			tuner.tune(true);

			String printString = tuner.getLCM() + ": Maximum F1: " + tuner.getMaxF1() + UtilityInterface.DOUBLE_TAB + "Threshold to achieve that F1: "+ tuner.getMaxF1_threshold();
			try {
				PrintWriter out = new PrintWriter(new FileWriter(outFilePath,true));
				System.out.println(printString);
				out.println(printString);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			try {
				String concludingString = "\n";
				PrintWriter out = new PrintWriter(new FileWriter(outFilePath,true));
				System.out.println(concludingString);
				out.println(concludingString);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
	}

	public static void tuneManyThresholds() {
		//		CosineCharNgramVectorMeasurer cosineCharNgramVectorMeasurer1 = new CosineCharNgramVectorMeasurer(1);
		//				CosineCharNgramVectorMeasurer cosineCharNgramVectorMeasurer2 = new CosineCharNgramVectorMeasurer(2);
		//		CosineCharNgramVectorMeasurer cosineCharNgramVectorMeasurer3 = new CosineCharNgramVectorMeasurer(3);
		//		CosineCharNgramVectorMeasurer cosineCharNgramVectorMeasurer4 = new CosineCharNgramVectorMeasurer(4);
		//		CosineCharNgramVectorMeasurer cosineCharNgramVectorMeasurer5 = new CosineCharNgramVectorMeasurer(5);
		//		EMAlignedCharNgramMeasurers not included because they must be created on the fly
		//		JaccardCharNgramsInCommonMeasurer jaccardCharNgramsInCommonMeasurer1 = new JaccardCharNgramsInCommonMeasurer(1);
		//		JaccardCharNgramsInCommonMeasurer jaccardCharNgramsInCommonMeasurer2 = new JaccardCharNgramsInCommonMeasurer(2);
		//		JaccardCharNgramsInCommonMeasurer jaccardCharNgramsInCommonMeasurer3 = new JaccardCharNgramsInCommonMeasurer(3);
		//		JaccardCharNgramsInCommonMeasurer jaccardCharNgramsInCommonMeasurer4 = new JaccardCharNgramsInCommonMeasurer(4);
		//		JaccardCharNgramsInCommonMeasurer jaccardCharNgramsInCommonMeasurer5 = new JaccardCharNgramsInCommonMeasurer(5);
		//		JaccardConsonantNgramsInCommonMeasurer jaccardConsonantNgramsInCommonMeasurer1 = new JaccardConsonantNgramsInCommonMeasurer(1);
		//		JaccardConsonantNgramsInCommonMeasurer jaccardConsonantNgramsInCommonMeasurer2 = new JaccardConsonantNgramsInCommonMeasurer(2);
		//		TfIdfMeasurer tfIdfMeasurer = new TfIdfMeasurer(stoplist,sentences,inputFile1,inputFile2);


		for(int s = 0; s < 10; s ++) {
			String titleString = "EVALUATING ON SECTION " + s;
			try {
				PrintWriter out = new PrintWriter(new FileWriter("/Users/gwprice/Desktop/project_data/idealThresholdsAndF1s.txt",true));
				System.out.println(titleString);
				out.println(titleString);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//no linear combination measurer cus obvi that's our goal

			SimilarityMeasurer[] measurers  = {
					//					cosineCharNgramVectorMeasurer1,
					//					cosineCharNgramVectorMeasurer2,
					//					cosineCharNgramVectorMeasurer3,
					//					cosineCharNgramVectorMeasurer4,
					//					cosineCharNgramVectorMeasurer5,
					//					jaccardCharNgramsInCommonMeasurer1,
					//					jaccardCharNgramsInCommonMeasurer2,
					//					jaccardCharNgramsInCommonMeasurer3,
					//					jaccardCharNgramsInCommonMeasurer4,
					//					jaccardCharNgramsInCommonMeasurer5,
					//					jaccardConsonantNgramsInCommonMeasurer1,
					//					jaccardConsonantNgramsInCommonMeasurer2,
					//					tfIdfMeasurer,
					//					new EMAlignedCharNgramMeasurer(1,s),
					//					new EMAlignedCharNgramMeasurer(2,s),
			};

			//SHOULD I JUST SEE HOW THE 10 DOES WITH THIS THRESHOLD RIGHT NOW???? WHY NOT RIGHT???
			//IF IT ADDS EXTRA TIME DON'T DO IT LOLOLOLOL WE ARE AT THAT POINT BILL
			//AND IT DEF WILL ADD EXTRA TIME

			String authority = "/Users/gwprice/Desktop/project_data/hand_aligned/IsukhaIdakho_SamiaLugwe"+s+"_90/";
			HashSet<SingleAlignment> correctAlignments = getCorrectAlignments(authority + "/data.langOneRaw",authority + "/data.langTwoRaw"); 

			for(SimilarityMeasurer m : measurers) {
				ThresholdTuner tt = new ThresholdTuner(m, correctAlignments, 0, 1, 0.05, inputFile);
				String printString = m + ": Maximum F1: " + tt.getMaxF1() + UtilityInterface.DOUBLE_TAB + "Threshold to achieve that F1: "+ tt.getThresholdForMaxF1();
				try {
					PrintWriter out = new PrintWriter(new FileWriter("/Users/gwprice/Desktop/project_data/idealThresholdsAndF1s.txt",true));
					System.out.println(printString);
					out.println(printString);
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			try {
				String concludingString = "\n";
				PrintWriter out = new PrintWriter(new FileWriter("/Users/gwprice/Desktop/project_data/idealThresholdsAndF1s.txt",true));
				System.out.println(concludingString);
				out.println(concludingString);
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	public static void testThresholdTuner() {
		//		String inputFile1 = "/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho_short.txt";
		//		String inputFile2 = "/Users/gwprice/Desktop/project_data/final_data/Samia-Lugwe_short.txt";
		//		String authorityDataFile = "/Users/gwprice/Desktop/project_data/hand_aligned/IsukhaIdakho_SamiaLugwe_First10.txt";

		//		TfIdfMeasurer tfIdf = new TfIdfMeasurer(stoplist, sentences, inputFile1, inputFile2);
		//		ThresholdTuner tfidfTuner = new ThresholdTuner(tfIdf, correctAlignments, -1, 1.05, 0.05,
		//				inputFile1, inputFile2);

		CosineCharNgramVectorMeasurer ngrams = new CosineCharNgramVectorMeasurer(2);
		//		HashSet<SingleAlignment> correctAlignments = getCorrectAlignments(authorityDataFileLangOne, authorityDataFileLangTwo);
		String authority = "/Users/gwprice/Desktop/project_data/hand_aligned/IsukhaIdakho_SamiaLugwe0_90/";
		HashSet<SingleAlignment> correctAlignments = getCorrectAlignments(authority + "/data.langOneRaw",authority + "/data.langTwoRaw"); 


		ThresholdTuner ngramsTuner = new ThresholdTuner(ngrams, correctAlignments, 0, 1, 0.05,inputFile, true);

		System.out.println("Ngrams:");
		System.out.println("F1 threshold: " + ngramsTuner.getThresholdForMaxF1());
		System.out.println("Precision threshold: " + ngramsTuner.getThresholdForMaxPrecision());
		System.out.println("Recall threshold: " + ngramsTuner.getThresholdForMaxRecall());
	}

	public static HashSet<SingleAlignment> getCorrectAlignments(
			String authorityDataFileLangOne, String authorityDataFileLangTwo) {
		HashSet<SingleAlignment> correctAlignments = new HashSet<SingleAlignment>();

		try {
			BufferedReader br1 = new BufferedReader(new FileReader(authorityDataFileLangOne));
			BufferedReader br2 = new BufferedReader(new FileReader(authorityDataFileLangTwo));
			String line1 = "";
			while((line1 = br1.readLine()) != null) {
				String line2 = br2.readLine();
				if(line2 == null) {
					br1.close();
					br2.close();
					throw new IOException("Authority files are of different lengths");
				}
				SingleAlignment e = new SingleAlignment(SimilarityMeasurer.isolateLex(line1),SimilarityMeasurer.isolateLex(line2));
				correctAlignments.add(e);
			}
			br1.close();
			br2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return correctAlignments;
	}

	//	String line1 = "";
	//	while((line1 = br1.readLine()) != null){
	//		String line2 = br2.readLine();
	//		if(line2 == null) {
	//			throw new IOException("Authority files are of different lengths");
	//		}
	//		SingleAlignment e = new SingleAlignment(SimilarityMeasurer.isolateLex(line1),SimilarityMeasurer.isolateLex(line2));
	//		correctAlignments.add(e);
	//	}


}
