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
	
	public int index (Node<V,E> node);
	
	public Node<V,E> getNode (int index);
	public Node<V,E> getNode (V node);
	
	// Links
	
	public Link<V,E> getLink(Node<V,E> source, Node<V,E> destination);
	public Link<V,E> getLink(int source, int destination);
	public Link<V,E> getLink(V source, V destination);
	
	
	public List<Link<V,E>> outLinkList (int node);
	public List<Link<V,E>> outLinkList (V node);
	
	public List<Link<V,E>> inLinkList (int node);
	public List<Link<V,E>> inLinkList (V node);
}
