package sandbox.math;

import ikor.math.Functions;

/**
 * Binomial distribution: B(n,p)
 * 
 * Discrete probability distribution of the number of successes 
 * in a sequence of n independent yes/no experiments, 
 * each of which yields success with probability p.
 *  
 * http://en.wikipedia.org/wiki/Binomial_distribution
 * 
 * When n = 1, the binomial distribution is a Bernoulli distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class BinomialDistribution extends DiscreteDistribution implements Distribution 
{
	private int n;
	private double p;
	
	public BinomialDistribution (int n, double p)
	{
		this.n = n;
		this.p = p;
	}
	
	@Override
	public double pdf (double x) 
	{
		int k = (int) x;
		
		if ((k<0) || (k>n))
			return 0;
		else
			return Math.exp ( k*Math.log(p) + (n-k)*Math.log(1-p) + Functions.logBinomial(n,k) );

	}

	@Override
	public double cdf (double x) 
	{
		int k = (int) x;
		
		if (k<=0)
			return 0;
		else if (k>=n)
			return 1;
		else
			return 1 - Functions.betaI(k, n-k+1, p);
	}

	@Override
	public double idf (double p) 
	{
		if (p==0)
			return 0;
		else if (p==1)
			return n;
		else {
			int k = (int) Math.max ( 0, Math.min( n, (int)(n*p) ) );
			return idfsearch ( p, k, 0, (int)n+1);
		}
	}
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}

}
