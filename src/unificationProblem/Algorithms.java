/**
 * 
 */
package unificationProblem;

import java.util.ArrayList;

import elements.Element;
import elements.Variable;
import tool.Tuple;

/**
 * This class is stores the needed Algorithms as static functions.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 *
 */
public class Algorithms {

	public static boolean preUnification(Problem prob) {

		ArrayList<Tuple<Element>> unificationProblem = prob.p;

		while ((unificationProblem.size())!=0) {
			if(trivial(unificationProblem.get(0))) {
				unificationProblem.remove(0);
				continue;
			}
			
			// Clash
			// Decompose
			
		}


		return false;
	}

	// The Pre-Unification Rules:

	private static boolean trivial(Tuple<Element> t) {
		if(t.getFirst() instanceof Variable && t.getSecond() instanceof Variable)
			if(t.getFirst().equals(t.getSecond())) return true;
		return false;
	}
	
	private static void decomposition() {
		
	}


}
