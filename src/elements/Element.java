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

	/**
	 * @return the Name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the Name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Test if this is equal to another element, where equality means, the 
	 * elements have the same name.
	 * @param e
	 * @return
	 */
	public boolean equals(Element e) {
		return (this.name.equals(e.name));
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if ((obj instanceof Element))
			return this.equals((Element) obj);
		return false;
	}


}
