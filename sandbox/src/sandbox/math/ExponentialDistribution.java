package sandbox.math;

/**
 * Exponential distribution (a.k.a. negative exponential distribution)
 * http://en.wikipedia.org/wiki/Exponential_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class ExponentialDistribution implements Distribution 
{
	private double beta;
	
	public ExponentialDistribution (double beta)
	{
		this.beta = beta;
	}
	
	@Override
	public double pdf (double x) 
	{
		if (x<0)
			return 0;
		else
			return beta * Math.exp(-beta*x);
	}

	@Override
	public double cdf (double x) 
	{
		if (x<0)
			return 0;
		else
			return 1 - Math.exp(-beta*x);
	}

	@Override
	public double idf (double p) 
	{
		if (p==0)
			return 0;
		else if (p==1)
			return Double.POSITIVE_INFINITY;
		else
			return -Math.log(1-p)/beta;
	}
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}

}

