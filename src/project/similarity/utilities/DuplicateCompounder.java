package project.similarity.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;

import project.similarity.SimilarityMeasurer;
import project.similarity.SingleAlignment;

public class DuplicateCompounder implements UtilityInterface {
	
	static final String SPACE_COLON_SPACE = " ; ";
	
	public static void main(String[] args) {
		for(String dictFile : DICTIONARY_FILES) {
			LinkedHashMap<String,String[]> dictionaryMap = new LinkedHashMap<String, String[]>();
			try {
				BufferedReader br = new BufferedReader(new FileReader(dictFile + "_Duplicates.txt"));
				String line = "";
				while((line = br.readLine()) != null){
					line = line.toLowerCase();
					String lex = SimilarityMeasurer.isolateLex(line);
					String[] params = line.split("\t\t");
					if(dictionaryMap.containsKey(lex)) {
						String[] oldParams = dictionaryMap.get(lex);
						String[] largeArray;
						String[] smallArray;
						if (params.length > oldParams.length) {
							largeArray = params;
							smallArray = oldParams;
						} else {
							largeArray = oldParams;
							smallArray = params;
						}
						for(int i = 1; i < smallArray.length; i++) {
							//start at 1 cus we don't need to overwrite lex
							largeArray[i] = largeArray[i] + SPACE_COLON_SPACE + smallArray[i];
						}
						dictionaryMap.put(lex, largeArray);
					}
					else {
						dictionaryMap.put(lex, params);
					}
				}
				br.close();
				PrintWriter out = new PrintWriter(new FileWriter(dictFile + ".txt"));
				for(String lex : dictionaryMap.keySet()) {
					String[] total = dictionaryMap.get(lex);
					StringBuilder entryLine = new StringBuilder();
					for(String part : total) {
						entryLine.append(part + DOUBLE_TAB);
					}
					out.println(entryLine.toString());
				}
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
