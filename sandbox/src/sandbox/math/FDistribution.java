package sandbox.math;

import ikor.math.Functions;

/**
 * F-distribution (a.k.a. Snedecor's F distribution or the Fisher-Snedecor distribution)
 * http://en.wikipedia.org/wiki/F_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class FDistribution implements Distribution 
{
	private double d1;
	private double d2;
	private double factor;
	
	public FDistribution (double d1, double d2)
	{
		this.d1 = d1;
		this.d2 = d2;

		this.factor = 0.5*(d1*Math.log(d1)+d2*Math.log(d2)) 
		        	+ Functions.logGamma(0.5*(d1+d2)) 
		        	- Functions.logGamma(0.5*d1)
		        	- Functions.logGamma(0.5*d2);
	}

	@Override
	public double pdf(double f) 
	{
		if (f<=0)
			return 0;
		else
			return Math.exp((0.5*d1-1.0)*Math.log(f)-0.5*(d1+d2)*Math.log(d2+d1*f)+factor);
	}

	@Override
	public double cdf(double f) 
	{
		if (f<=0)
			return 0;
		else
			return Functions.betaI(0.5*d1,0.5*d2,d1*f/(d2+d1*f));
	}
	

	@Override
	public double idf(double p) 
	{ 
		if (p==0) {
			return 0.0;
		} else if (p==1) {
			return Double.POSITIVE_INFINITY;
		} else {
			double x = Functions.betaIinv(p, 0.5*d1, 0.5*d2);
			return d2*x/(d1*(1-x));
		}
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
		if (d2>2)
			return d2/(d2-2);
		else
			return Double.NaN;
	}

	@Override
	public double variance() 
	{
		if (d2>4)
			return 2*d2*d2*(d1+d2-2) / ( d1*(d2-2)*(d2-2)*(d2-4) );
		else
			return Double.NaN;
	}

	@Override
	public double skewness() 
	{
		if (d2>6)
			return (2*d1+d2-2) * Math.sqrt(8*(d2-4)) / ( (d2-6)*Math.sqrt(d1*(d1+d2-2)) );
		else
			return Double.NaN;
	}

	@Override
	public double kurtosis() 
	{
		if (d2>8)
			return 12 * ( d1 * (5*d2-22) * (d1+d2-2) + (d2-4)*(d2-2)*(d2-2) ) / ( d1 * (d2-6) * (d2-8) * (d1+d2-2) );
		else
			return Double.NaN;
	}

}
