package ikor.collection.graph;

// Title:       Graph node
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Graph node.
 */

public interface GraphNode<V> extends GraphElement<V>
{
	public V getContent();
	
	public GraphLink outLink (int arc);
	public GraphLink inLink (int arc);	
	
	public int degree();
	public int outDegree();
	public int inDegree();
}
