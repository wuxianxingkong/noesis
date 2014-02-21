package noesis.network.filter;

import noesis.CollectionFactory;
import noesis.Network;
import ikor.collection.Set;

public class NodeFilter implements NetworkFilter 
{
	private Network      net;
	private Set<Integer> nodeset;

	public NodeFilter (Network net)
	{
		this.net = net;
		this.nodeset = CollectionFactory.createSet();
	}
	
	public NodeFilter (Network net, int index)
	{
		this(net);
		removeNode(index);
	}

	public void removeNode (int index)
	{
		nodeset.add(index);
	}
	
	@Override
	public boolean node(int node) 
	{
		return !nodeset.contains(node);
	}

	@Override
	public boolean link(int node, int link) 
	{
		int target = net.outLink(node, link);
		
		return !nodeset.contains(node) && !nodeset.contains(target);
	}

}
