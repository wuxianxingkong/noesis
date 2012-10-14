package ikor.collection.graph;

//Title:       Graph ADT
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org


/**
 * Mutable explicit graph (with Nodes and Links)
 * 
 * @author Fernando Berzal
 */

public interface GraphInterface<V, E> extends Graph<V,E>, ReadOnlyGraphInterface<V, E> 
{
	// Nodes
	
	public abstract boolean remove (GraphNode<V> node);
	
	// Links
	
    public abstract boolean remove (GraphLink<E> link);
	
}
