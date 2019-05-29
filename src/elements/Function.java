package elements;

import java.util.ArrayList;
import elements.Element;

/**
 *
 * The class Function handles the functions.
 *
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 * 
 */
public class Function extends Element {

	private ArrayList<Element> arguments;

	/**
	 * This is the constructor for the class Function.
	 * @param name the name of the function.
	 */
	public Function(String name) {
		this.setName(name);
		arguments = new ArrayList<>();
	}

	/**
	 * Return the list of arguments.
	 * @return the arguments
	 */
	public ArrayList<Element> getArguments() {
		return arguments;
	}

	/**
	 * Set the arguments.
	 * @param arguments the arguments to set.
	 */
	public void setArguments(ArrayList<Element> arguments) {
		this.arguments = arguments;
	}
	/**
	 * Add an argument.
	 * @param argument the argument to add.
	 */
	public void addArgument(Element argument) {
		this.arguments.add(argument);
	}

	/**
	 * This is the constructor to create a Function that can represent a Name.
	 * @param name The name.
	 * @param isName {@code boolean} indicating if this is a Name.
	 */
	public Function(String name, boolean isName) {
		this(name);
		representName(isName);
	}
	/**
	 * The number of arguments of the function.
	 * @return the functions arity.
	 */
	public int arity() {
		return arguments.size();
	}

	/**
	 * This method converts the function to a string.
	 * @return String This returns a string.
	 */
	@Override
	public String toString() {
		String ret = this.getName() + "(";

		for(Element e : arguments){
			ret += e.toString() + ",";
		}

		ret = ret.substring(0,ret.length()-1) + ")";

		return ret;
	}
	/* (non-Javadoc)
	 * @see elements.Element#occurs(elements.Element)
	 */
	@Override
	public boolean occurs(Element e) {
		if(this.equals(e)) {
			return true;
		}else {
			boolean retVal = false;
			int i=0;
			while(i<arguments.size()&&!(retVal)) {
				retVal = retVal||arguments.get(i).occurs(e);
			}
			return retVal;
		}
	}



}
