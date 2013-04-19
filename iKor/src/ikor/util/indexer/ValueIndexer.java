package ikor.util.indexer;

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
		if ((value!=null) && (max>min))
			return (int) (n * (value-min)/(max-min));
		else
			return 0;
	}

	public double minValue ()
	{
		return min;
	}
	
	public double maxValue ()
	{
		return max;
	}
	
	@Override
	public int min() 
	{
		return 0;
	}

	@Override
	public int max() 
	{
		return n;
	}

}
