package unificationProblem;

import java.util.ArrayList;
import elements.*;

/**
 *
 * The class InputParser handles the input.
 * [a - t] are function symbols and u is the fist variable. 
 * This means that constants are function symbols with arity zero and [u - z] are variables.
 * Names are witten in UPPER case letters.
 *
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 * 
 */
public class InputParser {

	private static char first_variable = 'u';

	/**
	 * This method parses the input in two parts, left and right.
	 * @param input String, which should be unified.
	 * @return This returns a list with the parts.
	 */
	public static ArrayList<Unifier> parse(String input) {

		ArrayList<Unifier> ret = new ArrayList<>();
		int split;
		Unifier unif;


		for(String s : input.split(";")) {
			unif = new Unifier();

			if(s.contains("=?")){
				split = s.indexOf("=?");
				unif.left = parseSub(s.substring(0,split-1));
				unif.right = parseSub(s.substring(split+2));
			}
			else {
				split = s.indexOf("=");
				unif.left = parseSub(s.substring(split-1));
				unif.right = parseSub(s.substring(split+1));
			}

			ret.add(unif);
		}

		return ret;
	}

	/**
	 * This method parses a string and identifies to which element each character 
	 * belongs (function, variable, constant,name).
	 * @param input This is a string.
	 * @return This returns an element.
	 */
	private static Element parseSub(String input) {

		String name; // name of Element
		boolean inName;
		char c;
		Element elem = null;
		int closing;

		name = "";
		inName = false;

		for (int pos = 0; pos < input.length(); pos++) {
			c = input.charAt(pos);
			if(c==' '&&!inName) {
				continue;
			}
			if(c=='(') {
				closing = input.lastIndexOf(')');
				elem = new Function(name);
				for (String s : input.substring(pos+1, closing).split(",")) {
					((Function)elem).arguments.add(parseSub(s));
				}
				break;
			}
			else if(c<first_variable) {
				if(inName) {
					name += c;
				}
				else {
					inName = true;
					name += c;
					elem = new Constant();
				}
			}
			else if(c<='z') {
				if(inName) {
					name += c;
				}
				else {
					inName = true;
					name += c;
					elem = new Variable();
				}
			}
			else if(Character.isUpperCase(c)) {
				if(inName) {
					name += c;
				}
				else {
					inName = true;
					name += c;
					elem = new Name();
				}
			}
			else {
				elem.setName(name);
				return elem;
			}
		}
		if(name!="") {
			elem.setName(name);
		}
		return elem;
	}


}
