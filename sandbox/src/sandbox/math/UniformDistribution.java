package sandbox.math;

/**
 * Uniform distribution 
 * http://en.wikipedia.org/wiki/Uniform_distribution_(continuous)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class UniformDistribution implements Distribution 
{
	private double min;
	private double max;
	
	public UniformDistribution (double min, double max)
	{
		this.min = min;
		this.max = max;
	}
	
	@Override
	public double pdf (double x) 
	{
		if ((x>=min) && (x<=max))
			return 1.0/(max-min);
		else
			return 0.0;
	}

	@Override
	public double cdf (double x) 
	{
		if (x<=min)
			return 0.0;
		else if (x>=max)
			return 1.0;
		else
			return (x-min)/(max-min);
	}

	@Override
	public double idf (double p) 
	{
		return min + p*(max-min);
	}
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}

	@Override
	public double mean() 
	{
		return (min+max)/2;
	}

	@Override
	public double variance() 
	{
		return (max-min)*(max-min)/12;
	}

	@Override
	public double skewness() 
	{
		return 0;
	}

	@Override
	public double kurtosis() 
	{
		return -6.0/5.0;
	}

}
