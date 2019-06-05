package unificationProblem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import tool.Matrix;



/**
 * Test Cases for constraint simplification algorithm.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
class AlgorithmsJUnit2 {

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
		System.out.println("P = " + unif.prob.getP().toString());
		System.out.println("C = " + unif.prob.getC().toString());
		System.out.println("s = " + unif.prob.getSigma().toString());
	}

	
	/**
	 * Test method for {@link unificationProblem.Algorithms#constrainSimplification(unificationProblem.Problem, tool.Matrix, float)}.
	 * Tests "Example 3".
	 **/
	@Test
	void testConstraintSimplification() {
		String s="p(x,y,x)";
		String t="q(f(a),g(d),y)";
		unif= InputParser.parse(s+" =? "+t).get(0);
		Algorithms.preUnification(unif);
		System.out.println("\n" + "C = " + unif.prob.getC().toString());
		
		Matrix matrix = new Matrix();
		matrix.addRelation("a", "b", 0.7f);
		matrix.addRelation("b", "c", 0.7f);
		matrix.addRelation("c", "d", 0.7f);
		matrix.addRelation("a", "b1", 0.7f);
		matrix.addRelation("b1", "c1", 0.7f);
		matrix.addRelation("c1", "d", 0.7f);
		matrix.addRelation("f", "g", 0.7f);
		matrix.addRelation("p", "q", 0.7f);
		
		System.out.println("R =" + matrix.toString());
		
		boolean result=Algorithms.constraintSimplification(unif.prob, matrix, 0.5f);
		// TODO find the bugs
		System.out.println(unif.prob.getC().toString());
		System.out.println(unif.prob.getSigma().toString());
		assertTrue(result);
		
	}
	//*/

}
