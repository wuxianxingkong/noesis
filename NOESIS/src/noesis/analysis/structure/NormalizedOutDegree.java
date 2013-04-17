package noesis.analysis.structure;

import noesis.Network;

public class NormalizedOutDegree extends NodeMetrics 
{
		
	public NormalizedOutDegree (Network network)
	{
		super(network);
	}
	
	@Override
	public String getName() 
	{
		return "out-degree-norm";
	}	
	
	@Override
	public String getDescription() 
	{
		return "Normalized out-degree";
	}			
	
	@Override
	public double compute (int node) 
	{
		Network net = getNetwork();
		
		return ((double)net.outDegree(node))/(net.size()-1);
	}

}