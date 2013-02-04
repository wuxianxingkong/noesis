package sandbox.math;

import ikor.math.Constants;
import ikor.math.Functions;

/**
 * Half-normal distribution (i.e. a folded  normal distribution where the fold occurs at a cumulative probability of p = 0.5)
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

	@Override
	public double mean() 
	{
		return sigma * Math.sqrt(2/Math.PI);
	}

	@Override
	public double variance() 
	{
		return sigma * sigma * ( 1 - 2/Math.PI );
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
