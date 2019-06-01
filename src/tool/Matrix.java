/**
 * 
 */
package tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

import elements.*;

/**
 * Proximity Matrix class. Representation of a symmetric matrix of size n>=1,
 * where only the values above the main diagonal are stored and are different
 * from 1. All values must be in [0,1].
 * 
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.2
 */
public class Matrix {

	private Map<String, Map<String, Float>> relations;

	/**
	 * Constructor of the class.
	 */
	public Matrix() {
		relations = new HashMap<String, Map<String, Float>>();
	}

	/**
	 * Adds a relation to the matrix given by two elements.
	 * @param elem1 first element.
	 * @param elem2 second element.
	 * @param f relation value to be set.
	 */
	public void addRelation(Element elem1, Element elem2, float f) {
		addRelation(elem1.getName(), elem2.getName(), f);
	}

	/**
	 * Adds a relation to the matrix given by two strings.
	 * @param s1 first string.
	 * @param s2 second string.
	 * @param f relation value to be set.
	 */
	public void addRelation(String s1, String s2, float f) {
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
			if(relations.containsKey(s1) && relations.containsKey(s2)) {
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
		
		for (Map.Entry<String, Float> m : relations.get(s1).entrySet()) {
			if(m.getKey()==s1) {
				self = true;
			}
			if(m.getValue()>=lambda) {
				ret.add(new Function(m.getKey()));
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
				if (relations.get(s1).get(s2) != 1 && s1 == s2) {
					return false;
				}
				if (relations.get(s1).get(s2) > 1 || relations.get(s1).get(s2) < 0) {
					return false;
				}
			}
		}
		
		return true;
	}

	//TODO delete code if everything works correctly, where this methods were used
	/**
	 * Constructor
	 * 
	 * @param size of the n x n Matrix.
	 */
	/*
	 * public Matrix(int size){ if(size<1) size=1; this.size=size; int
	 * realsize=((1+(size-1))*(size-1))/2; content= new float[realsize]; contentE =
	 * new Element[size]; }
	 * 
	 *//**
		 * This method defines the position of the element.
		 * 
		 * @param elem     The element, which should be specified to a position.
		 * @param position The position of the element.
		 */
	/*
	 * public void setRef(Element elem, int position) { if(position<0 || position>=
	 * size) { throw new IndexOutOfBoundsException("Index "+position+
	 * " not allowed."); } else { contentE[position] = elem; } }
	 * 
	 *//**
		 * This method puts a relation value between elements.
		 * 
		 * @param elems This are the elements which should be related with a value.
		 * @param v     This is the relation value.
		 * @throws NoSuchElementException Throws exception if element is not in the
		 *                                matrix.
		 */
	/*
	 * public void putRef(Tuple<Element> elems, float v) throws
	 * NoSuchElementException{
	 * 
	 * Tuple<Integer> ti = new
	 * Tuple<>(getIndex(elems.getFirst()),getIndex(elems.getSecond()));
	 * 
	 * if(ti.getFirst()<ti.getSecond()) { int temp = ti.getSecond();
	 * ti.setSecond(ti.getFirst()); ti.setFirst(temp); } if(ti.getSecond()<0) {
	 * throw new NoSuchElementException("One of the Elements was not found!"); }
	 * 
	 * putAt(ti, v); }
	 * 
	 *//**
		 * Put a value at a position in the upper triangle-part of the Matrix.
		 * 
		 * @param indices The indices indicating the position
		 * @param v       the value to put.
		 * @throws IndexOutOfBoundsException if <br>
		 *                                   <ul>
		 *                                   <li>the indices are negative,</li>
		 *                                   <li>exceed matrix size or</li>
		 *                                   <li>a position in the main diagonal is
		 *                                   accessed.</li>
		 *                                   </ul>
		 * @throws IllegalArgumentException  if the value is not in [0,1].
		 */
	/*
	 * public void putAt(Tuple<Integer> indices, float v) throws
	 * IndexOutOfBoundsException,IllegalArgumentException{ int i=
	 * indices.getFirst(); int j= indices.getSecond(); if(i<0||i>=size||i==j) throw
	 * new IndexOutOfBoundsException("Index "+i+ " not allowed."); if(j<0||j>=size)
	 * throw new IndexOutOfBoundsException("Index "+j+" not allowed."); if(v<0||v>1)
	 * throw new IllegalArgumentException("Value "+v+" not allowed," +
	 * " only values between 0 and 1 are allowed."); content[(Math.max(i,
	 * j)*(Math.max(i, j)-1))/2+(Math.min(i, j))]=v; }
	 * 
	 *//**
		 * This method gets a relation value between elements.
		 * 
		 * @param elems This are the elements where we want to know the relation value.
		 * @return This returns the relation value.
		 * @throws NoSuchElementException Throws exception if element is not in the
		 *                                matrix.
		 */
	/*
	 * public float getRef(Tuple<Element> elems) throws NoSuchElementException{
	 * Tuple<Integer> ti = new
	 * Tuple<>(getIndex(elems.getFirst()),getIndex(elems.getSecond()));
	 * if(ti.getFirst()<ti.getSecond()) { int temp = ti.getSecond();
	 * ti.setSecond(ti.getFirst()); ti.setFirst(temp); } if(ti.getSecond()<0) {
	 * throw new NoSuchElementException("One of the Elements was not found!"); }
	 * return getAt(ti); }
	 * 
	 *//**
		 * Get a value from the Matrix.
		 * 
		 * @param indices the position where the value is stored.
		 * @return the value at position (indices.getFirst(),indices.getSecond());
		 * @throws IndexOutOfBoundsException if <br>
		 *                                   <ul>
		 *                                   <li>the indices are negative or</li>
		 *                                   <li>exceed matrix size.</li>
		 *                                   </ul>
		 */
	/*
	 * public float getAt(Tuple<Integer> indices) throws IndexOutOfBoundsException{
	 * int i = Math.min(indices.getFirst(),indices.getSecond()); int j =
	 * Math.max(indices.getFirst(),indices.getSecond()); if(i<0||i>=size) throw new
	 * IndexOutOfBoundsException("Index "+i+ " not allowed."); if(j<0||j>=size)
	 * throw new IndexOutOfBoundsException("Index "+j+" not allowed."); if(i==j)
	 * return 1; return content[(j*(j-1))/2+i]; }
	 * 
	 *//**
		 * Determines if a given matrix fulfills the criterion to be a proximity matrix.
		 * I.e. all values stored must be in [0,1] and the values on the main diagonal
		 * must be 1.
		 * 
		 * @param m The matrix to check.
		 * @return {@code boolean} true, iff the matrix fulfills the above mentioned
		 *         criterion.
		 */
	/*
	 * public static boolean isPM(Matrix m) { // The size must correspond to the
	 * array size. int result = m.content.length; result *= 2; result /= (m.size-1);
	 * result -= (m.size-1); if(result != 1) return false; // All values must be in
	 * [0,1]. for(int i=0;i<m.content.length;i++) {
	 * if(m.content[i]<0||m.content[i]>1) return false; } return true; }
	 * 
	 *//**
		 * @return the dimension of the Matrix.
		 */
	/*
	 * public int getSize() { return size; }
	 * 
	 *//**
		 * This method gets the index of an element.
		 * 
		 * @param elem This is the element where want to know the index.
		 * @return Returns the index. -1 if not found.
		 */
	/*
	 * private int getIndex(Element elem) { for(int i = 0; i<size;i++) {
	 * if(elem.getName() == contentE[i].getName()) { return i; } } return -1; }
	 * 
	 *//**
		 * This method delivers the relations.
		 * 
		 * @param elem   This is the element from which we want the relatives.
		 * @param lambda This is the lower bound of the relations.
		 * @return Returns a list of all relations.
		 *//*
			 * public ArrayList<Element> getR(Element elem, float lambda){
			 * 
			 * ArrayList<Element> ret = new ArrayList<>();
			 * 
			 * for(int i = 0; i<size;i++) { if(elem.getName() != contentE[i].getName()) {
			 * Tuple<Element> t = new Tuple<>(elem,contentE[0]); if(lambda <= getRef(t)) {
			 * ret.add(contentE[i]); } } } return ret; }
			 */
}
