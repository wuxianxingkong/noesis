package noesis.analysis.structure;

import noesis.Network;

public class AveragePathLength extends NodeMetrics 
{
	int diameter;
	
	public AveragePathLength (Network network)
	{
		super(network);
		diameter = 0;
	}	

	
	public double compute(int node) 
	{
		int radius;
		
		PathLength paths = new PathLength(getNetwork(), node);
		
		paths.compute();
		
		radius = (int) paths.max();
		
		if (radius>diameter)
			diameter = radius;
		
		return paths.averagePathLength();
	}	
	
	public double averagePathLength ()
	{
		checkDone();
		
		return average();
	}
	
	public int diameter ()
	{
		checkDone();
		
		return diameter;
	}

}
