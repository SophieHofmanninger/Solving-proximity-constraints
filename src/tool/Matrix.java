/**
 * 
 */
package tool;

/**
 * Proximity Matrix class.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
public class Matrix{

	private float[][] content;
	private int size;


	/**
	 * Constructor
	 * @param size of the n x n Matrix.
	 */
	public Matrix(int size){
		this.size=size;
		content= new float[size][size];
		for(int i = 0;i<size;i++) content[i][i]=1;
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
		content[j][i]=v;
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
		
		return content[j][i];
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
		for(int j=0;j<m.size;j++) {
			for(int i=j;i<m.size;i++) {
				float c=m.content[j][i];
				if(i==j && c!=1) return false;
				if(c<0||c>1) return false;
			}
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
