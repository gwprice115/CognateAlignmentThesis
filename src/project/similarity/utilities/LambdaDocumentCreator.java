package project.similarity.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

public class LambdaDocumentCreator implements UtilityInterface {

	public LambdaDocumentCreator() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		String[] files = new String[6];
		for(int i = 0; i < 5; i++) {
			files[i] = DICTIONARY_FILES[i] + TXT;
		}
		files[5] = "/Users/gwprice/Documents/workspace/CognateAlignmentThesis/data/sentences";

		TreeSet<String> wordSet = new TreeSet<String>();

		for(String filePath : files) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(filePath));
				String line = "";
				while((line = br.readLine()) != null){
					String[] params = line.split(DOUBLE_TAB);
					String[] defWords;
					if(params.length > 1) {
						defWords = params[1].split(LITERAL_SPACE);
					}
					else {
						defWords = line.split(LITERAL_SPACE);
					}
					for(String word : defWords) {
						if(word.contains("		")) {
							System.out.println("here");
						}
						wordSet.add(word);
					}
				}
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		StringBuilder allWords = new StringBuilder();
		for(String word : wordSet) {
			if(word.equals("bereavement")) {
				System.out.println("there");
			}
			allWords.append(word);
			allWords.append(LITERAL_SPACE);
		}
		try {
			PrintWriter out = new PrintWriter(new FileWriter("/Users/gwprice/Desktop/project_data/raw_data/lambda_document.txt"));
			out.write(allWords.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
