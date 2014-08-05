package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.algorithms.traversal.StronglyConnectedComponents;
import noesis.analysis.NodeScore;

// Normalized betweenness centrality, between 0 and 1.
// - Freeman's betweenness between (2n-1) and (n^2-(n-1)) in strongly-connected networks
// - Normalization:  ( score - (2n-1) ) / ( n^2 - (n-1) - (2n-1) ) where n is the size of the strongly-connected component

@Label("norm-betweenness")
@Description("Normalized betweenness")
public class NormalizedBetweenness extends Betweenness
{
	public NormalizedBetweenness (Network network)
	{
		super(network);
	}	

	
	@Override
	public void compute ()
	{
		super.compute();

		NodeScore score = getResult();
		
		// Normalization into the [0,1] interval taking into account component sizes
		
		StronglyConnectedComponents scc;
		
		scc = new StronglyConnectedComponents( getNetwork() );
		scc.compute();

		for (int node=0; node<getNetwork().size(); node++) {
			int size = scc.componentSize(node);
			score.set ( node, ( score.get(node) - (2*size-1) ) /(size*size-size+1) );
		}
		
		setResult(score);
	}
}
