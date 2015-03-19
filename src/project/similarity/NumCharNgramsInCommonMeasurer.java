package project.similarity;

import java.util.HashMap;

public class NumCharNgramsInCommonMeasurer extends SimilarityMeasurer {
	int n;

	public NumCharNgramsInCommonMeasurer(int n) {
		this.n = n;
	}
	
	double getWordSimilarityValue(String line1, String line2) {
		String one = isolateLex(line1);
		String two = isolateLex(line2);
		if(n < 1 || n > one.length() || n > two.length()) {
//			System.out.println(n + " is not a valid n-gram size");
			return -1;
		}
		int gramCounter = 0;
		for(int i = n-1; i < one.length(); i++) {
			for(int j = n-1; j < two.length(); j++) {
				String tri1 = one.substring(i-(n-1), i+1);
				String tri2 = two.substring(j-(n-1), j+1);
				if(tri1.equals(tri2)) {
					gramCounter++; //maybe better to implement a precision/recall kind of thing?
				}
			}
		}
		return gramCounter / ((one.length() + two.length())/2);
	}
	
//	public static void main(String[] args) {
//		NumCharNgramsInCommonMeasurer x = new NumCharNgramsInCommonMeasurer(3);
//		System.out.println(x.getWordSimilarityValue("Absolutely", "Positively"));
//	}

}
