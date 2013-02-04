package sandbox.math;

import ikor.math.Functions;

/**
 * Negative binomial distribution: NB(r,p)
 * 
 * Discrete probability distribution of the number of successes in a sequence of Bernoulli trials 
 * before a specified (non-random) number of failures (denoted r) occur, with success probability p.
 *  
 * http://en.wikipedia.org/wiki/Negative_binomial_distribution
 * 
 * The Pascal distribution and Polya distribution are special cases of the negative binomial. 
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class NegativeBinomialDistribution extends DiscreteDistribution implements Distribution 
{
	private int r;
	private double p;
	
	public NegativeBinomialDistribution (int r, double p)
	{
		this.r = r;
		this.p = p;
	}
	
	@Override
	public double pdf (double x) 
	{
		int k = (int) x;
		
		if (k<0)
			return 0;
		else
			return Math.exp ( k*Math.log(p) + r*Math.log(1-p) + Functions.logBinomial(k+r-1,k) );

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
			return 1 - Functions.betaI(k+1, r, p);
	}

	@Override
	public double idf (double p) 
	{
		if (p==0.0)
			return 0;
		else if (p==1.0)
			return Double.POSITIVE_INFINITY;
		else {
			int k = 0; // (p*r/(1-p)) mean value
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
		return p*r/(1-p);
	}

	@Override
	public double variance() 
	{
		return p*r/((1-p)*(1-p));
	}

	@Override
	public double skewness() 
	{
		return (1+p)/Math.sqrt(p*r);
	}

	@Override
	public double kurtosis() 
	{
		return 6.0/r + (1-p)*(1-p)/(p*r);
	}

}
