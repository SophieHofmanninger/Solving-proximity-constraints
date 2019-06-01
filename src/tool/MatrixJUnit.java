/**
 * 
 */
package tool;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Cases for Matrix.
 * 
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
class MatrixJUnit {
	/*
	 * Matrix m;
	 *//**
		 * @throws java.lang.Exception ignored
		 */
	/*
	 * @BeforeEach void setUp() throws Exception {
	 * 
	 * }
	 * 
	 *//**
		 * @throws java.lang.Exception ignored
		 */
	/*
	 * @AfterEach void tearDown() throws Exception { }
	 * 
	 *//**
		 * Test method for {@link tool.Matrix#putAt(tool.Tuple, float)} and
		 * {@link tool.Matrix#getAt(tool.Tuple)}.
		 * 
		 * @throws java.lang.Exception ignored
		 */
	/*
	 * @Test void testPutGetAt() throws Exception{ m=new Matrix(8); Tuple<Integer>
	 * pos1 = new Tuple<Integer>(2,3); Tuple<Integer> pos2 = new
	 * Tuple<Integer>(3,2); m.putAt(pos1, 0.5f);
	 * 
	 * // Test Symmetry assertEquals(0.5f,m.getAt(pos1));
	 * assertEquals(0.5f,m.getAt(pos2));
	 * 
	 * // Test Main diagonal for(int i=0;i<8;i++) { assertEquals(1f,m.getAt(new
	 * Tuple<Integer>(i,i))); }
	 * 
	 * Test Errors: 1. PutErrors 1.1 Index smaller 0 1.2 Index greater size 1.3
	 * Indices equal 1.4 Value not in [0,1] 2. Get Errors 2.1 Index smaller 0 2.2
	 * Index greater size
	 * 
	 * Tuple<Integer> err11 = new Tuple<Integer>(-1,3);
	 * assertThrows(IndexOutOfBoundsException.class,()->m.putAt(err11, 0.3f));
	 * Tuple<Integer> err12 = new Tuple<Integer>(2,8);
	 * assertThrows(IndexOutOfBoundsException.class,()->m.putAt(err12, 0.4f));
	 * Tuple<Integer> err13 = new Tuple<Integer>(2,2);
	 * assertThrows(IndexOutOfBoundsException.class,()->m.putAt(err13, 0.5f));
	 * Tuple<Integer> err14 = new Tuple<Integer>(1,2);
	 * assertThrows(IllegalArgumentException.class,()->m.putAt(err14, 1.5f));
	 * Tuple<Integer> err15 = new Tuple<Integer>(1,-2);
	 * assertThrows(IndexOutOfBoundsException.class,()->m.getAt(err15));
	 * Tuple<Integer> err16 = new Tuple<Integer>(9,2);
	 * assertThrows(IndexOutOfBoundsException.class,()->m.getAt(err16));
	 * 
	 * }
	 * 
	 * 
	 *//**
		 * Test method for {@link tool.Matrix#isPM(tool.Matrix)}.
		 *//*
			 * @Test void testIsPM() { m = new Matrix(5); Tuple<Integer> pos1 = new
			 * Tuple<Integer>(0,1); Tuple<Integer> pos2 = new Tuple<Integer>(0,2);
			 * Tuple<Integer> pos3 = new Tuple<Integer>(0,3); Tuple<Integer> pos4 = new
			 * Tuple<Integer>(0,4); Tuple<Integer> pos5 = new Tuple<Integer>(1,2);
			 * Tuple<Integer> pos6 = new Tuple<Integer>(1,3); Tuple<Integer> pos7 = new
			 * Tuple<Integer>(1,4); Tuple<Integer> pos8 = new Tuple<Integer>(2,3);
			 * Tuple<Integer> pos9 = new Tuple<Integer>(2,4); Tuple<Integer> pos10 = new
			 * Tuple<Integer>(3,4); m.putAt(pos1, 0.11f); m.putAt(pos2, 0.22f);
			 * m.putAt(pos3, 0.33f); m.putAt(pos4, 0.44f); m.putAt(pos5, 0.55f);
			 * m.putAt(pos6, 0.66f); m.putAt(pos7, 0.77f); m.putAt(pos8, 0.88f);
			 * m.putAt(pos9, 0.99f); m.putAt(pos10, 1.0f); assertTrue(Matrix.isPM(m));
			 * 
			 * }
			 */

}
