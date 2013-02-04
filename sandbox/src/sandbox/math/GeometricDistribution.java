package sandbox.math;

/**
 * Geometric distribution: G(p)
 * 
 * Discrete probability distribution of the number Y = X - 1 of failures 
 * before the first success in X Bernoulli trials, supported on the set { 0, 1, 2, 3, ... }
 * 
 * http://en.wikipedia.org/wiki/Geometric_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class GeometricDistribution extends DiscreteDistribution implements Distribution 
{
	private double p;
	
	public GeometricDistribution (double p)
	{
		this.p = p;
	}
	
	@Override
	public double pdf (double x) 
	{
		int k = (int) x;
		
		if (k<0)
			return 0;
		else
			return p * Math.pow ( 1-p, k );
	}

	@Override
	public double cdf (double x) 
	{
		int k = (int) x;
		
		if (k<=0)
			return 0.0;
		else if (k==Double.POSITIVE_INFINITY)
			return 1.0;
		else
			return 1 - Math.pow(1-p, k+1);
	}

	@Override
	public double idf (double p) 
	{
		if (p==0.0)
			return 0;
		else if (p==1.0)
			return Double.POSITIVE_INFINITY;
		else {
			int k = 1; // (p*r/(1-p)) mean value
			return idfsearch (p, k, 0, Integer.MAX_VALUE);
		}
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
		return (1-p)/p;
	}

	@Override
	public double variance() 
	{
		return (1-p)/(p*p);
	}

	@Override
	public double skewness() 
	{
		return (2-p)/Math.sqrt(1-p);
	}

	@Override
	public double kurtosis() 
	{
		return 6 + p*p/(1-p);
	}

}
