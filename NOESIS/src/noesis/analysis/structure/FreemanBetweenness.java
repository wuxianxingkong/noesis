package noesis.analysis.structure;

import noesis.Network;

// Normalized betweenness centrality, between (2n-1)/(n^2-(n-1)) and 1 in strongly-connected networks

public class FreemanBetweenness extends Betweenness
{
	public FreemanBetweenness (Network network)
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
		return "Freeman's betweenness";
	}		
	
	@Override
	public double get(int node)
	{
		return freemanBetweenness(node); // vs. standardBetweenness(node);
	}
	
	// Conventional normalization (n^2)
	
	public double standardBetweenness (int node)
	{
		int size = getNetwork().size();
		
		checkDone();
		
		return super.get(node)/(size*size); 
	}
	
	// Original version (Freeman'1977)
	
	public double freemanBetweenness (int node)
	{
		int size = getNetwork().size();
		
		checkDone();
		
		return super.get(node)/(size*size-size+1); 
	}	

}
