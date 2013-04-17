package noesis.io.graphics;

public class ValueIndexer extends Indexer<Double> 
{
	private double min;
	private double max;
	private int    n;
	
	public ValueIndexer (double min, double max, int n)
	{
		this.min = min;
		this.max = max;
		this.n   = n;
	}	

	@Override
	public int index (Double value) 
	{
		if (max>min)
			return (int) (n * (value-min)/(max-min));
		else
			return 0;
	}

}
