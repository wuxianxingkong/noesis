package ikor.collection.graph.search;

// Title:       Graph Search
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.ReadOnlyList;
import ikor.collection.Visitor;

import ikor.collection.graph.ReadOnlyGraphInterface;
import ikor.collection.graph.GraphNode;
import ikor.collection.graph.GraphLink;

/**
 * Depth-First Search in Graphs
 * 
 * @author Fernando Berzal
 */

public class DFS<V,E> extends GraphSearch<V,E>
{
    // Constructor

	public DFS (ReadOnlyGraphInterface<V,E> graph)
	{
		super(graph);
	}

	public DFS (ReadOnlyGraphInterface<V,E> graph, Visitor<GraphNode<V>> nodeVisitor, Visitor<GraphLink<E>> linkVisitor)
	{
		super(graph, nodeVisitor, linkVisitor);
	}

	// Graph exploration

	public void explore (GraphNode<V> current)
	{
		int                currentIndex, targetIndex;
		GraphNode<V>       target;
		ReadOnlyList<GraphLink<E>> links;
		GraphLink<E>       link;

 	    currentIndex = graph.index(current);
   	    state[currentIndex] = GraphSearch.State.DISCOVERED;

		visitNode(current);

		links = graph.outLinkList(currentIndex);

		if (links!=null) {

			for (int i=0; i<links.size(); i++) {

				link = links.get(i);

				visitLink (link);

				if (graph.isDirected()) {
					target = link.getDestination();		
				} else {
					target = (link.getDestination()==current)? link.getSource(): link.getDestination();
				}


				targetIndex = graph.index(target);

				if (state[targetIndex]==GraphSearch.State.UNDISCOVERED) {
					explore(target);
				}
			}
		}

   	    state[currentIndex] = GraphSearch.State.EXPLORED;
	}

}

