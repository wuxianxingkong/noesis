package ikor.collection.graph;

// Title:       Graph ADT
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.*;

/**
 * Graph interface.
 * 
 * @author Fernando Berzal
 */

public interface ReadOnlyGraph<V, E> extends ReadOnlyList<V>
{
	public boolean isDirected ();

	// Nodes

	public int index (V node);

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
	
	public int[] outLinks (int node);
	public int[] outLinks (V node);

	public int[] inLinks (int node);
	public int[] inLinks (V node);
}

