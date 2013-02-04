package ikor.math.statistics;

import ikor.math.Constants;
import ikor.math.Functions;
import ikor.math.random.Random;

/**
 * Lognormal distribution (a.k.a. Galton distribution)
 * http://en.wikipedia.org/wiki/Log-normal_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class LognormalDistribution implements Distribution 
{
	private double mu;
	private double sigma;
	
	public LognormalDistribution (double mu, double sigma)
	{
		this.mu = mu;
		this.sigma = sigma;
	}
	
	@Override
	public double pdf (double x) 
	{
		if (x<=0) {
			
			return 0;
			
		} else {
			
			double z = (Math.log(x)-mu)/sigma;
			
			return Math.exp(-0.5*z*z) / ( sigma * Constants.SQRT2PI * x);
		}
	}

	@Override
	public double cdf (double x) 
	{
		if (x<=0) {
			
			return 0;
			
		} else {
			
			double z = (Math.log(x)-mu)/sigma;

			return 0.5*Functions.erfc(-z/Constants.SQRT2);
		}
	}

	@Override
	public double idf (double p) 
	{
		if (p==0)
			return 0.0;
		else
			return Math.exp(mu - Constants.SQRT2*sigma*Functions.erfcinv(2*p));	
	}
	
	
	// Random number generator
	
	@Override
	public double random() 
	{
		return Math.exp( mu + sigma*Random.gaussian() );
	}
	
	
	// Statistics

	@Override
	public double mean() 
	{
		return Math.exp ( mu + sigma*sigma/2 );
	}

	@Override
	public double variance() 
	{
		return ( Math.exp(sigma*sigma) - 1 ) * Math.exp ( 2*mu + sigma*sigma );
	}

	@Override
	public double skewness() 
	{
		return ( Math.exp(sigma*sigma) + 2 ) * Math.sqrt( Math.exp (sigma*sigma ) - 1 );
	}

	@Override
	public double kurtosis() 
	{
		return Math.exp(4*sigma*sigma) + 2*Math.exp(3*sigma*sigma) + 3*Math.exp(2*sigma*sigma) - 6;
	}

}
