package noesis.analysis.structure;

import noesis.Network;

public class NormalizedDegree extends NodeMeasure 
{
		
	public NormalizedDegree (Network network)
	{
		super(network);
	}
	
	@Override
	public String getName() 
	{
		return "degree-norm";
	}	
	
	@Override
	public String getDescription() 
	{
		return "Normalized total degree";
	}		
	
	@Override
	public double compute (int node) 
	{
		Network net = getNetwork();
		
		if (net.size()>1)
			return ((double) net.inDegree(node) + net.outDegree(node))/(2.0*(net.size()-1));
		else
			return 0;
	}

}