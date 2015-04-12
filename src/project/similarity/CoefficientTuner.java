package project.similarity;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;

import project.similarity.measurers.CosineCharNgramVectorMeasurer;
import project.similarity.measurers.JaccardCharNgramsInCommonMeasurer;
import project.similarity.measurers.LinearCombinationMeasurer;
import project.similarity.measurers.SimilarityMeasurer;
import project.similarity.measurers.TfIdfMeasurer;
import project.similarity.utilities.UtilityInterface;

public class CoefficientTuner {

	int s;

	static int numMeasurers;
	SimilarityMeasurer[] measurers;
	LinearCombinationMeasurer lcm;
	String inputFile;
	String authorityFile1;
	String authorityFile2;
	int precision;
	ArrayList<double[]> weightCombos;
	double[] tunedWeights = null;
	double maxF1;
	double maxF1_threshold;

	/**
	 * 
	 * @param measurers the set of measurers to be included in the linear combination
	 * @param precision the increment will be 1/precision
	 */
	public CoefficientTuner(SimilarityMeasurer[] measurers, int precision, int s) {
		this(measurers,precision,
				"/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho_X_Samia-Lugwe.txt",
				"/Users/gwprice/Desktop/project_data/final_data/Isukha-Idakho.txt",
				"/Users/gwprice/Desktop/project_data/final_data/Samia-Lugwe.txt",s);
	}

	public CoefficientTuner(SimilarityMeasurer[] measurers, int precision, String inputFile,String authorityFile1,String authorityFile2, int s) {
		this.measurers = measurers;
		try {
			this.lcm = new LinearCombinationMeasurer(measurers);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.numMeasurers = measurers.length;
		this.inputFile = inputFile;
		this.authorityFile1 = authorityFile1;
		this.authorityFile2 = authorityFile2;
		this.precision = precision;
		this.weightCombos = getWeightCombinations();
		this.s = s;
		maxF1 = Double.NEGATIVE_INFINITY;
		maxF1_threshold = 0;
	}

	public static void main(String[] args) {
		SimilarityMeasurer[] measurers = {new JaccardCharNgramsInCommonMeasurer(2), new CosineCharNgramVectorMeasurer(2), new TfIdfMeasurer()};
		int precision = 10;
		CoefficientTuner tuner = new CoefficientTuner(measurers,precision,0);
		for(double[] vector : tuner.getWeightCombinations()) {
			System.out.println(vectorToString(vector));
		}
	}

	public double[] tune() {
		return tune(false, false);
	}

	public double[] tune(boolean verbose) {
		return tune(false, verbose);
	}

	public double[] tune(boolean forceTune, boolean verbose) {
		if((tunedWeights != null) && !forceTune) {
			return tunedWeights;
		}

		try {
			for(double[] vector : weightCombos) {
				if(verbose) {
					System.out.println(vectorToString(vector));
				}
				lcm.setWeights(vector);
				ThresholdTuner tt = new ThresholdTuner(lcm, 0.05, s);
				double f1 = tt.getMaxF1();
				if(verbose) {
					System.out.println(f1);
				}
				if (f1 > maxF1) {
					if(verbose) {
						System.out.println("new max: " + vectorToString(vector) + " yields an F1 of " + f1);
					}
					tunedWeights = vector;
					maxF1 = f1;
					maxF1_threshold = tt.getThresholdForMaxF1();
				}
			}
			lcm.setWeights(tunedWeights);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private ArrayList<double[]> getWeightCombinations() {
		ArrayList<Integer[]> intVectorSet = new ArrayList<Integer[]>();
		gen_vectors(intVectorSet,numMeasurers,new ArrayList<Integer>(),precision);
		//		put a check here to make sure that the proper number of feature combinations are being computed.
		//		the expected value is easy to compute
		// maybe not worth the overhead

		double increment = 1 / ((double) precision);
		ArrayList<double[]> doubleVectorSet = new ArrayList<double[]>();

		for(Integer[] intVector : intVectorSet) {
			if(intVector[0] < 5 && intVector[2] > 1) {
				double[] doubleVector = new double[numMeasurers];
				for(int c = 0; c < intVector.length; c++) {
					int i = intVector[c];
					double d = ((double) i) * increment;
					doubleVector[c] = d;
				}
				doubleVectorSet.add(doubleVector);
			}
		}

		return doubleVectorSet;
	}

	public ArrayList<double[]> getPrecalculatedWeightCombinations() {
		return weightCombos;
	}

	private static void gen_vectors(ArrayList<Integer[]> accumulator, int numWeightsRemaining, ArrayList<Integer> vector, int sum) {
		if (sum < 1) { //effectively if sum = 0 but doesn't mess up with the double arithmetic inaccuracy
			if(numWeightsRemaining == numMeasurers) {
				return;
			} else if (numWeightsRemaining < numMeasurers) {
				int zeroes = numMeasurers - vector.size();
				for(int i = 0; i < zeroes; i++) {
					vector.add(0);
				}
				accumulator.add(vector.toArray(new Integer[numMeasurers]));
				for(int i = 0; i < zeroes; i++) {
					vector.remove(vector.size()-1);
				}
			}
			else {
				System.err.println("BROKEN");
				System.exit(1);
			}

		}
		else if(numWeightsRemaining == 1) {
			vector.add(sum);
			accumulator.add(vector.toArray(new Integer[numMeasurers]));
			vector.remove(vector.size()-1);
		}
		else {
			for(int i = 0; i < sum + 1; i++) {
				vector.add(i);
				gen_vectors(accumulator, numWeightsRemaining - 1, vector, sum - i);
				vector.remove(vector.size()-1);
			}
		}
	}

	/**
	 * @return the maxF1
	 */
	public double getMaxF1() {
		return maxF1;
	}

	/**
	 * @return the maxF1_threshold
	 */
	public double getMaxF1_threshold() {
		return maxF1_threshold;
	}

	/**
	 * @return the tunedWeights
	 */
	public double[] getTunedWeights() {
		return tunedWeights;
	}

	static String vectorToString(double[] vec) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for(double d : vec) {
			sb.append(d);
			sb.append(" ,");
		}
		sb.append("]");
		return sb.toString();
	}

	public LinearCombinationMeasurer getLCM() {
		// TODO Auto-generated method stub
		return lcm;
	}


}
