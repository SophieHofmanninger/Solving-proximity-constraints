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
	
}
