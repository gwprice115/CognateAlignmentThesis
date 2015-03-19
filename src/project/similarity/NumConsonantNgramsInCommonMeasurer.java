package project.similarity;

import java.util.HashMap;
import java.util.HashSet;

public class NumConsonantNgramsInCommonMeasurer extends SimilarityMeasurer {
	int n;
	private static final HashSet<Character> vowels = new HashSet<Character>();

	public NumConsonantNgramsInCommonMeasurer(int n) {
		this.n = n;
		vowels.add('a');
		vowels.add('e');
		vowels.add('i');
		vowels.add('o');
		vowels.add('u');
	}

	double getWordSimilarityValue(String line1, String line2) {
		String one = removeVowels(isolateLex(line1));
		String two = removeVowels(isolateLex(line2));
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

	private static String removeVowels(String word) {
		StringBuilder consonants = new StringBuilder();
		for(char c : word.toCharArray()) {
			if(!vowels.contains(c))
				consonants.append(c);
		}
		return consonants.toString();
	}

}
