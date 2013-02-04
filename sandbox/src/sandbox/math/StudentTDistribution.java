package sandbox.math;

import ikor.math.Functions;

/**
 * Student-t distribution (generalization of Cauchy distribution)
 * http://en.wikipedia.org/wiki/Student's_t-distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class StudentTDistribution  implements Distribution 
{
	private double nu;    // Degrees of freedom
	private double mu;    // Center
	private double sigma; // Width
	
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

	public StudentTDistribution (double degrees)
	{
		this(degrees,0,1);
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
	
	/**
	 * Two-sided version of CDF 
	 */
	
	public double cdf2 (double t) 
	{
		return 1.0 - Functions.betaI (0.5*nu, 0.5, nu/(nu+t*t));
	}
	

	@Override
	public double idf (double p) 
	{
		double x;
		
		x = Functions.betaIinv(2.0*Math.min(p,1.0-p), 0.5*nu, 0.5);
		x = sigma*Math.sqrt(nu*(1.-x)/x);
		
		return ((p>=0.5)? mu+x : mu-x);
	}
	
	
	/**
	 * Two-sided version of IDF
	 */
	public double idf2 (double p) 
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

	@Override
	public double mean() 
	{
		if (nu>1)
			return 0;
		else
			return Double.NaN;
	}

	@Override
	public double variance() 
	{
		if (nu<=1)
			return Double.NaN;
		else if (nu<=2)
			return Double.POSITIVE_INFINITY;
		else
			return nu/(nu-2);
	}

	@Override
	public double skewness() 
	{
		if (nu>3)
			return 0;
		else
			return Double.NaN;
	}

	@Override
	public double kurtosis() 
	{
		if (nu<=2)
			return Double.NaN;
		else if (nu<=4)
			return Double.POSITIVE_INFINITY;
		else
			return 6/(nu-4);
	}
}