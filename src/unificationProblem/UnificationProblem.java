/**
 * 
 */
package unificationProblem;

import java.util.ArrayList;
import java.util.Map;

import elements.Element;
import elements.Function;
import tool.Matrix;
import tool.Tuple;

import unificationProblem.Algorithms;

/**
 *
 * The class Unifier handles the left and right part of the unification problem. 
 * This means that the unification problem will be splitted in a left and right hand side.
 * For example: p(x,y,x) =? q(f(a),g(b),y) has the left= p(x,y,x) , right= q(f(a),g(b),y)
 *
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.1
 * 
 */
public class UnificationProblem {
	// Left and right hand side of the equation:
	private Element right;
	private Element left;

	// A list of Functions and their proximity relations (represented as Matrix):
	private ArrayList<Function> sortedListOfFunctions;
	private Matrix proximityRelations;

	// The open cases for the proximity relations.
	private ArrayList<Tuple<Function>> openCases;

	// The Unification Problem:
	public Problem prob;
	public float lambda=1;
	
	//Solving status
	private int status=0; //0: nothing done, 1: pre-unification successful, 2: constraint Simplification successful
						  //-1: pre-unification failed, -2: constraint Simplification failed

	/**
	 * Constructor for a UnificationProblem.
	 * @param left lhs of the equation.
	 * @param right rhs of the equation.
	 */
	public UnificationProblem(Element left, Element right) {
		this.setLeft(left);
		this.setRight(right);
		openCases = new ArrayList<Tuple<Function>>();
		prob = new Problem(new Tuple<Element>(left,right));
	}

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
	 * Get the number of open cases.
	 * @return the number of open Cases.
	 */
	public int getNumberOfOpenCases() {
		return openCases.size();
	}

	/**
	 * Close an open Case.
	 * @param t the tuple to remove from the list. Note: (a,b) will also be removed, if the
	 * method is called with (b,a).
	 * @param p the value to set. Must be in [0,1].
	 * @return boolean indicating if the case could have been closed.
	 */
	public boolean CloseCase(Tuple<Function> t, float p) {
		if(p<0||p>1) return false;
		if(openCases.contains(t)) {
			
			proximityRelations.addRelation(t.getFirst(), t.getSecond(), p);
			
			openCases.remove(t);			
			return true;
		}
		Tuple<Function> rev = new Tuple<Function>(t.getSecond(),t.getFirst());
		if(openCases.contains(rev)) {
			proximityRelations.addRelation(rev.getFirst(), rev.getSecond(), p);

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
		return sortedListOfFunctions.size();
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
	 * @param lambda float value, to calculate the lambda-cut.
	 * @return a list of proximity relations
	 */
	/*public ArrayList<Tuple<Function>> getProximityRelations(float lambda){
		ArrayList<Tuple<Function>> ret = new ArrayList<Tuple<Function>>();
		for(int i=0;i<sortedListOfFunctions.size()-1;i++) {
			for(int j=i+1;j<sortedListOfFunctions.size();j++) {
				if(proximityRelations.getAt
						(new Tuple<Integer>(i,j))>=lambda) {
					Function f = sortedListOfFunctions.get(i);
					Function g = sortedListOfFunctions.get(j);
					Tuple<Function> addMe = new Tuple<Function>(f,g);
					ret.add(addMe);
				}
			}
		}
		return ret;
	}*/

	/**
	 * @param proximityRelations the proximity relations to set, represented as a matrix.
	 * Note: If the matrix is not a proximity relations Matrix (i.e. all values are in
	 * [0,1], and all values on the main diagonal are 1) the matrix will be set to the
	 * corresponding unity matrix. Also, function symbols with different arity have to be
	 * set to have proximity 0.
	 */
	// TODO überarbeiten
	public void setProximityRelations(Matrix proximityRelations) {
		/*boolean error= false;
		if(!proximityRelations.isPM()) {
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
			this.proximityRelations= new Matrix(this.getNumberOfFunctions());
			return;
		}*/


		this.proximityRelations = proximityRelations;

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s="";
		s+=left.toFullString()+" =?" + right.toFullString();
		s+=System.lineSeparator();
		s+="Lambda = "+lambda;
		s+=" and proximity relations : ";
		s+= proximityRelations.toString(lambda);
		return s;
	}
	
	/**
	 * Solves the next algorithm. 
	 * @return True, if it was successful and False otherwise.
	 */
	public boolean solveNext() {
				
		if(status==0) {
			if(Algorithms.preUnification(this)) {
				status = 1;
			}
			else {
				status = -1;
				return false;
			}
		}
		
		if(status == 1) {
			if(Algorithms.constraintSimplification(prob,proximityRelations,lambda)){
				status = 2;
				return true;
			}
			else {
				status = -2;
				return false;
			}
		}
		
		return false;
	}
 
	/**
	 * Generates the output depending on the status. 
	 * @return Returns the output string.
	 */
	public String resultString() {		
		String ret = "";
		
		switch(status) {
			case -2:
				ret = "Problem not solveable! Constraint Simplification failed!";
				break;
			case -1:
				ret = "Problem not solveable! pre-Unification failed!";
				break;
			case 0:
				ret = this.toString();
				break;
			case 1:
				ret = this.toString();
				ret += System.lineSeparator();
				ret += "sigma = {";				
				for(Tuple<Element> t : this.prob.sigma) {
					ret+=t.getFirst().toFullString() + " -> ";
					ret+=t.getSecond().toFullString() + ",";
				}
				ret = ret.substring(0,ret.length()-1)+"}";
				
				break;
			case 2:
				Problem p = this.prob;
				int i = 1;
				do {
					ret += "psi"+i+" = [";
					for(Map.Entry<String, ArrayList<Element>> m : p.psi.entrySet()) {
						ret += m.getKey() + " -> {";
						for(Element e : m.getValue()) {
							ret += e.getName() + ",";
						}
						ret = ret.substring(0,ret.length()-1) + "},";					
					}					
					ret = ret.substring(0,ret.length()-1)+"],";
					ret += System.lineSeparator();
					
					p=p.branch;
					i++;
				}while(p.branch != null);
				
				ret += "sigma = {";
				
				for(Tuple<Element> t : this.prob.sigma) {
					ret+=t.getFirst().toString() + " -> ";
					ret+=t.getSecond().toString() + ",";
				}
				ret = ret.substring(0,ret.length()-1)+"}";
				
				break;
			default:
				ret = "Unknown Error!";
		}
		
		return ret;
	}

	/**
	 * Return the set P.
	 * @return the set P.
	 */
	public ArrayList<Tuple<Element>> getP() {
		return prob.p;
	}

	/**
	 * Set the set P.
	 * @param p the set P to set.
	 */
	public void setP(ArrayList<Tuple<Element>> p) {
		this.prob.p = p;
	}


}
