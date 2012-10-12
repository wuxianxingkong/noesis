package noesis;

// Title:       Network base class
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.util.Iterator;

import ikor.collection.graph.Graph;
import ikor.collection.graph.GraphNodeIterator;
import ikor.collection.graph.GraphLinkIterator;

/**
 * Network ADT.
 */

public abstract class Network<V, E> implements Graph<V,E>
{
	// Network ID
	
	private String id;
	
	public String getID()
	{
		return id;
	}

	public void setID(String id)
	{
		this.id = id;
	}
	
	
	// Directedness
	
	private boolean directed = true;
	
	
	public boolean isDirected ()
	{
		return directed;
	}
	
	public void setDirected (boolean directed)
	{
		this.directed = directed;
	}
	
	// Network size
	
	public abstract int size();

	public abstract void setSize(int nodes);

	public abstract int links();
	

	// Network dynamics
	// ----------------
	
	// Nodes
	
	public abstract int add(V node);

	// Links
	
	public abstract boolean add(int sourceIndex, int destinationIndex);

	public abstract boolean add(int sourceIndex, int destinationIndex, E content);

	/*
	public final boolean add (XLink<E> link)
	{
		return add (link.getSource(), link.getDestination(), link.getContent());
	}
	*/
	
	public final boolean add (V source, V destination, E link)
	{
		int sourceIndex = index(source);
		int destinationIndex = index(destination);
		
		if ( (sourceIndex!=-1) && (destinationIndex!=-1) )
			return add(sourceIndex, destinationIndex, link);
		else
			return false;
	}
	
	public final boolean add (V source, V destination)
	{
		int sourceIndex = index(source);
		int destinationIndex = index(destination);
		
		if ( (sourceIndex!=-1) && (destinationIndex!=-1) )
			return add(sourceIndex, destinationIndex);
		else
			return false;
	}	
	

	// Biderectional links
	
	public final boolean add2 (V source, V destination)
	{
		boolean ok;
		
		ok = add(source,destination);
		
		if (ok)
			ok = add(destination,source);
		
		return ok;
	}
	
	public final boolean add2 (V source, V destination, E link)
	{
		boolean ok;
		
		ok = add(source,destination,link);
		
		if (ok)
			ok = add(destination,source,link);
		
		return ok;
	}
	
	// Accessors
	
	public abstract V get(int index);

	public abstract E get(int source, int destination);

	public abstract E get(V source, V destination);

	public abstract boolean contains(V object);
	
	public abstract int index(V node);
	
	
	
	public final Iterator<V> iterator ()
	{
		return new GraphNodeIterator<V>(this);
	}
	
	public final Iterator<E> linkIterator ()
	{
		return new GraphLinkIterator<E>(this);
	}
	

	
	// Node degrees
	
	public final int degree (int node)
	{
		return outDegree(node);
	}
	
	public final int degree (V node)
	{
		return outDegree(node);
	}
	
	public abstract int inDegree(int node);

	public final int inDegree(V node) 
	{
		return inDegree ( index(node) );
	}
	
	public abstract int outDegree(int node);

	public final int outDegree(V node) 
	{
		return outDegree ( index(node) );
	}	
	
	
	@Override
	public String toString ()
	{
		return ((id!=null)? id: "Network") + " ("+size()+" nodes, "+links()+" links)";
	}

}