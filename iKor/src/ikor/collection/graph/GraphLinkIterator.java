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

public class GraphLinkIterator<E> extends CollectionIterator<E> 
{
	ReadOnlyGraph<?,E> graph;
	int        nodeIndex, linkIndex;
	int        lastNode, lastLink;
	int[]      links;

	public GraphLinkIterator (ReadOnlyGraph<?,E> graph)
	{
		this(graph,0,graph.size()-1);
	}

	public GraphLinkIterator (ReadOnlyGraph<?,E> graph, int start, int end)
	{
		this.graph     = graph;
		this.nodeIndex = start;
		this.lastNode  = end;
		this.links     = graph.outLinks(start);
		this.linkIndex = 0;
		this.lastLink  = graph.outDegree(start)-1;
	}

	@Override
	public boolean hasNext() {
		return (nodeIndex<=lastNode) && (linkIndex<=lastLink);
	}

	@Override
	public E next() 
	{	
		E result = null;
		
		if ((nodeIndex<=lastNode) && (linkIndex<=lastLink)) {
			
			result = graph.get(nodeIndex, links[linkIndex]);
			
			linkIndex++;
			
			if (linkIndex>lastLink) {
				
				linkIndex = 0;
				
				do {
					nodeIndex++;
					
					if (nodeIndex<graph.size()) {
						links = graph.outLinks(nodeIndex);
						lastLink = graph.outDegree(nodeIndex)-1;
					}
					
				} while ( (nodeIndex<=lastNode) && (linkIndex>lastLink) );
			}
		} 
		
		return result;
	}

}