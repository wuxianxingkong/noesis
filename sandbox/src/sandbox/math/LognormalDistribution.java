package sandbox.math;

import ikor.math.Constants;
import ikor.math.Functions;

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
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}

}
