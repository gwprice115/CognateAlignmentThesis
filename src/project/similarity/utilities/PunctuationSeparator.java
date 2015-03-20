package project.similarity.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

import project.similarity.SimilarityMeasurer;

public class PunctuationSeparator implements UtilityInterface {

	public PunctuationSeparator() {
	}
	
	public static void main(String[] args) {
		for(String dictFile : DICTIONARY_FILES) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(dictFile + "_UNTOKENIZED.txt"));
				PrintWriter out = new PrintWriter(new FileWriter(dictFile + ".txt"));
				String line = "";
				while((line = br.readLine()) != null){
					line = line.toLowerCase();
					String[] params = line.split("\t\t");
					
					StringBuilder entryLine = new StringBuilder();
					entryLine.append(params[0]);
					entryLine.append(DOUBLE_TAB);
					for(int i = 1; i < params.length; i++) {//no need to process lex
						String[] words = params[i].split(LITERAL_SPACE);
						for(String word : words) {
							//now do the actual work of finding the punctuation
							entryLine.append(LITERAL_SPACE);
						}
						entryLine.append(DOUBLE_TAB);
					}
					out.println(entryLine.toString());
					out.close();
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
