package noesis.algorithms.traversal;

import ikor.collection.Visitor;

import noesis.Network;

public class NetworkDFS<V,E> extends NetworkTraversal<V,E>
{
    // Constructor

	public NetworkDFS (Network<V,E> network)
	{
		super(network);
	}

	public NetworkDFS (Network<V,E> network, Visitor<V> nodeVisitor, Visitor<E> linkVisitor)
	{
		super(network, nodeVisitor, linkVisitor);
	}

	// Network traversal

	public void traverse (int current)
	{
		int   target;
		int[] links;

   	    state[current] = NetworkTraversal.State.DISCOVERED;

		visitNode(network.get(current));

		links = network.outLinks(current);

		if (links!=null) {

			for (int i=0; i<links.length; i++) {

				target = links[i];

				visitLink (network.get(current, target));

				if (state[target]==NetworkTraversal.State.UNDISCOVERED) {
					traverse(target);
				}
			}
		}

   	    state[current] = NetworkTraversal.State.EXPLORED;
	}

}

