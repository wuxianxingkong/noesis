package noesis.analysis.structure;

import noesis.Network;

public class OutDegree extends NodeMetrics 
{
		
	public OutDegree (Network network)
	{
		super(network);
	}
	
	public double compute (int node) 
	{
		Network net = getNetwork();

		return net.outDegree(node);
	}

}
