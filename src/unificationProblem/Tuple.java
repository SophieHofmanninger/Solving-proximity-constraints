package unificationProblem;

/**
 * Generic Tuple Class
 * @author Jan-Michael Holzinger & Sophie Hofmanninger
 * @version 1.0
 */
public class Tuple<E> {

	private E f,s;

	public Tuple(E first, E second) {
		this.f=first;
		this.s=second;
	}

	/**
	 * @return the first element
	 */
	public E getFirst() {
		return f;
	}

	/**
	 * @param f the first element to set
	 */
	public void setFirst(E f) {
		this.f = f;
	}

	/**
	 * @return the second element
	 */
	public E getSecond() {
		return s;
	}

	/**
	 * @param s the second element to set
	 */
	public void setSecond(E s) {
		this.s = s;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Tuple [f=" + f + ", s=" + s + "]";
	}


}
