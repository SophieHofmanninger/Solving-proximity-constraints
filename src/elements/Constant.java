package elements;

/**
 *
 * The class Constants handles the constants.
 *
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 * 
 */
public class Constant extends Function{

	/**
	 * This is the constructor for the class Constant.
	 * Constants are treated as 0-ary functions.
	 * @param name The name.
	 */
	public Constant(String name) {
		super(name);
	}

	/**
	 * This is the constructor to create a Constant that can represent a Name.
	 * @param name The name.
	 * @param isName {@code boolean} indicating if this is a Name.
	 */
	public Constant(String name, boolean isName) {
		super(name,isName);
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

}
