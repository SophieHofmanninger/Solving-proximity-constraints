/**
 * 
 */
package unificationProblem;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import elements.Function;
import elements.Variable;

/**
 * Test Cases for UnificationProblem.
 * Further testing also done by testing the Interfaces.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
class UnificationProblemJUnit {

	UnificationProblem unif;
	UnificationProblem unifClone;
	UnificationProblem unifModClone;
	/**
	 * @throws java.lang.Exception ignored
	 */
	@BeforeEach
	void setUp() throws Exception {
		Function left = new Function("f");
		left.addArgument(new Variable("x"));
		left.addArgument(new Function("a"));
		
		Function right = new Function("g");
		Function h =new Function("h");
		h.addArgument(new Function("b"));
		right.addArgument(h);
		right.addArgument(new Variable("y"));
		
		unif = new UnificationProblem(left,right);
		unifClone=unif.clone();
		unifModClone = unif.clone();
		unifModClone.setLambda(0.5f);
	}

	/**
	 * @throws java.lang.Exception ignored
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test equals().
	 */
	@Test
	void testEquals() {
		assertTrue(unif.equals(unifClone));
		assertFalse(unif.equals(unifModClone));
	}
	
	/**
	 * Test hasCode().
	 */
	@Test
	void testHashCode() {
		assertEquals(unif.hashCode(),unifClone.hashCode());
		assertFalse(unif.hashCode()==unifModClone.hashCode());
	}

	
	
}
