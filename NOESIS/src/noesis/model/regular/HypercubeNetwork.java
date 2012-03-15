package noesis.model.regular;

public class HypercubeNetwork extends RegularNetwork 
{
	private int dimension;
	
	public HypercubeNetwork (int dimension)
	{
		int size = 1<<dimension;
		int bit;	
		
		setID("HYPERCUBE NETWORK (D="+dimension+")");
		setSize(size);
		
		this.dimension = dimension;
		
		for (int i=0; i<size; i++) {
			for (int d=0; d<dimension; d++) {
				bit = 1<<d;
				add(i, i^bit);
			}
		}
	}
	
	public int distance (int origin, int destination)
	{
		int diff = 0;
		int bit;
		
		for (int d=0; d<dimension; d++) {
			bit = 1<<d;
			if ( (origin&bit) != (destination&bit))
				diff++;
		}
		
		return diff;
	}
	
	public int diameter ()
	{
		return dimension;
	}
	
	public int radius (int node)
	{	
		return dimension;
	}	
	
	
	public int minDegree ()
	{
		return dimension;
	}
	
	public int maxDegree ()
	{
		return dimension; 
	}	
	
	public double averageDegree ()
	{
		return dimension;	}
	
	public double averagePathLength ()
	{
		return ((double)size())/(size()-1)*(dimension/2.0);
	}

	public double averagePathLength (int i)
	{
		return averagePathLength();
	}

	@Override
	public double clusteringCoefficient(int node) 
	{
		return 0.0;
	}
	
	// 2^(D-1) * (D+2) 
	// 2, 8, 20, 48, 112, 256, 576, 1280... 
	// http://oeis.org/A001792
	
	public double betweenness (int node)
	{
		return Math.pow(2,dimension-1)*(dimension+2);
	}
}
