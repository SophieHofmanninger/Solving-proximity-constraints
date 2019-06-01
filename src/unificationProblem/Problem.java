package unificationProblem;

import java.util.ArrayList;
import java.util.Map;
import elements.Element;
import tool.Tuple;

/**
 *
 * The class Problem handles the unification problem sets.
 * p - contains unification problem to be solved
 * c - set of neighborhood constraint
 * sigma - set of pre unifiers
 * psi - set of name-class mapping
 * branch - contains branch of the problem when psi splits
 *
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 * 
 */
public class Problem {

	public ArrayList<Tuple<Element>> p;
	public ArrayList<Tuple<Element>> c;
	public ArrayList<Tuple<Element>> sigma;
	public Map<String,ArrayList<Element>> psi;

	public Problem branch = null;
}
