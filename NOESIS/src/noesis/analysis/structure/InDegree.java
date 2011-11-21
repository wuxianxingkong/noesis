package noesis.analysis.structure;

import noesis.Network;

public class InDegree extends NodeMetrics 
{
		
	public InDegree (Network network)
	{
		super(network);
	}
	
	public double compute (int node) 
	{
		Network net = getNetwork();
		
		return net.inDegree(node);
	}

}