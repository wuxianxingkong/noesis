package noesis.model.regular;

public class RingNetwork extends RegularNetwork 
{
	
	public RingNetwork (int size)
	{
		setSize(size);
		setID("RING (n="+size+")");
		
		for (int i=0; i<size; i++) {
			add(i, (i+1) % size);		// Next
			add(i, (i+size-1) % size);	// Previous
		}
	}
	
	
	public int distance (int origin, int destination)
	{
		int diff = Math.abs( destination - origin );
		
		if (diff<(size()/2.0))
			return diff;
		else
			return size()-diff;
	}
	
	public int diameter ()
	{
		return size()/2;
	}
	
	public int radius (int i)
	{
		if (size()>2)
			return diameter();
		else
			return size()-1;
	}	
	
	public int minDegree ()
	{
		return 2;
	}
	
	public int maxDegree ()
	{
		return 2;
	}	
	
	public double averageDegree ()
	{
		return 2;
	}
	
	public double averagePathLength ()
	{
		double n = size();
		
		if ((n%2) == 0) {
			return (n*n)/(4*(n-1));
		} else {
			return (n+1)/4;
		}
	}

	public double averagePathLength (int node)
	{
		return averagePathLength();
	}


	@Override
	public double clusteringCoefficient(int node) 
	{
		if (size()==3)
			return 1;
		else
			return 0;
	}
	
	@Override
	public double betweenness (int node) 
	{
		return size()*size()/4 + size();
	}		
}
