package elements;

/**
*
* The abstract class Element handles the elements, which can be functions,
* constants, variables or names.
*
* @author  Jan-Michael Holzinger & Sophie Hofmanninger
* @version 1.0
* 
*/
public abstract class Element {
	public String name;
	
	/**
	 * This method converts the element to a string.
	 * @return This returns a string.
	 */
	public String toString() {
		return name;
	}
	
}
