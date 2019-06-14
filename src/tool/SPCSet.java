/**
 * 
 */
package tool;

import java.util.ArrayList;
import java.util.Collection;

import elements.Element;

/**
 * Class for the sigma/problem/constraint sets.
 * Now the name is also a shortcut for SolvingProximityConstraints.
 * Basically a facade for ArrayList<Tuple<Element>>
 * @author Jan-Michael Holzinger
 * @version 1.1
 */
public class SPCSet implements TupleSet<Element> {

	private ArrayList<Tuple<Element>> content;
	private final String TOKEN;

/**
 * A set of Tuple &lt;Element &gt;s
 * @param symbol The symbol to use between the first and second element.
 */
	public SPCSet(String symbol) {
		content=new ArrayList<Tuple<Element>>();
		TOKEN = symbol;
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
			s+=t.getFirst().toFullString();
			s+=TOKEN;
			s+=t.getSecond().toFullString();
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

	/* (non-Javadoc)
	 * @see tool.TupleSet#trim()
	 */
	@Override
	public void trim() {
		ArrayList<Tuple<Element>> contentNew = new ArrayList<Tuple<Element>>();
		for(int i=0;i<content.size();i++) {
			boolean alreadyIn = false;
			Tuple<Element> t1 = content.get(i);
			for(int j=0;j<contentNew.size();j++) {
				Tuple<Element> t2= contentNew.get(j);
				if((t1.getFirst().equals(t2.getFirst()))
						&& (t1.getSecond().equals(t2.getSecond()))) {
					alreadyIn=true;
					break;
				}
				if((t1.getSecond().equals(t2.getFirst()))
						&& (t1.getFirst().equals(t2.getSecond()))) {
					alreadyIn=true;
					break;
				}
				
			}
			if(!alreadyIn) contentNew.add(t1);
		}
		content=contentNew;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public SPCSet clone() {
		SPCSet ret = new SPCSet(this.TOKEN);
		ret.content = new ArrayList<Tuple<Element>>();
		for(Tuple<Element> c : this.content) {
			ret.content.add(new Tuple<Element>(c.getFirst(),c.getSecond()));
		}
		return ret;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof SPCSet)) return false;
		SPCSet o = (SPCSet) obj;
		if(!(o.TOKEN.equals(TOKEN))) return false;
		if(!(o.content.equals(content))) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return TOKEN.hashCode()+content.hashCode();
	}

	
	
	
	
}
