package ikor.math.statistics;

import ikor.math.random.Random;

/**
 * Rayleigh distribution
 * http://en.wikipedia.org/wiki/Rayleigh_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class RayleighDistribution implements Distribution 
{
	private double sigma;
	
	public RayleighDistribution (double mode)
	{
		this.sigma = mode;
	}
	
	@Override
	public double pdf (double x) 
	{
		if (x<0)
			return 0;
		else
			return x * Math.exp(-0.5*(x/sigma)*(x/sigma)) / (sigma*sigma);
	}

	@Override
	public double cdf (double x) 
	{
		if (x<0)
			return 0;
		else
			return 1 - Math.exp(-0.5*(x/sigma)*(x/sigma));
	}

	@Override
	public double idf (double p) 
	{
		if (p==0)
			return 0;
		else if (p==1)
			return Double.POSITIVE_INFINITY;
		else {
			double q = 1.0 - p;
	        return sigma * Math.sqrt( -2*Math.log(q) );
		}
	}
	
	
	// Random number generator
	
	@Override
	public double random() 
	{
		return sigma * Math.sqrt( -2*Math.log( Random.random() ) );
	}

	@Override
	public double mean() 
	{
		return sigma*Math.sqrt(Math.PI/2);
	}

	@Override
	public double variance() 
	{
		return (4-Math.PI)*sigma*sigma/2;
	}

	@Override
	public double skewness() 
	{
		return 2*Math.sqrt(Math.PI)*(Math.PI-3)/Math.pow(4-Math.PI,1.5);
	}

	@Override
	public double kurtosis() 
	{
		return -(6*Math.PI*Math.PI-24*Math.PI+16) / ( (4-Math.PI)*(4-Math.PI) );
	}

}