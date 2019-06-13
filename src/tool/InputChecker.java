/**
 * 
 */
package tool;

/**
 * Checks correctness of Inputs for the Problem.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 *
 */
public interface InputChecker {

	/**
	 * Checks if the String matches the desired criterion
	 * @param s the string to check.
	 * @return {@code true} if the string fits.
	 */
		public boolean check(String s);
}
