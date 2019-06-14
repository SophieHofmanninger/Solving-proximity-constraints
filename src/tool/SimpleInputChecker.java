/**
 * 
 */
package tool;

/**
 * Simple InputChecker, only checks for non-null.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
public class SimpleInputChecker implements InputChecker {

	/* (non-Javadoc)
	 * @see tool.InputChecker#check(java.lang.String)
	 */
	@Override
	public boolean check(String s){
		if(s==null) return false;
		if(!(checkParenthesis(s))) return false;
		if(!(checkArguments(s))) return false;


		return true;



	}



	/**
	 * Checks if the number of opening and closing parenthesis
	 * is the same for each side of the equation.
	 * @param s the String to check
	 * @return {@code true} if the String passes this check.
	 */
	private boolean checkParenthesis(String s) {
		String[] sub = s.split("=");
		for(int i=0;i<sub.length;i++) {
			if(sub[i].chars().filter(ch -> ch == '(').count()!=
					sub[i].chars().filter(ch -> ch == ')').count())
				return false;
		}
		return true;
	}

	/**
	 * Checks if there is no situation like "(," or ",)", i.e.
	 * that no arguments are missing.
	 * @param s the String to check
	 * @return {@code true} if the String passes this check.
	 */
	private boolean checkArguments(String s) {
		while (s.contains("(")||s.contains(",")) {
			//both occur, "(" first;
			if(s.contains("(")&&s.contains(",")&&s.indexOf("(")<s.indexOf(",")) {
				s=s.substring(s.indexOf("("));
				// Opening parenthesis at the end.
				if(s.length()==1) return false;
				// (,
				if(s.substring(1,2).equals(","))
					return false;
				s=s.substring(1);
				continue;
			}
			//both occur, "," first;
			if(s.contains("(")&&s.contains(",")&&s.indexOf("(")>s.indexOf(",")) {
				s=s.substring(s.indexOf(","));

				// ,)
				if(s.substring(1,2).equals(")")||
						s.substring(1,2).equals(","))
					return false;
				s=s.substring(1);
				continue;
			}
			// "(" occurs;
			if(s.contains("(")&&!s.contains(",")) {
				s=s.substring(s.indexOf("("));
				// Opening parenthesis at the end.
				if(s.length()==1) return false;
				s=s.substring(1);
				continue;
			}
			// "," occurs;
			if(!s.contains("(")&&s.contains(",")) {
				s=s.substring(s.indexOf(","));
				if(s.substring(1,2).equals(")")||
						s.substring(1,2).equals(","))
					return false;
				s=s.substring(1);
				continue;
			}

		}
		return true;
	}
}
