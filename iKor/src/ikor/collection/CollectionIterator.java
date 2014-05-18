package ikor.collection;

// Title:       Queue collection ADT
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.Iterator;

/**
 * Base iterator for collections
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public abstract class CollectionIterator<T> implements Iterator<T> 
{
	public abstract boolean hasNext();

	public abstract T next();

	// For mutable collections...

	@Override
	public void remove() 
	{
		throw new UnsupportedOperationException("Unsupported operation in read-only iterators.");
	}

}
