/**
 * This class represents a word similarity determiner.
 * It is called from a shell script that takes a stop list,
 * a corpus, and a list of requests to calculate similar words
 * for a given word according to a given weighting schema
 * and similarity measure.
 * 
 * @author Shannon Lubetich, George Price
 * @version 10/28/14
 * Assignment 5b
 *
 */

package project.similarity.measurers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class TfIdfMeasurer extends SimilarityMeasurer {
	HashMap<String, Integer> docFreq; 
	HashMap<String, Integer> wordFreq; 


	HashMap<String, HashMap<String, Double>> occVectors;
	HashMap<String, HashMap<String, Double>> tfidfVectors;
	HashMap<String, HashMap<String, Double>> pmiVectors;

	//FOR LUYIA

	public static final String NUMBER = "%NUMBER%";
	public static final Pattern PUNCTUATION = Pattern.compile("\\p{Punct}");
	public static final Pattern LETTER = Pattern.compile("[a-z]*");
	private int lineCount;
	private int wordCount;
	private HashMap<String, HashMap<String,Double>> defs1;
	private HashMap<String, HashMap<String,Double>> defs2;


	public TfIdfMeasurer() {
		this("/Users/gwprice/Documents/workspace/CognateAlignmentThesis/data/stoplist",
				"/Users/gwprice/Documents/workspace/CognateAlignmentThesis/data/sentences",
				"/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho.txt",
				"/Users/gwprice/Desktop/project_data/final_data/Samia-Lugwe.txt");
	}
	
	public TfIdfMeasurer(String dict1, String dict2) {
		this("/Users/gwprice/Documents/workspace/CognateAlignmentThesis/data/stoplist",
				"/Users/gwprice/Documents/workspace/CognateAlignmentThesis/data/sentences",dict1,dict2);
	}



	/**
	 * 
	 * @param stoplist the stoplist
	 * @param sentences the source sentences to give it idf of English
	 * @param inputFile1 the first dictionary
	 * @param inputFile2 the second dictionary
	 */
	public TfIdfMeasurer(String stoplist, String sentences, String inputFile1, String inputFile2) {
		preprocess(stoplist, sentences);
		generateVectors(inputFile1,inputFile2);
	}

	/**
	 * gets the words out of sentenceFile that are not in stoplistFile 
	 * and processes them appropriately
	 * @param stoplistFile the stop list file
	 * @param sentenceFile the sentence file
	 */
	public void preprocess(String stoplistFile, String sentenceFile){
		try{
			//stopwords
			HashSet<String> stops = new HashSet<String>();
			BufferedReader stopsBr = new BufferedReader(new FileReader(stoplistFile));
			String next= "";
			while((next = stopsBr.readLine()) != null){
				stops.add(next);
			}
			stops.add(NUMBER);


			stopsBr.close();

			docFreq = new HashMap<String, Integer>();
			wordFreq = new HashMap<String, Integer>();
			occVectors = new HashMap<String, HashMap<String, Double>>();

			lineCount = 0; 
			wordCount = 0;
			//process and store input
			BufferedReader sentenceBr = new BufferedReader(new FileReader(sentenceFile));
			while((next = sentenceBr.readLine()) != null){
				lineCount++;
				//lowercase and get rid of stopwords and punctuation
				String[] words = next.split("\\s");
				ArrayList<String> procWords = new ArrayList<String>();
				for(int i = 0; i < words.length; i++){
					String word = words[i].toLowerCase();
					if ((!(stops.contains(word) || PUNCTUATION.matcher(word).matches()) && LETTER.matcher(word).matches())){
						procWords.add(word);
					}
				}

				//now have only valid words, so process and store data
				//docWords keeps track of words we've seen in document
				HashSet<String> docWords = new HashSet<String>();
				for(int i = 0; i < procWords.size(); i++){
					String word = procWords.get(i);
					//count document frequency
					if(!docWords.contains(word)){
						Integer hashFreq = docFreq.get(word);
						int freq = hashFreq==null ? 0 : hashFreq;
						docFreq.put(word, freq+1);
					}
					docWords.add(word);

					//count word frequency
					wordCount++;
					Integer hashFreq = wordFreq.get(word);
					hashFreq = hashFreq==null ? 0 : hashFreq;
					wordFreq.put(word, hashFreq+1);

					//save word window
					HashMap<String, Double> occVec = occVectors.get(word);
					occVec = occVec == null ? new HashMap<String, Double>() : occVec;
					occVectors.put(word, occVec);
					for(int n : validNeighbors(i, procWords.size())){
						String neighbor = procWords.get(n);
						Double hashNeigh = occVec.get(neighbor);
						double neighFreq = hashNeigh==null ? 0 : hashNeigh;
						occVec.put(neighbor, neighFreq+1);
					}

				}
			}	
			sentenceBr.close(); 

			tfidfVectors = new HashMap<String, HashMap<String, Double>>();
			pmiVectors = new HashMap<String, HashMap<String, Double>>();
			calculateTfidf();
			calculatePmi();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return;
	}

	/**
	 * finds all neighbors of the word at index in an array of the given size.
	 * this method is necessary because we want to include all indices
	 * within 2 of our word that aren't out of the array bounds 
	 * @param index the index of the word
	 * @param size the size of the word
	 * @return the valid neighbors in the array
	 */
	private ArrayList<Integer> validNeighbors(int index, int size){
		ArrayList<Integer> neigh = new ArrayList<Integer>();
		for(int i = index-2; i<=index+2; i++){
			if(i!= index && i>=0 && i<size){ 
				neigh.add(i);
			}
		}
		return neigh;
	}


	//for each line in the input file, (which specifies a single word)
	//calculate 10 most similar words with weighting and sim metric specified
	//BUT there might be less than 10 words in data set, so check.
	//If fewer than 10, just do for as many words are there

	//weighting is going to be TF, TFIDF, PMI
	//similarity is going to be L1, Euclidean(L2), Cosine [all normalized by L2]

	//go through every occVec in occVectors and weight
	//getLineCount gives "number of documents"
	/**
	 * calculates the TF-IDF of every word and stores them in tfidfVectors.
	 */
	public void calculateTfidf(){
		double N = Math.log10(lineCount);
		for(String word : occVectors.keySet()){
			HashMap<String, Double> occVec = occVectors.get(word);
			HashMap<String, Double> tfidfVec = new HashMap<String, Double>();
			for(String neighbor : occVec.keySet()){
				double tf = occVec.get(neighbor);
				double tfidf = tf * (N - Math.log10(docFreq.get(neighbor)));
				tfidfVec.put(neighbor, tfidf);
			}
			tfidfVectors.put(word, tfidfVec);
		}
	}

	/**
	 * calculates the PMI of every word and stores them in pmiVectors.
	 */
	public void calculatePmi(){
		for(String word : occVectors.keySet()){
			HashMap<String, Double> occVec = occVectors.get(word);
			HashMap<String, Double> pmiVec = new HashMap<String, Double>();

			double wordProb = getProb(word);

			for(String neighbor : occVec.keySet()){
				double pmi = Math.log10(getProb(word, neighbor)/(wordProb*getProb(neighbor)));
				pmiVec.put(neighbor, pmi);
			}
			pmiVectors.put(word, pmiVec);
		}
	}

	/**
	 * gets the probability of this word occurring in this document: p(word) / wordCount
	 * @param word the word
	 * @return the probability of the word occurring
	 */
	private double getProb(String word){
		return wordFreq.get(word)/((double) wordCount);
	}

	/**
	 * gets the probability of these two words occurring together in this document: p(word1, word2) / wordCount
	 * @param word1 the first word
	 * @param word2 the second word
	 * @return the probability of the two words occurring in the same context in this document
	 */
	private double getProb(String word1, String word2){
		return occVectors.get(word1).get(word2)/((double) wordCount);
	}

	/**
	 * gets the cosine similarity measure for these two words using the vector set
	 * @param word1 the first word
	 * @param word2 the second words
	 * @param vectorSet the vector set with the weighting scheme already applied
	 * @return the cosine similarity of the words
	 */
	public double getCosine(String word1, String word2, HashMap<String, HashMap<String, Double>> vectorSet){
		HashSet<String> allNeighbors = getNeighborSet(word1, word2);
		HashMap<String, Double> vec1 = vectorSet.get(word1);
		HashMap<String, Double> vec2 = vectorSet.get(word2);

		return vectorCosine(allNeighbors, vec1, vec2);
	}

	/**
	 * gets the length of this vector
	 * @param vector the vector
	 * @return ||vector||
	 */
	private double getVectorLength(HashMap<String, Double> vector){
		double length = 0;
		for(String dim : vector.keySet()){
			double magnitude = vector.get(dim);
			length += magnitude * magnitude;
		}
		return Math.sqrt(length);
	}

	/**
	 * gets the Euclidean distance measure for these two words using the vector set
	 * @param word1 the first word
	 * @param word2 the second words
	 * @param vectorSet the vector set with the weighting scheme already applied
	 * @return the Euclidean distance of the words
	 */
	public double getEuclid(String word1, String word2, HashMap<String, HashMap<String, Double>> vectorSet){
		HashSet<String> allNeighbors = getNeighborSet(word1, word2);
		HashMap<String, Double> vec1 = vectorSet.get(word1);
		HashMap<String, Double> vec2 = vectorSet.get(word2);		
		double length1 = getVectorLength(vec1);
		double length2 = getVectorLength(vec2);
		double squareDiff = 0;	
		for(String neighbor : allNeighbors){
			Double hashCount =  vec1.get(neighbor);
			double count1 = hashCount == null ? 0 : hashCount;
			hashCount =  vec2.get(neighbor);
			double count2 = hashCount == null ? 0 : hashCount;

			double diff = (count1/length1)-(count2/length2);
			squareDiff += diff * diff;
		}
		return Math.sqrt(squareDiff);
	}

	/**
	 * gets the L1 distance measure for these two words using the vector set
	 * @param word1 the first word
	 * @param word2 the second words
	 * @param vectorSet the vector set with the weighting scheme already applied
	 * @return the L1 distance of the words
	 */
	public double getL1(String word1, String word2, HashMap<String, HashMap<String, Double>> vectorSet){
		HashSet<String> allNeighbors = getNeighborSet(word1, word2);
		HashMap<String, Double> vec1 = vectorSet.get(word1);
		HashMap<String, Double> vec2 = vectorSet.get(word2);		
		double length1 = getVectorLength(vec1);
		double length2 = getVectorLength(vec2);
		double absDiff = 0;	
		for(String neighbor : allNeighbors){
			Double hashCount =  vec1.get(neighbor);
			double count1 = hashCount == null ? 0 : hashCount;
			hashCount =  vec2.get(neighbor);
			double count2 = hashCount == null ? 0 : hashCount;

			absDiff += Math.abs((count1/length1)-(count2/length2));
		}
		return absDiff;
	}

	/**
	 * gets the set of all neighbors of either word
	 * @param word1 the first word
	 * @param word2 the second word
	 * @return the union of neighbors(word1) and neighbors(word2)
	 */
	private HashSet<String> getNeighborSet(String word1, String word2) {
		return vectorUnion(occVectors.get(word1),occVectors.get(word2));
	}

	/**
	 * gets the number of lines/documents
	 * @return the number of lines/documents
	 */
	private int getLineCount(){
		return lineCount;
	}

	/**
	 * gets the number of words in the file
	 * @return the number of words in the file
	 */
	private int getWordCount(){
		return wordCount;
	}

	/**
	 * gets the number of unique words in the file
	 * @return the number of unique words in the file
	 */
	private int getUniqueWordCount(){
		return occVectors.size();
	}


	/**
	 * prints the most similar words to word in the file with the given weighting scheme and similarity measure
	 * @param word the word
	 * @param weighting the weighting scheme
	 * @param simMeasure the similarity measure
	 */
	private void printMostSim(String word, String weighting, String simMeasure){
		System.out.println("SIM:\t" + word + "\t" + weighting + "\t" + simMeasure);

		HashMap<String, HashMap<String, Double>> vectorSet = getVectorSet(weighting);

		if(simMeasure.equals("L1")){
			mostSimL1(word, vectorSet);
		}else if(simMeasure.equals("EUCLIDEAN")){
			mostSimEuclid(word, vectorSet);
		}else if(simMeasure.equals("COSINE")){
			mostSimCosine(word, vectorSet);
		}else{
			throw new RuntimeException("Unrecognized weighting scheme.");
		}

	}

	/**
	 * gets the most similar words according to L1 distance and the vector set
	 * @param word the word
	 * @param vectorSet the vector set
	 */
	private void mostSimL1(String word, HashMap<String, HashMap<String, Double>> vectorSet) {
		TreeMap<Double, ArrayList<String>> sortedSim = new TreeMap<Double, ArrayList<String>>();

		for(String otherWord : docFreq.keySet()){
			if(!word.equals(otherWord)){
				double distance = getL1(word, otherWord, vectorSet);
				//store this distance
				addToSortedSim(distance, otherWord, sortedSim);
			}
		}
		printTop(sortedSim);
	}

	/**
	 * gets the most similar words according to Euclidean distance and the vector set
	 * @param word the word
	 * @param vectorSet the vector set
	 */
	private void mostSimEuclid(String word, HashMap<String, HashMap<String, Double>> vectorSet) {
		TreeMap<Double, ArrayList<String>> sortedSim = new TreeMap<Double, ArrayList<String>>();
		for(String otherWord : docFreq.keySet()){
			if(!word.equals(otherWord)){
				double distance = getEuclid(word, otherWord, vectorSet);
				//store this distance
				addToSortedSim(distance, otherWord, sortedSim);
			}
		}
		printTop(sortedSim);

	}

	/**
	 * gets the most similar words according to cosine similarity and the vector set
	 * @param word the word
	 * @param vectorSet the vector set
	 */
	private void mostSimCosine(String word, HashMap<String, HashMap<String, Double>> vectorSet) {
		TreeMap<Double, ArrayList<String>> sortedSim = new TreeMap<Double, ArrayList<String>>(Collections.reverseOrder());
		for(String otherWord : docFreq.keySet()){
			if(!word.equals(otherWord)){
				double distance = getCosine(word, otherWord, vectorSet);
				//store this distance
				addToSortedSim(distance, otherWord, sortedSim);
			}
		}
		printTop(sortedSim);
	}

	/**
	 * adds the similarites computed to the sortedSim structure for easy extraction in order
	 * @param distance the distance of otherWord from the word
	 * @param otherWord the word compared to word
	 * @param sortedSim the sorted similar-words structure for word
	 */
	private void addToSortedSim(double distance, String otherWord, TreeMap<Double, ArrayList<String>> sortedSim){
		if(!Double.isNaN(distance)){
			ArrayList<String> atDistance = sortedSim.get(distance);
			atDistance = atDistance == null ? new ArrayList<String>() : atDistance;
			atDistance.add(otherWord);
			sortedSim.put(distance, atDistance);
		}
	}

	/**
	 * prints the top 10 words
	 * @param sortedSim the sorted similar-words structure for word
	 */
	private void printTop(TreeMap<Double, ArrayList<String>> sortedSim){
		Iterator<Double> keyIterator = sortedSim.descendingKeySet().descendingIterator();
		int count = 0;
		while(count < 10 && keyIterator.hasNext()){
			Double sim = keyIterator.next();
			if(!Double.isNaN(sim)){
				for(String simWord : sortedSim.get(sim)){
					if(wordFreq.get(simWord) >= 3) {
						if(count<10) {
							System.out.println(simWord + "\t" + sim);
							count++;
						}
						else {
							return;
						}
					}
				}
			}
		}
	}


	/**
	 * gets the appropriate vector set given a weighting scheme abbreviation
	 * @param weighting the weighting scheme abbreviation
	 * @return the appropriate vector set
	 */
	private HashMap<String, HashMap<String, Double>> getVectorSet(String weighting){
		if(weighting.equals("TF")){
			return occVectors;
		}else if(weighting.equals("TFIDF")){
			return tfidfVectors;
		}else if(weighting.equals("PMI")){
			return pmiVectors;
		}else{
			throw new RuntimeException("Unrecognized weighting scheme.");
		}
	}

	//	/**
	//	 * prints the ten most similar words for each word, weighting scheme,
	//	 * and similarity measure in the file
	//	 * @param wordsFile the file with the words, weightings, and similarity measure abbreviations
	//	 */
	//	public void calculateWordSimilarities(String wordsFile){
	//		try{
	//			BufferedReader wordsBr = new BufferedReader(new FileReader(wordsFile));
	//			String next = "";
	//			while((next = wordsBr.readLine()) != null){
	//				String[] params = next.split("\\s+");
	//				printMostSim(params[0], params[1], params[2]);
	//			}
	//			wordsBr.close();
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//		}
	//
	//	}


	/**
	 * prints metadata about the file:
	 * # unique words
	 * # word occurrences
	 * # sentences/lines/documents 
	 */
	void printMetadata() {
		System.out.println(getUniqueWordCount() + " unique words");
		System.out.println(getWordCount() + " word occurrences");
		System.out.println(getLineCount() + " sentences/lines/documents");
		System.out.println();
	}

	/**
	 * computes metadata and top ten similar words according to the given parameters
	 * @param args the stop list, sentences file, and input file representing words, weightings, and similarity measures
	 */

	//	public static void main(String[] args) {
	//		//3 args
	//		//stoplist, sentences, inputfile
	//		if(args.length < 3){
	//			throw new RuntimeException("Less than the required three arguments.");
	//		}
	//		String stoplist = args[0];
	//		String sentences = args[1];
	//		String inputFile = args[2];
	//
	//		WordSim test = new WordSim();
	//		test.preprocess(stoplist, sentences);
	//		test.printMetadata();
	//		test.calculateWordSimilarities(inputFile);
	//	}


	public double getWordSimilarityValue(String line1, String line2) {

		/*
		 * NO LONGER REQUIRE ANY ATTENTION:
		 */
		//NOTE: WHAT TO DO ABOUT PUNCTUATION?? GET RID OF IT???
		//NOTE: THE CURRENT LAMBDA DOESN'T CREATE A REAL PROBABILITY DISTRIBUTION CUS IT ISN'T FACTORED INTO TOTAL WORD COUNTS OR DOC COUNTS OR ANYTHING
		//NOTE: "cause" is used a lot by the Bukusu author: probably a word that gets more attention than it deserves
		//NOTE: SOME WORDS APPEAR TWICE IN THE DICTIONARIES WITH DIFFERENT DEFINITIONS: HASH TABLE OVERWRITES
		//BUKUSU: endosi = "old cow"
		//WANGA: itwasi = "old"
		//WANGA: ikhafu = "cow"
		//WHY ARE OLD AND COW SCORED THE SAME?? SAME DOCUMENT-FREQUENCY?
		String one = isolateLex(line1);
		String two = isolateLex(line2);
		return vectorCosine(defs1.get(one),defs2.get(two));
	}



	/**
	 * @param inputFile
	 */
	private HashMap<String, HashMap<String,Double>> getDefVectors(String inputFile) {
		double N = Math.log10(lineCount);
		HashMap<String, HashMap<String,Double>> defs = new HashMap<String, HashMap<String,Double>>();
		try{
			BufferedReader br1 = new BufferedReader(new FileReader(inputFile));
			String next = "";
			while((next = br1.readLine()) != null){
				next = next.toLowerCase();
				//				if(next.contains("haya ( ohu )")) {
				//					System.out.println("I'm a gross fish");
				//				}
				String[] params = next.split("\t\t");
				HashMap<String,Double> defVector = new HashMap<String,Double>();
				for(String word : params[1].split(" ")) {
					Double hashCount = defVector.get(word);
					double tf = hashCount == null ? 1 : hashCount+1;
					defVector.put(word,tf);
				}
				for (String word : defVector.keySet()) {
					//					Double hashTf = defVector.get(word); THIS CASE SHOULD NEVER HAPPEN BCUZ ONLY GOES THROUGH WORDS IN THE SENTENCE
					//					double tf = hashTf == null ? LAMBDA : hashTf;
					Integer hashDf = docFreq.get(word); //THIS CASE SHOULD NEVER HAPPEN BECAUSE ALL WORDS ARE IN THE LAST DOCUMENT
					int df = hashDf == null ? -1 : hashDf;
					if (hashDf != null) { //meaning that word was not in the stoplist
						double tfidf = defVector.get(word) * (N - Math.log10(df));
						defVector.put(word, tfidf);
					}
				}
				defs.put(isolateLex(next),defVector);
			}
			br1.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return defs;
	}

	public void generateVectors(String inputFile1, String inputFile2) {
		defs1 = getDefVectors(inputFile1);
		defs2 = getDefVectors(inputFile2);
	}

	@Override
	String getDescription() {
		return "TfIdfMeasurer";
	}

}
