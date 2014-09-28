package noesis;

// Title:       Network base class
// Version:     1.1
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import java.util.Iterator;

import ikor.collection.graph.Graph;
import ikor.collection.graph.GraphNodeIterator;
import ikor.collection.graph.GraphLinkIterator;

/**
 * Network ADT.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class Network<V, E> implements Graph<V,E>
{	
	private String  id;					// Network ID
	private boolean directed = true;	// Directed network?
  
	
	// Network ID
	
	/**
	 * Get network ID.
	 * @return Network ID.
	 */
	public final String getID()
	{
		return id;
	}

	/**
	 * Set network ID.
	 * @param id Desired network ID.
	 */
	public final void setID(String id)
	{
		this.id = id;
	}
	
	
	// Directedness
		
	/**
	 * Is the network a directed network?
	 */
	public final boolean isDirected ()
	{
		return directed;
	}
	
	/**
	 * Set network directedness.
	 * @param directed true if the network is directed
	 */
	public final void setDirected (boolean directed)
	{
		this.directed = directed;
	}
	
	
	// Network size
	
	/**
	 * Network size (number of nodes).
	 */
	public final int size()
	{
		return nodes();
	}

	/**
	 * Network size (number of nodes).
	 */
	public abstract int nodes();
	
	/**
	 * Resize network.
	 * @param nodes Desired number of nodes.
	 */
	public abstract void setSize(int nodes);

	/**
	 * Network size (number of links).
	 */
	public abstract int links();
	
	
	// Accessors
	
	/**
	 * Node index.
	 */
	public abstract int index(V node);

	/**
	 * Access to node contents given its index.
	 */
	public abstract V get(int index);

	/**
	 * Access to link contents given its source and destination node indexes.
	 */
	public abstract E get(int source, int destination);

	/**
	 * Access to link contents given its source and destination nodes.
	 */
	public final E get(V source, V destination)
	{
		int sourceIndex = index(source);
		int destinationIndex = index(destination);
		
		if ((sourceIndex!=-1) && (destinationIndex!=-1))
			return get(sourceIndex,destinationIndex);
		else
			return null;		
	}

	/**
	 * Check whether the network contains a given node.
	 */
	public boolean contains(V object)
	{		
		return (index(object)!=-1);
	}
	
	/**
	 * Check whether the network contains a link between two nodes.
	 */
	public boolean contains (int source, int destination)
	{
		return get(source,destination)!=null;
	}


	// Iterators

	/**
	 * Node iterator.
	 */
	public final Iterator<V> iterator ()
	{
		return new GraphNodeIterator<V>(this);
	}
	
	/**
	 * Link iterator.
	 */
	public final Iterator<E> linkIterator ()
	{
		return new GraphLinkIterator<E>(this);
	}
	

	// Network dynamics
	// ----------------
	
	// Nodes
	
	/**
	 * Add a node to the network.
	 */
	@Override
	public abstract int add(V node);

	/**
	 * Set a given node
	 * @param node Node index
	 * @param value Node value
	 */

	@Override
	public abstract void set (int node, V value);

	/**
	 * Remove a node from the network.
	 * @throws UnsupportedOperationException when node removal is not allowed
	 */
	@Override
	public final boolean remove(V node) 
	{
		return remove(index(node));
	}

	/**
	 * Remove a node from the network given its index.
	 * @throws UnsupportedOperationException when node removal is not allowed
	 */
	@Override
	public boolean remove(int nodeIndex) 
	{	
		throw new UnsupportedOperationException("Node removal is not allowed.");
    }

	// Links

	/**
	 * Add a link to the network
	 * @param sourceIndex Source node index
	 * @param destinationIndex Destination node index
	 * @return true if link has been added to the network
	 */
	public abstract boolean add(int sourceIndex, int destinationIndex);

	/**
	 * Add a link to the network
	 * @param sourceIndex Source node index
	 * @param destinationIndex Destination node index
	 * @param content Link content
	 * @return true if link has been added to the network
	 */
	@Override
	public abstract boolean add(int sourceIndex, int destinationIndex, E content);

	/**
	 * Add a link to the network
	 * @param source Source node
	 * @param destination Destination node
	 * @return true if link has been added to the network
	 */
	public final boolean add (V source, V destination)
	{
		int sourceIndex = index(source);
		int destinationIndex = index(destination);
		
		if ( (sourceIndex!=-1) && (destinationIndex!=-1) )
			return add(sourceIndex, destinationIndex);
		else
			return false;
	}	

	/**
	 * Add a link to the network
	 * @param source Source node
	 * @param destination Destination node
	 * @param content Link content
	 * @return true if link has been added to the network
	 */
	@Override
	public final boolean add (V source, V destination, E content)
	{
		int sourceIndex = index(source);
		int destinationIndex = index(destination);
		
		if ( (sourceIndex!=-1) && (destinationIndex!=-1) )
			return add(sourceIndex, destinationIndex, content);
		else
			return false;
	}
	

	/**
	 * Remove a link from the network
	 * @param sourceIndex Source node index
	 * @param destinationIndex Destination node index
	 * @return true if link has been removed from the network
	 * @throws UnsupportedOperationException when link removal is not allowed
	 */
	public boolean remove(int sourceIndex, int destinationIndex)
	{
		throw new UnsupportedOperationException("Link removal is not allowed.");
	}

	/**
	 * Remove a link from the network
	 * @param sourceIndex Source node index
	 * @param destinationIndex Destination node index
	 * @param content Link content
	 * @return true if link has been removed from the network
	 * @throws UnsupportedOperationException when link removal is not allowed
	 */
	@Override
	public boolean remove(int sourceIndex, int destinationIndex, E content)
	{
		throw new UnsupportedOperationException("Link removal is not allowed.");
	}

	
	/**
	 * Remove a link from the network
	 * @param source Source node 
	 * @param destination Destination node
	 * @return true if link has been removed from the network
	 * @throws UnsupportedOperationException when link removal is not allowed
	 */
	public final boolean remove(V source, V destination) 
	{
		int sourceIndex = index(source);
		int destinationIndex = index(destination);
		
		if ( (sourceIndex!=-1) && (destinationIndex!=-1) )
			return remove(sourceIndex, destinationIndex);
		else
			return false;
	}

	/**
	 * Remove a link from the network
	 * @param source Source node
	 * @param destination Destination node
	 * @param content Link content
	 * @return true if link has been removed from the network
	 * @throws UnsupportedOperationException when link removal is not allowed
	 */
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
	
	/**
	 * Add a bidirectional link to the network (i.e. two links: source->destination + destination->source)
	 * @param source "Source" node
	 * @param destination "Destination" node
	 * @return true if bidirectional link has been added to the network
	 */
	public final boolean add2 (V source, V destination)
	{
		boolean ok;
		
		ok = add(source,destination);
		
		if (ok)
			ok = add(destination,source);
		
		return ok;
	}
	
	/**
	 * Add a bidirectional link to the network (i.e. two links: source->destination + destination->source)
	 * @param source "Source" node
	 * @param destination "Destination" node
	 * @param content Link content
	 * @return true if bidirectional link has been added to the network
	 */
	public final boolean add2 (V source, V destination, E content)
	{
		boolean ok;
		
		ok = add(source,destination,content);
		
		if (ok)
			ok = add(destination,source,content);
		
		return ok;
	}

	/**
	 * Add a bidirectional link to the network (i.e. two links: source->destination + destination->source)
	 * @param sourceIndex "Source" node index
	 * @param destinationIndex "Destination" node index
	 * @return true if bidirectional link has been added to the network
	 */
	public final boolean add2 (int sourceIndex, int destinationIndex)
	{
		boolean ok;
		
		ok = add(sourceIndex,destinationIndex);
		
		if (ok)
			ok = add(destinationIndex,sourceIndex);
		
		return ok;
	}
	
	/**
	 * Add a bidirectional link to the network (i.e. two links: source->destination + destination->source)
	 * @param sourceIndex "Source" node index
	 * @param destinationIndex "Destination" node index
	 * @param content Link content
	 * @return true if bidirectional link has been added to the network
	 */
	public final boolean add2 (int sourceIndex, int destinationIndex, E content)
	{
		boolean ok;
		
		ok = add(sourceIndex,destinationIndex,content);
		
		if (ok)
			ok = add(destinationIndex,sourceIndex,content);
		
		return ok;
	}

	
	/**
	 * Remove a bidirectional link from the network (i.e. two links: source->destination + destination->source)
	 * @param source Source node 
	 * @param destination Destination node
	 * @return true if bidirectional link has been removed from the network
	 * @throws UnsupportedOperationException when link removal is not allowed
	 */	
	public final boolean remove2 (V source, V destination)
	{
		boolean ok;
		
		ok = remove(source,destination);
		
		if (ok)
			ok = remove(destination,source);
		
		return ok;
	}
	
	/**
	 * Remove a bidirectional link from the network (i.e. two links: source->destination + destination->source)
	 * @param source Source node 
	 * @param destination Destination node
	 * @param content Link content
	 * @return true if bidirectional link has been removed from the network
	 * @throws UnsupportedOperationException when link removal is not allowed
	 */	
	public final boolean remove2 (V source, V destination, E content)
	{
		boolean ok;
		
		ok = remove(source,destination,content);
		
		if (ok)
			ok = remove(destination,source,content);
		
		return ok;
	}

	/**
	 * Remove a bidirectional link from the network (i.e. two links: source->destination + destination->source)
	 * @param sourceIndex Source node index
	 * @param destinationIndex Destination node
	 * @return true if bidirectional link has been removed from the network
	 * @throws UnsupportedOperationException when link removal is not allowed
	 */	
	public final boolean remove2 (int sourceIndex, int destinationIndex)
	{
		boolean ok;
		
		ok = remove(sourceIndex,destinationIndex);
		
		if (ok && contains(destinationIndex, sourceIndex))
			ok = remove(destinationIndex,sourceIndex);
		
		return ok;
	}
	
	/**
	 * Remove a bidirectional link from the network (i.e. two links: source->destination + destination->source)
	 * @param sourceIndex Source node index
	 * @param destinationIndex Destination node index
	 * @param content Link content
	 * @return true if bidirectional link has been removed from the network
	 * @throws UnsupportedOperationException when link removal is not allowed
	 */	
	public final boolean remove2 (int sourceIndex, int destinationIndex, E content)
	{
		boolean ok;
		
		ok = remove(sourceIndex,destinationIndex,content);
		
		if (ok)
			ok = remove(destinationIndex,sourceIndex,content);
		
		return ok;
	}
	
	
	/**
	 * Clear network (i.e. remove all nodes from the current network)
	 */
	@Override
	public void clear() 
	{
		while (size()>0) {
			remove(size()-1);
		}
	}
	
	
	// Node degrees
	
	/**
	 * Node degree
	 */
	public final int degree (int node)
	{
		return outDegree(node);
	}
	
	/**
	 * Node degree
	 */
	public final int degree (V node)
	{
		return outDegree(node);
	}
	
	/**
	 * Node in-degree
	 */
	public abstract int inDegree(int node);

	/**
	 * Node in-degree
	 */
	public final int inDegree(V node) 
	{
		return inDegree ( index(node) );
	}
	
	/**
	 * Node out-degree
	 */
	public abstract int outDegree(int node);

	/**
	 * Node out-degree
	 */
	public final int outDegree(V node) 
	{
		return outDegree ( index(node) );
	}	
	
	// Links
	
	/**
	 * Destination of a link from a given node
	 * @param node Node index
	 * @param link Link index (from 0 to degree-1)
	 * @return Destination node index
	 */
	@Override
	public abstract int outLink(int node, int link);

	/**
	 * Source of a link pointing to a given node
	 * @param node Destination node index
	 * @param link Link index (from 0 to degree-1)
	 * @return Source node index
	 */
	@Override
	public abstract int inLink(int node, int link);
	
	/**
	 * Out-links from a given node
	 * @param node Source node 
	 * @return Destination node indices
	 */
	@Override
	public final int[] outLinks (V node) 
	{
		return outLinks(index(node));
	}

	/**
	 * Out-links from a given node
	 * @param node Source node index
	 * @return Destination node indices
	 */
	@Override
	public final int[] outLinks (int node)
	{
		int   outDegree = outDegree(node);
		int[] links     = null;
		
		if (outDegree>0) {
			
			links = new int[outDegree];
		
			for (int i=0; i<outDegree; i++)
				links[i] = outLink(node,i);
		}

		return links;
	}


	/**
	 * In-links to a given node
	 * @param node Destination node
	 * @return Source node indices
	 */
	@Override
	public final int[] inLinks(V node) 
	{
		return inLinks(index(node));
	}

	/**
	 * In-links to a given node
	 * @param node Destination node index
	 * @return Source node indices
	 */
	@Override
	public final int[] inLinks(int node) 
	{
		int   inDegree = inDegree(node);
		int[] links    = null;
		
		if (inDegree>0) {
			
			links = new int[inDegree];
		
			for (int i=0; i<inDegree; i++)
				links[i] = inLink(node,i);
		}

		return links;
	}

	
	
	
	// toString
	
	/**
	 * @see Object#toString()
	 */
	@Override
	public String toString ()
	{
		return ((id!=null)? id: "Network") + " ("+size()+" nodes, "+links()+" links)";
	}


}