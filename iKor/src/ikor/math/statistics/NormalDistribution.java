package ikor.math.statistics;

import ikor.math.Constants;
import ikor.math.Functions;
import ikor.math.random.Random;

/**
 * Normal distribution (a.k.a. Gaussian distribution)
 * http://en.wikipedia.org/wiki/Normal_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class NormalDistribution implements Distribution 
{
	private double mu;
	private double sigma;
	
	public NormalDistribution (double mean, double deviation)
	{
		this.mu = mean;
		this.sigma = deviation;
	}
	
	@Override
	public double pdf (double x) 
	{
		return Math.exp(-(x-mu)*(x-mu)/(2*sigma*sigma)) / ( sigma * Constants.SQRT2PI);
	}

	@Override
	public double cdf (double x) 
	{
		return Functions.Phi((x-mu)/sigma);
	}

	@Override
	public double idf (double p) 
	{
		return mu + sigma*Functions.probit(p);
	}
	
	// Random number generation
	//
	// - IDF: idf(U(0,1)) will follow the normal distribution.
	//   Drawback: relies on the calculation of the probit function, which cannot be done analytically.
	//
	// - The Box–Muller method uses two independent random numbers.
	//
	// - The Marsaglia polar method is a modification of the Box–Muller method algorithm, 
    //   which does not require computation of functions sin() and cos().
	//
	// - The Ratio method is a rejection method.
	//
	// - The ziggurat algorithm Marsaglia & Tsang (2000) is faster than the Box–Muller transform and still exact.
	
	@Override
	public double random() 
	{
		return mu + sigma*Random.gaussian();  // using a N(0,1) random number generator
	}
	
	
	// Statistics

	@Override
	public double mean() 
	{
		return mu;
	}
	
	public double deviation()
	{
		return sigma;
	}

	@Override
	public double variance() 
	{
		return sigma*sigma;
	}

	@Override
	public double skewness() 
	{
		return 0;
	}

	@Override
	public double kurtosis() 
	{
		return 0;
	}

}
