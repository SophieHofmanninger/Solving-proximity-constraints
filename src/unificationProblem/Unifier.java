/**
 * 
 */
package unificationProblem;

import java.util.ArrayList;

import elements.Element;
import elements.Function;
import tool.Matrix;
import tool.Tuple;

/**
 *
 * The class Unifier handles the left and right part of the unification problem. 
 * This means that the unification problem will be splitted in a left and right hand side.
 * For example: p(x,y,x) =? q(f(a),g(b),y) has the left= p(x,y,x) , right= q(f(a),g(b),y)
 *
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 * 
 */
public class Unifier {
	private Element right;
	private Element left;
	private int numberOfFunctions;
	private ArrayList<Function> sortedListOfFunctions;
	private Matrix proximityRelations;
	private ArrayList<Tuple<Function>> openCases;



	/**
	 * Adds a case where the proximity needs to be determined.
	 * @param t The tuple of functions to add.
	 * @return boolean indicating if the case was added.
	 */
	public boolean addOpenCase(Tuple<Function> t) {
		if(t.getFirst().arity()==t.getSecond().arity() 
				&& !(t.getFirst().equals(t.getSecond()))) {
			openCases.add(t);
			return true;
		}
		return false;
	}

	/**
	 * @return a Tuple of Function whos proximity is to be determined, {@code null}
	 * if there are no more such.
	 */
	public Tuple<Function> getNextOpenCase(){
		if(openCases.size()>0) return openCases.get(0);
		return null;
	}

	/**
	 * Close an open Case.
	 * @param t the tuple to remove from the list. Note: (a,b) will also be removed, if the
	 * method is called with (b,a).
	 * @param p the value to set. Must be in [0,1].
	 * @return boolean indicating if the case could be closed.
	 */
	public boolean CloseCase(Tuple<Function> t, float p) {
		if(p<0||p>1) return false;
		if(openCases.contains(t)) {
			int k= sortedListOfFunctions.indexOf(t.getFirst());
			int l= sortedListOfFunctions.indexOf(t.getSecond());
			if(k<l) {
				proximityRelations.putAt(new Tuple<Integer>(k, l), p);
			}else {
				proximityRelations.putAt(new Tuple<Integer>(l,k), p);
			}
			openCases.remove(t);			
			return true;
		}
		Tuple<Function> rev = new Tuple<Function>(t.getSecond(),t.getFirst());
		if(openCases.contains(rev)) {
			int k= sortedListOfFunctions.indexOf(rev.getFirst());
			int l= sortedListOfFunctions.indexOf(rev.getSecond());
			if(k<l) {
				proximityRelations.putAt(new Tuple<Integer>(k, l), p);
			}else {
				proximityRelations.putAt(new Tuple<Integer>(l,k), p);
			}
			openCases.remove(rev);			
			return true;
		}


		return false;
	}


	/**
	 * @return the right Element.
	 */
	public Element getRight() {
		return right;
	}
	/**
	 * @param right Element to set to the rhs.
	 */
	public void setRight(Element right) {
		this.right = right;
	}
	/**
	 * @return the left Element.
	 */
	public Element getLeft() {
		return left;
	}
	/**
	 * @param left Element to set to the lhs.
	 */
	public void setLeft(Element left) {
		this.left = left;
	}
	/**
	 * @return the number of functions symbols (and constants).
	 */
	public int getNumberOfFunctions() {
		return numberOfFunctions;
	}
	/**
	 * @param numberOfFunctions the number Of functions symbols to set.
	 */
	public void setNumberOfFunctions(int numberOfFunctions) {
		this.numberOfFunctions = numberOfFunctions;
	}
	/**
	 * @return a list of functions appearing in the unification problem. Note: Constants
	 * are treated as 0-ary functions.
	 */
	public ArrayList<Function> getSortedListOfFunctions() {
		return sortedListOfFunctions;
	}
	/**
	 * Sets the list of functions.
	 * @param sortedListOfFunctions the list of functions of this unification problem
	 */
	public void setSortedListOfFunctions(ArrayList<Function> sortedListOfFunctions) {
		this.sortedListOfFunctions = sortedListOfFunctions;
	}


	/**
	 * @return the proximity relations, represented as a matrix.
	 */
	public Matrix getProximityRelations() {
		return proximityRelations;
	}


	/**
	 * @param proximityRelations the proximity relations to set, represented as a matrix.
	 * Note: If the matrix is not a proximity relations Matrix (i.e. all values are in
	 * [0,1], and all values on the main diagonal are 1) the matrix will be set to the
	 * corresponding unity matrix. Also, function symbols with different arity have to be
	 * set to have proximity 0.
	 */
	public void setProximityRelations(Matrix proximityRelations) {
		boolean error= false;
		if(!Matrix.isPM(proximityRelations)) {
			error=true;
		}
		for(int j=1;j<proximityRelations.getSize();j++) {
			if(error) break;
			for(int i=0;i<j;i++) {
				if(proximityRelations.getAt(new Tuple<Integer>(i,j))>0
						&& sortedListOfFunctions.get(i).arity()!=
						sortedListOfFunctions.get(j).arity()){
					error= true;
					break;
				}
				if(error) break;
			}
		}

		if(error) {
			this.proximityRelations= new Matrix(this.numberOfFunctions);
			return;
		}


		this.proximityRelations = proximityRelations;

	}



	// TODO ToString()



}
