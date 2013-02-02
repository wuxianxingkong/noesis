package sandbox.math;

/**
 * Cauchy distribution (a.k.a. Lorentz distrbution)
 * http://en.wikipedia.org/wiki/Cauchy_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class CauchyDistribution implements Distribution 
{
	private double mu;
	private double sigma;
	
	public CauchyDistribution (double center, double width)
	{
		this.mu = center;
		this.sigma = width;
	}
	
	@Override
	public double pdf (double x) 
	{
		double z = (x-mu)/sigma;
		
		return 1.0 / (  Math.PI * sigma * ( 1.0 + z*z ) );
	}

	@Override
	public double cdf (double x) 
	{
		return 0.5 + Math.atan2(x-mu,sigma)/Math.PI;
	}

	@Override
	public double idf (double p) 
	{
		if (p==0)
			return Double.NEGATIVE_INFINITY;
		else if (p==1)
			return Double.POSITIVE_INFINITY;
		else
			return mu + sigma*Math.tan(Math.PI*(p-0.5));
	}
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}

}
