import tool.Matrix;
import unificationProblem.InputParser;
import unificationProblem.UnificationProblem;

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
		UnificationProblem uni = InputParser.parse(test).get(0);

		System.out.println(uni.getLeft().toString());
		System.out.println(uni.getRight().toString());

		String test2 = "f(x1,x2,y) =? g(h(x2),y,x1)";
		UnificationProblem uni2 = InputParser.parse(test2).get(0);

		System.out.println(uni2.getLeft().toString());
		System.out.println(uni2.getRight().toString());
		
		// Test
		Matrix matr = new Matrix();
		matr.addRelation("a", "b", 0.7f);
		matr.addRelation("b", "c", 0.7f);
		matr.addRelation("c", "d", 0.7f);
		matr.addRelation("a", "b1", 0.7f);
		matr.addRelation("b1", "c1", 0.7f);
		matr.addRelation("c1", "d", 0.7f);
		matr.addRelation("f", "g", 0.7f);
		matr.addRelation("p", "q", 0.7f);
		matr.addRelation("f", "q", 0.2f);
		
		uni.setProximityRelations(matr);
		uni.lambda = 0.5f;
		
		System.out.println(uni.resultString());
		//Following is in endless loop, because of pre-Unification (conjecture)
		//uni.solveNext();
		//System.out.println(uni.resultString());
		//uni.solveNext();
		//System.out.println(uni.resultString());
		
	}

}
