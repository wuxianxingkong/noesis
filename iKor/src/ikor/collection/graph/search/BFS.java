package ikor.collection.graph.search;

// Title:       Graph Search
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.ReadOnlyList;
import ikor.collection.Queue;
import ikor.collection.Visitor;

import ikor.collection.graph.ReadOnlyGraphInterface;
import ikor.collection.graph.GraphNode;
import ikor.collection.graph.GraphLink;

/**
 * Breadth-First Search in Graphs
 * 
 * @author Fernando Berzal
 */

public class BFS<V,E> extends GraphSearch<V,E>
{

    // Constructor

	public BFS (ReadOnlyGraphInterface<V,E> graph)
	{
		super(graph);
	}

	public BFS (ReadOnlyGraphInterface<V,E> graph, Visitor<GraphNode<V>> nodeVisitor, Visitor<GraphLink<E>> linkVisitor)
	{
		super(graph, nodeVisitor, linkVisitor);
	}

	// Graph exploration

	public void explore (GraphNode<V> start)
	{
		Queue<GraphNode<V>> queue = new Queue<GraphNode<V>>();
		int                 currentIndex, targetIndex;
		GraphNode<V>        current, target;
		ReadOnlyList<GraphLink<E>>  links;
		GraphLink<E>        link;

		queue.enqueue ( start );

		while (queue.size()>0) {

			  current      = queue.dequeue();
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

					  if (state[targetIndex] == GraphSearch.State.UNDISCOVERED) {
						  // current -> target
						  queue.enqueue(target);
						  state[targetIndex] = GraphSearch.State.DISCOVERED;
					  }
				  }
			  }

			  state[currentIndex] = GraphSearch.State.EXPLORED;
		}


	}

}

