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

	// Node degrees

	public int degree (int node);
	public int inDegree (int node);
	public int outDegree (int node);

	// Edges

	public int links ();

	public List<Link<V,E>> outLinks (int node);
	public List<Link<V,E>> inLinks (int node);
}

