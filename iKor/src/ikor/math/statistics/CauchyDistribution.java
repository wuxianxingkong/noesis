package ikor.math.statistics;

import ikor.math.random.Random;

/**
 * Cauchy distribution (a.k.a. Lorentz distrbution)
 * http://en.wikipedia.org/wiki/Cauchy_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class CauchyDistribution implements Distribution 
{
	private double mu;
	private double sigma;
	
	public CauchyDistribution (double center, double width)
	{
		this.mu = center;
		this.sigma = width;
	}
	
	@Override
	public double pdf (double x) 
	{
		double z = (x-mu)/sigma;
		
		return 1.0 / (  Math.PI * sigma * ( 1.0 + z*z ) );
	}

	@Override
	public double cdf (double x) 
	{
		return 0.5 + Math.atan2(x-mu,sigma)/Math.PI;
	}

	@Override
	public double idf (double p) 
	{
		if (p==0)
			return Double.NEGATIVE_INFINITY;
		else if (p==1)
			return Double.POSITIVE_INFINITY;
		else
			return mu + sigma*Math.tan(Math.PI*(p-0.5));
	}
	
	
	// Random number generator
	
	@Override
	public double random() 
	{
		double v1,v2;
		
		do {
			v1=2.0*Random.random()-1.0;
			v2=Random.random();
		} while ( (v1*v1+v2*v2 >= 1.0) || (v2 == 0.0));
		
		return mu + sigma*v1/v2;		
	}

	
	// Statistics
	
	@Override
	public double mean() 
	{
		return Double.NaN;
	}

	@Override
	public double variance() 
	{
		return Double.NaN;
	}

	@Override
	public double skewness() 
	{
		return Double.NaN;
	}

	@Override
	public double kurtosis() 
	{
		return Double.NaN;
	}

}
