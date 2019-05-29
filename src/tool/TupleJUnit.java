/**
 * 
 */
package tool;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Cases for Tuple.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0

 */
class TupleJUnit {

	/**
	 * @throws java.lang.Exception ignored
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception ignored
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link tool.Tuple#Tuple(java.lang.Object, java.lang.Object)}, 
	 * {@link tool.Tuple#getFirst()}, {@link tool.Tuple#getSecond()},
	 * {@link tool.Tuple#setFirst(java.lang.Object)} and
	 *  {@link tool.Tuple#setSecond(java.lang.Object)}.
	 */
	@Test
	void testTuple() {
		
		Tuple<Integer> t = new Tuple<Integer>(-1,1);
		assertEquals(Integer.valueOf(-1),t.getFirst());
		assertEquals(Integer.valueOf(1),t.getSecond());
		t.setFirst(5);
		assertEquals(Integer.valueOf(5),t.getFirst());
		t.setSecond(-5);
		assertEquals(Integer.valueOf(-5),t.getSecond());
		
		Tuple<String> s = new Tuple<String>("Hello ","World!");
		assertEquals("Hello ",s.getFirst());
		assertEquals("World!",s.getSecond());
		s.setFirst("Greetings ");
		s.setSecond("User!");
		assertEquals("Greetings ",s.getFirst());
		assertEquals("User!",s.getSecond());
	}



}
