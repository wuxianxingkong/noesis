package noesis.analysis.structure;

import noesis.Network;

public class InDegreeDistribution extends NodeMetrics 
{
		
	public InDegreeDistribution (Network network)
	{
		super(network);
	}
	
	@Override
	public void compute() 
	{
		Network net = getNetwork();
		int     size = net.size();
		
		for (int node=0; node<size; node++)
			set (node, net.inDegree(node));
	}

}