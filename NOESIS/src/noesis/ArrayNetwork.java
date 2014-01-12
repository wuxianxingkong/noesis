package noesis;

// Title:       Network ADT
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.List;
import ikor.collection.Dictionary;

import ikor.collection.DynamicList;
import ikor.collection.DynamicDictionary;

/**
 * Network ADT implementation using arrays.
 * 
 * @author Fernando Berzal
 */
public class ArrayNetwork<V,E> extends Network<V, E> 
{
	private BasicNetwork net;

	private List<V>   nodes;
	private List<E>[] content;
	
	private Dictionary<V,Integer> hash;
	
	
	public ArrayNetwork ()
	{
		this.net = new BasicNetwork();
		this.nodes = new DynamicList();
		this.hash = new DynamicDictionary();
	}
	
	@Override
	public final int size() 
	{
		return net.size();
	}

	@Override
	public final void setSize(int size) 
	{
		net.setSize(size);
	}

	@Override
	public final int links() 
	{
		return net.links();
	}

	// Nodes

	@Override
	public int add(V node) 
	{
		int pos = nodes.size();
		
		nodes.add(node);
		hash.set(node, pos);
			
		if (nodes.size()>=size())
			setSize(nodes.size());

		return pos;
	}
	
	@Override
	public boolean remove(int nodeIndex) 
	{
		throw new UnsupportedOperationException("Node removal is not allowed.");
	}

	// Links
	
	@Override
	public final boolean add(int source, int destination) 
	{
		return net.add(source,destination);
	}

	@Override
	public final boolean add(int sourceIndex, int destinationIndex, E value) 
	{
		boolean ok = add(sourceIndex,destinationIndex);
		
		if (ok) {
			
			if (content==null)
				content = new DynamicList[size()];
			
			if (content[sourceIndex]==null)
				content[sourceIndex] = new DynamicList<E>();
			
			content[sourceIndex].add(value);			
		}

		return ok;
	}

	@Override
	public boolean remove(int sourceIndex, int destinationIndex) 
	{
		throw new UnsupportedOperationException("Link removal is not allowed.");
	}

	@Override
	public boolean remove(int sourceIndex, int destinationIndex, E content) 
	{
		throw new UnsupportedOperationException("Link removal is not allowed.");
	}

	// Accessors

	@Override
	public final V get(int index) 
	{
		if (nodes!=null)
			return nodes.get(index);
		else
			return null;
	}

	@Override
	public final E get(int source, int destination) 
	{
		int index;
		
		if ((content!=null) && (content[source]!=null)) {
			
			index = net.getLinkIndex(source,destination);
			
			if (index!=-1)
				return content[source].get(index);
			else			
				return null;
			
		} else {
			
			return null;
		}
	}


	@Override
	public final boolean contains(V node) 
	{
		return hash.contains(node);
	}

	@Override
	public final int index(V node) 
	{	
		Integer entry = hash.get(node);
		
		if (entry!=null)
			return entry;
		else
			return -1;
	}


	@Override
	public final int inDegree(int node) 
	{
		return net.inDegree(node);
	}

	@Override
	public final int outDegree(int node) 
	{
		return net.outDegree(node);
	}

	@Override
	public int outLink (int node, int index) 
	{
		return net.outLink(node, index);
	}



	@Override
	public int inLink (int node, int index) 
	{
		return net.inLink(node, index);
	}

	
	@Override
	public String toString ()
	{
		return "["+super.toString()+"] ("+size()+" nodes, "+links()+" links)";
	}


}
