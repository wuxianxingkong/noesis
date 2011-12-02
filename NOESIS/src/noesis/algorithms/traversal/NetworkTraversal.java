package noesis.algorithms.traversal;

import ikor.collection.Visitor;

import noesis.Network;

public abstract class NetworkTraversal<V,E> 
{
	public enum State { UNDISCOVERED, DISCOVERED, EXPLORED };

	protected Network<V,E> network;
	protected State[]      state;

	private   Visitor<V> nodeVisitor;
	private   Visitor<E> linkVisitor;


    // Constructor

	public NetworkTraversal (Network<V,E> network)
	{
		this(network,null,null);
	}

	public NetworkTraversal (Network<V,E> network, Visitor<V> nodeVisitor, Visitor<E> linkVisitor)
	{
		this.network = network;
		this.state   = new State[network.size()];

		java.util.Arrays.fill(state, State.UNDISCOVERED);

		this.nodeVisitor = nodeVisitor;
		this.linkVisitor = linkVisitor;
	}

	// Node visitor

	public void setNodeVisitor (Visitor<V> visitor)
	{
		this.nodeVisitor = visitor;
	}

	protected void visitNode (V node)
	{
		if (nodeVisitor!=null)
			nodeVisitor.visit(node);
	}


	// Edge visitor

	public void setLinkVisitor (Visitor<E> visitor)
	{
		this.linkVisitor = visitor;
	}

	protected void visitLink (E link)
	{
		if (linkVisitor!=null)
			linkVisitor.visit(link);
	}


	// Graph exploration

	public final void traverse ()
	{
		traverse (0);
	}

	public abstract void traverse (int index);

	// Explored nodes

	public State status (int index)
	{
		return state[index];
	}
}
