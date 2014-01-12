package ikor.collection.graph;

// Title:       Graph ADT
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.*;

/**
 * Mutable graph interface.
 * 
 * @author Fernando Berzal
 */

public interface Graph<V,E> extends ReadOnlyGraph<V,E>, Collection<V>
{
	// Nodes

	public int add (V node);

	public boolean remove (V node);
	public boolean remove (int nodeIndex);


	// Links

	public boolean add (V source, V destination, E content);
	public boolean add (int sourceIndex, int destinationIndex, E content);

	public boolean remove (V source, V destination, E content);
	public boolean remove (int sourceIndex, int destinationIndex, E content);
}

