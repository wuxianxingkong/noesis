package noesis.algorithms.traversal;

import ikor.collection.Visitor;

import noesis.Network;
import noesis.algorithms.NodeVisitor;

public class NetworkDFS<V,E> extends NetworkTraversal<V,E>
{
	private Visitor<V>  postContentVisitor;
	private NodeVisitor postVisitor;
	
	public void setNodePostVisitor (NodeVisitor nodeVisitor)
	{
		this.postVisitor = nodeVisitor;
	}	

	public void setNodePostVisitor (Visitor<V> visitor)
	{
		this.postContentVisitor = visitor;
	}	
	
	
	protected final void postVisitNode (int node)
	{
		if (postVisitor!=null)
			postVisitor.visit(node);
		
		if (postContentVisitor!=null) {
			V nodeContent = network.get(node);
			postContentVisitor.visit(nodeContent);
		}
	}	
	
    // Constructor

	public NetworkDFS (Network<V,E> network)
	{
		super(network);
	}

	public NetworkDFS (Network<V,E> network, Visitor<V> nodeVisitor)
	{
		super(network, nodeVisitor, null);
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

		visitNode(current);

		links = network.outLinks(current);

		if (links!=null) {

			for (int i=0; i<links.length; i++) {

				target = links[i];

				visitLink (current, target);

				if (state[target]==NetworkTraversal.State.UNDISCOVERED) {
					traverse(target);
				}
			}
		}
		
		postVisitNode(current);

   	    state[current] = NetworkTraversal.State.EXPLORED;
	}

}

