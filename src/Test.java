import unificationProblem.InputParser;
import unificationProblem.Unifier;

/**
 *
 * The class Test contains the main method. 
 * 
 * @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 * 
 */
public class Test {

	/**
	 * This is the main method.
	 * @param args Nothing
	 */
	public static void main(String[] args) {

		String test = "p(x,y,x) =? q(f(a),g(d),y)";
		Unifier uni = InputParser.parse(test).get(0);

		System.out.println(uni.left.toString());
		System.out.println(uni.right.toString());

		String test2 = "f(x1,x2,y) =? g(h(x2),y,x1)";
		Unifier uni2 = InputParser.parse(test2).get(0);

		System.out.println(uni2.left.toString());
		System.out.println(uni2.right.toString());


	}

}
