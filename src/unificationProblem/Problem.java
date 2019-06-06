package unificationProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import elements.Element;
import tool.SPCSet;
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

	private SPCSet p;
	private SPCSet c;
	private SPCSet sigma;
	public Map<String,ArrayList<Element>> psi;

	public Problem branch = null;
	
	/**
	 * Default Constructor.
	 */
	public Problem() {
		p = new SPCSet("~?");
		c = new SPCSet("=?");
		sigma=new SPCSet("->");
		psi=new HashMap<String,ArrayList<Element>>();
	}
	
	/**
	 * Constructor with Input.
	 * @param first The first Tuple to add to P.
	 */
	public Problem(Tuple<Element> first) {
		p = new SPCSet("~?");
		p.add(first);
		c= new SPCSet("=?");
		sigma=new SPCSet("->");
		psi=new HashMap<String,ArrayList<Element>>();
	}

	/**
	 * @return the p
	 */
	public SPCSet getP() {
		return p;
	}

	/**
	 * @param p the p to set
	 */
	public void setP(SPCSet p) {
		this.p = p;
	}

	/**
	 * @return the c
	 */
	public SPCSet getC() {
		return c;
	}

	/**
	 * @param c the c to set
	 */
	public void setC(SPCSet c) {
		this.c = c;
	}

	/**
	 * @return the sigma
	 */
	public SPCSet getSigma() {
		return sigma;
	}

	/**
	 * @param sigma the sigma to set
	 */
	public void setSigma(SPCSet sigma) {
		this.sigma = sigma;
	}
	
	
}
