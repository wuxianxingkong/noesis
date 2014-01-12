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
	
	// Accessors
	
	public abstract V get(int index);

	public abstract E get(int source, int destination);

	public abstract E get(V source, V destination);

	public abstract boolean contains(V object);
	
	public abstract int index(V node);
	

	// Iterators
	
	public final Iterator<V> iterator ()
	{
		return new GraphNodeIterator<V>(this);
	}
	
	public final Iterator<E> linkIterator ()
	{
		return new GraphLinkIterator<E>(this);
	}
	

	// Network dynamics
	// ----------------
	
	// Nodes
	
	@Override
	public abstract int add(V node);

	@Override
	public final boolean remove(V node) 
	{
		return remove(index(node));
	}

	@Override
	public boolean remove(int nodeIndex) 
	{	
		throw new UnsupportedOperationException("Node removal is not allowed.");
    }

	// Links
	
	public abstract boolean add(int sourceIndex, int destinationIndex);

	@Override
	public abstract boolean add(int sourceIndex, int destinationIndex, E content);

	
	public final boolean add (V source, V destination)
	{
		int sourceIndex = index(source);
		int destinationIndex = index(destination);
		
		if ( (sourceIndex!=-1) && (destinationIndex!=-1) )
			return add(sourceIndex, destinationIndex);
		else
			return false;
	}	

	@Override
	public final boolean add (V source, V destination, E link)
	{
		int sourceIndex = index(source);
		int destinationIndex = index(destination);
		
		if ( (sourceIndex!=-1) && (destinationIndex!=-1) )
			return add(sourceIndex, destinationIndex, link);
		else
			return false;
	}
	

	public boolean remove(int sourceIndex, int destinationIndex)
	{
		throw new UnsupportedOperationException("Link removal is not allowed.");
	}

	@Override
	public boolean remove(int sourceIndex, int destinationIndex, E content)
	{
		throw new UnsupportedOperationException("Link removal is not allowed.");
	}

	
	public final boolean remove(V source, V destination) 
	{
		int sourceIndex = index(source);
		int destinationIndex = index(destination);
		
		if ( (sourceIndex!=-1) && (destinationIndex!=-1) )
			return remove(sourceIndex, destinationIndex);
		else
			return false;
	}

	@Override
	public final boolean remove(V source, V destination, E content) 
	{
		int sourceIndex = index(source);
		int destinationIndex = index(destination);
		
		if ( (sourceIndex!=-1) && (destinationIndex!=-1) )
			return remove(sourceIndex, destinationIndex, content);
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

	public final boolean remove2 (V source, V destination)
	{
		boolean ok;
		
		ok = remove(source,destination);
		
		if (ok)
			ok = remove(destination,source);
		
		return ok;
	}
	
	public final boolean remove2 (V source, V destination, E link)
	{
		boolean ok;
		
		ok = remove(source,destination,link);
		
		if (ok)
			ok = remove(destination,source,link);
		
		return ok;
	}

	@Override
	public void clear() 
	{
		while (size()>0) {
			remove(size()-1);
		}
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
	
	// Links
	
	@Override
	public final int[] outLinks(V node) 
	{
		return outLinks(index(node));
	}

	@Override
	public abstract int[] outLinks(int node);
	
	@Override
	public abstract int outLink(int node, int link);


	@Override
	public final int[] inLinks(V node) 
	{
		return inLinks(index(node));
	}

	@Override
	public abstract int[] inLinks(int node);

	@Override
	public abstract int inLink(int node, int link);
	
	
	
	// toString
	
	@Override
	public String toString ()
	{
		return ((id!=null)? id: "Network") + " ("+size()+" nodes, "+links()+" links)";
	}



}