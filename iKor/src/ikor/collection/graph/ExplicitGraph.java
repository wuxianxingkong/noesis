package ikor.collection.graph;

//Title:       Graph ADT
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import ikor.collection.List;

/**
 * Explicit graph base class (with Nodes and Links).
 * 
 * @author Fernando Berzal
 */

public interface ExplicitGraph<V,E> extends Graph<V, E> 
{
	// Nodes
	
	public int index (GraphNode<V> node);
	
	public GraphNode<V> getNode (int index);
	public GraphNode<V> getNode (V node);
	
	// Links
	
	public GraphLink<E> getLink(GraphNode<V> source, GraphNode<V> destination);
	public GraphLink<E> getLink(int source, int destination);
	public GraphLink<E> getLink(V source, V destination);
	
	
	public List<GraphLink<E>> outLinkList (int node);
	public List<GraphLink<E>> outLinkList (V node);
	
	public List<GraphLink<E>> inLinkList (int node);
	public List<GraphLink<E>> inLinkList (V node);
}
