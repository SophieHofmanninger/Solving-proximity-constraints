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

	@Test
	void testClone() {
		Variable x= new Variable("x");
		Variable y= new Variable("y");
		Variable w= new Variable();
		assertTrue(x.clone().equals(x));
	}

}
