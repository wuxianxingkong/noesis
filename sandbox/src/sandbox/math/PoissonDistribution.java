package sandbox.math;

/**
 * Poisson distribution:
 * 
 * Discrete probability distribution that expresses the probability of 
 * a given number of events occurring in a fixed interval of time and/or space 
 * if these events occur with a known average rate 
 *    and independently of the time since the last event.
 *     
 * http://en.wikipedia.org/wiki/Poisson_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class PoissonDistribution extends DiscreteDistribution implements Distribution 
{
	private double lambda;
	
	/**
	 * Poisson distribution
	 * @param lambda > 0 (mean and variance of the Poisson distribution)
	 */
	public PoissonDistribution (double lambda)
	{
		this.lambda = lambda;
	}
	
	@Override
	public double pdf (double x) 
	{
		int n = (int) x;
		
		if (n<0)
			return 0;
		else
			return Math.exp(-lambda + n*Math.log(lambda) - Functions.logGamma(n+1));
	}

	@Override
	public double cdf (double x) 
	{
		int n = (int) x;
		
		if (n<=0)
			return 0;
		else
			return Functions.gammaQ(n,lambda);
	}

	@Override
	public double idf (double p) 
	{
		if (p==0)
			return 0;
		else if (p==1)
			return Double.POSITIVE_INFINITY;
		else if (p < Math.exp(-lambda)) 
			return 0;
		else {
			int n = (int) Math.max(Math.sqrt(lambda),5);
			return idfsearch (p, n, 0, Integer.MAX_VALUE);
		}
	}
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}

}

