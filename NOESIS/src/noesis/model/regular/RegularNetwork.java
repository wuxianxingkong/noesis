package noesis.model.regular;

import noesis.SimpleNetwork;

public abstract class RegularNetwork extends SimpleNetwork 
{
	public abstract int distance (int origin, int destination);

	public abstract int diameter ();
	
	public abstract int radius (int node);
	
	public abstract int minDegree ();
	
	public abstract int maxDegree ();

	public abstract double averageDegree ();
	
	public abstract double averagePathLength ();

	public abstract double averagePathLength (int node);
	
	public final double closeness (int node)
	{
		double averagePathLength = averagePathLength(node);
		
		if (averagePathLength>0)
			return 1.0/averagePathLength;
		else
			return 0.0;
	}
	
	public final double linkEfficiency ()
	{
		return 1 - averagePathLength()/size();
	}

	public abstract double clusteringCoefficient (int node);
	
	public abstract double betweenness (int node);
}
