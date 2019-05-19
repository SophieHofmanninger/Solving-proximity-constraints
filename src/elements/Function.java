package elements;

import java.util.ArrayList;
import java.util.List;
import elements.Element;

/**
 *
 * The class Function handles the functions.
 *
 * @author  Jan-Michael Holzinger & Sophie Hofmanninger
 * @version 1.0
 * 
 */
public class Function extends Element {
	public ArrayList<Element> arguments;

	/**
	 * This is the constructor for the class Function.
	 * @param name the name of the function.
	 */
	public Function(String name) {
		this.name=name;
		arguments = new ArrayList<>();
	}

	public int arity() {
		return arguments.size();
	}

	/**
	 * This method converts the function to a string.
	 * @return String This returns a string.
	 */
	@Override
	public String toString() {
		String ret = this.name + "(";

		for(Element e : arguments){
			ret += e.toString() + ",";
		}

		ret = ret.substring(0,ret.length()-1) + ")";

		return ret;
	}


}
