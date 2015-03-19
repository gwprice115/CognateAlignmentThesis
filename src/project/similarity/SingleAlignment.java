/**
 * 
 */
package project.similarity;

/**
 * @author gwprice
 *
 */
public class SingleAlignment implements Comparable<SingleAlignment> {
	
	Double key;
	String word1;
	String word2;
	
	/**
	 * @param key
	 * @param word1
	 * @param word2
	 */
	public SingleAlignment(double key, String word1, String word2) {
		this.key = key;
		this.word1 = word1;
		this.word2 = word2;
	}
	
	public SingleAlignment(double key) {
		this.key = key;
		word1 = word2 = "";
	}
	
	public SingleAlignment(String word1, String word2) {
		this.word1 = word1;
		this.word2 = word2;
	}

	@Override
	public int compareTo(SingleAlignment o) {
		// TODO Auto-generated method stub
		return key.compareTo(o.getKey());
	}
	/**
	 * @return the key
	 */
	public double getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(double key) {
		this.key = key;
	}
	/**
	 * @return the word1
	 */
	public String getWord1() {
		return word1;
	}
	/**
	 * @param word1 the word1 to set
	 */
	public void setWord1(String word1) {
		this.word1 = word1;
	}
	/**
	 * @return the word2
	 */
	public String getWord2() {
		return word2;
	}
	/**
	 * @param word2 the word2 to set
	 */
	public void setWord2(String word2) {
		this.word2 = word2;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * Returns true if the two words are identical, even if the keys are different
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		
		if((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		
		SingleAlignment other = (SingleAlignment) obj;
		
		return word1.equalsIgnoreCase(other.getWord1()) && word2.equalsIgnoreCase(other.getWord2());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SingleAlignment [key=" + key + ", word1=" + word1 + ", word2="
				+ word2 + "]";
	}

}
