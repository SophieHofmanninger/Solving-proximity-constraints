/**
 * 
 */
package unificationProblem;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tool.Matrix;

/**
 * Test Cases for InputParser.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
class InputParserJUnit {

	String s;
	String t;
	String u;
	String v;
	
	/**
	 * @throws java.lang.Exception ignored 
	 */
	@BeforeEach
	void setUp() throws Exception {
		s="p(x,y,x)";
		t="q(f(a),g(d),y)";
		u="p(x,x)";
		v="q(f(y,y),f(a,c))";
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
	void testParseNormal1_1() {
		
		String test1 =s+" =? "+t;
		
		
		UnificationProblem unif1 = InputParser.parse(test1).get(0);
		assertEquals(6,unif1.getNumberOfFunctions());
		assertEquals(s,unif1.getLeft().toFullString());
		assertEquals(3,unif1.getNumberOfOpenCases());
				
	}
	
	/**
	 * Test normal input with "=?"
	 * Test method for {@link unificationProblem.InputParser#parse(java.lang.String)}.
	 */
	@Test
	void testParseNormal1_2() {
		
		String test1 =u+"=?"+v;
		
		
		UnificationProblem unif1 = InputParser.parse(test1).get(0);
		assertEquals(5,unif1.getNumberOfFunctions());
		assertEquals(u,unif1.getLeft().toFullString());
		assertEquals(4,unif1.getNumberOfOpenCases());		
	}
	
	/**
	 * Test normal input with "="
	 * Test method for {@link unificationProblem.InputParser#parse(java.lang.String)}.
	 */
	@Test
	void testParseNormal2_1() {
		
		String test2=s+" = "+t;
		UnificationProblem unif2 = InputParser.parse(test2).get(0);
		assertEquals(6, unif2.getNumberOfFunctions());
	}
	
	/**
	 * Test normal input with more than 1 equation.
	 * Test method for {@link unificationProblem.InputParser#parse(java.lang.String)}.
	 */
	@Test
	void testParseNormal3() {
		
		String test3=s+" =? "+t+ ";" +s+" = "+t;
		ArrayList<UnificationProblem> twoProblems = InputParser.parse(test3);
		assertEquals(2,twoProblems.size());
		UnificationProblem unif1 =  twoProblems.get(0);
		UnificationProblem unif2 = twoProblems.get(1);
		assertTrue(unif1.getLeft().equals(unif2.getLeft()));
		assertTrue(unif1.getRight().equals(unif2.getRight()));
		
	}
	
	/**
	 * Test method for {@link unificationProblem.InputParser#parseMatrixFromString(java.lang.String)}.
	 */
	@Test
	void testParseMatrixFromString() {
		String test="";
		test += "f g h" + System.lineSeparator();
		test += "f 1 0.4 0.7" + System.lineSeparator();
		test += "g 0.4 1 0.5" + System.lineSeparator();
		test += "h 0.7 0.5 1";
		
		
		try {
			Matrix m = InputParser.parseMatrixFromString(test);
			assertTrue(m.getRelation("f", "g")==0.4f);
			assertTrue(m.getRelation("f", "h")==0.7f);
			assertTrue(m.getRelation("h", "g")==0.5f);
		} catch (IOException e) {
			//fail();
		}

	}
	/**
	 * Test method for {@link unificationProblem.InputParser#parseMatrixFromString(java.lang.String)}.
	 */
	@Test
	void testParseMatrixFromString2() {
		String test="";
		test += "f g h a b" + System.lineSeparator();
		test += "f 1 0.4 0.7 0.1 0.2" + System.lineSeparator();
		test += "g 0.4 1 0.5" + System.lineSeparator();
		test += "a 0.7 0.5 0.3 1 0.5";
		
			
		try {
			Matrix m = InputParser.parseMatrixFromString(test);
			assertTrue(m.getRelation("f", "f")==1f);
			assertTrue(m.getRelation("f", "h")==0.7f);
			assertTrue(m.getRelation("a", "b")==0.5f);
		} catch (IOException e) {
			//fail();
		}
		
	}
	

}
