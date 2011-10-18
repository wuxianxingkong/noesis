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

public interface MutableExplicitGraph<V, E> extends MutableGraph<V,E>, ExplicitGraph<V, E> 
{
	// Nodes
	
	public abstract boolean remove (Node<V,E> node);
	
	// Links
	
    public abstract boolean remove (Link<V,E> link);
	
}
