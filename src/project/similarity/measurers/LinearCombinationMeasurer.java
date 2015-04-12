package project.similarity.measurers;

import java.text.ParseException;

public class LinearCombinationMeasurer extends SimilarityMeasurer {

	private SimilarityMeasurer[] measurers;
	private double[] weights;
	public LinearCombinationMeasurer(SimilarityMeasurer[] measurers, double[] weights) throws ParseException {
		this.measurers = measurers;
		setWeights(weights);
	}
	
	public LinearCombinationMeasurer(SimilarityMeasurer[] measurers) throws ParseException {
		this(measurers,new double[measurers.length]);
	}

	/**
	 * Precondition: measurers and weights have same length
	 */ 
	@Override
	double getWordSimilarityValue(String line1, String line2) {
		double sum = 0;
		for(int i = 0; i < weights.length; i++) {
			SimilarityMeasurer m = measurers[i];
			double weight = weights[i];
			sum += weight * m.getWordSimilarityValue(line1, line2);
		}
		if(sum >= 1) {
			return 1;
		}
		else if(sum <= 0) {
			return 0;
		}
		return sum;	
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	//	public double dotProduct(double[] vec1, double[] vec2) throws Exception {
	//		double sum = 0;
	//		if(vec1.length != vec2.length) {
	//			throw new Exception("Arrays of different lengths");
	//		}
	//		for(int i = 0; i < vec1.length; i++) {
	//			sum += vec1[i] * vec2[i];
	//		}
	//		return sum;
	//	}
	
	@Override
	String getDescription() {
		StringBuilder b = new StringBuilder();
		for(int i = 0; i < measurers.length; i++) {
			b.append(weights[i] + " * " + measurers[i]);
			if(i < measurers.length - 1) {
				b.append(" + ");
			}
		}
		return "LinearCombinationMeasurer:" + b;
	}
	
	public void setWeights(double[] weights) throws ParseException {
		if(measurers.length != weights.length) {
			throw new ParseException("Arrays of different lengths", 0);
		}
		this.weights = weights;
	}

}
