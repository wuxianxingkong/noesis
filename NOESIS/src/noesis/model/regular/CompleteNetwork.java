package noesis.model.regular;

public class CompleteNetwork extends RegularNetwork 
{
	
	public CompleteNetwork (int size)
	{
		setSize(size);
		setID("COMPLETE NETWORK (n="+size+")");
		
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				if (j!=i)
					add(i,j);
			}
		}
	}
	
	public int distance (int origin, int destination)
	{
		if (origin!=destination)
			return 1;
		else
			return 0;
	}
	
	public int diameter ()
	{
		return 1;
	}
	
	public int radius (int i)
	{
		return diameter();
	}	
	
	
	public int minDegree ()
	{
		return size()-1;
	}
	
	public int maxDegree ()
	{
		return size()-1;
	}	
	
	public double averageDegree ()
	{
		return size()-1;
	}
	
	public double averagePathLength ()
	{
		return 1;
	}

	public double averagePathLength (int i)
	{
		return averagePathLength();
	}

	@Override
	public double clusteringCoefficient(int node) 
	{
		return 1.0;
	}
}
