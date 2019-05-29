package elements;

/**
 *
 * The class Variable handles the variables.
 *
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 * 
 */
public class Variable extends Element{

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getName();
	}

	/* (non-Javadoc)
	 * @see elements.Element#occurs(elements.Element)
	 */
	@Override
	public boolean occurs(Element e) {
		return equals(e);
	}

	/**
	 * Unification of 2 variables
	 * @param v the variable to unify with.
	 */
	public void mapsto(Variable v) {
		setName(v.getName());
	}


	/**
	 * This is the default constructor for the class Variable.
	 * @param name The name.
	 */
	public Variable() {	}

	/**
	 * This is the constructor for the class Variable.
	 * @param name The name.
	 */
	public Variable(String name) {
		this.setName(name);
	}

	/**
	 * This is the constructor to create a Variable that can represent a Name.
	 * @param name The name.
	 * @param isName {@code boolean} indicating if this is a Name.
	 */
	public Variable(String name, boolean isName) {
		this(name);
		representName(isName);
	}

}
