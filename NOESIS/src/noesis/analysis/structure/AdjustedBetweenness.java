package noesis.analysis.structure;

import noesis.Network;
import noesis.algorithms.traversal.StronglyConnectedComponents;

// Betweenness centrality adjusted to component sizes
// - Freeman's betweenness between (2n-1) and (n^2-(n-1)) in strongly-connected networks
// - Adjustment:  ( score - (2n-1) ) where n is the size of the strongly-connected component

public class AdjustedBetweenness extends Betweenness
{
	public AdjustedBetweenness (Network network)
	{
		super(network);
	}	

	@Override
	public String getName() 
	{
		return "betweenness";
	}	

	@Override
	public String getDescription() 
	{
		return "Betweenness";
	}		
	
	@Override
	public double get(int node)
	{
		return adjustedBetweenness(node); 
	}

	// Normalized to the [0,1] interval taking into account component sizes
	
	private StronglyConnectedComponents scc;
	
	public double adjustedBetweenness (int node)
	{
		int size;
		
		checkDone();
		
		if (scc==null) {
			scc = new StronglyConnectedComponents( getNetwork() );
			scc.compute();
		}
		
		size = scc.componentSize(node);
				
		return ( super.get(node) - (2*size-1) ); 
	}	

}
