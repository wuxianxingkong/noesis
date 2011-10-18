package ikor.collection.graph;

//Title:       Graph ADT
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import ikor.collection.List;

/**
 * Explicit graph ancillary base class (with Nodes and Links).
 * 
 * @author Fernando Berzal
 */

public abstract class ExplicitGraphImplementation<V,E> implements ExplicitGraph<V,E> 
{
	// Nodes
	
	public abstract int index (Node<V,E> node);
	
	public abstract Node<V,E> getNode (int index);

	public final Node<V,E> getNode (V node)
	{
		return getNode (index(node));
	}	
	
	// Links
	
	public abstract Link<V,E> getLink(Node<V,E> source, Node<V,E> destination);
	
	public final Link<V, E> getLink(int source, int destination) {
		return getLink ( getNode(source), getNode(destination) );
	}

	public final Link<V, E> getLink(V source, V destination) {
		return getLink ( getNode(source), getNode(destination) );
	}
	
	
	public abstract List<Link<V,E>> outLinkList (int node);

	public final List<Link<V,E>> outLinkList (V node)
	{
		return outLinkList(index(node));
	}

	
	public abstract List<Link<V,E>> inLinkList (int node);
		
	public final List<Link<V,E>> inLinkList (V node)
	{
		return inLinkList(index(node));
	}		
	
	// Ancillary methods (Graph interface)

	@Override
	public final int[] outLinks(int node) 
	{
		List<Link<V,E>> list = outLinkList(node);
		int[] array = new int[list.size()];
		
		if (isDirected()) {
			
			for (int i=0; i<array.length; i++)
				array[i] = index (list.get(i).getDestination());
			
		} else {
			
			for (int i=0; i<array.length; i++) {
				Link<V,E> link = list.get(i);
				
				if (node==index(link.getSource())) {
					array[i] = index(link.getDestination());
				} else {
					array[i] = index(link.getSource());
				}
			}
		}

		return array;
	}

	@Override
	public final int[] outLinks(V node) 
	{
		return outLinks(index(node));
	}

	@Override
	public final int[] inLinks(int node) 
	{
		List<Link<V,E>> list = inLinkList(node);
		int[] array = new int[list.size()];
		
		if (isDirected()) {
		
			for (int i=0; i<array.length; i++)
				array[i] = index (list.get(i).getSource());

		} else {
		
			for (int i=0; i<array.length; i++) {
				Link<V,E> link = list.get(i);

				if (node==index(link.getDestination())) {
					array[i] = index(link.getSource());
				} else {
					array[i] = index(link.getDestination());
				}
			}
		}
		
		return array;
	}

	@Override
	public final int[] inLinks(V node) 
	{
		return inLinks(index(node));
	}	
}
