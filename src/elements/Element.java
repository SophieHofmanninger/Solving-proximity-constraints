package elements;

/**
 *
 * The abstract class Element handles the elements, which can be functions,
 * constants, variables or names.
 *
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.1
 * 
 */
public abstract class Element {
	private String name;
	private boolean representsName=false;
	private static int numberOfNames=1;

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public abstract Element clone();

	/**
	 * Creates an element with a new name from an element.
	 * @return a new name.
	 */
	public abstract Element rename();

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public abstract String toString();

	/**
	 * Full string representation.
	 * @return a string representation.
	 */
	public abstract String toFullString();

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
	 * Set a value, that indicates if this Element represents a Name.
	 * @param isName {@code boolean} indicating if this represents a Name.
	 */
	protected void representName(boolean isName) {
		representsName=isName;
	}


	/**
	 * Test if this is equal to another element, where equality means, the 
	 * elements have the same name.
	 * @param e The other element to test equality with.
	 * @return {@code boolean} true iff the elements are the same.
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

	/**
	 * Checks if another element occurs in this term.
	 * @param e The element to search for.
	 * @return {@code boolean} true iff the element occurs.
	 */
	public abstract boolean occurs(Element e);

	/**
	 * Get a boolean value, that indicates if this Element represents a Name.
	 * @return {@code boolean} indicating if this is a Name.
	 */
	public boolean isName() {
		return representsName;
	}

	/**
	 * Resets the number of names used.
	 */
	public static void reset() {
		setNumberOfNames(1);
	}

	/**
	 * @return the numberOfNames
	 */
	protected static int getNumberOfNames() {
		return numberOfNames;
	}

	/**
	 * @param numberOfNames the numberOfNames to set
	 */
	protected static void setNumberOfNames(int numberOfNames) {
		Element.numberOfNames = numberOfNames;
	}

}
