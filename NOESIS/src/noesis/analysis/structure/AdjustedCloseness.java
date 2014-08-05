package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.NodeScoreTask;

/**
 * Adjusted closeness centrality (for disconnected graphs), normalized within [0,1]
 * 
 * adjusted closeness = ( (component size - 1) / (sum of path lengths) ) * ( (component size - 1) / (network size - 1) )
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

@Label("adj-closeness")
@Description("Adjusted closeness")
public class AdjustedCloseness extends NodeScoreTask
{
	public AdjustedCloseness (Network network)
	{
		super(network);
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
