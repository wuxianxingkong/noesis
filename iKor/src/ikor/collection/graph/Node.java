package ikor.collection.graph;

// Title:       Graph node
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Graph node.
 */

public interface Node<V,E> extends Element<V>
{
	public V getContent();
	
	public Link<V,E> outLink (int arc);
	public Link<V,E> inLink (int arc);	
	
	public int degree();
	public int outDegree();
	public int inDegree();
}
