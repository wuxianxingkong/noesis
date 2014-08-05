package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.NodeScoreTask;

@Label("avg-path-length")
@Description("Average path length")
public class AveragePathLength extends NodeScoreTask 
{
	private int diameter;
	
	
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
		
		radius = (int) paths.getResult().max();
		
		if (radius>diameter)
			diameter = radius;
		
		return paths.averagePathLength();
	}	
	
	public double averagePathLength ()
	{
		checkDone();
		
		return getResult().average();
	}
	
	public int diameter ()
	{
		checkDone();
		
		return diameter;
	}

}
