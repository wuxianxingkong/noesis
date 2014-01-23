package noesis.network;

// Title:       Link index for networks
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import noesis.Network;

/**
 * Link index. Given a network, map its links to integers.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class LinkIndex implements LinkIndexer 
{
	private Network net;
	private int nodes;
	private int links;
	private int index[];

	/**
	 * Constructor, O(n).
	 * @param net Underlying network.
	 */
	public LinkIndex (Network net)
	{
		this.net = net;
		
		rebuild();
	}

	/**
	 * Underlying network, O(1).
	 * @return The network the index is based on.
	 */
	public Network network ()
	{
		return net;
	}
	
	/**
	 * Nodes in the underlying network, O(1).
	 * @return Number of nodes in the network when the index was built. 
	 * @see noesis.network.LinkIndexer#nodes()
	 */
	@Override
	public int nodes ()
	{
		return nodes;
	}
	
	/**
	 * Links in the underlying network, O(1).
	 * @return Number of links in the network when the index was built.
	 * @see noesis.network.LinkIndexer#links()
	 */
	@Override
	public int links ()
	{
		return links;
	}
	
	/**
	 * Rebuild link index after changes to the underlying network, O(n).
	 */
	public void rebuild ()
	{
		this.nodes = net.size();
		this.links = net.links();
		this.index = new int[net.size()];
		
		int currentLink = 0;
		
		for (int node=0; node<net.size(); node++) {
			index[node] = currentLink;
			currentLink += net.outDegree(node);
		}				
	}
	
	
	/**
	 * Index of a given link, O(d).
	 * @param source Source node index.
	 * @param destination Destination node index.
	 * @return Link index (0..m-1), -1 if link does not exist.
	 * @see noesis.network.LinkIndexer#index(int, int)
	 */
	@Override
	public int index (int source, int destination)
	{
		int base = index[source];
		int link = 0;
		int degree = net.outDegree(source); 
		
		while ((link<degree) && (net.outLink(source, link)!=destination) )
			link++;
		
		if (link<degree)
			return base + link;
		else
			return -1;
	}
	
	
	/**
	 * Source node of a given link, O(log n).
	 * @param link Link index
	 * @return Source node index of the corresponding link
	 * @see noesis.network.LinkIndexer#source(int)
	 */
	@Override
	public int source (int link)
	{
		if (link>=links)
			return -1;
		
		// Pseudo-binary search
		
		int left = 0;
		int right = index.length-1;
		int middle = (left+right)/2;
		
		
		while ((left<=right) && (index[middle]!=link)) {
			
			if (link<index[middle]) {
				right = middle-1;
			} else {
				left = middle+1;
			}
			
			middle = (left+right)/2;
			
		}
		
		if (left>right)
			return left-1;
		else
			return middle;
	}
	
	
	/**
	 * Destination node of a given link, O(log n).
	 * @param link Link index
	 * @return Destination node index
	 * @see noesis.network.LinkIndexer#destination(int)
	 */
	@Override
	public int destination (int link)
	{
		int sourceNode = source(link);
		
		if (sourceNode!=-1)
			return net.outLink(sourceNode, link-index[sourceNode]);
		else
			return -1;
	}
	
	
}
