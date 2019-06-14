package tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import elements.*;

/**
 * Proximity Matrix class. Representation of a symmetric matrix where
 * the diagonal consists of 1 and all other values must be in [0,1].
 * 
 * @author Sophie Hofmanninger
 * @version 2.0
 */
public class Matrix {

	private HashMap<String, Map<String, Float>> relations;
	private ArrayList<Function> listOfFunctions;

	/**
	 * Constructor of the class.
	 */
	public Matrix() {
		relations = new HashMap<String, Map<String, Float>>();
		listOfFunctions= new ArrayList<Function>();
	}

	/**
	 * Constructor of the class.
	 * @param lOF list of functions.
	 */
	public Matrix(ArrayList<Function> lOF) {
		relations = new HashMap<String, Map<String, Float>>();
		listOfFunctions = lOF;
		for(Function f : listOfFunctions) {
			this.addRelation(f, f, 1.0f);
		}
	}
	
	/**
	 * Adds a relation to the matrix given by two elements.
	 * @param elem1 first element.
	 * @param elem2 second element.
	 * @param f relation value to be set.
	 */
	public void addRelation(Function elem1, Function elem2, float f) {
		
		if(!existsFunction(elem1.getName())) {
			Function fun = (Function) elem1.clone();
			listOfFunctions.add(fun);
		}
		
		if(!existsFunction(elem2.getName())) {
			Function fun = (Function) elem2.clone();
			listOfFunctions.add(fun);
		}
		
		addRelation(elem1.getName(), elem2.getName(), f);
	}

	/**
	 * Adds a relation to the matrix given by two strings.
	 * @param s1 first string.
	 * @param s2 second string.
	 * @param f relation value to be set.
	 */
	public void addRelation(String s1, String s2, float f) {
		
		if(!existsFunction(s1)) {
			Function fun = new Function(s1);
			listOfFunctions.add(fun);
		}
		if(!existsFunction(s2)) {
			Function fun = new Function(s2);
			listOfFunctions.add(fun);
		}
		
		if(!hasSameArity(s1,s2)) {
			f=0.0f;
		}
		
		if (relations.containsKey(s1)) {
			relations.get(s1).put(s2, f);
		} else {
			relations.put(s1, new HashMap<String, Float>());
			relations.get(s1).put(s2, f);
		}

		if (relations.containsKey(s2)) {
			relations.get(s2).put(s1, f);
		} else {
			relations.put(s2, new HashMap<String, Float>());
			relations.get(s2).put(s1, f);
		}
	}

	/**
	 * To get the relation of two elements from the matrix.
	 * @param elem1 first element.
	 * @param elem2 second element.
	 * @return the relation value.
	 */
	public float getRelation(Element elem1, Element elem2) {
		return getRelation(elem1.getName(), elem2.getName());
	}

	/**
	 * To get the relation of two strings from the matrix.
	 * @param s1 first string.
	 * @param s2 second string.
	 * @return the relation value.
	 */
	public float getRelation(String s1, String s2) {
		if(s1==s2) {
			return 1;
		}
		else {
			if(relations.containsKey(s1) && relations.get(s1).containsKey(s2)) {
				return relations.get(s1).get(s2);				
			}
			else {
				return 0;
			}
		}
	}

	/**
	 * To get all elements with relation higher or equal to lambda.
	 * @param elem1 element to check.
	 * @param lambda lambda.
	 * @return list of elements.
	 */
	public ArrayList<Element> getRelations(Element elem1, float lambda) {
		return getRelations(elem1.getName(),lambda);
	}

	/**
	 * To get all elements with relation higher or equal to lambda.
	 * @param s1 element name.
	 * @param lambda lambda.
	 * @return list of elements.
	 */
	public ArrayList<Element> getRelations(String s1, float lambda) {
		ArrayList<Element> ret = new ArrayList<>();
		boolean self = false;
		
		if(relations.containsKey(s1)) {
			for (Map.Entry<String, Float> m : relations.get(s1).entrySet()) {
				if(m.getKey().compareTo(s1)==0) {
					self = true;
				}
				if(m.getValue()>=lambda) {
					ret.add(new Function(m.getKey()));
				}
			}
		}
		
		if(!self) {
			ret.add(new Function(s1));
		}
		
		return ret;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toString(0);
	}

	/**
	 * Convert the matrix to a relation list with only relations higher or equal to lambda.
	 * @param lambda lambda.
	 * @return relation list as a string.
	 */
	public String toString(float lambda) {
		String ret = "{";
		List<String> keys = new ArrayList<String>(relations.keySet());
		Collections.sort(keys);

		int comp;

		for (String s1 : keys) {
			for (String s2 : keys) {
				if(relations.get(s1).containsKey(s2)) {
					comp = s1.compareTo(s2);
					if (comp < 0 && relations.get(s1).get(s2) >= lambda) {
						ret += "(" + s1 + "," + s2 + "),";
					}
				}
			}
		}

		ret = ret.substring(0, ret.length() - 1) + "}";
		return ret;
	}
	
	/**
	 * Checks if the matrix is a poximity relation matrix.
	 * @return True if the matrix is a poximity relation matrix and false otherwise.
	 */
	public boolean isPM() {

		List<String> keys = new ArrayList<String>(relations.keySet());
		for (String s1 : keys) {
			for (String s2 : keys) {
				if (this.getRelation(s1, s2) != 1 && s1 == s2) {
					return false;
				}
				if (this.getRelation(s1, s2) > 1 || this.getRelation(s1, s2) < 0) {
					return false;
				}
			}
		}
		
		return true;
	}

	/**
	 * Checks if a function is already in the listOfFunctions.
	 * @param s function name to be checked.
	 * @return true if function name found in the list.
	 */
	private boolean existsFunction(String s) {
		for(Function f : listOfFunctions) {
			if(f.getName().compareTo(s)==0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * To get the list of functions, which appear in the matrix.
	 * @return list of functions.
	 */
	public ArrayList<Function> getListOfFunctions(){
		return listOfFunctions;
	}
	
	/**
	 * To get the open cases.
	 * @return a list of cases, which should be set.
	 */
	public ArrayList<Tuple<Function>> getOpenCases(){
		
		ArrayList<Tuple<Function>> ret = new ArrayList<Tuple<Function>>();
		Tuple<Function> temp;
		
		for(Function f1: listOfFunctions) {
			for(Function f2:listOfFunctions) {
				
				if(f1.getName().compareTo(f2.getName()) < 0 && f1.arity()==f2.arity()) {
					if(!relations.get(f1.getName()).containsKey(f2.getName())) {
						temp = new Tuple<Function>((Function)f1.clone(),(Function)f2.clone());
						ret.add(temp);
					}
				}
				
				
			}
		}
		
		return ret;
	}
	
	/**
	 * Checks if two functions have the same arity.
	 * @param s1 name of the first function.
	 * @param s2 name of the second function.
	 * @return true, if the functions have the same arity, otherwise false.
	 */
	private boolean hasSameArity(String s1, String s2) {
		
		for(Function f1: listOfFunctions) {
			for(Function f2:listOfFunctions) {
				
				if(f1.getName().compareTo(s1) == 0 && f2.getName().compareTo(s2) == 0) {
					if(f1.arity()==f2.arity()) {
						return true;
					}
					else {
						return false;
					}
				}
			}
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Matrix clone(){
		// TODO Auto-generated method stub
		Matrix ret = new Matrix();
		
		ret.listOfFunctions = new ArrayList<Function>();
		ret.relations = new HashMap<String, Map<String, Float>>();
		
		for(Function f : this.listOfFunctions) {
			ret.listOfFunctions.add((Function) f.clone());
		}

		for(Map.Entry<String, Map<String, Float>> m : this.relations.entrySet()) {
			Map<String, Float> tmp = new HashMap<String, Float>();
			for(Map.Entry<String, Float> n : m.getValue().entrySet()) {
				tmp.put(n.getKey(), n.getValue());
			}
			ret.relations.put(m.getKey(), tmp);
		}
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Matrix) {
			Matrix m = (Matrix) obj;
			
			if(m.relations.equals(this.relations) &&
					m.listOfFunctions.equals(this.listOfFunctions)) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return relations.hashCode()+listOfFunctions.hashCode();
	}
	
}