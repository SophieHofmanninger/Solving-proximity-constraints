/**
 * 
 */
package userInterfaces;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import elements.Function;
import tool.SimpleInputChecker;
import tool.Tuple;
import unificationProblem.InputParser;
import unificationProblem.UnificationProblem;

/**
 * Use the Algorithm via the command line.
 * @author Jan-Michael Holzinger
 * @version 1.0
 *
 */
// TODO make it use -f , -e,....
public class SPC_CL {
	private static BufferedReader br = 
			new BufferedReader(new InputStreamReader(System.in));
	private static UnificationProblem intermediateResult;
	
	
	/**
	 * @param args the Unification Problem as String; if empty
	 * the program will ask for it.
	 * Suitable for one equation.
	 * @return the final status of the problem.
	 * @see unificationProblem.UnificationProblem#status
	 */
	// TODO File not yet supported.
	public static int main(String[] args) {
		ArrayList<String> aal = new ArrayList<String>(Arrays.asList(args));
		// equation, file, lambda, silentOn
		String equation=null;
		File f;
		float lambda=0;
		boolean silentOn=false;

		boolean equationSet=false;
		boolean fileSet = false;
		boolean lambdaSet= false;
		boolean silentSet= false;


		switch(args.length) {
		case 4:
			silentSet= true;
			if(args[3].equals("y")) {
				silentOn=true;
			}
		case 3:
			try {
				lambda=Float.parseFloat(args[2]);
				if(lambda<0||lambda>1) throw new NumberFormatException();
				lambdaSet=true;
			}catch(NumberFormatException e) {
				System.out.println
				("The value you have entered does not seem to be a float in [0,1].");
			}
		case 2:
			f = new File(args[1]);
			fileSet=true;
		case 1:
			equation= args[0];
			if(checkInput(equation)) equationSet=true;
		}


		if(!silentSet) {

			System.out.println
			("Do you want to see intermediate results and steps? Enter y/n");
			try {
				String input = br.readLine();
				if(!input.equals("y")) {
					silentOn=true;
				}
			} catch (IOException e) {

			} finally {
				silentSet=true;
			}
		}

		equationSet=checkInput(equation);

		while(!equationSet) {
			System.out.println
			("Please enter the equation in the form p =? q.");
			try {
				String input = br.readLine();
				if(checkInput(input)) {
					equation=input;
					equationSet=true;
				}
			} catch (IOException e) {

			}
		}


		UnificationProblem sTP = InputParser.parse(equation).get(0);

		// Get Open Cases

		if(sTP.checkOpenCases()) {
			boolean oneValue=false;
			System.out.println
			("Do you want to enter one value for all proximity relations? y/n");
			try {
				String input = br.readLine();
				if(input.equals("y")) {
					oneValue=true;
				}
			} catch (IOException e) {

			}
			float value = 0;
			if(oneValue) {
				boolean oneValueSet=false;
				while(!oneValueSet) {

					System.out.println
					("Please enter a proximity value for all functions.");
					try {
						String input = br.readLine();
						value = Float.parseFloat(input);
						if(value<0||value>1) throw new NumberFormatException();
						oneValueSet=true;
					} catch (IOException e) {
						System.out.println("Unkown Error.");
					} catch(NumberFormatException e) {
						System.out.println
						("The value you have entered does not seem to be a float in [0,1].");
					}				
				}

				sTP.setAllOpenCasesTo(value);
			}

		}

		while(sTP.checkOpenCases()) {
			Tuple<Function> oC = sTP.getNextOpenCase();
			System.out.println
			("Please enter a proximity value for "+oC.getFirst()
			+" and "+oC.getSecond()+".");
			try {
				String input = br.readLine();
				float value = Float.parseFloat(input);
				if(value<0||value>1) throw new NumberFormatException();
				sTP.closeCase(oC, value);
			} catch (IOException e) {
				System.out.println("Unkown Error.");
			} catch(NumberFormatException e) {
				System.out.println
				("The value you have entered does not seem to be a float in [0,1].");
			}
		}

		boolean nextRound=true;
		while(nextRound) {
			if(!lambdaSet) lambda=getLambda();
			sTP.setLambda(lambda);
			lambdaSet=true;
			nextRound=false;


			// ALGORITHM
			if(!silentOn) {
				System.out.println("Before pre-unification");
				System.out.println(sTP.problemToString());
			}
			StringBuffer sb= new StringBuffer();
			boolean result = sTP.solveNext(sb);
			if(!result) {
				System.out.println("Pre-unification failed, there is no solution.");
				if(!silentOn) System.out.println(sb);
				break;
			}else {
				System.out.println("Pre-unification successful.");
				intermediateResult = sTP.clone();
				if(!silentOn) {
					System.out.println(sb);
					System.out.println("After pre-unification");
					System.out.println(sTP.problemToString());
				}
				sb=new StringBuffer();
				result = sTP.solveNext(sb);
				if(!result) {
					System.out.println("Constraint simplification failed, there is no solution.");
					if(!silentOn) System.out.println(sb);
				}else {
					System.out.println("Constraint simplification successful.");
					if(!silentOn) {
						System.out.println(sb);
						System.out.println("After constraint simplification");
						System.out.println(sTP.problemToString());
					}

				}


				System.out.println
				("Do you want to perform the algorithm again with another lambda? y/n");
				try {
					String input = br.readLine();
					if(input.equals("y")) {
						nextRound=true;
						lambdaSet=false;
						sTP=intermediateResult.clone();
					}

				} catch (IOException e) {

				}
			}
		}

		try {
			br.close();
		} catch (IOException e) {
			System.out.println("Stream could not be closed.");
		}
		return sTP.getStatus();

	}

	/**
	 * Ask for user input for lambda.
	 * @return {@code float} lambda.
	 */
	private static float getLambda() {
		float ret =0;
		boolean succ=false;
		while(!succ) {
			System.out.println
			("Please enter a value for lambda in [0,1].");
			try {
				String input = br.readLine();
				ret = Float.parseFloat(input);
				if(ret<0||ret>1) throw new NumberFormatException();
				succ=true;
			} catch (IOException e) {
				System.out.println("Unkown Error for lambda.");
			} catch(NumberFormatException e) {
				System.out.println
				("The value you have entered does not seem to be a float in [0,1].");
			}				
		}
		return ret;
	}

	/**
	 * Checks the String via the defined InputChecker.
	 * @param equation the equation to check.
	 * @return {@code true} if the equation fits.
	 */
	private static boolean checkInput(String equation) {
	
		return new SimpleInputChecker().check(equation);
	}
}