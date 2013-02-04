package sandbox.math;

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
	        return sigma * Math.sqrt( -Math.log(q * q) );
		}
	}
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}

}