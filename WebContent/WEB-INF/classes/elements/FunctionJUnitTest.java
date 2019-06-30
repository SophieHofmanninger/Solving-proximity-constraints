package elements;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

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

	/**
	 * Method to test addArguments().
	 */
	@Test
	void testAddArgument() {
		Function f = new Function("f");
		Variable x= new Variable("x");
		Variable y= new Variable("y");
		ArrayList<Element> arg=new ArrayList<Element>();
		arg.add(x);
		arg.add(y);
		f.setArguments(arg);
		f.addArgument(x);
		assertEquals("f(x,y,x)", f.toFullString());
	}
	
	/**
	 * Method to test arity().
	 */
	@Test
	void testArity() {
		Function a = new Function("a");
		Function f = new Function("f");
		Variable x= new Variable("x");
		Variable y= new Variable("y");
		ArrayList<Element> arg=new ArrayList<Element>();
		arg.add(x);
		arg.add(y);
		f.setArguments(arg);
		assertEquals(0, a.arity());
		assertEquals(2, f.arity());
	}

	/**
	 * Method to test clone().
	 */
	@Test
	void testClone() {
		Function f = new Function("f");
		Variable x= new Variable("x");
		Variable y= new Variable("y");
		ArrayList<Element> arg=new ArrayList<Element>();
		arg.add(x);
		arg.add(y);
		f.setArguments(arg);
		assertTrue(f.clone().equals(f));
	}
	
	/**
	 * Method to test getArgument().
	 */
	@Test
	void testGetArgument() {
		Function f = new Function("f");
		Variable x= new Variable("x");
		Variable y= new Variable("y");
		ArrayList<Element> arg=new ArrayList<Element>();
		arg.add(x);
		arg.add(y);
		f.setArguments(arg);
		assertEquals("y",f.getArgument(1).toString());
		assertEquals("x",f.getArgument(0).toString());
	}
	
	/**
	 * Method to test getArguments().
	 */
	@Test
	void testGetArguments() {
		Function a = new Function("a");
		Function b = new Function("b");
		Function f = new Function("f");
		Variable x= new Variable("x");
		Variable y= new Variable("y");
		ArrayList<Element> arg=new ArrayList<Element>();
		arg.add(x);
		arg.add(y);
		arg.add(b);
		f.setArguments(arg);
		assertEquals("[x, y, b]",f.getArguments().toString());
		assertEquals("[]",a.getArguments().toString());
	}
	
	/**
	 * Method to test getVars().
	 */
	@Test
	void testGetVars() {
		Function a = new Function("a");
		Function b = new Function("b");
		Function f = new Function("f");
		Variable x= new Variable("x");
		Variable y= new Variable("y");
		ArrayList<Element> arg=new ArrayList<Element>();
		arg.add(x);
		arg.add(y);
		arg.add(b);
		f.setArguments(arg);
		assertEquals("[x, y]",f.getVars().toString());
		assertEquals("[]",a.getVars().toString());
	}
	
	/**
	 * Method to test occurs().
	 */
	@Test
	void testOccurs() {
		Function a = new Function("a");
		Function b = new Function("b");
		Function f = new Function("f");
		Variable x= new Variable("x");
		Variable y= new Variable("y");
		ArrayList<Element> arg=new ArrayList<Element>();
		arg.add(x);
		arg.add(y);
		arg.add(b);
		f.setArguments(arg);
		assertTrue(f.occurs(b));
		assertFalse(f.occurs(a));
	}
	
	/**
	 * Method to test rename().
	 */
	@Test
	void testRename() {
		Function a = new Function("a");
		Function b = new Function("b");
		Function f = new Function("f");
		Variable x= new Variable("x");
		Variable y= new Variable("y");
		ArrayList<Element> arg=new ArrayList<Element>();
		arg.add(x);
		arg.add(y);
		arg.add(b);
		f.setArguments(arg);
		
		assertTrue(f.rename().toFullString().compareTo("f(x,y,b)") !=0);
		assertTrue(a.rename().toFullString().compareTo("a") !=0);
	}
}
