package org.custom.models;

/**
 * The Class Parents.
 */
public class Parents {
	
	/** The Parents. */
	Parents[] Parents=null;
	
	/** The ch2. */
	Children ch,ch1,ch2;
	
	/**
	 * Adds the.
	 *
	 * @param ch the ch
	 */
	public void add(Children ch){
		
		ch=new Children();
		ch1=new Children();
		ch2=new Children();
		
		Parents[0]=ch;
		Parents[1]=ch1;
		Parents[2]=ch2;
		
	}

}
