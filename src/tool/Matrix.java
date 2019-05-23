/**
 * 
 */
package tool;

/**
 * Proximity Matrix class.
 * Representation of a matrix of size n>=1, where only the values above
 * the main diagonal are stored and are different from 1.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.1
 */
public class Matrix{

	private float[] content;
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
	}

	/**
	 * Put a value at a position in the upper triangle-part of the Matrix.
	 * @param indices The indices indicating the position
	 * @param v the value to put.
	 * @throws IndexOutOfBoundsException if <br><ul>
	 * <li>the indices are negative,</li>
	 * <li>exceed matrix size or</li>
	 * <li>a position in the main diagonal or lower is accessed.</li>
	 * </ul>
	 * @throws IllegalArgumentException if the value is not in [0,1]. 
	 */
	public void putAt(Tuple<Integer> indices, float v)
			throws IndexOutOfBoundsException,IllegalArgumentException{
		int i= indices.getFirst();
		int j= indices.getSecond();
		if(i<0||i>=size||i>=j) 
			throw new IndexOutOfBoundsException("Index "+i+ " not allowed.");
		if(j<0||j>=size)
			throw new IndexOutOfBoundsException("Index "+j+" not allowed.");
		if(v<0||v>1)
			throw new IllegalArgumentException("Value "+v+" not allowed,"
					+ " only values between 0 and 1 are allowed.");
		content[i*(size-1)+(j-1)]=v;
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
		int i= indices.getFirst();
		int j= indices.getSecond();
		if(i<0||i>=size) 
			throw new IndexOutOfBoundsException("Index "+i+ " not allowed.");
		if(j<0||j>=size)
			throw new IndexOutOfBoundsException("Index "+j+" not allowed.");
		if(i==j) return 1;
		return content[i*(size-1)+(j-1)];
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

}
