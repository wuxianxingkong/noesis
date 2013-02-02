package sandbox.math;

/**
 * Two-tailed Student-t distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class StudentTwoTailedTDistribution extends StudentTDistribution 
{

	public StudentTwoTailedTDistribution (double degrees) 
	{
		super(degrees, 0, 1);
	}

	@Override
	public double cdf (double t) 
	{
		return 1.0 - Functions.betaI (0.5*nu, 0.5, nu/(nu+t*t));
	}

	@Override
	public double idf (double p) 
	{
		double x = Functions.betaIinv(1.0-p, 0.5*nu, 0.5);
		
		return Math.sqrt(nu*(1.0-x)/x);
	}
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}
	
}
