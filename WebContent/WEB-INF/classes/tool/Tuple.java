package tool;

/**
 * Generic Tuple Class
 * @author Jan-Michael Holzinger
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
		return "(" + f.toString() + ", " + s.toString() + ")";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Tuple<E> clone(){
		return new Tuple<E>(f,s);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Tuple<?>))
			return false;
		Tuple<?> t = (Tuple<?>) obj;
		if(!(t.getFirst().equals(f))) return false;
		if(!(t.getSecond().equals(s))) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int hc = f.hashCode();
		hc+= s.hashCode();
		return hc;
	}

	
	
	
	
}
