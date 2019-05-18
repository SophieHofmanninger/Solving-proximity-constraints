/**
 * 
 */
package unificationProblem;

import elements.Element;

/**
*
* The class Unifier handles the left and right part of the unification problem. 
* This means that the unification problem will be splitted in a left and right hand side.
* For example: p(x,y,x) =? q(f(a),g(b),y) has the left= p(x,y,x) , right= q(f(a),g(b),y)
*
* @author  Jan-Michael Holzinger & Sophie Hofmanninger
* @version 1.0
* 
*/
public class Unifier {
	public Element rigth;
	public Element left;
	
}
