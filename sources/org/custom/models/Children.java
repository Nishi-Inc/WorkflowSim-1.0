package org.custom.models;

/**
 * The Class Children.
 */
public class Children extends Parents {
	
	/** The Parents. */
	Children[] Parents=null;
	
/* (non-Javadoc)
 * @see com.pc.impl.Parents#add(com.pc.impl.Children)
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
