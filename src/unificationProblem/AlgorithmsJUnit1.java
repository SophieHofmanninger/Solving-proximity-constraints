/**
 * 
 */
package unificationProblem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test Cases for Pre-Unification.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
class AlgorithmsJUnit1 {

	UnificationProblem unif;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		System.out.println("After:");
		System.out.println("P = " + unif.prob.getP().toString());
		System.out.println("C = " + unif.prob.getC().toString());
		System.out.println("s = " + unif.prob.getSigma().toString());
	}

	/**
	 * Test method for {@link unificationProblem.Algorithms#preUnification(unificationProblem.UnificationProblem)}.
	 */
	@Test
	void testPreUnification1() {
		String s="p(x,y,x)";
		String t="q(f(a),g(d),y)";
		unif= InputParser.parse(s+" =? "+t).get(0);
		
		System.out.println("Before:");
		System.out.println("P = " + unif.prob.getP().toString());
		System.out.println("C = " + unif.prob.getC().toString());
		System.out.println("s = " + unif.prob.getSigma().toString());
		
		boolean result=Algorithms.preUnification(unif);
		assertTrue(result);
		
	}
//	/**
//	 * Test method for {@link unificationProblem.Algorithms#preUnification(unificationProblem.UnificationProblem)}.
//	 */
//	@Test
//	void testPreUnification2() {
//		String s="p(x,x)";
//		String t="q(f(y,y),f(a,c))";
//		unif= InputParser.parse(s+" =? "+t).get(0);
//		
//		System.out.println("Before:");
//		System.out.println("P = " + unif.prob.getP().toString());
//		System.out.println("C = " + unif.prob.getC().toString());
//		System.out.println("s = " + unif.prob.getSigma().toString());
//		
//		boolean result=Algorithms.preUnification(unif);
//		assertTrue(result);
//		
//	}
}
