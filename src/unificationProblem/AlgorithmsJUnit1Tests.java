package unificationProblem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test Cases for Pre-Unification.
 * @author Jan-Michael Holzinger
 * @version 1.0
 */
class AlgorithmsJUnit1Tests {

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
	 * Test method for {@link unificationProblem.Algorithms#preUnification(unificationProblem.UnificationProblem)}.
	 * Tests "Example 4".
	 */
	@Test
	void testPreUnification1() {
		String s="p(x,y,x)";
		String t="q(f(a),g(d),y)";
		unif= InputParser.parse(s+" =? "+t).get(0);

		System.out.println("Before:");
		System.out.println("P = " + unif.getP().toString());
		System.out.println("C = " + unif.getC().toString());
		System.out.println("s = " + unif.getSigma().toString());

		boolean result=Algorithms.preUnification(unif);
		assertTrue(result);

	}
	/**
	 * Test method for {@link unificationProblem.Algorithms#preUnification(unificationProblem.UnificationProblem)}.
	 * Tests Example 5.
	 */
	@Test
	void testPreUnification2() {
		String s="p(x,x)";
		String t="q(f(y,y),f(a,c))";
		unif= InputParser.parse(s+" =? "+t).get(0);

		System.out.println("Before:");
		System.out.println("P = " + unif.getP().toString());
		System.out.println("C = " + unif.getC().toString());
		System.out.println("s = " + unif.getSigma().toString());

		boolean result=Algorithms.preUnification(unif);
		assertTrue(result);

	}

	/**
	 * Test method for {@link unificationProblem.Algorithms#preUnification(unificationProblem.UnificationProblem)}.
	 * Tests FAIL from (Occ).
	 */
	@Test
	void testPreUnification3() {
		String s="p(f(x))";
		String t="q(x)";
		unif= InputParser.parse(s+" =? "+t).get(0);

		System.out.println("Before:");
		System.out.println("P = " + unif.getP().toString());
		System.out.println("C = " + unif.getC().toString());
		System.out.println("s = " + unif.getSigma().toString());

		boolean result=Algorithms.preUnification(unif);
		assertFalse(result);

	}

	/**
	 * Test method for {@link unificationProblem.Algorithms#preUnification(unificationProblem.UnificationProblem)}.
	 * Tests FAIL from (Cla). (Simple occurrence)
	 */
	@Test
	void testPreUnification4() {
		String s="p(f(x))";
		String t="q(a,y)";
		unif= InputParser.parse(s+" =? "+t).get(0);

		System.out.println("Before:");
		System.out.println("P = " + unif.getP().toString());
		System.out.println("C = " + unif.getC().toString());
		System.out.println("s = " + unif.getSigma().toString());

		boolean result=Algorithms.preUnification(unif);
		assertFalse(result);

	}



	/**
	 * Test method for {@link unificationProblem.Algorithms#preUnification(unificationProblem.UnificationProblem, java.lang.StringBuffer)}.
	 * @throws FileNotFoundException ignored
	 */
	@Test
	void testPreUnification5() throws FileNotFoundException {
		String s="p(x,y,x)";
		String t="q(f(a),g(d),y)";
		unif= InputParser.parse(s+" =? "+t).get(0);
		StringBuffer step=new StringBuffer();

		System.out.println("Before:");
		System.out.println("P = " + unif.getP().toString());
		System.out.println("C = " + unif.getC().toString());
		System.out.println("s = " + unif.getSigma().toString());

		boolean result=Algorithms.preUnification(unif,step);
		System.out.println(step);
		assertTrue(result);

	}

	/**
	 * Test method for {@link unificationProblem.Algorithms#preUnification(unificationProblem.UnificationProblem)}.
	 * Fail from (Occ). (Real cycle)
	 */
	@Test
	void testPreUnification6() {
		String s="f(x,y,z)";
		String t="g(h(z),i(x),j(y))";
		unif= InputParser.parse(s+" =? "+t).get(0);

		System.out.println("Before:");
		System.out.println("P = " + unif.getP().toString());
		System.out.println("C = " + unif.getC().toString());
		System.out.println("s = " + unif.getSigma().toString());

		StringBuffer step=new StringBuffer();
		
		boolean result=Algorithms.preUnification(unif,step);
		System.out.println(step);
		assertFalse(result);

		
	}
	
	/**
	 * Test method for {@link unificationProblem.Algorithms#preUnification(unificationProblem.UnificationProblem)}.
	 * Fail from (Occ). (Real cycle)
	 */
	@Test
	void testPreUnificationPre1() {
		String s="p(x,z)";
		String t="q(f(b),f(x))";
		unif= InputParser.parse(s+" =? "+t).get(0);

		System.out.println("Before:");
		System.out.println("P = " + unif.getP().toString());
		System.out.println("C = " + unif.getC().toString());
		System.out.println("s = " + unif.getSigma().toString());

		StringBuffer step=new StringBuffer();
		
		boolean result=Algorithms.preUnification(unif,step);
		System.out.println(step);
		assertTrue(result);

		
	}
	
}
