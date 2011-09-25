package ikor.collection.graph;

// Title:       Graph ADT
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.*;

/**
 * Graph
 * 
 * @author Fernando Berzal
 */

public interface MutableGraph<V,E> extends Graph<V,E>, MutableCollection<V>
{
	// Nodes

	public boolean add (V node);

	public boolean remove (V node);
	public boolean remove (int nodeIndex);
	public boolean remove (Node<V,E> node);


	// Edges

	public boolean add (V source, V destination, E content);
	public boolean add (int sourceIndex, int destinationIndex, E content);

	public boolean remove (V source, V destination, E content);
	public boolean remove (int sourceIndex, int destinationIndex, E content);
    public boolean remove (Link<V,E> link);

}

