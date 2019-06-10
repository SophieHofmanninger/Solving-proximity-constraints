package unificationProblem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;

import elements.*;
import tool.Matrix;
//import tool.Tuple;

/**
 *
 * The class InputParser handles the input.
 * [a - t] are function symbols and u is the fist variable. 
 * This means that constants are function symbols with arity zero and [u - z] are variables.
 * Names are witten in UPPER case letters.
 *
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 3.0
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
	public static ArrayList<UnificationProblem> parse(String input) {
		Element.reset();
		ArrayList<UnificationProblem> ret = new ArrayList<>();
		int split;
		UnificationProblem unif;

		for(String s : input.split(";")) {
			listOfFunctions = new ArrayList<Function>();
			Element left=null;
			Element right=null;
			if(s.contains("=?")){
				split = s.indexOf("=?");
				left=parseSub(s.substring(0,split-1));
				right=parseSub(s.substring(split+2));
			}
			else {
				split = s.indexOf("=");
				left=parseSub(s.substring(0,split-1));
				right=parseSub(s.substring(split+1));
			}
			unif = new UnificationProblem(left,right);

			unif.setProximityRelations(new Matrix(listOfFunctions));
			//sort(listOfFunctions);
			//unif.setSortedListOfFunctions(listOfFunctions);
			
			/* Determine "open Cases" for unif.
			 * Iterate through listOfFunctions, check for Functions with same
			 * arity, and add the pair to the list of open cases.
			 */
			//not needed anymore - gets determined in Matrix
			/*for(int i = 0;i<listOfFunctions.size()-1;i++) {
				Function f1 = listOfFunctions.get(i);
				for(int j=i+1;j<listOfFunctions.size();j++) {
					Function f2 = listOfFunctions.get(j);
					if(f1.arity()==f2.arity()) {
						unif.addOpenCase(new Tuple<Function>(f1,f2));
					}else {
						break;
					}
				}
			}*/


			ret.add(unif);
		}

		return ret;
	}

	/**
	 * Sorts the list of function symbols, such that constants are at the beginning, and 
	 * those with higher arity come later.
	 * @param l ArrayList to be sorted.
	 */
	//Not needed anymore
	/*private static void sort(ArrayList<Function> l) {
		Collections.sort(l, new Comparator<Function>(){
			public int compare(Function f1, Function f2) {
				return f1.arity()-f2.arity();
			}
		});
	}*/

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
				closing = getIndexOfCorrespondingBracket(input,pos);
				elem = new Function(name);
				boolean alreadyIn = false;
				for(int i =0; i< listOfFunctions.size();i++) {
					if(elem.equals(listOfFunctions.get(i))) alreadyIn=true;
				}
				if(!alreadyIn) listOfFunctions.add((Function) elem);
				for (String s : getCorrectSubstrings(input,pos+1,closing-1)) {
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
					elem = new Function("");
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
					elem = new Function(name, true);
				}
			}
			else {
				elem.setName(name);
				return elem;
			}
		}
		if(name!="") {
			
			elem.setName(name);
			if(elem instanceof Function && ((Function) elem).arity()==0) {
				boolean alreadyIn = false;
				for(int i =0; i< listOfFunctions.size();i++) {
					if(elem.equals(listOfFunctions.get(i))) alreadyIn=true;
				}
				if(!alreadyIn) listOfFunctions.add((Function) elem);
			}
		}
		return elem;
	}

	/**
	 * Get the corresponding to a opening bracket.
	 * @param s input String.
	 * @param startIndex index of the opening bracket in the string.
	 * @return the index of the closing bracket, -1 if startIndex isn't an opening bracket.
	 */
	private static int getIndexOfCorrespondingBracket(String s,int startIndex) {
		//startIndex has to be the Index of the opening Bracket
		if(s.charAt(startIndex) != '(') {
			return -1;
		}
		
		int level=0;
		int index=startIndex;
		
		do {
			if(s.charAt(index)=='(') {
				level++;
			}
			
			if(s.charAt(index)==')') {
				level--;
			}
			
			index++;
		}while(level>0);
		
		return --index;
		
	}
	
	/**
	 * Generates Substring of a String, splitting the String on ',' chars, which are on the right level.
	 * @param s input String.
	 * @param startIndex Index of first char.
	 * @param endIndex Index of the last char.
	 * @return List of Substrings.
	 */
	private static ArrayList<String> getCorrectSubstrings(String s, int startIndex, int endIndex){
		ArrayList<String> ret = new ArrayList<String>();
		
		int start = startIndex;
		int index = startIndex;
		int level=0;
		
		do {
			if(s.charAt(index)=='(') {
				level++;
			}
			
			if(s.charAt(index)==')') {
				level--;
			}
			
			if(s.charAt(index)==','&&level==0) {
				ret.add(s.substring(start,index));
				start=++index;
			}
			
			index++;
		}while(index<endIndex);
		
		ret.add(s.substring(start,endIndex+1));
		
		return ret;
	}
	
	
	public static Matrix parseMatrixFromFile(String inputFile) throws IOException {
		FileReader fReader = new FileReader(inputFile);
		BufferedReader bReader = new BufferedReader(fReader);
		return parseMatrix(bReader);
	}
	
	
	public static Matrix parseMatrixFromString(String input) throws IOException {
		BufferedReader bReader = new BufferedReader(new StringReader(input));
		return parseMatrix(bReader);
	}
	
	
	private static Matrix parseMatrix(BufferedReader reader) throws IOException {
		Matrix ret = new Matrix();
		ArrayList<String> functions = new ArrayList<String>();
		char c;
		int column=0;
		String line = reader.readLine();
		String str="";
		String fName = "";
		boolean inStr=false;
		boolean hasFName=false;
		
		for(int pos = 0; pos<line.length();pos++) {
			c = line.charAt(pos);
			if(c==' ') {
				if(inStr) {
					functions.add(str);
					str="";
					inStr=false;
				}
			}
			else {
				inStr = true;
				str += c;
			}
		}
		
		while((line=reader.readLine()) != null) {
			for(int pos = 0; pos< line.length();pos++) {
				c=line.charAt(pos);
				if(c == ' ') {
					if(inStr) {
						if(hasFName) {
							ret.addRelation(functions.get(column), fName, Float.parseFloat(str));
							column++;
							str="";
						}
						else {
							fName=str;
							str="";
							hasFName=true;
						}
					}
					else
					{
						str+=c;
					}
				}
				if(str!=""&&hasFName) {
					ret.addRelation(functions.get(column), fName, Float.parseFloat(str));
					column++;
					str="";
				}
				
				hasFName = false;
				str="";
				column=0;
			}
		}
		return ret;
	}

}
