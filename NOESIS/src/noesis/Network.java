package noesis;

//Title:       Network base class
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import java.util.Iterator;

import ikor.collection.graph.Graph;
import ikor.collection.graph.GraphIterator;

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
	
	
	// Network size
	
	public abstract int size();

	public abstract void setSize(int nodes);

	public abstract int links();
	

	// Network dynamics
	
	public abstract int add(V node);

	public abstract boolean add(int sourceIndex, int destinationIndex);

	public abstract boolean add(int sourceIndex, int destinationIndex, E content);

	
	// Accessors
	
	public abstract V get(int index);

	public abstract E get(int source, int destination);

	public abstract E get(V source, V destination);


	public abstract boolean contains(V object);
	
	public abstract int index(V node);
	
	
	public final Iterator<V> iterator ()
	{
		return new GraphIterator<V>(this);
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
	

}