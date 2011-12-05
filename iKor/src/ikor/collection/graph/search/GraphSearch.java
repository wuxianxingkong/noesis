package ikor.collection.graph.search;

// Title:       Graph Search
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.Visitor;
import ikor.collection.graph.ExplicitGraph;
import ikor.collection.graph.GraphNode;
import ikor.collection.graph.GraphLink;


/**
 * Graph Search
 * 
 * @author Fernando Berzal
 */

public abstract class GraphSearch<V,E>
{
	public enum State { UNDISCOVERED, DISCOVERED, EXPLORED };

	protected ExplicitGraph<V,E> graph;
	protected State[]            state;

	private   Visitor<GraphNode<V>> nodeVisitor;
	private   Visitor<GraphLink<E>> linkVisitor;


    // Constructor

	public GraphSearch (ExplicitGraph<V,E> graph)
	{
		this(graph,null,null);
	}

	public GraphSearch (ExplicitGraph<V,E> graph, Visitor<GraphNode<V>> nodeVisitor, Visitor<GraphLink<E>> linkVisitor)
	{
		this.graph       = graph;
		this.state       = new State[graph.size()];

		java.util.Arrays.fill(state, State.UNDISCOVERED);

		this.nodeVisitor = nodeVisitor;
		this.linkVisitor = linkVisitor;
	}

	// Node visitor

	public void setNodeVisitor (Visitor<GraphNode<V>> visitor)
	{
		this.nodeVisitor = visitor;
	}

	protected void visitNode (GraphNode<V> node)
	{
		if (nodeVisitor!=null)
			nodeVisitor.visit(node);
	}


	// Edge visitor

	public void setLinkVisitor (Visitor<GraphLink<E>> visitor)
	{
		this.linkVisitor = visitor;
	}

	protected void visitLink (GraphLink<E> link)
	{
		if (linkVisitor!=null)
			linkVisitor.visit(link);
	}


	// Graph exploration

	public final void explore ()
	{
		explore (0);
	}

	public final void explore (int index)
	{
		explore ( graph.getNode(index) );
	}

	public abstract void explore (GraphNode<V> start);

	// Explored nodes

	public State status (int index)
	{
		return state[index];
	}

	public State status (GraphNode<V> node)
	{
		return status( graph.index(node) );
	}

}

