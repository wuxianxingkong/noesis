package ikor.math.statistics;

import ikor.math.random.Random;

/**
 * Laplace distribution (a.k.a. double exponential distribution)
 * http://en.wikipedia.org/wiki/Laplace_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class LaplaceDistribution implements Distribution 
{
	private double mu;
	private double b;
	
	/**
	 * Laplace distribution
	 * @param location
	 * @param scale > 0
	 */
	public LaplaceDistribution (double location, double scale)
	{
		this.mu = location;
		this.b  = scale;
	}

	@Override
	public double pdf(double x) 
	{
		return 0.5 * Math.exp ( -Math.abs(x-mu)/b ) / b;
	}

	@Override
	public double cdf(double x) 
	{
		if (x>=mu)
			return 0.5 + 0.5 * ( 1 - Math.exp(-(x-mu)/b) );
		else
			return 0.5 - 0.5 * ( 1 - Math.exp(-(mu-x)/b) );
	}
	

	@Override
	public double idf(double p) 
	{ 
		if (p==0)
			return Double.NEGATIVE_INFINITY;
		else if (p==1)
			return Double.POSITIVE_INFINITY;
		else if (p>=0.5)
			return mu - b * Math.log(1 - 2*(p-0.5) );
		else
			return mu + b * Math.log(1 - 2*(0.5-p) );
	}
	

	// Random number generator
	// Inverse CDF algorithm
	
	private ExponentialDistribution exponential = null;
	
	@Override
	public double random() 
	{
		if (exponential==null)
			exponential = new ExponentialDistribution(1/b);

		// 1. Generate Y and U independently from Exponential(1/ b) and Uniform(0,1), respectively.
		
		double y = exponential.random();
		double u = Random.random();      

		// 2. If U>=0.5, set X=a+Y; otherwise set X=a-Y.
		
		if (u>=0.5)
			return mu + y;
		else
			return mu - y;
	}

	
	// Statistics
	
	@Override
	public double mean() 
	{
		return mu;
	}

	@Override
	public double variance() 
	{
		return 2*b*b;
	}

	@Override
	public double skewness() 
	{
		return 0;
	}

	@Override
	public double kurtosis() 
	{
		return 3;
	}

}
