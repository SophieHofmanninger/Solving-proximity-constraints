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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

}
