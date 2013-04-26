package noesis.analysis.structure;

import noesis.Network;

/**
 * Closeness centrality (suitable for connected graphs).
 * 
 * closeness = (component size - 1) / (sum of path lengths)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class Closeness extends NodeMeasure 
{
	public Closeness (Network network)
	{
		super(network);
	}	

	
	@Override
	public String getName() 
	{
		return "closeness";
	}	

	@Override
	public double compute(int node) 
	{
		Network    network = getNetwork();
		PathLength paths = new PathLength(network, node);
		
		paths.compute();
		
		return paths.closeness();
	}	
}
