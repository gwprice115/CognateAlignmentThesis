package project.similarity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import project.similarity.measurers.CosineCharNgramVectorMeasurer;
import project.similarity.measurers.SameDefinitionMeasurer;
import project.similarity.measurers.TfIdfDrivenCharacterMeasurer;
import project.similarity.measurers.TfIdfMeasurer;

public class Orchestrator {

	public static void main(String[] args) throws Exception {
		//		testThresholdTuner();
		//3 args
		//stoplist, sample english sentences, inputfile1, inputfile2
		//		if(args.length < 4){
		//			throw new RuntimeException("Less than the required four arguments.");
		//		}
		//		String stoplist = args[0];
		//		String sentences = args[1];
		//		String inputFile1 = args[2];
		//		String inputFile2 = args[3];

		String stoplist = "data/stoplist";
		String sentences = "data/sentences";
		String inputFile1 = "/Users/gwprice/Desktop/project_data/final_data/Samia-Lugwe.txt";
		String inputFile2 = "/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho.txt";


		//		TfIdfMeasurer tfIdf = new TfIdfMeasurer(stoplist, sentences, inputFile1, inputFile2);
		//		HashMap<String, HashMap<String,Double>> table = tfIdf.calculateLuyiaSimilarities(inputFile1, inputFile2, 0.9);

		//		NumCharNgramsInCommonMeasurer lexical = new NumCharNgramsInCommonMeasurer(3);
		//		HashMap<String, HashMap<String,Double>> table = lexical.calculateLuyiaSimilarities(inputFile1, inputFile2, 3);

		//		TfIdfDrivenCharacterMeasurer tdcm = new TfIdfDrivenCharacterMeasurer(tfIdf,inputFile1,inputFile2);
		//		HashMap<String, HashMap<String,Double>> table = tdcm.calculateLuyiaSimilarities(inputFile1, inputFile2, 0);

		//		CosineCharNgramVectorMeasurer charCosine = new CosineCharNgramVectorMeasurer(1);
		//		HashMap<String, HashMap<String,Double>> table = charCosine.calculateLuyiaSimilarities(inputFile1, inputFile2, 0.8); 

		SameDefinitionMeasurer sameDef = new SameDefinitionMeasurer(inputFile1, inputFile2);
		HashMap<String, HashMap<String,Double>> table = sameDef.calculateLuyiaSimilarities(inputFile1, inputFile2, 0.9);

		//Thought: rather than calling calculateLuyiaSimilarities, we should just take every word pairwise and run that pair through many different
		//metrics whose values we would then linearly combine. Is this possible with the current architecture? Yes. Just call constructor and then a modified calculateLuyiaSimilarities that returns the value for every pair according to each metric

		for(String one : table.keySet()) {
			for(String two : table.get(one).keySet()) {
				if(!one.equals(two)) {
					double sim = table.get(one).get(two);
					System.out.println(one + " - " + two + ": " + sim);
				}
			}
		}
	}

	public static void testThresholdTuner() {
		String stoplist = "data/stoplist";
		String sentences = "data/sentences";
		String inputFile1 = "/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho_short.txt";
		String inputFile2 = "/Users/gwprice/Desktop/project_data/final_data/Samia-Lugwe_short.txt";

		String authorityDataFile = "/Users/gwprice/Desktop/project_data/hand_aligned/IsukhaIdakho_SamiaLugwe_First10.txt";

		HashSet<SingleAlignment> correctAlignments = new HashSet<SingleAlignment>();

		try {
			BufferedReader br = new BufferedReader(new FileReader(authorityDataFile));
			String line = "";
			while((line = br.readLine()) != null){
				String[] splitLine = line.split("\t\t\t\t\t");
				SingleAlignment e = new SingleAlignment(splitLine[0],splitLine[1]);
				correctAlignments.add(e);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

		//		TfIdfMeasurer tfIdf = new TfIdfMeasurer(stoplist, sentences, inputFile1, inputFile2);
		//		ThresholdTuner tfidfTuner = new ThresholdTuner(tfIdf, correctAlignments, -1, 1.05, 0.05,
		//				inputFile1, inputFile2);

		CosineCharNgramVectorMeasurer ngrams = new CosineCharNgramVectorMeasurer(2);
		ThresholdTuner ngramsTuner = new ThresholdTuner(ngrams, correctAlignments, -1, 1.05, 0.05,inputFile1, inputFile2, true);

		System.out.println("Ngrams:");
		System.out.println("F1 threshold: " + ngramsTuner.getThresholdForF1());
		System.out.println("Precision threshold: " + ngramsTuner.getThresholdForPrecision());
		System.out.println("Recall threshold: " + ngramsTuner.getThresholdForRecall());
	}

}
