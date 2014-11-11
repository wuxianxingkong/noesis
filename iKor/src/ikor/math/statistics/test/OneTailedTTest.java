package ikor.math.statistics.test;

import ikor.math.Vector;

/**
 * One-sided t-test
 * 
 * http://en.wikipedia.org/wiki/Student's_t-test
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class OneTailedTTest extends TTest 
{
	public enum Tail { LEFT, RIGHT };
	
	private Tail tail;
	
	
	public OneTailedTTest (Vector data, Tail tail)
	{
		super(data);
		
		this.tail = tail;
	}
	
	public OneTailedTTest (Vector data, double mean, Tail tail)
	{
		super(data,mean);
		
		this.tail = tail;
	}
	
	// Paired t-test
	
	public OneTailedTTest (Vector x, Vector y, Tail tail)
	{
		this(x.subtract(y),tail);
	}

	public OneTailedTTest (Vector x, Vector y, double mean, Tail tail)
	{
		this(x.subtract(y),mean,tail);
	}
	
	/**
	 * Two-tailed t-test
	 * 
	 * @return p-value
	 */
	public double pvalue ()
	{
		if (tail==Tail.RIGHT)
			return tdist.cdf(-tstat());
		else // tail==Tail.LEFT
			return tdist.cdf(tstat());
	}

	// Confidence interval
	
	private double criticalValue (double alpha)
	{
		return tdist.idf( 1-alpha ) * sd() / Math.sqrt(data.size());
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
		if (tail==Tail.RIGHT)
			return data.average() - criticalValue(alpha);
		else // tail==Tail.LEFT
			return Double.NEGATIVE_INFINITY;
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
		if (tail==Tail.RIGHT)
			return Double.POSITIVE_INFINITY;
		else // tail==Tail.LEFT
			return data.average() + criticalValue(alpha);
	}
	

}
