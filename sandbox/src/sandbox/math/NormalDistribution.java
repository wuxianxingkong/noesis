package sandbox.math;

/**
 * Normal distribution
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
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}

}
