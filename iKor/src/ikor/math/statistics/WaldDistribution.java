package ikor.math.statistics;

import ikor.math.Configuration;
import ikor.math.Constants;
import ikor.math.Functions;
import ikor.math.random.Random;

/**
 * Wald distribution (a.k.a.  inverse Gaussian distribution)
 * http://en.wikipedia.org/wiki/Inverse_Gaussian_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class WaldDistribution implements Distribution 
{
	private double mu;
	private double lambda;
	
	/**
	 * Wald distribution == Inverse Gaussian distribution 
	 * @param mu > 0 (mean)
	 * @param lambda > 0 (shape parameter)
	 */
	public WaldDistribution (double mu, double lambda)
	{
		this.mu = mu;
		this.lambda = lambda;
	}
	
	@Override
	public double pdf (double x) 
	{
		if (x<=0)
			return 0;
		else
			return Math.sqrt(lambda/(x*x*x))/Constants.SQRT2PI * Math.exp ( -lambda*(x-mu)*(x-mu) / (2*mu*mu*x) );	
	}

	@Override
	public double cdf (double x) 
	{	
		if (x<=0) {
			return 0.0;
		} else {
			double d = x/mu;
			double r = Math.sqrt(lambda/x);
			return Functions.Phi(r*(d-1)) + Math.exp(2*lambda/mu)*Functions.Phi(-r*(d+1));
		}
	}

	@Override
	public double idf (double p) 
	{
		if (p==0)
			return 0;
		else if (p==1)
			return Double.POSITIVE_INFINITY;
		else
			return idfsearch(p,0,1e6);
	}
	
	// Bisection method:
	// value k in [min,max] such that cdf(<k) <= p < cdf(<=k)

	private double idfsearch (double p, double min, double max)
	{		
		double v = min;
		
		while (max-min > Configuration.EPSILON) { 
			v = (min+max)/2;
			if (p <= cdf(v)) 
				max = v;
			else 
				min = v;
		}

		return v;		
	}
	
	// Random number generation
	
	// "Generating Random Variates Using Transformations with Multiple Roots"
	// by John R. Michael, William R. Schucany and Roy W. Haas, 
	// American Statistician, Vol. 30, No. 2 (May, 1976), pp. 88–90
	
	@Override
	public double random() 
	{
		double v = Random.gaussian();   // N(0,1)
		double y = v*v;
		double x = mu + (mu*mu*y)/(2*lambda) - (mu/(2*lambda)) * Math.sqrt(4*mu*lambda*y + mu*mu*y*y);
		double test = Random.random();  // U(0,1)
		
		if (test <= (mu)/(mu + x))
			return x;
		else
			return (mu*mu)/x;
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
		return mu*mu*mu/lambda;
	}

	@Override
	public double skewness() 
	{
		return 3*Math.sqrt(mu/lambda);
	}

	@Override
	public double kurtosis() 
	{
		return 15*mu/lambda;
	}

}
