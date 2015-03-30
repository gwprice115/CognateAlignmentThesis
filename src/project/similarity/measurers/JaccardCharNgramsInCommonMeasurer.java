package project.similarity.measurers;

//MAYBE CONVERT THESE TO JACCARD INDEX INSTEAD OF DIVIDING BY THE AVERAGE
public class JaccardCharNgramsInCommonMeasurer extends SimilarityMeasurer {
	int n;

	public JaccardCharNgramsInCommonMeasurer(int n) {
		this.n = n;
	}
	
	double getWordSimilarityValue(String line1, String line2) {
		String one = isolateLex(line1);
		String two = isolateLex(line2);
		if(n < 1 || n > one.length() || n > two.length()) {
//			System.out.println(n + " is not a valid n-gram size");
			return -1;
		}
		return getJaccardIndex(one, two, n);
	}
	
//	public static void main(String[] args) {
//		NumCharNgramsInCommonMeasurer x = new NumCharNgramsInCommonMeasurer(3);
//		System.out.println(x.getWordSimilarityValue("Absolutely", "Positively"));
//	}

}
