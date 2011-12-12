package noesis.algorithms.traversal;

import ikor.collection.Visitor;
import ikor.collection.util.Pair;

import noesis.Network;

public abstract class NetworkTraversal<V,E> 
{
	public enum State { UNDISCOVERED, DISCOVERED, EXPLORED };

	protected Network<V,E> network;
	protected State[]      state;

	private   Visitor<V>       nodeVisitor;
	private   Visitor<E>       linkVisitor;
	private   Visitor<Integer>               nodeIndexVisitor;
	private   Visitor<Pair<Integer,Integer>> linkIndexVisitor;


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

		this.nodeVisitor = nodeVisitor;
		this.linkVisitor = linkVisitor;
	}

	// Index visitors

	public void setNodeIndexVisitor (Visitor<Integer> visitor)
	{
		this.nodeIndexVisitor = visitor;
	}

	public void setLinkIndexVisitor (Visitor<Pair<Integer,Integer>> visitor)
	{
		this.linkIndexVisitor = visitor;
	}	
	
	// Node visitor

	public void setNodeVisitor (Visitor<V> visitor)
	{
		this.nodeVisitor = visitor;
	}

	// Edge visitor

	public void setLinkVisitor (Visitor<E> visitor)
	{
		this.linkVisitor = visitor;
	}


	// Graph exploration

	public final void traverse ()
	{
		traverse (0);
	}

	public abstract void traverse (int index);

	
	protected final void visitNode (int node)
	{
		if (nodeIndexVisitor!=null)
			nodeIndexVisitor.visit(node);
		
		if (nodeVisitor!=null) {
			V nodeContent = network.get(node);
			nodeVisitor.visit(nodeContent);
		}
	}	
	
	protected final void visitLink (int source, int destination)
	{
		if (linkIndexVisitor!=null)
			linkIndexVisitor.visit( new Pair(source,destination) );
		
		if (linkVisitor!=null) {
			E linkContent = network.get(source,destination);
			linkVisitor.visit(linkContent);
		}
	}	
	
	// Explored nodes

	public State status (int index)
	{
		return state[index];
	}
}
