package unificationProblem;

import java.util.List;

import elements.Tupel;

/**
*
* The class Problm handles the unification problem sets.
* P - contains unification problem to be solved
* C - set of neighborhood constraint
* sigma - set of pre unifiers
* psi - set of name-class mapping
*
* @author  Jan-Michael Holzinger & Sophie Hofmanninger
* @version 1.0
* 
*/
public class Problem {
public List<Tupel> p;
public List<Tupel> c;
public List<Tupel> sigma;
public List<Tupel> psi;
public List<Tupel> r;
}
