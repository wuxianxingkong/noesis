package sandbox.math;

import ikor.math.Functions;

/**
 * Chi-Squared distribution (special case of the Gamma distribution)
 * http://en.wikipedia.org/wiki/Cauchy_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class ChiSquaredDistribution implements Distribution 
{
	private double k;
	private double f;
	
	public ChiSquaredDistribution (double degrees)
	{
		this.k = degrees;
		this.f = Math.log(2)*0.5*k + Functions.logGamma(0.5*k);
	}

	@Override
	public double pdf(double x) 
	{
		if (x<=0)
			return 0;
		else
			return Math.exp( -0.5*(x-(k-2)*Math.log(x)) - f);
	}

	@Override
	public double cdf(double x) 
	{
		if (x<=0)
			return 0;
		else
			return Functions.gammaP(0.5*k, 0.5*x);
	}
	
	/**
	 * The p-value is the probability of observing a test statistic at least as extreme in a chi-squared distribution. 
	 * Accordingly, since the cumulative distribution function (CDF) for the appropriate degrees of freedom (df) 
	 * gives the probability of having obtained a value less extreme than this point, subtracting the CDF value from 1 gives the p-value.
	 * 
	 * @param x point of evaluation
	 * @return p-value
	 */

	public double pvalue (double x)
	{
		return 1-cdf(x);
	}
	

	@Override
	public double idf(double p) 
	{ 
		if (p==0)
			return 0.0;
		else if (p==1)
			return Double.POSITIVE_INFINITY;
		else
			return 2*Functions.gammaPinv(p, 0.5*k);
	}
	


	@Override
	public double random() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
