/**
 * 
 */
package unificationProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import elements.Element;
import elements.Function;
import elements.Variable;
import tool.Matrix;
import tool.SPCSet;
import tool.Tuple;

/**
 * This class is stores the needed Algorithms as static functions.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 *
 */


public final class Algorithms {

	private static int NEXT_BRANCH=0; 

	/* +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	 * +                                                                           +
	 * +                                   ALG 1                                   + 
	 * +                                                                           +
	 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ 
	 */

	/**
	 * Performs the Pre-Unification Algorithm 
	 *
	 * @param unif the Unification Problem
	 * @return {@code false}, if there is no unifier.
	 */
	public static boolean preUnification(UnificationProblem unif) {
		StringBuffer dummy= new StringBuffer();
		return preUnification(unif,dummy);
	}


	/**
	 * Performs the Pre-Unification Algorithm 
	 *
	 * @param unif the Unification Problem
	 * @param steps StringBuffer to keep track of the performed steps.
	 * @return {@code false}, if there is no unifier.
	 */
	public static boolean preUnification(UnificationProblem unif, StringBuffer steps) {
		SPCSet problem = unif.getP();
		if(problem==null) return false;
		while (problem.size()!=0) {
			Tuple<Element> t = problem.get(0);


			if(t.getFirst() instanceof Variable && t.getSecond() instanceof Variable) {
				Variable f = (Variable) t.getFirst();
				Variable s = (Variable) t.getSecond();

				// Trivial
				Tuple<Variable> var = new Tuple<Variable>(f,s);
				if(trivial(var)) {
					steps.append("(Tri), ");
					problem.remove(0);
					continue;
				}

				// Vars Only
				boolean onlyVars = true;
				int i=1;
				while (i<problem.size() && onlyVars) {
					Tuple<Element> current =problem.get(i);
					onlyVars = (current.getFirst() instanceof Variable &&
							current.getSecond() instanceof Variable);
					i++;
				}

				if(onlyVars) {
					unif.getSigma().add(problem.get(0));
					problem.remove(0);
					steps.append("(VO), ");
					varsOnly(var,problem);
					continue;
				}

				/* Delay:
				 * The first tuple is a tuple of variables, but they are neither the same,
				 * nor are all other tuples of type (var,var). So this tuple
				 * is placed at the end of the list, so other tuples can be dealt with before.
				 */
				problem.remove(0);
				problem.add(t);
				continue;
			}

			if(t.getFirst() instanceof Function && t.getSecond() instanceof Function) {
				Function f = (Function) t.getFirst();
				Function s = (Function) t.getSecond();

				// Clash
				Tuple<Function> fun = new Tuple<Function>(f,s);
				if(clash(fun)) {
					steps.append("(Cla), ");
					return false;
				}

				// Decompose
				steps.append("(Dec), ");
				Tuple<ArrayList<Tuple<Element>>> probAndCons = decomposition(fun);
				problem.remove(0);
				problem.addAll(0,probAndCons.getFirst());
				unif.getC().addAll(probAndCons.getSecond());
				continue;
			}



			if(!(t.getFirst() instanceof Variable) && t.getSecond() instanceof Variable) {

				// Orient
				steps.append("(Ori), ");
				Tuple<Element> oriented = orient(t);
				problem.remove(0);
				problem.add(0,oriented);
				continue;
			}

			if(t.getFirst() instanceof Variable && !(t.getSecond() instanceof Variable)) {

				// Occur Check
				if(occurs(t)) {
					steps.append("(Occ), ");
					return false;
				}

				// Var. Elimination
				problem.remove(0);
				steps.append("(VE), ");
				Tuple<Element> newTerm = varElim(t,problem);
				problem.add(0, newTerm);
				unif.getSigma().add(new Tuple<Element>(t.getFirst(),newTerm.getFirst()));
				continue;
			}

		}

		unif.getC().trim();


		return true;
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
			Tuple<Element> p = new Tuple<Element>(f.getArguments().get(i),
					s.getArguments().get(i));
			probList.add(p);
		}
		ArrayList<Tuple<Element>> conList = new ArrayList<Tuple<Element>>();
		conList.add(new Tuple<Element>(f,s));
		return new Tuple<ArrayList<Tuple<Element>>>(probList,conList);

	}

	/**
	 * The Rule (Var.Elim.)
	 * @param t The tuple of Variable and Term to rename.
	 * @param problem the unification Problem.
	 * @return The renamed variable and term.
	 */
	private static Tuple<Element> varElim
	(Tuple<Element> t, SPCSet problem) {
		Element first = (t.getSecond().rename());
		for(int i=0;i<problem.size();i++) {
			problem.get(i).setFirst
			(tryReplace(problem.get(i).getFirst(),t.getFirst(),first));
			problem.get(i).setSecond
			(tryReplace(problem.get(i).getSecond(),t.getFirst(),first));
		}


		return new Tuple<Element>(first,t.getSecond());
	}

	/**
	 * Helper Function makes the checks for replacement and provides a copy.
	 * @param e The Element (Variable) to replace.
	 * @param x The Match.
	 * @param tprime the term to replace with.
	 * @return the replacement/original term.
	 */
	private static Element tryReplace(Element e, Element x, Element tprime) {
		if(e instanceof Variable && e.equals(x)) {
			return Variable.replace(tprime); 
		}else {
			return e;
		}
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
	 * @param problem the unification problem.
	 */
	private static void varsOnly(Tuple<Variable> var, SPCSet problem) {
		for(int i=0;i<problem.size();i++) {
			Tuple<Element> t=problem.get(i);
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


	public static boolean constraintSimplification(UnificationProblem unif) {
		StringBuffer dummy= new StringBuffer();
		return constraintSimplification(unif,dummy);
	}


	public static boolean constraintSimplification(UnificationProblem unif, StringBuffer steps) {
		Problem prob=unif.getProb();
		Matrix proxR=unif.getProximityRelations();
		float lambda=unif.getLambda();
		return constraintSimp(prob,proxR,lambda,steps,0);
	}
	// TODO JAVADOC
	/**
	 * The constraint simplification algorithm transforms constraint configurations, 
	 * exhaustively appling special rules.
	 * @param prob The problem to solve.
	 * @param proxR This is the proximity relation matrix.
	 * @param lambda the lambda.
	 * @param steps StringBuffer to keep track of the performed steps.
	 * @param branch int the branch.
	 * @return True, if the constraints can be simplified and otherwise false.
	 */
	private static boolean constraintSimp(Problem prob,Matrix proxR, float lambda, StringBuffer steps,int branch) {

		StringBuffer branchSteps = new StringBuffer();
		StringBuffer branchStepsTemp;
		SPCSet constraintProblem = prob.getC();
		Tuple<Element> t;
		boolean error = false;
		NEXT_BRANCH = branch+1;

		while(!constraintProblem.isEmpty()) {
			t=constraintProblem.get(0);

			// NN1 and NN2
			if((t.getFirst().isName()) && (t.getSecond().isName())) {
				branchStepsTemp = new StringBuffer();
				if(prob.psi.containsKey(t.getFirst().getName())) {
					steps.append("(NN1), ");
					if(nn1(t.getFirst(),t.getSecond(),prob, proxR, lambda, steps, branchStepsTemp)) {
						constraintProblem.remove(0);
						if(branchStepsTemp.length()>0) {
							branchSteps.append(branchStepsTemp);
						}
						continue;
					}
					else {
						error = true;
						break;
					}
				}
				else {
					steps.append("(NN2), (NN1), ");
					if(nn1(t.getSecond(),t.getFirst(),prob, proxR, lambda, steps, branchStepsTemp)) {
						constraintProblem.remove(0);
						if(branchStepsTemp.length()>0) {
							branchSteps.append(branchStepsTemp);
						}
						continue;						
					}
					else {
						error = true;
						break;
					}
				}
				
			}

			// FFS
			if(t.getFirst().isName()== false && t.getSecond().isName()==false ) {
				steps.append("(FFS), ");
				if(ffs((Function)t.getFirst(),(Function)t.getSecond(),proxR,lambda,steps)) {
					constraintProblem.remove(0);
					continue;
				}
				else {
					error = true;
					break;
				}				
			}

			//NFS
			if(t.getFirst().isName() && t.getSecond().isName() ==false) {
				steps.append("(NFS), ");
				if(nfs(t.getFirst(),(Function)t.getSecond(),prob.psi,proxR,lambda)) {
					constraintProblem.remove(0);
					continue;
				}
				else {
					error = true;
					break;
				}
			}

			//FSN
			if(t.getFirst().isName()==false && t.getSecond().isName()) {
				steps.append("(FSN), ");
				if(nfs(t.getSecond(),(Function)t.getFirst(),prob.psi,proxR,lambda)) {
					constraintProblem.remove(0);
					continue;
				}
				else {
					error = true;
					break;
				}
			}
		}

		steps.delete(steps.length()-2, steps.length()); //remove last ,
		steps.append(System.lineSeparator());
		
		if(branchSteps.length() > 0) {
			steps.append(branchSteps);
		}
		
		if(error) {
			steps.insert(0, branch+"[Failed!]: ");
			if(prob.branch != null) {
				prob.setP(prob.branch.getP());
				prob.setC(prob.branch.getC());
				prob.setSigma(prob.branch.getSigma());
				prob.psi = prob.branch.psi;
				prob.branch = prob.branch.branch;
				return true;
			}
			else {
				return false;
			}
		}
		else {
			if(branch!=0) {
				steps.insert(0, branch+": ");
			}
			return true;	
		}

	}

	/**
	 * This method describes the rule (FFS).
	 * @param f1 function 1
	 * @param f2 function 2
	 * @param r Relation matrix
	 * @param lambda lower bound of relations
	 * @param steps StringBuffer to keep track of the performed steps.
	 * @return  true if method worked successful, false otherwise.
	 */ 
	private static boolean ffs(Function f1, Function f2, Matrix r, float lambda, StringBuffer steps) {

		float relation = r.getRelation(f1, f2);

		if(relation >= lambda) {
			return true;
		}
		else {
			steps.append("(Fail1), ");
			return false;
		}
	}

	/**
	 * This method describes the rule (NFS).
	 * @param n1 name 1
	 * @param f2 function 2
	 * @param psi name class mapping
	 * @param r relation matrix
	 * @param lambda the lambda.
	 * @return  true if method worked successful, false otherwise.
	 */
	private static boolean nfs(Element n1, Function f2, Map<String,ArrayList<Element>> psi, Matrix r, float lambda) {

		ArrayList<Element> list;

		if(psi.containsKey(n1.getName())) {
			ArrayList<Element> old = psi.get(n1.getName());			
			list = r.getRelations(f2,lambda);

			list = intersection(old,list);

			if(list.isEmpty()) {
				return false;
			}
			else {
				psi.put(n1.getName(), list);
				return true;
			}

		}
		else {
			list = r.getRelations(f2,lambda);
			psi.put(n1.getName(), list);	
			return true;
		}

		//return false;
	}

	/**
	 * This method describes the rule (NN1).
	 * @param n1 name 1
	 * @param n2 name 2
	 * @param cur current problem
	 * @param r relation matrix
	 * @param lambda lambda of the relation
	 * @param steps StringBuffer to keep track of the performed steps.
	 * @param branchSteps StringBuffer to keep track of the performed steps
	 * in the branch.
	 * @return  true if method worked successful, false otherwise.
	 */
	private static boolean nn1(Element n1, Element n2, Problem cur, Matrix r, float lambda, StringBuffer steps, StringBuffer branchSteps) {

		if(cur.psi.containsKey(n1.getName())) { //psi.containsKey(n1.getName()) is same a isMemberPsi
			if(cur.psi.get(n1.getName()).size() > 1) {
				//branchen
				
				steps.delete(steps.length()-3, steps.length());
				steps.append(" - Create Branch ["+NEXT_BRANCH+"]), ");
	
				Problem nextBranch = cur.branch;
				cur.branch = new Problem();
				cur.branch.branch=nextBranch;

				cur.branch.setC(cur.getC().copy());
				cur.branch.setP(cur.getP());
				cur.branch.setSigma(cur.getSigma());

				cur.branch.psi = new HashMap<String,ArrayList<Element>>();

				for(Map.Entry<String, ArrayList<Element>> m : cur.psi.entrySet()) {
					ArrayList<Element> temp = new ArrayList<Element>();
					for(Element t : m.getValue()) {
						temp.add(t);
					}

					cur.branch.psi.put(m.getKey(), temp);

				}
				
				cur.psi.put(n1.getName(), new ArrayList<Element>());
				cur.psi.get(n1.getName()).add(cur.branch.psi.get(n1.getName()).get(0));
				cur.branch.psi.get(n1.getName()).remove(0);
				
				if(!constraintSimp(cur.branch,r,lambda,branchSteps,NEXT_BRANCH)) {
					cur.branch = null;
				}

				
			}

			if(nfs(n2,new Function(cur.psi.get(n1.getName()).get(0).getName()),cur.psi,r,lambda)) {
				return true;
			}
			else {
				steps.append("(Fail2), ");
				return false;
			}
		}
		else {
			return false;
		}
	}

	/**
	 * Generates the intersection of two lists.
	 * @param list1 the first list.
	 * @param list2 the second list.
	 * @return the intersection list of list1 and list2.
	 */
	private static ArrayList<Element> intersection(ArrayList<Element> list1, ArrayList<Element> list2){
		ArrayList<Element> ret = new ArrayList<Element>();

		for(Element m : list1) {
			for(Element n : list2) {

				if(m.getName().compareTo(n.getName())==0) {
					ret.add(m);
				}

			}
		}

		return ret;
	}

}
