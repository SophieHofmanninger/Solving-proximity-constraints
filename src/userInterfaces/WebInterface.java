package userInterfaces;

import java.util.ArrayList;

import elements.Function;
import unificationProblem.InputParser;
import unificationProblem.UnificationProblem;

/**
*
* The class WebInterface handles methods for the web application.
* 
* @author Sophie Hofmanninger
* @version 1.0
* 
*/
public class WebInterface {

	public static WebInterface onlyWI = new WebInterface();
	
	private UnificationProblem unif;
	private String inputString = "";
	private boolean error = false;
	private int[] additionalArity;
	private boolean silent;
	private boolean isSet=false;
	private float lambda;
	
	/**
	 * Constructor.
	 */
	private WebInterface() {}
	
	/**
	 * Sets lambda.
	 * @param l value for lambda.
	 */
	public void setLambda(float l) {
		this.lambda = l;
	}
	
	/**
	 * Should the process be silent?
	 * @param a True if the process should be silent and otherwise false.
	 */
	public void setSilent(boolean a) {
		this.silent = a;
	}
	
	/**
	 * Set the unification problem.
	 * @param input the problem as a string.
	 */
	public void setProblems(String input) {
		this.inputString = input;
		try {
			this.unif = InputParser.parse(input).get(0);
		}
		catch(IndexOutOfBoundsException e) {
			this.inputString = e.getMessage();
			error=true;
		}
	}
	
	 /**
	  * Checks if there is an error.
	  * @return a boolean value. True if there is an error and false otherwise.
	  */
	public boolean getError() {
		return error;
	}
	
	/**
	 * Method to get the original input String
	 * @return the saved input String
	 */
	public String getFunString() {
		return inputString;
	}
	
	/**
	 * Method to get the maximal arity.
	 * @return the maximal arity.
	 */
	public int getMaxArity() {
		int ret = 0;
		
		for(Function f : unif.getProximityRelations().getListOfFunctions()) {
			if(ret<f.arity()) {
				ret = f.arity();
			}
		}
		
		if(!isSet) {
			isSet=true;
			additionalArity = new int[ret+1];
		}
		
		return ret;
	}
	
	/**
	 * Method to add additional Functions at a specific arity.
	 * @param arity arity of the additional Functions.
	 * @param additional the number of functions.
	 */
	public void setAddArity(int arity, int additional) {
		additionalArity[arity] = additional;
	}
	
	/**
	 * Method to get the count of existing functions at a specific arity.
	 * @param arity arity of the existing functions.
	 * @return the number of functions.
	 */
	public int getFunCountAtArity(int arity) {
		int ret = 0;
		for(Function f : unif.getProximityRelations().getListOfFunctions()) {
			if(f.arity()==arity) {
				ret++;
			}
		}
		return ret;
	}
	
	/**
	 * Method to get the count of additional functions at a specific arity.
	 * @param arity arity of the existing functions.
	 * @return the number of functions.
	 */
	public int getAddFunAtArity(int arity) {
		return this.additionalArity[arity];
	}
	
	/**
	 * Method to get the count of all functions at a specific arity.
	 * @param arity arity of the functions.
	 * @return the number of function.
	 */
	public int getAllCountAtArity(int arity) {
		return this.getFunCountAtArity(arity)+this.getAddFunAtArity(arity);
	}
	
	/**
	 * To get all functions with specific arity.
	 * @param arity number of specific arity.
	 * @return a list of all function with the desired arity.
	 */
	public ArrayList<String> getFunAtArity(int arity){
		ArrayList<String> ret = new ArrayList<String>();
		
		for(Function f : unif.getProximityRelations().getListOfFunctions()) {
			if(f.arity()==arity) {
				ret.add(f.getName());
			}
		}
		
		return ret;
	}
	
	/**
	 * Adds the relation for two functions.
	 * @param s1 name of the fist function.
	 * @param s2 name of the second function.
	 * @param value value of the relation (between [0,1]).
	 */
	public void addRelation(String s1, String s2, float value) {
		unif.getProximityRelations().addRelation(s1, s2, value);
	}
	
	/**
	 * Closes the open cases.
	 */
	public void closeOpenCases() {
		if(!unif.checkOpenCases()) {
			unif.setAllOpenCasesTo(0.0f);
		}
	}
	
	/**
	 * Gives us the solution of the unification problem by using the Pre-Unification and Constraint Simplification algorithm.
	 * @return the solution as a string.
	 */
	public String getSolution() {

		unif.setLambda(lambda);
		
		String ret = "<h2>Solution:</h2>";
		StringBuffer steps1 = new StringBuffer();
		StringBuffer steps2 = new StringBuffer();
		
		if(unif.solveNext(steps1)) {
			unif.solveNext(steps2);
			ret += "<textarea style=\"resize: both;\" readonly>";
			ret += unif.resultString();
			ret += "</textarea>";
			if(!silent) {	
				ret += "<h2>Stepts PU:</h2>";
				ret += "<textarea style=\"resize: both;\" readonly>";
				ret += steps1;
				ret += "</textarea>";
				ret += "<h2>Stepts CS:</h2>";
				ret += "<textarea style=\"resize: both;\" readonly>";
				ret += steps2;
				ret += "</textarea>";
			}
		}
		else {
			ret += "<textarea style=\"resize: both;\" readonly>";
			ret += unif.resultString();
			ret += "</textarea>";
			if(!silent) {
				ret += "<h2>Stepts PU:</h2>";
				ret += "<textarea style=\"resize: both;\" readonly>";
				ret += steps1;
				ret += "</textarea>";
			}
		}
		return ret;
	}
	
	/**
	 * Resets the web interface.
	 */
	public void reset() {
		WebInterface.onlyWI = new WebInterface();
	}
}
