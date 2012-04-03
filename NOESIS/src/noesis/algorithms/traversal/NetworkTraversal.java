package noesis.algorithms.traversal;

import ikor.collection.Visitor;

import noesis.Network;
import noesis.algorithms.NodeVisitor;
import noesis.algorithms.LinkVisitor;

public abstract class NetworkTraversal<V,E> 
{
	public enum State { UNDISCOVERED, DISCOVERED, EXPLORED };

	protected Network<V,E> network;
	protected State[]      state;

	private Visitor<V>  nodeContentVisitor;
	private Visitor<E>  linkContentVisitor;
	
	private NodeVisitor nodeVisitor;
	private LinkVisitor linkVisitor;


    // Constructor

	public NetworkTraversal (Network<V,E> network)
	{
		this(network,null,null);
	}

	public NetworkTraversal (Network<V,E> network, Visitor<V> nodeVisitor)
	{
		this(network,nodeVisitor,null);
	}
	
	public NetworkTraversal (Network<V,E> network, Visitor<V> nodeVisitor, Visitor<E> linkVisitor)
	{
		this.network = network;
		this.state   = new State[network.size()];

		java.util.Arrays.fill(state, State.UNDISCOVERED);

		this.nodeContentVisitor = nodeVisitor;
		this.linkContentVisitor = linkVisitor;
	}

	// Index visitors

	public void setNodeVisitor (NodeVisitor nodeVisitor)
	{
		this.nodeVisitor = nodeVisitor;
	}
	
	public void setLinkVisitor (LinkVisitor linkVisitor)
	{
		this.linkVisitor = linkVisitor;
	}	
	
	// Node visitor

	public void setNodeVisitor (Visitor<V> visitor)
	{
		this.nodeContentVisitor = visitor;
	}
	
	// Edge visitor

	public void setLinkVisitor (Visitor<E> visitor)
	{
		this.linkContentVisitor = visitor;
	}


	// Graph exploration

	public final void traverse ()
	{
		for (int i=0; i<network.size(); i++)
			if (state[i] == State.UNDISCOVERED)
		       traverse (i);
	}

	public abstract void traverse (int index);

	
	protected final void visitNode (int node)
	{
		if (nodeVisitor!=null)
			nodeVisitor.visit(node);
		
		if (nodeContentVisitor!=null) {
			V nodeContent = network.get(node);
			nodeContentVisitor.visit(nodeContent);
		}
	}	
	
	protected final void visitLink (int source, int destination)
	{
		if (linkVisitor!=null)
			linkVisitor.visit(source,destination);
		
		if (linkContentVisitor!=null) {
			E linkContent = network.get(source,destination);
			linkContentVisitor.visit(linkContent);
		}
	}	
	
	// Explored nodes

	public State status (int index)
	{
		return state[index];
	}
}
