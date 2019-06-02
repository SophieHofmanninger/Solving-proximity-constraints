/**
 * 
 */
package tool;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import elements.Element;
import elements.Function;

/**
 * Test Cases for Matrix.
 * 
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
class MatrixJUnit {
	
	 Matrix m;
	 
	 /**
	  * @throws java.lang.Exception ignored
	  */
	
	  @BeforeEach 
	  void setUp() throws Exception {
		  m= new Matrix();
	  }
	  
	 /**
	  * @throws java.lang.Exception ignored
	  */
	
	  @AfterEach 
	  void tearDown() throws Exception { }
	  
	  /**
	   * Test method for {@link tool.Matrix#addRelation(elemets.Element,elemets.Element, float)} and 
	   * {@link tool.Matrix#addRelation(string, string, float)} and
	   * {@link tool.Matrix#getRelation(elemets.Element,elemets.Element)} and 
	   * {@link tool.Matrix#getRelation(string , string)}.
	   */
	  @Test 
	  void testAddRelation() {
		  Function f =new Function("f");
		  Function g =new Function("g");
		  Function h =new Function("h");
		  
		  m.addRelation(f, g, 0.11f);
		  m.addRelation(h, g, 0.22f);
		  
		  assertEquals(0.11f,m.getRelation(f, g));
		  assertEquals(0f,m.getRelation(f,h));
		  
		// Test Symmetry 
		  assertEquals(0.11f,m.getRelation(f, g));
		  assertEquals(0.11f,m.getRelation(g,f));
		  
		// Test Main diagonal 
		  for(Element i : m.getAllElements()) { 
			  assertEquals(1f,m.getRelation(i,i)); 
		  }
	  }
	  
	
	  
	 /**
		 * Test method for {@link tool.Matrix#isPM(tool.Matrix)}.
		 */
			/*  @Test void testIsPM() { 
				  m = new Matrix(5); 
				  Tuple<Integer> pos1 = new Tuple<Integer>(0,1); 
				  Tuple<Integer> pos2 = new Tuple<Integer>(0,2);
				  Tuple<Integer> pos3 = new Tuple<Integer>(0,3); 
				  Tuple<Integer> pos4 = new Tuple<Integer>(0,4); 
				  Tuple<Integer> pos5 = new Tuple<Integer>(1,2);
				  Tuple<Integer> pos6 = new Tuple<Integer>(1,3);
				  Tuple<Integer> pos7 = new Tuple<Integer>(1,4); 
				  Tuple<Integer> pos8 = new Tuple<Integer>(2,3);
				  Tuple<Integer> pos9 = new Tuple<Integer>(2,4); 
				  Tuple<Integer> pos10 = new Tuple<Integer>(3,4); 
				  m.putAt(pos1, 0.11f); 
				  m.putAt(pos2, 0.22f);
				  m.putAt(pos3, 0.33f); 
				  m.putAt(pos4, 0.44f); 
				  m.putAt(pos5, 0.55f);
				  m.putAt(pos6, 0.66f); 
				  m.putAt(pos7, 0.77f); 
				  m.putAt(pos8, 0.88f);
				  m.putAt(pos9, 0.99f); 
				  m.putAt(pos10, 1.0f); 
				  assertTrue(Matrix.isPM(m));
			  
			  }*/
			 

}
