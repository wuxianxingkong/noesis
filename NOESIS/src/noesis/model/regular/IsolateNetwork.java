package noesis.model.regular;

// Network without links

public class IsolateNetwork extends RegularNetwork 
{
	
	public IsolateNetwork (int size)
	{
		setSize(size);
		setID("ISOLATE NETWORK (n="+size+")");
	}
	
	
	public int distance (int origin, int destination)
	{
		return 0;
	}
	
	public int diameter ()
	{
		return 0;
	}
	
	public int radius (int i)
	{
		return diameter();
	}	
	
	public int minDegree ()
	{
		return 0;
	}
	
	public int maxDegree ()
	{
		return 0;
	}	
	
	public double averageDegree ()
	{
		return 0;
	}
	
	public double averagePathLength ()
	{
		return 0;
	}

	public double averagePathLength (int i)
	{
		return averagePathLength();
	}


	@Override
	public double clusteringCoefficient(int node) 
	{
		return 0;
	}
}
