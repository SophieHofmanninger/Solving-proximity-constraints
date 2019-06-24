import java.io.IOException;

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

		System.out.println(uni.getLeft().toFullString());
		System.out.println(uni.getRight().toFullString());

		String test2 = "f(x1,x2,y) =? g(h(x2),y,x1)";
		UnificationProblem uni2 = InputParser.parse(test2).get(0);

		System.out.println(uni2.getLeft().toFullString());
		System.out.println(uni2.getRight().toFullString());

		String test3 = "p(x,z) =? q(f(b),f(x))";
		UnificationProblem uni3 = InputParser.parse(test3).get(0);
		
		String test4 = "p(a,x,a) =? q(y,b,x)";
		UnificationProblem uni4 = InputParser.parse(test4).get(0);

		System.out.println(uni3.getLeft().toFullString());
		System.out.println(uni3.getRight().toFullString());
		
		//StringBuffer steps = new StringBuffer();
/*
		// Test
		System.out.println(System.lineSeparator()+"Test1:");
		
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
		uni.setLambda(0.5f);
		
		System.out.println(uni.resultString());
		uni.solveNext(steps);
		System.out.println(uni.resultString());
		System.out.print(steps);
		steps = new StringBuffer();
		uni.solveNext(steps);
		System.out.println("\n");
		System.out.println(uni.resultString());
		System.out.print(steps);
		
*/		
		// Test3
		System.out.println(System.lineSeparator()+"Test3:");

		Matrix matr3 = new Matrix();
		//matr3.addRelation("a", "a1", 0.7f);
		//matr3.addRelation("a1", "b", 0.7f);
		matr3.addRelation("b", "c1", 0.7f);
		matr3.addRelation("c1", "c", 0.7f);
		matr3.addRelation("p", "q", 0.7f);
		//matr3.addRelation("f", "g", 0.7f);

		uni3.setProximityRelations(matr3);
		uni3.setLambda(0.5f);

		StringBuffer steps3 = new StringBuffer();
		
		System.out.println(uni3.resultString());
		uni3.solveNext(steps3);
		System.out.println(uni3.resultString());
		System.out.print(steps3);
		steps3 = new StringBuffer();
		uni3.solveNext(steps3);
		System.out.println("\n");
		System.out.println(uni3.resultString());
		System.out.print(steps3);
//*/		
		// Test4
				System.out.println(System.lineSeparator()+"Test4:");

				StringBuffer steps4 = new StringBuffer();
				
				Matrix matr4 = new Matrix();
				//matr4.addRelation("a", "b", 0.7f);
				matr4.addRelation("b", "c", 0.7f);
				//matr4.addRelation("c", "d", 0.7f);
				//matr4.addRelation("a", "b1", 0.7f);
				//matr4.addRelation("b1", "c1", 0.7f);
				//matr4.addRelation("c1", "d", 0.7f);
				//matr4.addRelation("f", "g", 0.7f);
				matr4.addRelation("p", "q", 0.7f);
				matr4.addRelation("f", "q", 0.2f);

				uni4.setProximityRelations(matr4);
				uni4.setLambda(0.5f);
				
				System.out.println(uni4.resultString());
				uni4.solveNext(steps4);
				System.out.println(uni4.resultString());
				System.out.print(steps4);
				steps4 = new StringBuffer();
				uni4.solveNext(steps4);
				System.out.println("\n");
				System.out.println(uni4.resultString());
				System.out.print(steps4);
				
				
		//Test InputParser
		System.out.println(System.lineSeparator()+"Test InputParser Matrix");
		
		String testString="";
		testString += "f g h" + System.lineSeparator();
		testString += "f 1 0.4 0.7" + System.lineSeparator();
		testString += "g 0.4 1 0.5" + System.lineSeparator();
		testString += "h 0.7 0.5 1";
		
		try {
			Matrix mat = InputParser.parseMatrixFromString(testString);
			System.out.println(mat.toString());
		} catch (IOException e) {
			
		}
	}

}
