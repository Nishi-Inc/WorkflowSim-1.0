package org.custom.models;

/**
 * The Class Sbest.
 */
public class Sbest {
	

	/**
	 * Gets the psoluation.
	 *
	 * @return the psoluation
	 */
	public Population getPsoluation() {
		return Psoluation;
	}
	
	/**
	 * Sets the psoluation.
	 *
	 * @param psoluation the new psoluation
	 */
	public void setPsoluation(Population psoluation) {
		Psoluation = psoluation;
	}
	
	/**
	 * Gets the csoluation.
	 *
	 * @return the csoluation
	 */
	public Children getCsoluation() {
		return Csoluation;
	}
	
	/**
	 * Sets the csoluation.
	 *
	 * @param csoluation the new csoluation
	 */
	public void setCsoluation(Children csoluation) {
		Csoluation = csoluation;
	}
	
	/** The Psoluation. */
	Population Psoluation=null;
	
	/** The Csoluation. */
	Children Csoluation=null;
	

}
