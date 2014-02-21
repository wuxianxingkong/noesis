package noesis.network;

import noesis.AttributeNetwork;
import noesis.Network;
import noesis.network.filter.NetworkFilter;


public class FilteredNetwork extends AttributeNetwork 
{
    private AttributeNetwork net;
	private NetworkFilter    filter;
	
	private int index[];
	private int reverse[];
	
	private int nodes;
	private int links;
	
	public FilteredNetwork (Network original, NetworkFilter filter)
	{
		if (original instanceof AttributeNetwork) {
			this.net = (AttributeNetwork) original;
			
			for (int i=0; i<net.getNodeAttributeCount(); i++)
				this.addNodeAttribute( new FilteredAttribute(this,net.getNodeAttribute(i)) );
			
			for (int j=0; j<net.getLinkAttributeCount(); j++)
				this.addLinkAttribute( new FilteredLinkAttribute(this, net.getLinkAttribute(j)) );
			
		} else {
			this.net = new AttributeNetwork(original);
		}
		
		setFilter(filter);
	}
	
	
	public NetworkFilter getFilter ()
	{
		return filter;
	}
	
	public void setFilter (NetworkFilter filter)
	{
		this.filter = filter;
		
		this.index = new int[net.size()];
		this.reverse = new int[net.size()];
		this.nodes = 0;
		this.links = 0;
		
		for (int i=0; i<net.size(); i++) {
			if (filter.node(i)) {
				index[nodes] = i;
				reverse[i] = nodes;
				nodes++;
				
				// Links
				
				int degree = net.outDegree(i);
				
				for (int j=0; j<degree; j++) {
					if (filter.link(i,j)) {
						links++;
					}
				}
			} else {
				reverse[i] = -1;
			}
		}
	}
	
	
	// Network size
	
	/**
	 * Number of nodes in the filtered network, O(1)
	 */
	@Override
	public int nodes ()
	{
		return nodes;
	}
	
	/**
	 * Number of links in the filtered network, O(1)
	 */
	@Override
	public int links ()
	{
		return links;
	}
	
	
	// Nodes
	
	@Override
	public Integer get (int node)
	{
		return index[node];
	}

	@Override
	public boolean contains(Integer node) 
	{
		return ((node>=0) && (node<net.size()) && (reverse[node]!=-1));
	}

	@Override
	public int index(Integer node) 
	{
		if ((node>=0) && (node<net.size()))
			return reverse[node];
		else
			return -1;
	}	
	
	// Node degrees
	
	/**
	 * In-degree, O(d^2)
	 */
	@Override
	public int inDegree(int node) 
	{
		int degree = 0;
		int source;
		int link;
		
		if ((node>=0) && (node<nodes)) {
			int original = index[node];
			int originalDegree = net.inDegree(original);

			if ((node<nodes) && (originalDegree>0)) {
				for (int j=0; j<originalDegree; j++) {
					source = net.inLink(original,j);
					link = net.getLinkIndex(source, original); // O(d)
					if (filter.link(source,link)) {
						degree++;
					}
				}			
			}
		}
		
		return degree;
	}

	/**
	 * Out-degree, O(d)
	 */
	@Override
	public int outDegree(int node) 
	{
		int degree = 0;
		
		if ((node>=0) && (node<nodes)) {
			int original = index[node];
			int originalDegree = net.outDegree(original);

			if (originalDegree>0) {
				for (int j=0; j<originalDegree; j++) {
					if (filter.link(original,j)) {
						degree++;
					}
				}			
			}
		}
		
		return degree;
	}

	
	// Links
	
	/**
	 * Access to individual out-links, O(d)
	 */
	@Override
	public int outLink (int node, int link)
	{
		int original = index[node];
		int current = -1;
		int position = -1;
		
		do {
			
			current++;

			if (filter.link(original,current)) {
				position++;;
			}			
			
		} while (position<link);
		
		return reverse[net.outLink(original,current)];
	}
	

	/**
	 * Access to individual in-links, O(d^2)
	 */
	@Override
	public int inLink (int node, int link)
	{
		int current = -1;
		int position = -1;
		int originalNode = index[node];
		int originalSource;
		int originalLink;
		
		do {
			
			current++;
			
			originalSource = net.inLink(originalNode,current);
			originalLink = net.getLinkIndex(originalSource, originalNode); // O(d)

			if (filter.link(originalSource,originalLink)) {
				position++;;
			}			
			
		} while (position<link);
		
		return reverse[net.inLink(originalNode,current)];
	}
	
}
