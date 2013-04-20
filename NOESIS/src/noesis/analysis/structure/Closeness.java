package noesis.analysis.structure;

import noesis.Network;

public class Closeness extends NodeMetrics 
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
		double     averagePathLength;
		PathLength paths = new PathLength(getNetwork(), node);
		
		paths.compute();
		
		averagePathLength = paths.averagePathLength();
		
		if (averagePathLength>0)
			return 1.0/averagePathLength * paths.reachable(); // Normalized according to number of reachable nodes
		else
			return 0.0;
	}	
}
