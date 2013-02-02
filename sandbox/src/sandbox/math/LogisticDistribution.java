package sandbox.math;

/**
 * Logistic distribution
 * http://en.wikipedia.org/wiki/Logistic_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class LogisticDistribution implements Distribution 
{
	private double mu;
	private double sigma;
	
	private static final double FACTOR = Math.PI / (2.0*Math.sqrt(3));
	private static final double IFACTOR = Math.sqrt(3) / Math.PI;
	
	public LogisticDistribution (double mean, double deviation)
	{
		this.mu = mean;
		this.sigma = deviation;
	}
	
	@Override
	public double pdf (double x) 
	{
		double e = Math.exp( -FACTOR*Math.abs(x-mu)/sigma );
		
		return FACTOR * e / (sigma*(1+e)*(1+e));
	}

	@Override
	public double cdf (double x) 
	{
		double e = Math.exp( -FACTOR*Math.abs(x-mu)/sigma );
		
		if (x>=mu)
			return 1/(1+e);
		else
			return e/(1+e);
	}

	@Override
	public double idf (double p) 
	{
		return mu + IFACTOR*sigma*Math.log(p/(1-p));
	}
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}

}
