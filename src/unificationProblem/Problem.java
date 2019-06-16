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
	private Map<String,ArrayList<Element>> psi;

	/**
	 * @return the psi
	 */
	public Map<String, ArrayList<Element>> getPsi() {
		return psi;
	}

	/**
	 * @param psi the psi to set
	 */
	public void setPsi(Map<String, ArrayList<Element>> psi) {
		this.psi = psi;
	}

	private Problem branch = null;

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
	 * @param first first Tuple to add to P.
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

	/**
	 * @return the branch
	 */
	public Problem getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(Problem branch) {
		this.branch = branch;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String sb= "";
		sb+=("P=" + p);
		sb+=System.lineSeparator();
		sb+=("C=" + c);
		sb+=System.lineSeparator();
		sb+=("sigma=" + sigma);
		sb+=System.lineSeparator();
		sb+=("psi=" + psi);
		return sb;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Problem clone(){
		Problem probcopy = new Problem();
		SPCSet pcopy = p.clone();
		SPCSet ccopy = c.clone();
		SPCSet sigmacopy = sigma.clone();
		Map<String,ArrayList<Element>> psicopy =
				new HashMap<String,ArrayList<Element>>();
		psicopy.putAll(psi);
		Problem branchcopy;
		if(branch==null) {
			branchcopy=null;
		}else {
			branchcopy = branch.clone();
		}
		probcopy.setP(pcopy);
		probcopy.setC(ccopy);
		probcopy.setSigma(sigmacopy);
		probcopy.setPsi(psicopy);
		probcopy.setBranch(branchcopy);

		return probcopy;

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Problem)) return false;
		Problem o = (Problem) obj;
		if(!(p.equals(o.p))) return false;
		if(!(c.equals(o.c))) return false;
		if(!(sigma.equals(o.sigma))) return false;
		if(!(psi.equals(psi))) return false;
		if(branch==null&&o.branch!=null) return false;
		if(branch!=null&&o.branch==null) return false;
		if(branch==null&&o.branch==null) return true;
		if(!(branch.equals(o.branch))) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hc=p.hashCode()+c.hashCode()+sigma.hashCode();
		hc+=psi.hashCode();
		if(branch!=null) {
			hc+=branch.hashCode();
		}
		return hc;
	}





}
