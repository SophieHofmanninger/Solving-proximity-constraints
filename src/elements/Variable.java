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
	 * Replaces a Variable with an Element.
	 * @param e the element the Variable is replaced with
	 * @return A new Element.
	 */
	public static Element replace(Element e) {
		return e.clone();
	}


	/**
	 * Unification of 2 variables
	 * @param v the element to unify with.
	 */
	public void mapsto(Element v) {
		setName(v.getName());
	}


	/**
	 * This is the default constructor for the class Variable.
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

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Element clone() {
		return new Variable(this.getName(),this.isName());
	}

	/* (non-Javadoc)
	 * @see elements.Element#toFullString()
	 */
	@Override
	public String toFullString() {
		return this.toString();
	}

	/* (non-Javadoc)
	 * @see elements.Element#rename()
	 */
	@Override
	public Element rename() {
		String newName=getNumberOfNames()+"N";
		Variable ret = new Variable(newName,true);
		setNumberOfNames(getNumberOfNames() + 1);
		return ret;
	}




}
