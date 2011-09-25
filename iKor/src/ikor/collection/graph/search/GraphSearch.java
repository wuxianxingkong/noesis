package ikor.collection.graph.search;

// Title:       Graph Search
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.graph.Graph;
import ikor.collection.graph.Node;
import ikor.collection.graph.Link;

import ikor.util.Visitor;

/**
 * Graph Search
 * 
 * @author Fernando Berzal
 */

public abstract class GraphSearch<V,E>
{
	public enum State { UNDISCOVERED, DISCOVERED, EXPLORED };

	protected Graph<V,E>         graph;
	protected State[]            state;

	private   Visitor<Node<V,E>> nodeVisitor;
	private   Visitor<Link<V,E>> linkVisitor;


    // Constructor

	public GraphSearch (Graph<V,E> graph)
	{
		this(graph,null,null);
	}

	public GraphSearch (Graph<V,E> graph, Visitor<Node<V,E>> nodeVisitor, Visitor<Link<V,E>> linkVisitor)
	{
		this.graph       = graph;
		this.state       = new State[graph.size()];

		java.util.Arrays.fill(state, State.UNDISCOVERED);

		this.nodeVisitor = nodeVisitor;
		this.linkVisitor = linkVisitor;
	}

	// Node visitor

	public void setNodeVisitor (Visitor<Node<V,E>> visitor)
	{
		this.nodeVisitor = visitor;
	}

	protected void visitNode (Node<V,E> node)
	{
		if (nodeVisitor!=null)
			nodeVisitor.visit(node);
	}


	// Edge visitor

	public void setLinkVisitor (Visitor<Link<V,E>> visitor)
	{
		this.linkVisitor = visitor;
	}

	protected void visitLink (Link<V,E> link)
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

	public abstract void explore (Node<V,E> start);

	// Explored nodes

	public State status (int index)
	{
		return state[index];
	}

	public State status (Node<V,E> node)
	{
		return status( graph.index(node) );
	}

}

