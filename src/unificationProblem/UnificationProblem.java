/**
 * 
 */
package unificationProblem;

import java.util.ArrayList;
import java.util.Map;

import elements.Element;
import elements.Function;
import tool.Matrix;
import tool.SPCSet;
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
	private ArrayList<Function> listOfNecesaryFunctions;
	private Matrix proximityRelations;

	// The open cases for the proximity relations.
	private ArrayList<Tuple<Function>> openCases;

	// The Unification Problem:
	private Problem prob;
	private float lambda=1;

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
	 * Recalculate open cases and if there are open cases.
	 * @return true, if there are no open cases, otherwise false
	 */
	public boolean checkOpenCases() {
		this.openCases = this.proximityRelations.getOpenCases();
		if(this.openCases.size()!=0) {
			return true;
		}
		else {
			return false;
		}
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
	public boolean closeCase(Tuple<Function> t, float p) {
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
	 * Sets all open cases to a desired float value.
	 * @param p value for the open cases.
	 */
	public void setAllOpenCasesTo(float p) {
		if(this.checkOpenCases()) {
			ArrayList<Tuple<Function>> tempOC = new ArrayList<Tuple<Function>>(openCases);
			for(Tuple<Function> t : tempOC) {
				this.closeCase(t, p);
			}
		}
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
		return proximityRelations.getListOfFunctions().size();
	}

	/**
	 * @return the proximity relations, represented as a matrix.
	 */
	public Matrix getProximityRelations() {
		return proximityRelations;
	}

	/**
	 * Sets the proximity relations.
	 * @param proximityRelations the proximity relations to set, represented as a matrix.
	 * Note: If the matrix is not a proximity relations Matrix (i.e. all values are in
	 * [0,1], and all values on the main diagonal are 1) the matrix will be set to the
	 * corresponding unity matrix. Also, function symbols with different arity have to be
	 * set to have proximity 0.
	 */
	public void setProximityRelations(Matrix proximityRelations) {

		if(this.proximityRelations == null) {
			this.listOfNecesaryFunctions = proximityRelations.getListOfFunctions();
		}
		else {
			for(Function fn : this.listOfNecesaryFunctions) {
				proximityRelations.addRelation(fn, fn, 1.0f);
			}
		}
		
		this.proximityRelations = proximityRelations;
		this.openCases = proximityRelations.getOpenCases();

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
	 * Returns a String representation of the problem.
	 * @return a string representation.
	 */
	public String problemToString() {
		String s = "";
		s+=prob.toString();
		return s;
	}

	/**
	 * Solves the next algorithm. 
	 * @return True, if it was successful and False otherwise.
	 */
	public boolean solveNext() {
		StringBuffer dummy= new StringBuffer();
		return solveNext(dummy);
	}


	/**
	 * Solves the next algorithm. 
	 * @param steps StringBuffer to log the performed steps.
	 * @return True, if it was successful and False otherwise.
	 */
	public boolean solveNext(StringBuffer steps) {

		if(status==0) {
			if(Algorithms.preUnification(this,steps)) {
				status = 1;
				return true;
			}
			else {
				status = -1;
				return false;
			}
		}

		if(status == 1) {
			if(Algorithms.constraintSimplification(this,steps)){
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
			ret += "sigma = ";				
			ret += prob.getSigma().toString();

			break;
		case 2:
			Problem p = this.prob;
			int i = 1;
			do {
				if(i!=1) {
					p=p.branch;
				}
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
				i++;
			}while(p.branch != null);

			ret += "sigma = ";

			ret += p.getSigma().toString();

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
	public SPCSet getP() {
		return prob.getP();
	}

	/**
	 * Set the set P.
	 * @param p the set P to set.
	 */
	public void setP(SPCSet p) {
		prob.setP(p);
	}

	/**
	 * Return the set C.
	 * @return the set C.
	 */
	public SPCSet getC() {
		return prob.getC();
	}

	/**
	 * Set the set C.
	 * @param c the set C to set.
	 */
	public void setC(SPCSet c) {
		prob.setC(c);
	}

	/**
	 * Return the set Sigma.
	 * @return the set Sigma.
	 */
	public SPCSet getSigma() {
		return prob.getSigma();
	}

	/**
	 * Set the set Sigma.
	 * @param s the set Sigma to set.
	 */
	public void setSigma(SPCSet s) {
		prob.setSigma(s);
	}

	/**
	 * Returns the Problem.
	 * @return the problem.
	 */
	public Problem getProb() {
		return this.prob;
	}

	/**
	 * Returns Lambda.
	 * @return lambda.
	 */
	public float getLambda() {
		return this.lambda;
	}

	/**
	 * Sets the problem.
	 * @param prob the problem to set
	 */
	public void setProb(Problem prob) {
		this.prob = prob;
	}

	/**
	 * Sets lambda.
	 * @param lambda the lambda to set
	 */
	public void setLambda(float lambda) {
		this.lambda = lambda;
	}
	
	/**
	 * @return the current Status
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		if(status >= -2 && status <= 2) {
			this.status = status;
		}
	}
}
