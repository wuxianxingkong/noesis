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
	
	public double linkEfficiency ()
	{
		return 1 - averagePathLength()/size();
	}

}
