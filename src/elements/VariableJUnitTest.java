package elements;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Variable.
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
class VariableJUnitTest {

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
	}

	/**
	 * Test for method clone.
	 */
	@Test
	void testClone() {
		Variable x= new Variable("x");
		Variable y= new Variable("y");
		assertTrue(x.clone().equals(x));
		assertFalse(y.clone().equals(x));
	}
	
	/**
	 * Test for method mapsto.
	 */
	@Test
	void testMapsto() {
		Variable x= new Variable("x");
		Variable z= new Variable("z");
		x.mapsto(z);
		assertEquals("z",x.toString());
	}
	
	/**
	 * Test for method replace.
	 */
	@Test
	void testReplace() {
		Variable x= new Variable("x");
		Variable z= new Variable("z");
		x.replace(z);
		assertTrue(x.equals(z));
	}
}
