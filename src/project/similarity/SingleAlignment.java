/**
 * 
 */
package project.similarity;

/**
 * @author gwprice
 *
 */
public class SingleAlignment implements Comparable<SingleAlignment> {
	
	private Double key;
	private String word1;
	private String word2;
	private String definition1;
	private String definition2;
	
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

	public SingleAlignment(double key, String word1, String word2, String def1,
			String def2) {
		this.key = key;
		this.word1 = word1;
		this.word2 = word2;
		this.definition1 = def1;
		this.definition2 = def2;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SingleAlignment [key=" + key + ", word1=" + word1 + ", word2="
				+ word2 + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word1 == null) ? 0 : word1.hashCode());
		result = prime * result + ((word2 == null) ? 0 : word2.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * Returns true if the two words are identical, even if the keys are different
	 */
	@Override
	public boolean equals(Object obj) {
		
//		if(word1.equalsIgnoreCase("lukaka") && word2.equalsIgnoreCase("engaka")) {
//			System.out.println("HI WE'RE IN EQUALS");
//		}
		if(obj == this) {
			return true;
		}
		
		if((obj == null) || (obj.getClass() != this.getClass())) {
			return false;
		}
		
		SingleAlignment other = (SingleAlignment) obj;
		
		return word1.equalsIgnoreCase(other.getWord1()) && word2.equalsIgnoreCase(other.getWord2());
	}

	/**
	 * @return the definition1
	 */
	public String getDefinition1() {
		return definition1;
	}

	/**
	 * @param definition1 the definition1 to set
	 */
	public void setDefinition1(String definition1) {
		this.definition1 = definition1;
	}

	/**
	 * @return the definition2
	 */
	public String getDefinition2() {
		return definition2;
	}

	/**
	 * @param definition2 the definition2 to set
	 */
	public void setDefinition2(String definition2) {
		this.definition2 = definition2;
	}

}
