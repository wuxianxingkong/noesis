package ikor.collection.graph;

//Title:       Generic Graph ADT iterator
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import java.util.Iterator;

/**
 * Graph node iterator.
 * 
 * @author Fernando Berzal
 */

public class GraphIterator<V> implements Iterator<V> 
{
	Graph<V,?> graph;
	int        index;
	int        last;

	public GraphIterator (Graph<V,?> graph)
	{
		this(graph,0,graph.size()-1);
	}

	public GraphIterator (Graph<V,?> graph, int start, int end)
	{
		this.graph = graph;
		this.index = start;
		this.last  = end;
	}

	@Override
	public boolean hasNext() {
		return (index<last);
	}

	@Override
	public V next() {
		
		if (index<=last)
			return graph.get(index++);
		else
			return null;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Node removal unavailable from graph iterator.");
	}
}
