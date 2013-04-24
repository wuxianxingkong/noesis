package noesis.analysis.structure;

import noesis.Network;

// Normalized betweenness centrality, between (2n-1)/(n^2-(n-1)) and 1 -> [0,1]

public class NormalizedBetweenness extends Betweenness
{
	public NormalizedBetweenness (Network network)
	{
		super(network);
	}	

	@Override
	public String getName() 
	{
		return "f-betweenness";
	}	

	@Override
	public String getDescription() 
	{
		return "Normalized betweenness";
	}		
	
	@Override
	public double get(int node)
	{
		return normalizedBetweenness(node); 
	}
	
	// Conventional betweenness normalization (n^2)
	
	@Override
	public double standardBetweenness (int node)
	{
		int size = getNetwork().size();
		
		checkDone();
		
		return super.get(node)/(size*size); 
	}
	
	// Original betweenness normalization adjusted to the [0,1] interval: Freeman'1977
	
	@Override
	public double normalizedBetweenness (int node)
	{
		int size = getNetwork().size();
		
		checkDone();
		
		return ( super.get(node) - 2*size + 1) / (size*size-size+1); 
	}
}
