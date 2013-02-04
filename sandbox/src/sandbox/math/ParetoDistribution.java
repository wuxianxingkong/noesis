package sandbox.math;

/**
 * Pareto distribution (a.k.a. power law distribution)
 * http://en.wikipedia.org/wiki/Pareto_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class ParetoDistribution implements Distribution 
{
	private double lambda;
	private double kappa;

	/**
	 * Pareto distribution
	 * @param exponent > 0
	 */
	public ParetoDistribution (double exponent)
	{
		this(exponent,1);
	}
	
	/**
	 * Pareto distribution
	 * @param exponent > 0
	 * @param scale > 0
	 */

	public ParetoDistribution (double exponent, double scale)
	{
		this.kappa = exponent;
		this.lambda = scale;
	}
	

	@Override
	public double pdf(double x) 
	{
		if (x>lambda)
			return kappa*Math.pow(lambda,kappa)/Math.pow(x,kappa+1);
		else
			return 0.0;
	}

	@Override
	public double cdf(double x) 
	{
		if (x>lambda)
			return 1.0 - Math.pow(lambda/x, kappa);
		else
			return 0.0;
	}
	

	@Override
	public double idf(double p) 
	{ 
		if (p==0)
			return lambda;
		else if (p==1)
			return Double.POSITIVE_INFINITY;
		else
			return lambda * Math.pow(1-p, -1/kappa);
	}
	


	@Override
	public double random() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double mean() 
	{
		if (kappa<=1)
			return Double.POSITIVE_INFINITY;
		else
			return kappa*lambda/(kappa-1);
	}

	@Override
	public double variance() 
	{
		if (kappa<=2)
			return Double.POSITIVE_INFINITY;
		else
			return (kappa*lambda*lambda)/((kappa-1)*(kappa-1)*(kappa-2));
	}

	@Override
	public double skewness() 
	{
		if (kappa<=3)
			return Double.NaN;
		else
			return ( 2*(1+kappa)/(kappa-3) ) * Math.sqrt ( (kappa-2)/kappa );
	}

	@Override
	public double kurtosis() 
	{
		if (kappa<=4)
			return Double.NaN;
		else
			return 6 * ( kappa*kappa*kappa + kappa*kappa - 6*kappa - 2 ) / ( kappa*(kappa-3)*(kappa-4) );
	}

}