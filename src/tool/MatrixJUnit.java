/**
 * 
 */
package tool;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	   * {@link tool.Matrix#addRelation(java.lang.string, java.lang.string, float)} and
	   * {@link tool.Matrix#getRelation(elemets.Element,elemets.Element)} and 
	   * {@link tool.Matrix#getRelation(java.lang.string , java.lang.string)}.
	   */
	  @Test 
	  void testAddGetRelation() {
		  Function f =new Function("f");
		  Function g =new Function("g");
		  Function h =new Function("h");
		  
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
		  for(Element i : m.getListOfFunctions()) { 
			  assertEquals(1f,m.getRelation(i,i)); 
		  }
	  }
	  
	  /**
	   * Test method for {@link tool.Matrix#agetRelations(java.lang.String, float)}.
	   */
	  @Test 
	  void testGetRelations() {
		  Function f =new Function("f");
		  Function g =new Function("g");
		  Function h =new Function("h");
		  Function j =new Function("j");
		  Function a =new Function("a");
		  Function b =new Function("b");
		  
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
	  void testGetListOfFunctions() {
		  Function f =new Function("f");
		  Function g =new Function("g");
		  Function h =new Function("h");
		  Function a =new Function("a");
		  Function b =new Function("b");
		  Function d =new Function("d");
		  
		  ArrayList<Element> elem = new ArrayList<Element>(); 
		  ArrayList<Function> test;
		  
		  //empty matrix
		  test=m.getListOfFunctions();
		  
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
		  
		  test=m.getListOfFunctions();
		  
		  for(Element e : test) {
			  assertTrue(elem.contains(e));
		  }
		  assertEquals(elem.size(),test.size());
	  }		
	  
	  /**
	   * Test method for {@link tool.Matrix#openCases()}.
	   */
	  @Test 
	  void testOpenCases() {
		  Function f =new Function("f");
		  Function g =new Function("g");
		  Function h =new Function("h");
		  Function a =new Function("a");
		  Function b =new Function("b");
		  Function d =new Function("d");
		  
		  f.addArgument(a);
		  g.addArgument(b);
		  h.addArgument(d);
		  
		  m.addRelation(f, g, 0.11f);
		  m.addRelation(h, g, 0.22f);
		  m.addRelation(h, f, 0.33f);
		  m.addRelation(a, b, 0.44f);
		  m.addRelation(b, d, 0.55f);
		  
		  ArrayList<Tuple<Function>> testList =new ArrayList<Tuple<Function>>();
		  testList.add(new Tuple<Function>(a,d));
		  
		  ArrayList<Tuple<Function>> returnList = m.getOpenCases();
		  
		  assertTrue(returnList.size()==1);
		  assertTrue(returnList.get(0).getFirst().getName()==testList.get(0).getFirst().getName());
		  assertTrue(returnList.get(0).getSecond().getName()==testList.get(0).getSecond().getName());
		  
	  }
}
