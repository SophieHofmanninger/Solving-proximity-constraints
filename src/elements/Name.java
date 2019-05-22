package elements;

/**
*
* The class Name handles the names.
*
* @author  Jan-Michael Holzinger &amp; Sophie Hofmanninger
* @version 1.0
* 
*/
public class Name extends Element{

	/* (non-Javadoc)
	 * @see elements.Element#occurs(elements.Element)
	 */
	@Override
	public boolean occurs(Element e) {
		return equals(e);
	}

}
