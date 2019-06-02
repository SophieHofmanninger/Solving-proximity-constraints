package unificationProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import elements.Element;
import tool.PCSSet;
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

	public PCSSet p;
	public PCSSet c;
	public PCSSet sigma;
	public Map<String,ArrayList<Element>> psi;

	public Problem branch = null;
	
	/**
	 * Default Constructor.
	 */
	public Problem() {
		p = new PCSSet('p');
		c = new PCSSet('c');
		sigma=new PCSSet('s');
		psi=new HashMap<String,ArrayList<Element>>();
	}
	
	/**
	 * Constructor with Input.
	 * @param first The first Tuple to add to P.
	 */
	public Problem(Tuple<Element> first) {
		p = new PCSSet('p');
		p.add(first);
		c= new PCSSet('c');
		sigma=new PCSSet('s');
		psi=new HashMap<String,ArrayList<Element>>();
	}
}
