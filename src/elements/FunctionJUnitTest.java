package elements;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for Function.
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 *
 */
class FunctionJUnitTest {

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
	void testAddArgument() {
		Function f = new Function("f");
		Variable x= new Variable("x");
		assertEquals("hi", "ji");
	}

}
