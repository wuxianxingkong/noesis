package noesis.model.regular;

public class TandemNetwork extends RegularNetwork 
{
	
	public TandemNetwork (int size)
	{
		setSize(size);
		setID("TANDEM (n="+size+")");
		
		for (int i=0; i<size; i++) {
			
			if (i<size)
			   add(i, i+1);		// Next
			
			if (i>0)
				add(i, i-1);	// Previous
		}
	}
	
	
	public int distance (int origin, int destination)
	{
		return Math.abs( destination - origin );
	}
	
	public int diameter ()
	{
		return size()-1;
	}
	
	public int radius (int i)
	{
		return Math.max ( i, size()-i-1 );
	}
	
	
	public int minDegree ()
	{
		return 1;
	}
	
	public int maxDegree ()
	{
		if (size()>2)
			return 2;
		else
			return size()-1;
	}	
	
	public double averageDegree ()
	{
		if (size()>2)
			return ((size()-2)*2.0+2*1.0) / size();
		else
			return size()-1;
	}
	
	public double averagePathLength ()
	{
		return (size()+1.0)/3.0;
	}
	
	public double averagePathLength (int i)
	{
		double sumLeft  = i*(i+1)/2;
		double sumRight = (size()-i)*(size()-i-1)/2; 
	
		return (sumLeft+sumRight)/(size()-1.0);
	}


	@Override
	public double clusteringCoefficient(int node) 
	{
		return 0;
	}
	
	@Override
	public double betweenness (int node) 
	{
		return 2*(node+1)*(size()-node)-1;
	}	
}
