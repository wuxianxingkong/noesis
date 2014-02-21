package noesis.network.filter;

import noesis.Network;

public class MaskFilter implements NetworkFilter 
{
	private Network net;
	private boolean mask[];

	public MaskFilter (Network net, boolean mask[])
	{
		this.net = net;
		this.mask = mask;
	}
	
	@Override
	public boolean node (int node) 
	{
		return mask[node];
	}

	@Override
	public boolean link (int node, int link) 
	{
		int target = net.outLink(node, link);
		
		return mask[node] && mask[target];
	}

}
