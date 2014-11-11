package ikor.math.statistics.test;

import ikor.math.Vector;
import ikor.math.statistics.StudentTDistribution;

/**
 * One-sample & paired t-test
 * 
 * http://en.wikipedia.org/wiki/Student's_t-test
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class TTest 
{
	protected Vector data;
	protected double mean;
	protected StudentTDistribution tdist;
	
	public TTest (Vector data)
	{
		this(data,0.0);
	}
	
	public TTest (Vector data, double mean)
	{
		this.data  = data;
		this.mean  = mean;
		this.tdist = new StudentTDistribution(df());
	}
	
	public TTest (Vector x, Vector y)
	{
		this( x.subtract(y) );
	}
	
	public TTest (Vector x, Vector y, double mean)
	{
		this( x.subtract(y), mean);
	}
	
	// Statistics

	public double tstat ()
	{
		double sampleMean = data.average();
		double sampleSize = data.size();
		
		return (sampleMean-mean)/sd()*Math.sqrt(sampleSize);
	}
	
	public int df ()
	{
		return data.size()-1;
	}
	
	public double sd ()
	{
		return data.sampleDeviation();
	}
	
	/**
	 * Two-tailed t-test
	 * 
	 * @return p-value
	 */
	public double pvalue ()
	{
		return 2*tdist.cdf(-Math.abs(tstat()));
	}

	// Confidence interval
	
	private double criticalValue (double alpha)
	{
		return tdist.idf( 1-alpha/2 ) * sd() / Math.sqrt(data.size());
	}
	
	/**
	 * Confidence interval
	 * 
	 * @param alpha Significance level
	 * 
	 * @return Lower boundary of the 100*(1-alpha)% confidence interval
	 */	
	public double minConfidenceInterval (double alpha)
	{
		return data.average() - criticalValue(alpha);
	}
	
	/**
	 * Confidence interval
	 * 
	 * @param alpha Significance level
	 * 
	 * @return Upper boundary of the 100*(1-alpha)% confidence interval
	 */
	public double maxConfidenceInterval (double alpha)
	{
		return data.average() + criticalValue(alpha);
	}
	
}
