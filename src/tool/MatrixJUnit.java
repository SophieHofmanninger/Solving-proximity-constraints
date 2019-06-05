/**
 * 
 */
package tool;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import elements.Constant;
import elements.Element;
import elements.Function;

/**
 * Test Cases for Matrix.
 * 
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 2.0
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
	  void testAddGetRelation() {
		  Element f =new Function("f");
		  Element g =new Function("g");
		  Element h =new Function("h");
		  
		  m.addRelation(f, g, 0.11f);
		  m.addRelation(h, g, 0.22f);
		  
		  assertEquals(0.11f,m.getRelation(f, g));
		  assertEquals(0f,m.getRelation(f,h));
		  
		//String
		  m.addRelation("a", "b", 0.7f);
		  m.addRelation("a", "c", 0.7f);
		  m.addRelation("d", "c", 0.6f);
		  m.addRelation("e", "c", -0.6f);
		  
		  assertEquals(0.7f,m.getRelation("b", "a"));
		  assertEquals(0.7f,m.getRelation("a", "c"));
		  assertEquals(0.6f,m.getRelation("c","d"));
		  assertEquals(0f,m.getRelation("c","b"));
		  assertEquals(-0.6f,m.getRelation("e","c"));
		  
		// Test Symmetry 
		  assertEquals(0.11f,m.getRelation(f, g));
		  assertEquals(0.11f,m.getRelation(g,f));
		  
		// Test Main diagonal 
		  for(Element i : m.getAllElements()) { 
			  assertEquals(1f,m.getRelation(i,i)); 
		  }
	  }
	  
	  /**
	   * Test method for {@link tool.Matrix#agetRelations(String, float)}.
	   */
	  @Test 
	  void testGetRelations() {
		  Element f =new Function("f");
		  Element g =new Function("g");
		  Element h =new Function("h");
		  Element j =new Function("j");
		  Element a =new Constant("a");
		  Element b =new Constant("b");
		  
		  ArrayList<Element> list=new ArrayList<Element>();
		  ArrayList<Element> test;
		  list.add(f);
		  
		  //only f to itself
		  m.addRelation(f, g, 0.11f);
		  assertEquals(list,m.getRelations(f, 0.3f));
		  
		  //(h,g) not depend on f
		  m.addRelation(h, g, 0.22f);
		  assertEquals(list,m.getRelations(f, 0.3f));
		  
		  m.addRelation(h, f, 0.33f);		  
		  m.addRelation(f,j, 0.6f);
		  m.addRelation(a,b, 0.7f);
		  m.addRelation(g,j, 0.5f);
		  
		  //list==(f,h,j)
		  list.add(h);
		  list.add(j);
		  
		  test=m.getRelations(f, 0.3f);
		  
		  for(Element e : test) {
			  assertTrue(list.contains(e));
		  }
		  assertEquals(list.size(),test.size());
	  }
	  
	 /**
	  * Test method for {@link tool.Matrix#isPM()}.
	  */
	  @Test 
	  void testIsPM() {
		  m.addRelation("f", "g", 0.11f);
		  m.addRelation("h", "g", 0.22f);
		  m.addRelation("h", "f", 0.33f);
		  m.addRelation("a", "b", 0.44f);
		  m.addRelation("b", "d", 0.55f);
		  
		  assertTrue(m.isPM());
		  
		  //It doesn't matter what the user set to the diagonal, we always give back 1.
		  m.addRelation("b", "b", 0.5f);
		  assertTrue(m.isPM());
		  
		  //one entry greater than 1
		  m.addRelation("b", "d", 1.5f);
		  assertFalse(m.isPM());
		  
		  //one entry smaller than 0
		  m.addRelation("b", "d", -2f);
		  assertFalse(m.isPM());
	  }		 

	  /**
	   * Test method for {@link tool.Matrix#getAllElements()}.
	   */
	  @Test 
	  void testGetAllElements() {
		  Element f =new Function("f");
		  Element g =new Function("g");
		  Element h =new Function("h");
		  Element a =new Function("a");
		  Element b =new Function("b");
		  Element d =new Function("d");
		  
		  ArrayList<Element> elem = new ArrayList<Element>(); 
		  ArrayList<Element> test;
		  
		  //empty matrix
		  test=m.getAllElements();
		  
		  for(Element e : test) {
			  assertTrue(elem.contains(e));
		  }
		  assertEquals(elem.size(),test.size());
		  
		  //fill matrix
		  m.addRelation(f, g, 0.11f);
		  m.addRelation(h, g, 0.22f);
		  m.addRelation(h, f, 0.33f);
		  m.addRelation(a, b, 0.44f);			  
		  m.addRelation(b, d, 0.55f);
		  
		  elem.add(f);
		  elem.add(g);
		  elem.add(h);
		  elem.add(a);
		  elem.add(b);
		  elem.add(d);
		  
		  test=m.getAllElements();
		  
		  for(Element e : test) {
			  assertTrue(elem.contains(e));
		  }
		  assertEquals(elem.size(),test.size());
	  }		
}
