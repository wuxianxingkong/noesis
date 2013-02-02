package sandbox.math;

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
	


	@Override
	public double random() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
