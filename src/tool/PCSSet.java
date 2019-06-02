/**
 * 
 */
package tool;

import java.util.ArrayList;
import java.util.Collection;

import elements.Element;

/**
 * Class for the problem/Constraint/Sigma sets.
 * @author Jan-Michael Holzinger &amp; Sophie Hofmanninger
 * @version 1.0
 */
public class PCSSet implements TupleSet<Element> {

	private ArrayList<Tuple<Element>> content;
	private final String TOKEN;


	public PCSSet(char pcs) {
		content=new ArrayList<Tuple<Element>>();
		// TODO Probably use other Tokens.
		switch(pcs) {
		case 'p': 
			TOKEN = "~";
			break;
		case 'c':
			TOKEN = "=";
			break;
		case 's': 
			TOKEN = "->";
			break;
		default:
			TOKEN = ",";
		}
	}

	/* (non-Javadoc)
	 * @see tool.TupleSet#size()
	 */
	@Override
	public int size() {
		return content.size();
	}

	/* (non-Javadoc)
	 * @see tool.TupleSet#add(tool.Tuple)
	 */
	@Override
	public boolean add(Tuple<Element> e) {
		return content.add(e);
	}

	/* (non-Javadoc)
	 * @see tool.TupleSet#add(int, tool.Tuple)
	 */
	@Override
	public void add(int index, Tuple<Element> element) {
		if(index<0||index>size()) return;
		content.add(index, element);
	}

	/* (non-Javadoc)
	 * @see tool.TupleSet#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int index, Collection<? extends Tuple<Element>> c) {
		if(index<0||index>size()) return false;
		return content.addAll(index, c);
	}

	/* (non-Javadoc)
	 * @see tool.TupleSet#get(int)
	 */
	@Override
	public Tuple<Element> get(int index) {
		if(index<0||index>=size()) return null;
		return content.get(index);
	}

	/* (non-Javadoc)
	 * @see tool.TupleSet#remove(int)
	 */
	@Override
	public Tuple<Element> remove(int index) {
		if(index<0||index>=size()) return null;
		return content.remove(index);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s="{";
		for(int i=0;i<size();i++) {
			s+="";
			Tuple<Element> t = content.get(i);
			s+=t.getFirst();
			s+=TOKEN;
			s+=t.getSecond();
			s+="";
			if(i<size()-1) s+=", ";
		}
		s+="}";
		return s;
	}

	/* (non-Javadoc)
	 * @see tool.TupleSet#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends Tuple<Element>> c) {
		return content.addAll(c);
	}

	/* (non-Javadoc)
	 * @see tool.TupleSet#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return content.isEmpty();
	}



}
