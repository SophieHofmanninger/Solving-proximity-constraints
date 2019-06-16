/**
 * 
 */
package tool;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit tests for SimpleInputChecker.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
class SimpleInputCheckerJUnit {
	InputChecker inpC;
	

	
	/**
	 * @throws java.lang.Exception ignored
	 */
	@BeforeEach
	void setUp() throws Exception {
		inpC = new SimpleInputChecker();
	}

	/**
	 * @throws java.lang.Exception ignored
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	
	/**
	 * Test for null objects.
	 * Test method for {@link tool.SimpleInputChecker#check(java.lang.String)}.
	 */
	@Test
	void testNull() {
		assertFalse(inpC.check(null));
	}
	
	/**
	 * Test for regular input.
	 * Test method for {@link tool.SimpleInputChecker#check(java.lang.String)}.
	 */
	@Test
	void testRegular() {
		assertTrue(inpC.check("f(x,y,g(h(k),x))=?l(x,x,g(y),y)"));
	}
	
	/**
	 * Test for missing parenthesis.
	 * Test method for {@link tool.SimpleInputChecker#check(java.lang.String)}.
	 */
	@Test
	void testParenthesis() {
		assertFalse(inpC.check("f(x,y,g h(k),x))=?l(x,x,g(y),y)"));
	}
	/**
	 * Test for missing argument case 1. ",)"
	 * Test method for {@link tool.SimpleInputChecker#check(java.lang.String)}.
	 */
	@Test
	void testArgument1() {
		assertFalse(inpC.check("f(x,y,g(h(k),))=?l(x,x,g(y),y)"));
	}
	
	/**
	 * Test for missing argument case 2. "(,"
	 * Test method for {@link tool.SimpleInputChecker#check(java.lang.String)}.
	 */
	@Test
	void testArgument2() {
		assertFalse(inpC.check("f(,y,g(h(k),x))=?l(x,x,g(y),y)"));
	}
	
	/**
	 * Test for missing argument case 3. ",,"
	 * Test method for {@link tool.SimpleInputChecker#check(java.lang.String)}.
	 */
	@Test
	void testArgument3() {
		assertFalse(inpC.check("f(x,y,g(h(k),x))=?l(x,x,,y)"));
	}
	
}
