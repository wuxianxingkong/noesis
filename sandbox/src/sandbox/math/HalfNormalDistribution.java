package sandbox.math;

import ikor.math.Constants;
import ikor.math.Functions;

/**
 * Half-mormal distribution (a.k.a. Gaussian distribution)
 * http://en.wikipedia.org/wiki/Half-normal_distribution
 * 
 * If X has a normal(μ,σ) distribution, then |X−μ| has a half-normal(μ,σ) distribution.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class HalfNormalDistribution implements Distribution 
{
	private double mu;
	private double sigma;
	
	public HalfNormalDistribution (double location, double scale)
	{
		this.mu = location;
		this.sigma = scale;
	}
	
	@Override
	public double pdf (double x) 
	{
		if (x<mu)
			return 0;
		else
			return Constants.SQRT2 * Math.exp(-(x-mu)*(x-mu)/(2*sigma*sigma))/ ( sigma * Constants.SQRTPI);
	}

	@Override
	public double cdf (double x) 
	{
		if (x<mu)
			return 0;
		else
			return 2*Functions.Phi((x-mu)/sigma)-1;
	}

	@Override
	public double idf (double p) 
	{
		return mu + sigma*Functions.probit((1+p)/2);
	}
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}

}
