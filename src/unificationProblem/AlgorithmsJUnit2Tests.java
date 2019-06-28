package unificationProblem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import elements.Element;
import elements.Function;
import tool.Matrix;
import tool.SPCSet;
import tool.Tuple;

/**
 * Test Cases for constraint simplification algorithm.
 * @author Sophie Hofmanninger
 * @version 1.0
 */
class AlgorithmsJUnit2Tests {

	UnificationProblem unif;

	/**
	 * @throws java.lang.Exception ignored
	 */
	@BeforeEach
	void setUp() throws Exception {

	}

	/**
	 * @throws java.lang.Exception ignored.
	 */
	@AfterEach
	void tearDown() throws Exception {
		System.out.println("After:");
		System.out.println("P = " + unif.getP().toString());
		System.out.println("C = " + unif.getC().toString());
		System.out.println("s = " + unif.getSigma().toString());
	}

	
	/**
	 * Test method for {@link unificationProblem.Algorithms#constraintSimplification(unificationProblem.UnificationProblem)}.
	 * Tests "Example 3".
	 **/
	@Test
	void testExample3() {
		
		String s="p(x,y,x)";
		String t="q(f(a),g(d),y)";
		unif= InputParser.parse(s+" =? "+t).get(0);

		// Crate Constraint Set
		SPCSet c = new SPCSet("=");
		c.add(new Tuple<Element>(new Function("p"),new Function("q")));
		c.add(new Tuple<Element>(new Function("N1",true),new Function("f")));
		c.add(new Tuple<Element>(new Function("N2",true),new Function("a")));
		c.add(new Tuple<Element>(new Function("N3",true),new Function("g")));
		c.add(new Tuple<Element>(new Function("N4",true),new Function("d")));
		c.add(new Tuple<Element>(new Function("N1",true),new Function("N3",true)));
		c.add(new Tuple<Element>(new Function("N2",true),new Function("N4",true)));

		//Create PSIs
		ArrayList<Element> temp;
		Map<String,ArrayList<Element>> phi1 = new HashMap<String,ArrayList<Element>>();	
		temp = new ArrayList<Element>();
		temp.add(new Function("f"));
		phi1.put("N1", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("b"));
		phi1.put("N2", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("f"));
		temp.add(new Function("g"));
		phi1.put("N3", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("c"));
		phi1.put("N4", temp);

		Map<String,ArrayList<Element>> phi2 = new HashMap<String,ArrayList<Element>>();	
		temp = new ArrayList<Element>();
		temp.add(new Function("f"));
		phi2.put("N1", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("b1"));
		phi2.put("N2", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("f"));
		temp.add(new Function("g"));
		phi2.put("N3", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("c1"));
		phi2.put("N4", temp);

		Map<String,ArrayList<Element>> phi3 = new HashMap<String,ArrayList<Element>>();	
		temp = new ArrayList<Element>();
		temp.add(new Function("g"));
		phi3.put("N1", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("b"));
		phi3.put("N2", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("f"));
		temp.add(new Function("g"));
		phi3.put("N3", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("c"));
		phi3.put("N4", temp);

		Map<String,ArrayList<Element>> phi4 = new HashMap<String,ArrayList<Element>>();	
		temp = new ArrayList<Element>();
		temp.add(new Function("g"));
		phi4.put("N1", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("b1"));
		phi4.put("N2", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("f"));
		temp.add(new Function("g"));
		phi4.put("N3", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("c1"));
		phi4.put("N4", temp);

		//Create Matrix		
		Matrix matrix = new Matrix();
		matrix.addRelation("a", "b", 0.7f);
		matrix.addRelation("b", "c", 0.7f);
		matrix.addRelation("c", "d", 0.7f);
		matrix.addRelation("a", "b1", 0.7f);
		matrix.addRelation("b1", "c1", 0.7f);
		matrix.addRelation("c1", "d", 0.7f);
		matrix.addRelation("f", "g", 0.7f);
		matrix.addRelation("p", "q", 0.7f);
		
		//set Unif
		unif.setProximityRelations(matrix);
		unif.setC(c);
		unif.setStatus(1);		
		unif.setLambda(0.5f);
		
		//test Algorithm
		assertTrue(unif.solveNext());
		
		//test PSIs
		assertTrue(hasPsi(unif.getProb(),phi1));
		assertTrue(hasPsi(unif.getProb(),phi2));
		assertTrue(hasPsi(unif.getProb(),phi3));
		assertTrue(hasPsi(unif.getProb(),phi4));

	}
	
	/**
	 * Test method for {@link unificationProblem.Algorithms#constraintSimplification(unificationProblem.UnificationProblem)}.
	 * Tests "Example 5".
	 **/
	@Test
	void testExample5() {
		
		String s="p(x,x)";
		String t="q(f(y,y),f(a,c))";
		unif= InputParser.parse(s+" =? "+t).get(0);

		// Crate Constraint Set
		SPCSet c = new SPCSet("=");
		c.add(new Tuple<Element>(new Function("p"),new Function("q")));
		c.add(new Tuple<Element>(new Function("N1",true),new Function("f")));
		c.add(new Tuple<Element>(new Function("N2",true),new Function("a")));
		c.add(new Tuple<Element>(new Function("N3",true),new Function("c")));
		c.add(new Tuple<Element>(new Function("M",true),new Function("N2",true)));
		c.add(new Tuple<Element>(new Function("N3",true),new Function("M",true)));

		//Create PSIs
		ArrayList<Element> temp;
		Map<String,ArrayList<Element>> phi1 = new HashMap<String,ArrayList<Element>>();	
		temp = new ArrayList<Element>();
		temp.add(new Function("f"));
		phi1.put("N1", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("a1"));
		phi1.put("N2", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("c1"));
		phi1.put("N3", temp);
		temp = new ArrayList<Element>();
		temp.add(new Function("b"));
		phi1.put("M", temp);

		//Create Matrix		
		Matrix matrix = new Matrix();
		matrix.addRelation("a", "a1", 0.7f);
		matrix.addRelation("a1", "b", 0.7f);
		matrix.addRelation("b", "c1", 0.7f);
		matrix.addRelation("c1", "c", 0.7f);
		matrix.addRelation("p", "q", 0.7f);
		
		//set Unif
		unif.setProximityRelations(matrix);
		unif.setC(c);
		unif.setStatus(1);		
		unif.setLambda(0.5f);
		
		//test Algorithm
		assertTrue(unif.solveNext());
		
		//test PSIs
		assertTrue(hasPsi(unif.getProb(),phi1));
		

	}
	
	/**
	 * Tests if phi occurs in the problem.
	 * @param p this is the problem.
	 * @param phi the phi to look for.
	 * @return True, if phi in problem. Otherwise false.
	 */
	private boolean hasPsi(Problem p, Map<String,ArrayList<Element>> phi) {

		Map<String,ArrayList<Element>> curPsi = p.getPhi();
		boolean ok = true;
		boolean eok = false;

		if(curPsi.size()==phi.size()) {
			for(Map.Entry<String, ArrayList<Element>> m : curPsi.entrySet()) {
				if(phi.containsKey(m.getKey())) {
				ArrayList<Element> list = phi.get(m.getKey());
					if(m.getValue().size() == list.size()) {
						for(Element e : m.getValue()) {
							eok = false;
							for(Element f : list) {
								if(e.getName().compareTo(f.getName()) == 0) {
									eok = true;
								}
							}
							if(!eok) {
								ok=false;
								break;
							}
						}
						if(!ok) {
							break;
						}

					}
					else {
						ok=false;
					}

				}
				else {
					ok=false;
					break;
				}

			}
		}
		else {
			ok=false;
		}

		if(ok == true) {
			return true;
		}

		if(p.getBranch() != null) {
			return hasPsi(p.getBranch(),phi);
		}
		else {
			return false;
		}
	}

}
