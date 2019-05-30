/**
 * 
 */
package unificationProblem;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Cases for InputParser.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
class InputParserTest {

	String s;
	String t;
	
	/**
	 * @throws java.lang.Exception ignored 
	 */
	@BeforeEach
	void setUp() throws Exception {
		s="p(x,y,x)";
		t="q(f(a),g(d),y)";
	}

	/**
	 * @throws java.lang.Exception ignored
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test normal input with "=?"
	 * Test method for {@link unificationProblem.InputParser#parse(java.lang.String)}.
	 */
	@Test
	void testParseNormal1() {
		
		String test1 =s+" =? "+t;
		
		
		UnificationProblem unif1 = InputParser.parse(test1).get(0);
		assertEquals(6,unif1.getNumberOfFunctions());
		assertEquals(s,unif1.getLeft().toString());
		assertEquals(3,unif1.getNumberOfOpenCases());
				
	}
	
	/**
	 * Test normal input with "="
	 * Test method for {@link unificationProblem.InputParser#parse(java.lang.String)}.
	 */
	@Test
	void testParseNormal2() {
		
		String test2=s+" = "+t;
		UnificationProblem unif2 = InputParser.parse(test2).get(0);
		assertEquals(6, unif2.getNumberOfFunctions());
		System.out.println(test2);
		System.out.println(unif2.getLeft().toString());
		System.out.println(unif2.getRight().toString());
					
		
	}
	
	/**
	 * Test normal input with more than 1 equation.
	 * Test method for {@link unificationProblem.InputParser#parse(java.lang.String)}.
	 */
	@Test
	void testParseNormal3() {
		
		String test3=s+" =? "+t+ ";" +s+" = "+t;
		ArrayList<UnificationProblem> twoProblems = InputParser.parse(test3);
		
					
		
	}
	
	

}
