package noesis.analysis.structure;

import noesis.Network;

// Betweenness centrality, between (2n-1) and n^2-(n-1)

public class Betweenness extends NodeMetrics 
{
	public Betweenness (Network network)
	{
		super(network);
	}	

	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
		BetweennessScore score;
	
		for (int node=0; node<size; node++) {
			
			score = new BetweennessScore(net,node);
			score.compute();
			
			for (int i=0; i<size; i++) {
			    set (i, get(i) + score.get(i) );
			}
		}
		
		done = true;
	}	
	
	public double compute(int node) 
	{
		checkDone();		
		return get(node);
	}	
	
	// Conventional normalization (n^2)
	
	public double standardBetweenness (int node)
	{
		int size = getNetwork().size();
		
		checkDone();
		
		return get(node)/(size*size); 
	}
	
	// Original normalization: Freeman'1977
	
	public double normalizedBetweenness (int node)
	{
		int size = getNetwork().size();
		
		checkDone();
		
		return get(node)/(size*size-size+1); 
	}
}
