package ikor.collection.graph;

//Title:       Generic Graph ADT iterator
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import ikor.collection.CollectionIterator;

/**
 * Graph node iterator.
 * 
 * @author Fernando Berzal
 */

public class GraphNodeIterator<V> extends CollectionIterator<V> 
{
	ReadOnlyGraph<V,?> graph;
	int        index;
	int        last;

	public GraphNodeIterator (ReadOnlyGraph<V,?> graph)
	{
		this(graph,0,graph.size()-1);
	}

	public GraphNodeIterator (ReadOnlyGraph<V,?> graph, int start, int end)
	{
		this.graph = graph;
		this.index = start;
		this.last  = end;
	}

	@Override
	public boolean hasNext() {
		return (index<=last);
	}

	@Override
	public V next() {
		
		if (index<=last)
			return graph.get(index++);
		else
			return null;
	}

}
