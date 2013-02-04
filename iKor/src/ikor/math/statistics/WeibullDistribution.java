package ikor.math.statistics;

import ikor.math.Functions;
import ikor.math.random.Random;

/**
 * Weibull distribution (generalization of the exponential distribution, useful in hazard, survival, or reliability studies)
 * http://en.wikipedia.org/wiki/Weibull_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class WeibullDistribution implements Distribution 
{
	private double alpha;
	private double beta;
	
	/**
	 * Weibull distribution
	 * @param alpha > 0 (shape parameter, a.k.a. k)
	 * @param beta > 0 (scale parameter, a.k.a. lambda)
	 */
	
	public WeibullDistribution (double alpha, double beta)
	{
		this.alpha = alpha;
		this.beta = beta;
	}
	
	@Override
	public double pdf (double x) 
	{
		if (x<0)
			return 0;
		else
			return (alpha/beta) * Math.pow(x/beta, alpha-1) * Math.exp ( -Math.pow(x/beta,alpha) );
	}

	@Override
	public double cdf (double x) 
	{
		if (x<0)
			return 0;
		else
			return 1 - Math.exp( -Math.pow(x/beta,alpha) );
	}

	@Override
	public double idf (double p) 
	{
		if (p==0)
			return 0;
		else if (p==1)
			return Double.POSITIVE_INFINITY;
		else
			return beta * Math.pow( -Math.log(1-p), 1/alpha );
	}
	
	
	// Random number generator
	// -----------------------
	
	@Override
	public double random() 
	{
		return idf ( Random.random() );
	}	

	
	// Statistics
	// ----------
	// lambda -> beta
	// k -> alpha
	
	@Override
	public double mean() 
	{
		return beta*Functions.gamma(1+1/alpha);
	}

	@Override
	public double variance() 
	{
		double mu = mean();
		
		return beta*beta*Functions.gamma(1+2/alpha) - mu*mu;
	}

	@Override
	public double skewness() 
	{
		double mu = mean();
		double sigma2 = variance();

		return ( beta*beta*beta*Functions.gamma(1+3/alpha) - 3*mu*sigma2 - mu*mu*mu ) / Math.pow(sigma2,1.5);
	}

	@Override
	public double kurtosis() 
	{
		double mu = mean();
		double sigma2 = variance();
		double gamma1 = skewness();

		return ( beta*beta*beta*beta*Functions.gamma(1+4/alpha) 
				- 4*gamma1*Math.pow(sigma2,1.5)*mu 
				- 6*mu*mu*sigma2 - mu*mu*mu*mu ) / (sigma2*sigma2) - 3;
	}

}
