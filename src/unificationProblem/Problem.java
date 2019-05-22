package unificationProblem;

import java.util.ArrayList;
import elements.Element;
import tool.Tuple;

/**
 *
 * The class Problem handles the unification problem sets.
 * p - contains unification problem to be solved
 * c - set of neighborhood constraint
 * sigma - set of pre unifiers
 * psi - set of name-class mapping
 *
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 * 
 */
public class Problem {

	public ArrayList<Tuple<Element>> p;
	public ArrayList<Tuple<Element>> c;
	public ArrayList<Tuple<Element>> sigma;
	public ArrayList<Tuple<Element>> psi;

}
