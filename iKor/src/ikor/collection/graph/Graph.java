package ikor.collection.graph;

// Title:       Graph ADT
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.*;

/**
 * Graph
 * 
 * @author Fernando Berzal
 */

public interface Graph<V, E> extends List<V>
{
	public boolean isDirected ();

	// Nodes

	public int index (V node);
	public int index (Node<V,E> node);
	
	public Node<V,E> getNode (int index);
	public Node<V,E> getNode (V node);

	// Node degrees

	public int degree (int node);
	public int degree (V node);
	
	public int inDegree (int node);
	public int inDegree (V node);
	
	public int outDegree (int node);
	public int outDegree (V node);	

	// Edges

	public int links ();
	public E get(int source, int destination);
	public E get(V source, V destination);
	
	public Link<V,E> getLink(int source, int destination);
	public Link<V,E> getLink(V source, V destination);
	public Link<V, E> getLink(Node<V,E> source, Node<V,E> destination);
	

	public List<Link<V,E>> outLinks (int node);
	public List<Link<V,E>> outLinks (V node);

	public List<Link<V,E>> inLinks (int node);
	public List<Link<V,E>> inLinks (V node);
}

