/**
 * 
 */
package unificationProblem;

import java.util.ArrayList;

import elements.Element;
import elements.Function;
import elements.Name;
import elements.Variable;
import tool.Matrix;
import tool.Tuple;

/**
 * This class is stores the needed Algorithms as static functions.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 *
 */

// TODO Allow injection of StringBuffer etc.
public final class Algorithms {


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

				// Vars Only
				boolean onlyVars = true;
				int i=1;
				while (i<unificationProblem.size() && onlyVars) {
					Tuple<Element> current =unificationProblem.get(i);
					onlyVars = (current.getFirst() instanceof Variable &&
							current.getSecond() instanceof Variable);
					i++;
				}

				if(onlyVars) {
					// TODO probably better use a copy,...
					prob.sigma.add(unificationProblem.get(0));
					unificationProblem.remove(0);
					varsOnly(var,unificationProblem);
					continue;
				}

				/* Delay:
				 * The first tuple is a tuple of variables, but they are neither the same,
				 * nor are all other tuples of type (var,var). So this tuple
				 * is placed at the end of the list, so other tuples can be dealt with before.
				 */
				unificationProblem.remove(0);
				unificationProblem.add(t);
				continue;
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
				continue;
			}

			if(t.getFirst() instanceof Variable && !(t.getSecond() instanceof Variable)) {

				// Occur Check
				if(occurs(t)) {
					return false;
				}

				// TODO
				// Var. Elimination


			}

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

	/**
	 * The rule (Occ)
	 * @param t The tuple to check.
	 * @return {@code boolean} true iff the first occurs in the second.
	 */
	private static boolean occurs(Tuple<Element> t) {
		return t.getSecond().occurs(t.getFirst());
	}



	/**
	 * The rule (VO)
	 * @param var the tuple to unify.
	 * @param u the unification problem.
	 */
	private static void varsOnly(Tuple<Variable> var, ArrayList<Tuple<Element>> u) {
		for(Tuple<Element> t:u) {
			Variable f;
			if((f= (Variable)t.getFirst()).equals(var.getFirst()))
				f.mapsto(var.getSecond());
			if((f= (Variable)t.getSecond()).equals(var.getFirst()))
				f.mapsto(var.getSecond());
		}
	}


	/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * +                                                                           +
	 * +                                   ALG 2                                   + 
	 * +                                                                           +
	 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
	 */
    
	/**
	 * The constraint simplification algorithm transforms constraint configurations, 
	 * exhaustively appling special rules.
	 * @param prob The problem to solve.
	 * @param proxR This is the proximity relation matrix.
	 * @return True, if the constraints can be simplified and otherwise false.
	 */
	public static boolean constraintSimplification(Problem prob,Matrix proxR) {
		
		ArrayList<Tuple<Element>> constraintProblem = prob.c;
		Tuple<Element> t;
		
		while(!constraintProblem.isEmpty()) {
			t=constraintProblem.get(0);
			
			// FFS
			if((t.getFirst() instanceof Function) && (t.getSecond() instanceof Function)) {
				if(ffs((Function)t.getFirst(),(Function)t.getSecond(),proxR,prob.lambda)) {
					constraintProblem.remove(0);
					continue;
				}
				else {
					return false;
				}				
			}
			
			//NFS
			if((t.getFirst() instanceof Name) && (t.getSecond() instanceof Function)) {
				//TODO NFS
			}
			
			if((t.getFirst() instanceof Function) && (t.getSecond() instanceof Name)) {
				//TODO FSN
			}
			
			// NN1 and NN2
			if((t.getFirst() instanceof Name) && (t.getSecond() instanceof Name)) {
				if(isMemberOfPsi((Name)t.getFirst(),prob.psi)) {
					if(nn1((Name)t.getFirst(),(Name)t.getSecond(),prob.psi, prob.branch)) {
						constraintProblem.remove(0);
						continue;
					}
					else {
						return false;
					}
				}
				else {
					if(nn1((Name)t.getSecond(),(Name)t.getFirst(),prob.psi, prob.branch)) {
						constraintProblem.remove(0);
						continue;						
					}
					else {
						return false;
					}
				}
			}
		}
		
		return true;
	}

	/**
	 * This method describes the rule (FFS).
	 * @param f1 function 1
	 * @param f2 function 2
	 * @param r Relation matrix
	 * @param lambda lower bound of relations
	 * @return Returns true if method worked successful, false otherwise.
	 */ 
	private static boolean ffs(Function f1, Function f2, Matrix r, float lambda) {
		
		float relation = r.getRef(new Tuple<>(f1,f2));
		
		if(relation >= lambda) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * This method describes the rule (NFS).
	 * @param n1 name 1
	 * @param f2 function 2
	 * @param psi name class mapping
	 * @return Returns true if method worked successful, false otherwise.
	 */
	private static boolean nfs(Name n1, Function f2, ArrayList<Tuple<Element>> psi) {
	
		//TODO
		
		return false;
	}
	
	/**
	 * This method describes the rule (NN1).
	 * @param n1 name 1
	 * @param n2 name 2
	 * @param psi name class mapping
	 * @param branch Branch if a splitting happens.
	 * @return Returns true if method worked successful, false otherwise.
	 */
	private static boolean nn1(Name n1, Name n2, ArrayList<Tuple<Element>> psi, Problem branch) {
		
		if(isMemberOfPsi(n1,psi)) {
			//TODO
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * This method checks if name is member of psi.
	 * @param n name to check
	 * @param psi name class mapping
	 * @return Returns true if name is member of psi.
	 */
	private static boolean isMemberOfPsi(Name n, ArrayList<Tuple<Element>> psi) {
		
		for(Tuple<Element> t : psi) {
			if(t.getFirst().getName() == n.getName()) {
				return true;
			}
		}
		return false;
	}
	
}
