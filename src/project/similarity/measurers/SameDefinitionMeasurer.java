package project.similarity.measurers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import project.similarity.utilities.UtilityInterface;

public class SameDefinitionMeasurer extends SimilarityMeasurer {
	HashMap<String, String> defMap1 = new HashMap<String, String>();
	HashMap<String, String> defMap2 = new HashMap<String, String>();

	public SameDefinitionMeasurer(String inputFile1, String inputFile2) {
		try {
			BufferedReader br1 = new BufferedReader(new FileReader(inputFile1));		
			String line1 = "";
			while((line1 = br1.readLine()) != null){
				String[] params = line1.split(UtilityInterface.T_T);
				defMap1.put(params[0], params[1].replaceAll("\\s", " "));
			}
			br1.close();

			BufferedReader br2 = new BufferedReader(new FileReader(inputFile1));
			String line2 = "";
			while((line2 = br2.readLine()) != null){
				String[] params = line2.split(UtilityInterface.T_T);
				defMap2.put(params[0], params[1].replaceAll("\\s", " "));
			}
			br2.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	double getWordSimilarityValue(String line1, String line2) {
		if(defMap1.get(isolateLex(line1)).equalsIgnoreCase(defMap2.get(isolateLex(line2))))
			return 1;
		else
			return 0;
	}
}
