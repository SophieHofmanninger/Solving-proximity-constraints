/**
 * 
 */
package tool;

import java.util.Collection;

/**
 * Interface for sets, that consist of Tuples of Elements and behave like ArrayLists.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
public interface TupleSet<E> {

	/**
	 * The size of the set.
	 * @return the size.
	 */
	public int size();

	/**
	 * Add an element at the end of the set.
	 * @param e the element to add.
	 * @return {@code true} if the element was added.
	 */
	public boolean add(Tuple<E> e);

	/** 
	 * Inserts the specified element at the specified position in this set.
	 * @param index index at which the specified element is to be inserted.
	 * @param element element to be inserted.
	 */
	public void add(int index, Tuple<E> element);

	/**
	 * Add collection of elements to this set.
	 * @param c collection containing elements to be added to this set.
	 * @return {@code true} if this set changed as a result of the call
	 */
	public boolean addAll(Collection<? extends Tuple<E>> c);

	/**
	 * Inserts all of the elements in the specified collection into this set,
	 * starting at the specified position.
	 * @param index index at which to insert the first element from the specified
	 * collection. 
	 * @param c collection containing elements to be added to this set.
	 * @return {@code true} if this set changed as a result of the call.
	 */
	public boolean addAll(int index, Collection<? extends Tuple<E>> c);

	/**
	 * Returns the element at the specified position in this set.
	 * @param index index of the element to return.
	 * @return the element at the specified position in this set.
	 */
	public Tuple<E> get(int index);

	/**
	 * Removes the element at the specified index from the set.
	 * @param index the index of the element to be removed.
	 * @return the element that was removed from the set.
	 */
	public Tuple<E> remove(int index);

	/**
	 * Returns {@code true} if this set contains no elements.
	 * @return {@code true} if this set contains no elements.
	 */
	public boolean isEmpty();

	/**
	 * Trims the set, that means duplicate elements get removed.
	 */
	public void trim();

}
