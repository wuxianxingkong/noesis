package noesis;

// Title:       Network ADT
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.graph.Graph;

/**
 * Network ADT implementation using graphs 
 * 
 * @author Fernando Berzal
 */

public class GraphNetwork<V,E> extends Network<V,E> 
{
	private Graph<V,E> net;
	
	
	public GraphNetwork ()
	{
		this.net = CollectionFactory.createGraph();
	}
	
	
	/* (non-Javadoc)
	 * @see noesis.Network#setSize(int)
	 */
	@Override
	public void setSize (int nodes)
	{
		// Prepare for a given network size
		while (this.size()<nodes)
			this.add(null);
	}
	
	
	// Nodes
	
	/* (non-Javadoc)
	 * @see noesis.Network#add(V)
	 */
	@Override
	public int add (V node)
	{
		return net.add(node);
	}

	/* (non-Javadoc)
	 * @see noesis.Network#remove(int)
	 */
	@Override
	public boolean remove(int nodeIndex) 
	{
		return net.remove(nodeIndex);
	}

	// Links

	/* (non-Javadoc)
	 * @see noesis.Network#add(int, int)
	 */
	@Override
	public boolean add (int sourceIndex, int destinationIndex)
	{
		return net.add(sourceIndex, destinationIndex, null);
	}
	
	/* (non-Javadoc)
	 * @see noesis.Network#add(int, int, E)
	 */
	@Override
	public boolean add (int sourceIndex, int destinationIndex, E content)
	{
		return net.add(sourceIndex, destinationIndex, content);
	}
	
	/* (non-Javadoc)
	 * @see noesis.Network#remove(int, int)
	 */
	@Override
	public boolean remove(int sourceIndex, int destinationIndex) 
	{
		return net.remove(sourceIndex,destinationIndex,null);
	}

	/* (non-Javadoc)
	 * @see noesis.Network#remove(int, int, E)
	 */
	@Override
	public boolean remove(int sourceIndex, int destinationIndex, E content) 
	{
		return net.remove(sourceIndex,destinationIndex,content);
	}

	
	/* (non-Javadoc)
	 * @see noesis.Network#get(int)
	 */
	@Override
	public V get(int index) 
	{
		return net.get(index);
	}
	
	/* (non-Javadoc)
	 * @see noesis.Network#get(int, int)
	 */
	@Override
	public E get(int source, int destination)
	{
		return net.get(source,destination);
	}
	
	
	/* (non-Javadoc)
	 * @see noesis.Network#size()
	 */
	@Override
	public int nodes() 
	{
		return net.size();
	}

	/* (non-Javadoc)
	 * @see noesis.Network#contains(V)
	 */
	@Override
	public boolean contains(V object) 
	{
		return net.contains(object);
	}

	

	/* (non-Javadoc)
	 * @see noesis.Network#index(V)
	 */
	@Override
	public int index(V node) 
	{
		return net.index(node);
	}



	/* (non-Javadoc)
	 * @see noesis.Network#inDegree(int)
	 */
	@Override
	public int inDegree(int node) 
	{
		return net.inDegree(node);
	}


	/* (non-Javadoc)
	 * @see noesis.Network#outDegree(int)
	 */
	@Override
	public int outDegree(int node) 
	{
		return net.outDegree(node);
	}


	/* (non-Javadoc)
	 * @see noesis.Network#links()
	 */
	@Override
	public int links() 
	{
		return net.links();
	}


	
	@Override
	public int outLink (int node, int index) 
	{
		return net.outLink(node,index);
	}


	@Override
	public int inLink (int node, int index) 
	{
		return net.inLink(node,index);
	}


}