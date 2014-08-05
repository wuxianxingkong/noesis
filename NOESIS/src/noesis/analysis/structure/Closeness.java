package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.NodeScoreTask;

/**
 * Closeness centrality (suitable for connected graphs).
 * 
 * closeness = (component size - 1) / (sum of path lengths)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

@Label("closeness")
@Description("Closeness")
public class Closeness extends NodeScoreTask 
{
	public Closeness (Network network)
	{
		super(network);
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
