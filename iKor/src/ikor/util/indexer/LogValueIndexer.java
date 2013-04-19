package ikor.util.indexer;

public class LogValueIndexer extends ValueIndexer
{
	public LogValueIndexer (double min, double max, int n)
	{
		super(min,max,n);
	}	

	@Override
	public int index (Double value) 
	{
		if ((value!=null) && (maxValue()>minValue()))
			return (int) ( max() * ( Math.log(value-minValue()+1) / Math.log(maxValue()-minValue()+1) ) );
		else
			return 0;
	}
}
