package noesis.model.regular;

public class StarNetwork extends RegularNetwork 
{
	public final int CENTER = 0;
	
	public StarNetwork (int size)
	{
		setSize(size);
		setID("STAR NETWORK (n="+size+")");
		
		for (int i=1; i<size; i++) {
			add(CENTER,i);
			add(i,CENTER);
		}
	}
	
	public int distance (int origin, int destination)
	{
		if (origin!=destination) {
		
			if ((origin!=CENTER) && (destination!=CENTER)) {
				return 2;
			} else {
				return 1;
			}
			
		} else {
			return 0;
		}
	}
	
	public int diameter ()
	{
		if (size()>2)
			return 2;
		else
			return size()-1;
	}
	
	public int radius (int node)
	{
		if (node==CENTER)
			return 1;
		else if (size()>2)
			return 2;
		else
			return size()-1;
	}	
	
	
	public int minDegree ()
	{
		return 1;
	}
	
	public int maxDegree ()
	{
		return size()-1;
	}
	
	public double averageDegree ()
	{
		return 2.0*(size()-1.0)/size();
	}
	
	public double averagePathLength ()
	{
		return 2.0*(size()-1.0)/size();
	}

	public double averagePathLength (int node)
	{
		if (node==CENTER)
			return 1.0;
		else
			return (2.0*size()-3.0)/(size()-1);
	}

	@Override
	public double clusteringCoefficient(int node) 
	{
		return 0;
	}
	
	public double betweenness (int node)
	{
		if (node==CENTER)
			return size()*size() - (size()-1);
		else
			return 2*size()-1;
	}
}
