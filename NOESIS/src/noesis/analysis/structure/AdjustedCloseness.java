package noesis.analysis.structure;

import noesis.Network;

/**
 * Adjusted closeness centrality (for disconnected graphs), normalized within [0,1]
 * 
 * adjusted closeness = ( (component size - 1) / (sum of path lengths) ) * ( (component size - 1) / (network size - 1) )
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class AdjustedCloseness extends NodeMeasure 
{
	public AdjustedCloseness (Network network)
	{
		super(network);
	}	

	
	@Override
	public String getName() 
	{
		return "adj-closeness";
	}	

	@Override
	public double compute(int node) 
	{
		Network    network = getNetwork();
		PathLength paths = new PathLength(network, node);
		
		paths.compute();
		
		return paths.closeness()*paths.reachable();
	}	
}
