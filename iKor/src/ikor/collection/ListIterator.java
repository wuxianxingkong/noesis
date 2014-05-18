package ikor.collection;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org


/**
 * List iterator
 * 
 * @author Fernando Berzal
 *
 * @param <T> List item type
 */
public class ListIterator<T> extends CollectionIterator<T> 
{
	ReadOnlyList<T> list;
	int     index;
	int     last;

	public ListIterator (ReadOnlyList<T> list)
	{
		this(list,0,list.size()-1);
	}

	public ListIterator (ReadOnlyList<T> list, int start, int end)
	{
		this.list  = list;
		this.index = start;
		this.last  = end;
	}

	@Override
	public boolean hasNext() 
	{
		return (index<=last);
	}

	@Override
	public T next() 
	{	
		if (index<=last)
			return (T) list.get(index++);
		else
			return null;
	}
}