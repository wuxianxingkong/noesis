package sandbox.math;

/**
 * Student-t distribution (generalization of Cauchy distribution)
 * http://en.wikipedia.org/wiki/Student's_t-distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class StudentTDistribution  implements Distribution 
{
	protected double nu;    // Degrees of freedom
	protected double mu;    // Center
	protected double sigma; // Width
	
	private double np;
	private double fac;
	
	public StudentTDistribution (double degrees, double center, double width)
	{
		this.nu = degrees;
		this.mu = center;
		this.sigma = width;
		
		np = 0.5*(nu + 1.0);
		fac = Functions.logGamma(np) - Functions.logGamma(0.5*nu);
	}
	
	@Override
	public double pdf (double t) 
	{
		double z = (t-mu)/sigma;
		
		return Math.exp ( -np*Math.log(1.+z*z/nu) + fac) / ( Math.sqrt(Math.PI*nu) * sigma );
	}

	@Override
	public double cdf (double t) 
	{
		double z = (t-mu)/sigma;
		double p = 0.5*Functions.betaI(0.5*nu, 0.5, nu/(nu+z*z));
		
		if (t >= mu) 
			return 1.0 - p;
		else 
			return p;
	}

	@Override
	public double idf (double p) 
	{
		double x;
		
		x = Functions.betaIinv(2.0*Math.min(p,1.0-p), 0.5*nu, 0.5);
		x = sigma*Math.sqrt(nu*(1.-x)/x);
		
		return ((p>=0.5)? mu+x : mu-x);
	}
	
	@Override
	public double random() 
	{
		// TODO Random number generator
		return 0;
	}
}