/**
 * 
 */
package tool;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import elements.Element;

/**
 * Proximity Matrix class.
 * Representation of a symmetric matrix of size n>=1, where only the values above
 * the main diagonal are stored and are different from 1. All values must be in [0,1].
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.2
 */
public class Matrix{

	private float[] content;
	private Element[] contentE;
	private int size;


	/**
	 * Constructor
	 * @param size of the n x n Matrix.
	 */
	public Matrix(int size){
		if(size<1) size=1;
		this.size=size;
		int realsize=((1+(size-1))*(size-1))/2;
		content= new float[realsize];
		contentE = new Element[size];
	}

	/**
	 * This method defines the position of the element.
	 * @param elem The element, which should be specified to a position.
	 * @param position The position of the element.
	 */
	public void setRef(Element elem, int position) {
		if(position<0 || position>= size) {
			throw new IndexOutOfBoundsException("Index "+position+ " not allowed.");
		}
		else {
			contentE[position] = elem;
		}
	}

	/**
	 * This method puts a relation value between elements.
	 * @param elems This are the elements which should be related with a value. 
	 * @param v This is the relation value.
	 * @throws NoSuchElementException Throws exception if element is not in the matrix.
	 */
	public void putRef(Tuple<Element> elems, float v) 
			throws NoSuchElementException{

		Tuple<Integer> ti = new Tuple<>(getIndex(elems.getFirst()),getIndex(elems.getSecond()));

		if(ti.getFirst()<ti.getSecond()) {
			int temp = ti.getSecond();
			ti.setSecond(ti.getFirst());
			ti.setFirst(temp);
		}
		if(ti.getSecond()<0) {
			throw new NoSuchElementException("One of the Elements was not found!");
		}

		putAt(ti, v);
	}

	/**
	 * Put a value at a position in the upper triangle-part of the Matrix.
	 * @param indices The indices indicating the position
	 * @param v the value to put.
	 * @throws IndexOutOfBoundsException if <br><ul>
	 * <li>the indices are negative,</li>
	 * <li>exceed matrix size or</li>
	 * <li>a position in the main diagonal is accessed.</li>
	 * </ul>
	 * @throws IllegalArgumentException if the value is not in [0,1]. 
	 */
	public void putAt(Tuple<Integer> indices, float v)
			throws IndexOutOfBoundsException,IllegalArgumentException{
		int i= indices.getFirst();
		int j= indices.getSecond();
		if(i<0||i>=size||i==j) 
			throw new IndexOutOfBoundsException("Index "+i+ " not allowed.");
		if(j<0||j>=size)
			throw new IndexOutOfBoundsException("Index "+j+" not allowed.");
		if(v<0||v>1)
			throw new IllegalArgumentException("Value "+v+" not allowed,"
					+ " only values between 0 and 1 are allowed.");
		content[(Math.max(i, j)*(Math.max(i, j)-1))/2+(Math.min(i, j))]=v;
	}

	/**
	 * This method gets a relation value between elements.
	 * @param elems This are the elements where we want to know the relation value. 
	 * @return This returns the relation value.
	 * @throws NoSuchElementException Throws exception if element is not in the matrix.
	 */
	public float getRef(Tuple<Element> elems)
			throws NoSuchElementException{
		Tuple<Integer> ti = new Tuple<>(getIndex(elems.getFirst()),getIndex(elems.getSecond()));
		if(ti.getFirst()<ti.getSecond()) {
			int temp = ti.getSecond();
			ti.setSecond(ti.getFirst());
			ti.setFirst(temp);
		}
		if(ti.getSecond()<0) {
			throw new NoSuchElementException("One of the Elements was not found!");
		}
		return getAt(ti);
	}

	/**
	 * Get a value from the Matrix.
	 * @param indices the position where the value is stored.
	 * @return the value at position (indices.getFirst(),indices.getSecond());
	 * @throws IndexOutOfBoundsException if <br><ul>
	 * <li>the indices are negative or</li>
	 * <li>exceed matrix size.</li>
	 * </ul>
	 */
	public float getAt(Tuple<Integer> indices)
			throws IndexOutOfBoundsException{
		int i = Math.min(indices.getFirst(),indices.getSecond());
		int j = Math.max(indices.getFirst(),indices.getSecond());
		if(i<0||i>=size) 
			throw new IndexOutOfBoundsException("Index "+i+ " not allowed.");
		if(j<0||j>=size)
			throw new IndexOutOfBoundsException("Index "+j+" not allowed.");
		if(i==j) return 1;
		return content[(j*(j-1))/2+i];
	}

	/**
	 * Determines if a given matrix fulfills the criterion to be a proximity matrix.
	 * I.e. all values stored must be in [0,1] and the values on the main diagonal
	 *  must be 1.
	 * @param m The matrix to check.
	 * @return {@code boolean} true, iff the matrix fulfills the above mentioned
	 * criterion.
	 */
	public static boolean isPM(Matrix m) {
		// The size must correspond to the array size.
		int result = m.content.length;
		result *= 2;
		result /= (m.size-1);
		result -= (m.size-1);
		if(result != 1) return false;
		// All values must be in [0,1].
		for(int i=0;i<m.content.length;i++) {
			if(m.content[i]<0||m.content[i]>1) return false;
		}
		return true;
	}

	/**
	 * @return the dimension of the Matrix.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * This method gets the index of an element.
	 * @param elem This is the element where want to know the index.
	 * @return Returns the index. -1 if not found.
	 */
	private int getIndex(Element elem) {
		for(int i = 0; i<size;i++) {
			if(elem.getName() == contentE[i].getName()) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * This method delivers the relations.
	 * @param elem This is the element from which we want the relatives.
	 * @param lambda This is the lower bound of the relations.
	 * @return Returns a list of all relations.
	 */
	public ArrayList<Element> getR(Element elem, float lambda){

		ArrayList<Element> ret = new ArrayList<>();

		for(int i = 0; i<size;i++) {
			if(elem.getName() != contentE[i].getName()) {
				Tuple<Element> t = new Tuple<>(elem,contentE[0]);
				if(lambda <= getRef(t)) {
					ret.add(contentE[i]);
				}
			}
		}
		return ret;
	}
}
