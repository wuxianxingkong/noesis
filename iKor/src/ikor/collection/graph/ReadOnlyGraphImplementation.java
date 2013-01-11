package ikor.collection.graph;

//Title:       Graph ADT
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import ikor.collection.ReadOnlyList;

/**
 * Explicit graph ancillary base class (with Nodes and Links).
 * 
 * @author Fernando Berzal
 */

public abstract class ReadOnlyGraphImplementation<V,E> implements ReadOnlyGraphInterface<V,E> 
{
	// Nodes
	
	public abstract int index (GraphNode<V> node);
	
	public abstract GraphNode<V> getNode (int index);

	public final GraphNode<V> getNode (V node)
	{
		return getNode (index(node));
	}	
	
	// Links
	
	public abstract GraphLink<E> getLink(GraphNode<V> source, GraphNode<V> destination);
	
	public final GraphLink<E> getLink(int source, int destination) {
		return getLink ( getNode(source), getNode(destination) );
	}

	public final GraphLink<E> getLink(V source, V destination) {
		return getLink ( getNode(source), getNode(destination) );
	}
	
	
	public abstract ReadOnlyList<GraphLink<E>> outLinkList (int node);

	public final ReadOnlyList<GraphLink<E>> outLinkList (V node)
	{
		return outLinkList(index(node));
	}

	
	public abstract ReadOnlyList<GraphLink<E>> inLinkList (int node);
		
	public final ReadOnlyList<GraphLink<E>> inLinkList (V node)
	{
		return inLinkList(index(node));
	}		
	
	// Ancillary methods (Graph interface)

	@Override
	public final int outLink (int node, int index) 
	{
		ReadOnlyList<GraphLink<E>> list = outLinkList(node);
		
		if (isDirected()) {
			
			return index (list.get(index).getDestination());
			
		} else {
			
			GraphLink<E> link = list.get(index);
				
			if (node==index(link.getSource())) {
				return index(link.getDestination());
			} else {
				return index(link.getSource());
			}
		}		
	}
	
	@Override
	public final int[] outLinks(int node) 
	{
		ReadOnlyList<GraphLink<E>> list = outLinkList(node);
		int[] array = new int[list.size()];
		
		for (int i=0; i<array.length; i++)
			array[i] = outLink(node,i);

		return array;
	}

	@Override
	public final int[] outLinks(V node) 
	{
		return outLinks(index(node));
	}

	@Override
	public final int inLink (int node, int index) 
	{
		ReadOnlyList<GraphLink<E>> list = inLinkList(node);

		if (isDirected()) {
		
			return index (list.get(index).getSource());

		} else {
		
			GraphLink<E> link = list.get(index);

			if (node==index(link.getDestination())) {
				return index(link.getSource());
			} else {
				return index(link.getDestination());
			}
		}
	}
	
	@Override
	public final int[] inLinks(int node) 
	{
		ReadOnlyList<GraphLink<E>> list = inLinkList(node);
		int[] array = new int[list.size()];

		for (int i=0; i<array.length; i++)
			array[i] = inLink(node,i);
		
		return array;
	}

	@Override
	public final int[] inLinks(V node) 
	{
		return inLinks(index(node));
	}	
}
