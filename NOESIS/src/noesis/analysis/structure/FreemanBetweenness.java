package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.NodeScore;

// Normalized betweenness centrality, between (2n-1)/(n^2-(n-1)) and 1 in strongly-connected networks

@Label("f-betweenness")
@Description("Freeman's betweenness")
public class FreemanBetweenness extends Betweenness
{
	public FreemanBetweenness (Network network)
	{
		super(network);
	}	

	
	@Override
	public void compute ()
	{
		super.compute();

		// Normalization
		
		NodeScore score = getResult();

		for (int node=0; node<getNetwork().size(); node++) {
			score.set ( node, freemanBetweenness(node) );    // vs. standardBetweenness(node);
		}
		
		setResult(score);
	}	
	
	// Conventional normalization (n^2)
	
	protected double standardBetweenness (int node)
	{
		int size = getNetwork().size();
		
		return getResult(node)/(size*size); 
	}
	
	// Original version (Freeman'1977)
	
	protected double freemanBetweenness (int node)
	{
		int size = getNetwork().size();
		
		return getResult(node)/(size*size-size+1); 
	}	

}
