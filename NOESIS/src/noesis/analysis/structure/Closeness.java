package noesis.analysis.structure;

import noesis.Network;

public class Closeness extends NodeMetrics 
{
	public Closeness (Network network)
	{
		super(network);
	}	

	public double compute(int node) 
	{
		double     averagePathLength;
		PathLength paths = new PathLength(getNetwork(), node);
		
		paths.compute();
		
		averagePathLength = paths.averagePathLength();
		
		if (averagePathLength>0)
			return 1.0/averagePathLength;
		else
			return 0.0;
	}	
}
