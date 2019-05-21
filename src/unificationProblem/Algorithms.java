/**
 * 
 */
package unificationProblem;

import java.util.ArrayList;

import elements.Element;
import elements.Function;
import elements.Variable;
import tool.Tuple;

/**
 * This class is stores the needed Algorithms as static functions.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 *
 */
public class Algorithms {


	/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * +                                                                           +
	 * +                                   ALG 1                                   + 
	 * +                                                                           +
	 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
	 */
	public static boolean preUnification(Problem prob) {

		ArrayList<Tuple<Element>> unificationProblem = prob.p;

		while ((unificationProblem.size())!=0) {
			Tuple<Element> t = unificationProblem.get(0);


			if(t.getFirst() instanceof Variable && t.getSecond() instanceof Variable) {
				Variable f = (Variable) t.getFirst();
				Variable s = (Variable) t.getSecond();

				// Trivial
				Tuple<Variable> var = new Tuple<Variable>(f,s);
				if(trivial(var)) {
					unificationProblem.remove(0);
					continue;
				}
			}

			if(t.getFirst() instanceof Function && t.getSecond() instanceof Function) {
				Function f = (Function) t.getFirst();
				Function s = (Function) t.getSecond();

				// Clash
				Tuple<Function> fun = new Tuple<Function>(f,s);
				if(clash(fun)) {
					return false;
				}

				// Decompose
				Tuple<ArrayList<Tuple<Element>>> probAndCons = decomposition(fun);
				unificationProblem.remove(0);
				unificationProblem.addAll(0,probAndCons.getFirst());
				prob.c.addAll(0,probAndCons.getSecond());
				continue;
			}



			if(!(t.getFirst() instanceof Variable) && t.getSecond() instanceof Variable) {

				// Orient
				Tuple<Element> oriented = orient(t);
				unificationProblem.remove(0);
				unificationProblem.add(0,oriented);
			}


			// Occur Check
			// Var. Elimination



			// Vars Only
		}


		return false;
	}



	// The Pre-Unification Rules:

	/**
	 * The rule (Tri)
	 * @param t The tuple to check.
	 * @return {@code boolean} true iff the rule can be applied.
	 */
	private static boolean trivial(Tuple<Variable> t) {
		if(t.getFirst().equals(t.getSecond())) return true;
		return false;
	}

	/**
	 * The rule (Dec)
	 * @param t The tuple of functions to decompose.
	 * @return The new Unification subproblems to add and a new constraint.
	 */
	private static Tuple<ArrayList<Tuple<Element>>> decomposition(Tuple<Function> t) {
		Function f = t.getFirst();
		Function s = t.getSecond();

		ArrayList<Tuple<Element>> probList = new ArrayList<Tuple<Element>>();
		for(int i=0;i<f.arity();i++) {
			Tuple<Element> p = new Tuple<Element>(f.arguments.get(i),s.arguments.get(0));
			probList.add(p);
		}
		ArrayList<Tuple<Element>> conList = new ArrayList<Tuple<Element>>();
		conList.add(new Tuple<Element>(f,s));
		return new Tuple<ArrayList<Tuple<Element>>>(probList,conList);

	}


	/**
	 * The rule (Ori)
	 * @param t The tuple to orient.
	 * @return oriented tuple, where the Variable is the first element,
	 *  and the other term is the second.
	 */
	private static Tuple<Element> orient(Tuple<Element> t){
		Tuple<Element> oriented = new Tuple<Element>(t.getSecond(),t.getFirst());
		return oriented;
	}


	/**
	 * The rule (Cla)
	 * @param t The tuple of functions to check.
	 * @return {@code boolean} true iff the aritys do not match.
	 */
	private static boolean clash(Tuple<Function> t) {
		if(t.getFirst().arity()!=t.getSecond().arity()) return true;
		return false;
	}


	/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * +                                                                           +
	 * +                                   ALG 2                                   + 
	 * +                                                                           +
	 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
	 */



}
