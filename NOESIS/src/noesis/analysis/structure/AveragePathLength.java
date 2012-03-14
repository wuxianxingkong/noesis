package noesis.analysis.structure;

import noesis.Network;

public class AveragePathLength extends NodeMetrics 
{
	public AveragePathLength (Network network)
	{
		super(network);
	}	

	
	public double compute(int node) 
	{
		PathLength paths = new PathLength(getNetwork(), node);
		
		paths.compute();
		
		return paths.averagePathLength();
	}	
	
	public double averagePathLength ()
	{
		checkDone();
		
		return getVector().average();
	}	

}
