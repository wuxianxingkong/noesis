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
	
	
	public double averageDegree ()
	{
		return ((size()-2)*2.0+2*1.0) / size();
	
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
	
	public double linkEfficiency ()
	{
		return 1 - averagePathLength()/size();
	}

}
