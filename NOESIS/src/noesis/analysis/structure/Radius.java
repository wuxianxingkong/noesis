package noesis.analysis.structure;

import noesis.Network;

public class Radius extends NodeMetrics 
{
	public Radius (Network network)
	{
		super(network);
	}	

	
	public double compute(int node) 
	{
		PathLength paths = new PathLength(getNetwork(), node);
		
		paths.compute();
		
		return paths.max();
	}	
	
	public int diameter ()
	{
		checkDone();
		
		return (int) max();
	}

}
