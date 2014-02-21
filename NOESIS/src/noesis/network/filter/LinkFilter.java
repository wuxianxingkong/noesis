package noesis.network.filter;

import ikor.collection.Set;
import noesis.CollectionFactory;
import noesis.Network;

public class LinkFilter implements NetworkFilter 
{
	private Network   net;
	private Set<Long> linkset;
	
	public LinkFilter (Network net)
	{
		this.net = net;
		this.linkset = CollectionFactory.createSet();
	}
	
	public LinkFilter (Network net, int sourceIndex, int destinationIndex)
	{
		this(net);
		removeLink (sourceIndex, destinationIndex);
	}
	
	public void removeLink (int source, int destination)
	{
		int degree = net.outDegree(source);
		
		for (int link=0; link<degree; link++) {
			if ( net.outLink(source, link) == destination ) {
				linkset.add ( key(source,link));
			}
		}
	}
	
	public void removeLinkByIndex (int node, int link)
	{
		linkset.add ( key(node,link) );
	}
	
	private long key (int node, int link)
	{
		return (((long)node)<<32) | link;
	}
	
	@Override
	public boolean node (int node) 
	{
		return true;
	}

	@Override
	public boolean link (int node, int link) 
	{
		return !linkset.contains( key(node,link) );
	}

}
