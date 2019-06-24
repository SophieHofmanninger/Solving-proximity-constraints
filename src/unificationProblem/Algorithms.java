/**
 * 
 */
package unificationProblem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

	private static int NEXT_BRANCH=1; 

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

	private Algorithms() {};

	/**
	 * Performs the Pre-Unification Algorithm 
	 *
	 * @param unif the Unification Problem
	 * @param steps StringBuffer to keep track of the performed steps.
	 * @return {@code false}, if there is no unifier.
	 */
	public static boolean preUnification(UnificationProblem unif, StringBuffer steps) {
		SPCSet currentProblem = unif.getP();
		if(currentProblem==null||currentProblem.size()==0) return true;
		while (currentProblem.size()!=0) {
			Tuple<Element> t = currentProblem.get(0);


			if(t.getFirst() instanceof Variable && t.getSecond() instanceof Variable) {
				Variable f = (Variable) t.getFirst();
				Variable s = (Variable) t.getSecond();

				// Trivial
				Tuple<Variable> var = new Tuple<Variable>(f,s);
				if(trivial(var)) {
					steps.append("(Tri), ");
					currentProblem.remove(0);
					continue;
				}

				// Vars Only
				boolean onlyVars = true;
				int i=1;
				while (i<currentProblem.size() && onlyVars) {
					Tuple<Element> current =currentProblem.get(i);
					onlyVars = (current.getFirst() instanceof Variable &&
							current.getSecond() instanceof Variable);
					i++;
				}

				if(onlyVars) {
					unif.getSigma().add(currentProblem.get(0));
					currentProblem.remove(0);
					steps.append("(VO), ");
					varsOnly(var,currentProblem);
					continue;
				}

				/* Delay:
				 * The first tuple is a tuple of variables, but they are neither the same,
				 * nor are all other tuples of type (var,var). So this tuple
				 * is placed at the end of the list, so other tuples can be dealt with before.
				 */
				currentProblem.remove(0);
				currentProblem.add(t);
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
				currentProblem.remove(0);
				currentProblem.addAll(0,probAndCons.getFirst());
				unif.getC().addAll(probAndCons.getSecond());
				continue;
			}



			if(!(t.getFirst() instanceof Variable) && t.getSecond() instanceof Variable) {

				// Orient
				steps.append("(Ori), ");
				Tuple<Element> oriented = orient(t);
				currentProblem.remove(0);
				currentProblem.add(0,oriented);
				continue;
			}

			if(t.getFirst() instanceof Variable && !(t.getSecond() instanceof Variable)) {

				// Occur Check 1
				if(occurs1(t)) {
					steps.append("(Occ1), ");
					return false;
				}

				// Occur Check 2

				SPCSet p= unif.getP().clone();
				p.remove(t);
				Variable x = (Variable) t.getFirst();
				for(Variable xi: t.getSecond().getVars()) {
					if(occurs2(x,xi,p)) {
						steps.append("(Occ2), ");
						return false;
					}
				}


				// Var. Elimination
				currentProblem.remove(0);
				steps.append("(VE), ");
				Tuple<Element> newTerm = varElim(t,currentProblem);
				currentProblem.add(0, newTerm);
				unif.getSigma().add(new Tuple<Element>(t.getFirst(),newTerm.getFirst()));
				continue;
			}

		}

		unif.getC().trim();

		if(steps.length()>1) {
			steps.delete(steps.length()-2, steps.length());
		}

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
		Element newName = (t.getSecond().rename());
		for(int i=0;i<problem.size();i++) {
			// direct matches:
			if(problem.get(i).getFirst().equals(t.getFirst())) {
				problem.get(i).setFirst(newName.clone());
			}
			if(problem.get(i).getSecond().equals(t.getFirst())) {
				problem.get(i).setSecond(newName.clone());
			}
			
			// matches within functions
			if(problem.get(i).getFirst() instanceof Function) {
				tryReplace((Function) problem.get(i).getFirst(),
						t.getFirst(),newName);
			}
			if(problem.get(i).getSecond() instanceof Function) {
				tryReplace((Function) problem.get(i).getSecond(),
						t.getFirst(),newName);
			}
		}


		return new Tuple<Element>(newName,t.getSecond());
	}

	/**
	 * Helper Function makes the checks for replacement and provides a copy.
	 * @param f The function to search for needed replacements.
	 * @param x The match.
	 * @param tprime the term to replace with.
	 */
	private static void tryReplace(Function f, Element x, Element tprime) {

		for(int i=0; i<f.getArguments().size();i++) {
			
			if(f.getArgument(i) instanceof Function) {
				tryReplace((Function)f.getArgument(i),x,tprime);
			}else {
				if(f.getArgument(i).equals(x)) {
					f.getArguments().set(i, tprime.clone());
				}
			}
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
	 * The rule (Occ) part 1.
	 * @param t The tuple to check.
	 * @return {@code boolean} true iff the first occurs in the second.
	 */
	private static boolean occurs1(Tuple<Element> t) {
		return t.getSecond().occurs(t.getFirst());
	}
	/**
	 * The rule (Occ) part 2.
	 * @param x The match.
	 * @param xi The tuples to consider.
	 * @param p the current set P.
	 * @return {@code boolean} true iff there is a (real) occurrence cycle.
	 *
	 */
	private static boolean occurs2(Variable x, Variable xi, SPCSet p) {
		for(int i=0;i<p.size();i++) {
			Tuple<Element> t = p.get(i);
			Element cf = t.getFirst();
			Element cs = t.getSecond();
			// consider only those who are v - t pairs
			if(cf instanceof Variable && cf.equals(xi)&&
					!(cs instanceof Variable)) {
				// direct match if x is contained.
				if(cs.occurs(x)) return true;
				// maybe just another step
				SPCSet pclone= p.clone();
				pclone.remove(t);
				for(Variable v: cs.getVars()) {
					if(occurs2(x,v,pclone)) {
						return true;
					}
				}
			}
			// other way round
			if(cs instanceof Variable && cs.equals(xi)&&
					!(cf instanceof Variable)) {
				// direct match if x is contained.
				if(cf.occurs(x)) return true;
				// maybe just another step
				SPCSet pclone= p.clone();
				pclone.remove(t);
				for(Variable v: cf.getVars()) {
					if(occurs2(x,v,pclone)) {
						return true;
					}
				}
			}


		}		
		return false;
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


	/**
	 * Calls the constraint simplification algorithm without logging.
	 * @param unif is the unification problem.
	 * @return true if the constraint algorithm is successful, otherwise false.
	 */
	public static boolean constraintSimplification(UnificationProblem unif) {
		StringBuffer dummy= new StringBuffer();
		return constraintSimplification(unif,dummy);
	}


	/**
	 * Calls the constraint simplification algorithm with logging.
	 * @param unif is the unification problem.
	 * @param steps StringBuffer where the steps are logged to.
	 * @return true if the constraint algorithm is successful, otherwise false.
	 */
	public static boolean constraintSimplification(UnificationProblem unif, StringBuffer steps) {
		Problem prob=unif.getProb();
		Matrix proxR=unif.getProximityRelations();
		float lambda=unif.getLambda();
		return constraintSimp(prob,proxR,lambda,steps,0);
	}

	/**
	 * The constraint simplification algorithm transforms constraint configurations, 
	 * exhaustively appling special rules.
	 * @param prob problem to solve.
	 * @param proxR is the proximity relation matrix.
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
		//NEXT_BRANCH = branch+1;

		while(!constraintProblem.isEmpty()&& !prob.getC().isEmpty()) {
			t=constraintProblem.get(0);

			// NN1 and NN2
			if((t.getFirst().isName()) && (t.getSecond().isName())) {
				branchStepsTemp = new StringBuffer();
				if(prob.getPsi().containsKey(t.getFirst().getName())) {
					steps.append("(NN1), ");
					if(nn1(t.getFirst(),t.getSecond(),prob, proxR, lambda, steps, branchStepsTemp)) {
						constraintProblem.remove(0);
						if(branchStepsTemp.length()>0) {
							constraintProblem = prob.getC();
							branchSteps.append(branchStepsTemp);
						}
						continue;
					}
					else {
						if(branchStepsTemp.length()>0) {
							branchSteps.append(branchStepsTemp);
						}
						error = true;
						break;
					}
				}
				else {
					steps.append("(NN2), (NN1), ");
					Element temp=t.getFirst();
					prob.getC().get(0).setFirst(t.getSecond());
					prob.getC().get(0).setSecond(temp);
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
				if(nfs(t.getFirst(),(Function)t.getSecond(),prob.getPsi(),proxR,lambda)) {
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
				if(nfs(t.getSecond(),(Function)t.getFirst(),prob.getPsi(),proxR,lambda)) {
					constraintProblem.remove(0);
					continue;
				}
				else {
					error = true;
					steps.append("(Fail2), ");
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
			if(prob.getBranch() != null) {
				prob.setP(prob.getBranch().getP());
				prob.setC(prob.getBranch().getC());
				prob.setSigma(prob.getBranch().getSigma());
				prob.setPsi(prob.getBranch().getPsi());
				prob.setBranch(prob.getBranch().getBranch());
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
	 * @param phi name class mapping
	 * @param r relation matrix
	 * @param lambda the lambda.
	 * @return  true if method worked successful, false otherwise.
	 */
	private static boolean nfs(Element n1, Function f2, Map<String,ArrayList<Element>> phi, Matrix r, float lambda) {

		ArrayList<Element> list;

		if(phi.containsKey(n1.getName())) {
			ArrayList<Element> old = phi.get(n1.getName());			
			list = r.getRelations(f2,lambda);

			list = intersection(old,list);

			if(list.isEmpty()) {
				return false;
			}
			else {
				phi.put(n1.getName(), list);
				return true;
			}

		}
		else {
			list = r.getRelations(f2,lambda);
			phi.put(n1.getName(), list);	
			return true;
		}

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

		if(cur.getPsi().containsKey(n1.getName())) { //psi.containsKey(n1.getName()) is same a isMemberPsi
			if(cur.getPsi().get(n1.getName()).size() > 1) {
				//branchen

				List<Problem> branches = new ArrayList<Problem>();
				int cBranches = cur.getPsi().get(n1.getName()).size();
				int nextBranch = NEXT_BRANCH;
				int lastBranch = NEXT_BRANCH + cBranches-1;
				boolean success = false;

				steps.delete(steps.length()-6, steps.length());
				steps.append("Create Branch ["+NEXT_BRANCH+" - "+lastBranch+"]), ");

				NEXT_BRANCH += cBranches;

				for(int i = 0; i<cBranches;i++) {
					Problem tmp = new Problem();
					tmp.setC(cur.getC().clone());
					tmp.setP(cur.getP());
					tmp.setSigma(cur.getSigma());
					tmp.setPsi(clonePsi(cur.getPsi()));
					tmp.getPsi().put(n1.getName(), new ArrayList<Element>());
					tmp.getPsi().get(n1.getName()).add(cur.getPsi().get(n1.getName()).get(i));
					StringBuffer tmpB = new StringBuffer();

					if(constraintSimp(tmp,r,lambda,tmpB,i+nextBranch)){
						branches.add(tmp);
						success = true;
					}

					branchSteps.append(tmpB);
				}

				for(Problem p : branches) {
					if(!cur.getC().isEmpty()) {
						cur.setC(p.getC());
						cur.setPsi(p.getPsi());
						if(p.getBranch()!=null) {
							appendBranch(cur,p.getBranch());
						}
					}
					else {
						appendBranch(cur,p);
					}
				}

				return success;
			}
			else {
				if(nfs(n2,new Function(cur.getPsi().get(n1.getName()).get(0).getName()),cur.getPsi(),r,lambda)) {
					return true;
				}
				else {
					steps.append("(Fail2), ");
					return false;
				}
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

				if(m.getName().compareTo(n.getName())==0 && ret.contains(m)==false) {
					ret.add(m);
				}

			}
		}

		return ret;
	}

	/**
	 * Clones the HashMap psi.
	 * @param map the psi to clone.
	 * @return a clone of psi.
	 */
	private static Map<String,ArrayList<Element>> clonePsi(Map<String, ArrayList<Element>> map){
		Map<String,ArrayList<Element>> ret = new HashMap<String,ArrayList<Element>>();

		for(Map.Entry<String, ArrayList<Element>> m : map.entrySet()) {
			ArrayList<Element> temp = new ArrayList<Element>();
			for(Element t : m.getValue()) {
				temp.add(t);
			}

			ret.put(m.getKey(), temp);

		}

		return ret;
	}

	/**
	 * Appends a branch to a Problem.
	 * If the Problem has already a branch, it appends it to the branch.
	 * @param origin the original Problem.
	 * @param branch the branch Problem to append.
	 */
	private static void appendBranch(Problem origin, Problem branch) {
		if(origin.getBranch()==null) {
			origin.setBranch(branch);
		}
		else {
			appendBranch(origin.getBranch(),branch);
		}
	}
}
