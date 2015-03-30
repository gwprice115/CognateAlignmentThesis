package project.similarity.measurers;

import java.util.HashSet;

public class JaccardConsonantNgramsInCommonMeasurer extends SimilarityMeasurer {
	int n;
	private static final HashSet<Character> vowels = new HashSet<Character>();

	public JaccardConsonantNgramsInCommonMeasurer(int n) {
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
		return getJaccardIndex(one, two, n);
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
