package unificationProblem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

	private static final char FIRST_VARIABLE= 'u';
	private static ArrayList<Function> listOfFunctions;


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
			listOfFunctions = new ArrayList<Function>();

			if(s.contains("=?")){
				split = s.indexOf("=?");
				unif.setLeft(parseSub(s.substring(0,split-1)));
				unif.setRight(parseSub(s.substring(split+2)));
			}
			else {
				split = s.indexOf("=");
				unif.setLeft(parseSub(s.substring(split-1)));
				unif.setRight(parseSub(s.substring(split+1)));
			}

			unif.setNumberOfFunctions(listOfFunctions.size());

			sort(listOfFunctions);
			unif.setSortedListOfFunctions(listOfFunctions);

			/* TODO determine "open Cases" for unif.
			 * Iterate through listOfFunctions, check for Functions with same
			 * arity, and add the pair to the list of open cases.
			 */

			ret.add(unif);
		}

		return ret;
	}

	/**
	 * Sorts the list of function symbols, such that constants are at the beginning, and 
	 * those with higher arity come later.
	 * @param l ArrayList to be sorted.
	 */
	private static void sort(ArrayList<Function> l) {
		Collections.sort(l, new Comparator<Function>(){
			public int compare(Function f1, Function f2) {
				return f1.arity()-f2.arity();
			}
		});

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
				boolean alreadyIn = false;
				for(int i =0; i< listOfFunctions.size();i++) {
					if(elem.equals(listOfFunctions.get(i))) alreadyIn=true;
				}
				if(!alreadyIn) listOfFunctions.add((Function) elem);
				for (String s : input.substring(pos+1, closing).split(",")) {
					((Function)elem).addArgument((parseSub(s)));
				}
				break;
			}
			else if(c<FIRST_VARIABLE) {
				if(inName) {
					name += c;
				}
				else {
					inName = true;
					name += c;
					elem = new Constant("");
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
			if(elem instanceof Constant) {
				boolean alreadyIn = false;
				for(int i =0; i< listOfFunctions.size();i++) {
					if(elem.equals(listOfFunctions.get(i))) alreadyIn=true;
				}
				if(!alreadyIn) listOfFunctions.add((Function) elem);
			}
		}
		return elem;
	}


}
