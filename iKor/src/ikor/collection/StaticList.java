package ikor.collection;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.Iterator;

import ikor.collection.ReadOnlyList;

/**
 * Standard static list implementation, based on static arrays. 
 * 
 * A façade for uniform access to array-based collections.
 * 
 * @author Fernando Berzal
 */

public class StaticList<T> implements ReadOnlyList<T>
{
	// T[] cannot be used since Java does not allow dynamic creation of generic arrays
	private Object[] items;

	
	public StaticList (T singleton)
	{
		this.items = new Object[1];
		
		items[0] = singleton;
	}

	
	public StaticList (T[] array)
	{
		this.items = new Object[array.length];
		
		for (int i=0; i<array.length; i++)
			items[i] = array[i];
	}
	
	public StaticList (ReadOnlyList<T> list)
	{
		items = new Object[list.size()];
		
		for (int i=0; i<items.length; i++)
			items[i] = list.get(i);
	}
	
	
	@Override
	public T get(int index) {
		return (T) items[index];
	}

	@Override
	public int size() {
		return items.length;
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrayIterator<T>();
	}

	public class ArrayIterator<X> implements Iterator<X> {

		private int current = 0;
		
		@Override
		public boolean hasNext() {
			return current < items.length;
		}

		@Override
		public X next() {
			return (X) items[current++];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("An array cannot have elements removed");
		}
	}

	@Override
	public boolean contains(T object) {
		
		return (index(object) != -1);
	}

	@Override
	public int index(T object) {
		
		if (object!=null)
			for (int i=0; i<items.length; i++)
				if (object.equals(items[i]))
					return i;
			
		return -1;
	}

}
