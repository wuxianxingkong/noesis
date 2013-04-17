package noesis.analysis.structure;

import noesis.Network;

public class NormalizedInDegree extends NodeMetrics 
{
		
	public NormalizedInDegree (Network network)
	{
		super(network);
	}
	
	@Override
	public String getName() 
	{
		return "in-degree-norm";
	}	
	
	@Override
	public String getDescription() 
	{
		return "Normalized in-degree";
	}		
	
	@Override
	public double compute (int node) 
	{
		Network net = getNetwork();
		
		return ((double)net.inDegree(node))/(net.size()-1);
	}

}