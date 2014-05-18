package ikor.collection.graph;

// Title:       Graph edge/arc
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Graph link.
 */

public class GraphLink<E> implements GraphElement<E>
{
	private GraphNode source;
	private GraphNode destination;
	private E         content;

	public GraphLink (GraphNode source, GraphNode destination)
	{ 
		this(source,destination,null);
	}
	
	public GraphLink (GraphNode source, GraphNode destination, E content)
	{ 
		setSource(source);
		setDestination(destination);
		setContent(content);
	}
	
	public GraphNode getDestination() 
	{
		return destination;
	}
	
	private void setDestination( GraphNode destination) 
	{
		this.destination = destination;
	}
	
	public GraphNode getSource() 
	{
		return source;
	}
	
	private void setSource (GraphNode source) 
	{
		this.source = source;
	}

	public E getContent() 
	{
		return content;
	}
	
	public void setContent (E content) 
	{
		this.content = content;
	}

	// toString


	public String toString ()
	{
		return getSource() + " -> " + getDestination() + " (" + getContent() + ")";
	}
}
