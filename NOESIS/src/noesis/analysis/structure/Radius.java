package noesis.analysis.structure;

import ikor.model.data.DataModel;
import noesis.Network;

public class Radius extends NodeMetrics 
{
	public Radius (Network network)
	{
		super(network);
	}	

	@Override
	public String getName() 
	{
		return "radius";
	}	
	
	@Override
	public DataModel getModel()
	{
		return INTEGER_MODEL;
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
