package project.similarity.measurers;

import java.io.IOException;

public class AlignedCharNgramMeasurer extends SimilarityMeasurer {

	/**
	 * DON'T FORGET THE A <-> A, B <-> B ALIGNMENT STUFF. wait but might not do anything cus these are already being assigned highest prob it looks like? wait no sometimes they're not. probably try both
	 */
	public AlignedCharNgramMeasurer(int n) {
		Runtime rt = Runtime.getRuntime();
		try {
			Process pr = rt.exec("svm_learn -z c all_schools.features_train.txt ../regressionModels/all_schools.model");
			pr.waitFor();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	double getWordSimilarityValue(String word1, String word2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
