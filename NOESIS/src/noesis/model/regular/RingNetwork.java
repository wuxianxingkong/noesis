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
		
		if (diff<size()/2)
			return diff;
		else
			return size()-diff;
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
	
	public double linkEfficiency ()
	{
		return 1 - averagePathLength()/size();
	}

}
