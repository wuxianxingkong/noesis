package ikor.collection.graph;

// Title:       Graph edge/arc
// Version:     1.0
// Copyright:   2006
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Graph link.
 */

public class Link<V,E> implements Element<E>
{
	private Node<V,E> source;
	private Node<V,E> destination;
	private E         content;

	public Link (Node<V,E> source, Node<V,E> destination)
	{ 
		this(source,destination,null);
	}
	
	public Link (Node<V,E> source, Node<V,E> destination, E content)
	{ 
		setSource(source);
		setDestination(destination);
		setContent(content);
	}
	
	public Node<V,E> getDestination() {
		return destination;
	}
	public void setDestination(Node<V,E> destination) {
		this.destination = destination;
	}
	
	public Node<V,E> getSource() {
		return source;
	}
	public void setSource(Node<V,E> source) {
		this.source = source;
	}

	public E getContent() {
		return content;
	}
	public void setContent (E content) {
		this.content = content;
	}


	// toString


	public String toString ()
	{
		return getSource() + " -> " + getDestination() + " (" + getContent() + ")";
	}
}
