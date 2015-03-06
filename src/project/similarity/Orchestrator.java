package project.similarity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Orchestrator {

	public static void main(String[] args) throws Exception {
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
		String inputFile1 = "/Users/gwprice/Desktop/project_data/final_data/Tiriki.txt";
		String inputFile2 = "/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho.txt";


//		TfIdfMeasurer tfIdf = new TfIdfMeasurer(stoplist, sentences, inputFile1, inputFile2);
//		HashMap<String, HashMap<String,Double>> table = tfIdf.calculateLuyiaSimilarities(inputFile1, inputFile2, .9);

//		NumCharNgramsInCommonMeasurer lexical = new NumCharNgramsInCommonMeasurer(3);
//		HashMap<String, HashMap<String,Double>> table = lexical.calculateLuyiaSimilarities(inputFile1, inputFile2, 3);

//		TfIdfDrivenCharacterMeasurer tdcm = new TfIdfDrivenCharacterMeasurer(tfIdf,inputFile1,inputFile2);
//		HashMap<String, HashMap<String,Double>> table = tdcm.calculateLuyiaSimilarities(inputFile1, inputFile2, 0);
		
		CosineCharNgramVectorMeasurer charCosine = new CosineCharNgramVectorMeasurer(1);
		HashMap<String, HashMap<String,Double>> table = charCosine.calculateLuyiaSimilarities(inputFile1, inputFile2, 0.8); 
		//Thought: rather than calling calculateLuyiaSimilarities, we should just take every word pairwise and run that pair through many different
		//metrics whose values we would then linearly combine. Is this possible with the current architecture?

		for(String one : table.keySet()) {
			for(String two : table.get(one).keySet()) {
				double sim = table.get(one).get(two);
				System.out.println(one + " " + two + ": " + sim);
			}
		}
	}
	
}
