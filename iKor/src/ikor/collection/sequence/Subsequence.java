package ikor.collection.sequence;

import java.util.Iterator;

import ikor.collection.ListIterator;


public class Subsequence<T> implements Sequence<T>
{
	private Sequence<T> original;
	private int         start;
	private int         end;


	public Subsequence (Sequence<T> original, int start, int end)
	{
		this.original = original;
		this.start = start;
		this.end = end;
	}


	@Override
	public int size()
	{
		return end-start+1;
	}

	@Override
	public T get (int index)
	{
		if ((index>=0) && (index<size()) )
			return original.get(start+index);
		else
			return null;
	}


	@Override
	public boolean contains(T object) {
		
		return (index(object) != -1);
	}


	@Override
	public int index(T object) {
		
		if (object!=null) {
			for (int i=0; i<size(); i++)
				if (object.equals(get(start+i)))
					return i;
		}
		return -1;
	}


	@Override
	public Iterator<T> iterator() {
		return new ListIterator(this,start,end);
	}


}
