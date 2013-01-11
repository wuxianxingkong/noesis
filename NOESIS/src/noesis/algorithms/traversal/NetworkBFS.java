package noesis.algorithms.traversal;

import ikor.collection.Queue;
import ikor.collection.Visitor;

import noesis.Network;

public class NetworkBFS<V,E> extends NetworkTraversal<V,E>
{
    // Constructor

	public NetworkBFS (Network<V,E> graph)
	{
		super(graph);
	}

	public NetworkBFS (Network<V,E> graph, Visitor<V> nodeVisitor, Visitor<E> linkVisitor)
	{
		super(graph, nodeVisitor, linkVisitor);
	}

	// Network traversal

	public void traverse (int start)
	{
		Queue<Integer> queue = new Queue<Integer>();
		int            current, target, degree;

		queue.enqueue ( start );

		while (queue.size()>0) {

			  current = queue.dequeue();

			  state[current] = NetworkTraversal.State.DISCOVERED;

			  visitNode(current);
			  
			  degree = network.outDegree(current);

			  for (int i=0; i<degree; i++) {

				  target = network.outLink(current,i); 

				  visitLink (current, target);

				  if (state[target] == NetworkTraversal.State.UNDISCOVERED) {
					  queue.enqueue(target);
					  state[target] = NetworkTraversal.State.DISCOVERED;
				  }
			  }

			  state[current] = NetworkTraversal.State.EXPLORED;
		}


	}

}
