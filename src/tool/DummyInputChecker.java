/**
 * 
 */
package tool;

/**
 * Simple InputChecker, only checks for non-null.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
public class DummyInputChecker implements InputChecker {

	/* (non-Javadoc)
	 * @see tool.InputChecker#check(java.lang.String)
	 */
	@Override
	public boolean check(String s) {
		if(s!=null) return true;
		return false;
	}

}
