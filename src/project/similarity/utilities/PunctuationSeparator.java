package project.similarity.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;

import project.similarity.SimilarityMeasurer;

public class PunctuationSeparator implements UtilityInterface {

	public PunctuationSeparator() {
	}
	
	static Pattern letter = Pattern.compile("[a-zA-Z]");
	static Pattern punctuation = Pattern.compile("[^a-zA-Z]");
	
	/*
	 * DON'T USE THIS. USE THE MOSES TOKENIZER
	 */
    public void deadMain() {
		for(String dictFile : DICTIONARY_FILES) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(dictFile + "_UNTOKENIZED.txt"));
				PrintWriter out = new PrintWriter(new FileWriter(dictFile + TXT));
				String line = "";
				while((line = br.readLine()) != null){
					line = line.toLowerCase();
					String[] params = line.split(T_T);
					
					StringBuilder entryLine = new StringBuilder();
					entryLine.append(params[0]);
					entryLine.append(DOUBLE_TAB);
					for(int i = 1; i < params.length; i++) {//no need to process lex
						String[] words = params[i].split(LITERAL_SPACE);
						for(String word : words) {
							//now do the actual work of finding the punctuation
							boolean containsLetter = false;
							for(int j = 0; j < word.length(); j++) {
								String myChar = "" + word.charAt(j);
								if(!containsLetter && letter.matcher(myChar).matches()) { //first clause is for efficiency
									containsLetter = true;
								}
								if(punctuation.matcher(myChar).matches() && containsLetter) {
									entryLine.append(LITERAL_SPACE);
								}
								entryLine.append(myChar);
							}
							entryLine.append(LITERAL_SPACE);
						}
						entryLine.append(DOUBLE_TAB);
					}
					out.println(entryLine.toString());
				}
				out.close();
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
