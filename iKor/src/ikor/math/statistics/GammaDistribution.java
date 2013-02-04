package ikor.math.statistics;

import ikor.math.Functions;
import ikor.math.random.Random;

/**
 * Gamma distribution
 * http://en.wikipedia.org/wiki/Gamma_distribution
 * 
 * NOTE: The chi-squared distribution is a special case with alpha=df/2 and beta=1/2.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class GammaDistribution implements Distribution 
{
	private double alpha;
	private double beta;
	private double factor;
	
	/**
	 * Gamma distribution
	 * @param shape alpha parameter
	 * @param rate beta parameter
	 */
	public GammaDistribution (double shape, double rate)
	{
		this.alpha  = shape;
		this.beta   = rate;
		this.factor = alpha*Math.log(beta) - Functions.logGamma(alpha);
	}

	@Override
	public double pdf(double x) 
	{
		if (x<=0)
			return 0;
		else
			return Math.exp( -beta*x + (alpha-1)*Math.log(x) + factor);
	}

	@Override
	public double cdf(double x) 
	{
		if (x<=0)
			return 0;
		else
			return Functions.gammaP(alpha, beta*x);
	}
	

	@Override
	public double idf(double p) 
	{ 
		if (p==0)
			return 0.0;
		else if (p==1)
			return Double.POSITIVE_INFINITY;
		else
			return Functions.gammaPinv(p,alpha)/beta;
	}
	

	// Random number generator (adapted from Numerical Recipes)

	@Override
	public double random() 
	{		
		double u,v,x;
		double a1 = alpha-1./3.;
		double a2 = 1./Math.sqrt(9.*a1);
		
		do {
			do {
				x = Random.gaussian();  // N(0,1)
				v = 1. + a2*x;
			} while (v <= 0.);
			v = v*v*v;
			u = Random.random();        // U(0,1)
		} while ( (u > 1. - 0.331*x*x*x*x) && (Math.log(u) > 0.5*x*x + a1*(1.-v+Math.log(v))) );
		
		return a1*v/beta;
	}

	// Statistics
	
	@Override
	public double mean() 
	{
		return alpha/beta;
	}

	@Override
	public double variance() 
	{
		return alpha/(beta*beta);
	}

	@Override
	public double skewness() 
	{
		return 2/Math.sqrt(alpha);
	}

	@Override
	public double kurtosis() 
	{
		return 6/alpha;
	}

}
