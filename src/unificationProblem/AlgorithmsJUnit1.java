/**
 * 
 */
package unificationProblem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import elements.Element;
import tool.Matrix;
import tool.Tuple;


/**
 * Test Cases for Pre-Unification.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
class AlgorithmsJUnit1 {

	UnificationProblem unif;
	ArrayList<Tuple<Element>> problem;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		String s="p(x,y,x)";
		String t="q(f(a),g(d),y)";
		unif= InputParser.parse(s+" =? "+t).get(0);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link unificationProblem.Algorithms#preUnification(unificationProblem.UnificationProblem)}.
	 */
	@Test
	void testPreUnification() {
		boolean result=Algorithms.preUnification(unif);
		System.out.println(unif.prob.p.toString());
		System.out.println(unif.prob.c.toString());
		System.out.println(unif.prob.sigma.toString());
		assertTrue(result);
		
	}
	
	
	/**
	 * Test method for {@link unificationProblem.Algorithms#constrainSimplification(unificationProblem.Problem, tool.Matrix, float)}.
	 */
	@Test
	void testConstraintSimplification() {
		Algorithms.preUnification(unif);
		System.out.println("\n" + "C = " + unif.prob.c.toString());
		
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
		System.out.println(unif.prob.c.toString());
		System.out.println(unif.prob.sigma.toString());
		assertTrue(result);
		
	}
	//*/

}
