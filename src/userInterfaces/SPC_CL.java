/**
 * 
 */
package userInterfaces;

import java.util.ArrayList;

import tool.Matrix;
import unificationProblem.Algorithms;
import unificationProblem.InputParser;
import unificationProblem.UnificationProblem;

/**
 * Use the Algorithm via the command line.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 *
 */
public class SPC_CL {

	/**
	 * @param args the Unification Problem as String; if empty
	 * the program will ask for it.
	 */
	// TODO maybe change to solveNext()
	public static void main(String[] args) {
		String input="";
		if(args.length==0) {
			// TODO Get String
		}else {
			input=args[0];
		}

		// TODO checkInput

		ArrayList<UnificationProblem> listOfProblems = InputParser.parse(input);
		for(UnificationProblem unif:listOfProblems) {
			// TODO ask for logging
			boolean log=true;
			// TODO Get matrix
			Matrix mat = new Matrix();
			unif.setProximityRelations(mat);
			// TODO Get lambda
			float lambda = 0.5f;
			if(log) {
				StringBuffer logBuffer=new StringBuffer();
				if(!Algorithms.preUnification(unif, logBuffer)) {
					// TODO OUTPUT FALSE1
				}else {
					if(!Algorithms.constraintSimplification(unif, logBuffer)){
						// TODO OUTPUT FALSE2
					}else {
						// TODO OUTPUT SUCCESS
						// TODO ASK FOR DIFFERENT LAMBDA
					}
				}
			}else {
				if(!Algorithms.preUnification(unif)) {
					// TODO OUTPUT FALSE1
				}else {
					if(!Algorithms.constraintSimplification(unif)){
						// TODO OUTPUT FALSE2
					}else {
						// TODO OUTPUT SUCCESS
						// TODO ASK FOR DIFFERENT LAMBDA
					}
				}
			}

		}
	}
}
