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
import tool.Tuple;

import static org.easymock.EasyMock.*;


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

}
