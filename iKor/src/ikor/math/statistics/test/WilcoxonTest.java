package ikor.math.statistics.test;

import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.NormalDistribution;

/**
 * One-sample & paired Wilcoxon signed-rank test.
 * 
 * P is the probability of observing the given result, or one more extreme, 
 * by chance if the null hypothesis is true:
 * - Median is zero: new WilcoxonTest(data).
 * - Mediam is M: new WilcoxonTest(data,M).
 * - The difference between the matched samples in the vectors X and Y 
 *   comes from a distribution whose median is zero: new WilcoxonTest(x,y).
 * - The difference between the matched samples in the vectors X and Y 
 *   comes from a distribution whose median is M: new WilcoxonTest(x,y,M).
 *   
 * Small values of P cast doubt on the validity of the null hypothesis.
 * 
 * 
 * The data are assumed to come from a continuous distribution, symmetric about its median;
 * i.e. The differences X-Y are assumed to come from a continuous distribution, symmetric about its median. 
 * 
 * NOTE: The test computes the p-value using a normal approximation.
 * 
 * http://en.wikipedia.org/wiki/Wilcoxon_signed-rank_test
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class WilcoxonTest 
{
	private int n;
	private int nr;
	
	private Vector signrank;
	private Rank   rank;
	
	private static NormalDistribution normal = new NormalDistribution(0.0,1.0);
	
	public WilcoxonTest (Vector x)
	{
		Vector reduced = reduce(x);
		Vector sign = sign(reduced);
		Vector absolute = abs(reduced);

		this.n = x.size();
		this.nr = reduced.size();
		this.rank = new Rank(absolute);
		this.signrank = sign.arrayMultiply(rank.rank());
	}
	
	public WilcoxonTest (Vector x, double median)
	{
		this( x.subtract(median) );
	}
	
	public WilcoxonTest (Vector x, Vector y)
	{
		this( y.subtract(x) );
	}
	
	public WilcoxonTest (Vector x, Vector y, double median)
	{
		this( y.subtract(x), median);
	}
	
	
	// Exclude pairs with 0 difference & NaNs.
	
	private Vector reduce (Vector x) 
	{
		int nonzero = 0;
		
		for (int i=0; i<x.size(); i++)
			if (x.get(i)!=0 && !Double.isNaN(x.get(i)))
			   nonzero++;

		Vector reduced = MatrixFactory.createVector(nonzero);
		int    pos = 0;
		
		for (int i=0; i<x.size(); i++)
			if (x.get(i)!=0 && !Double.isNaN(x.get(i))) {
				reduced.set(pos, x.get(i));
				pos++;
			}
		
		return reduced;
	}
	
	// Sign
	
	private Vector sign (Vector x)
	{
		Vector sgn = MatrixFactory.createVector(x.size());
		
		for (int i=0; i<x.size(); i++)
			if (x.get(i)>0)
				sgn.set(i, +1);
			else if (x.get(i)<0)
				sgn.set(i, -1);
			else // == 0
				sgn.set(i, 0);
		
		return sgn;
	}

	// Absolute value

	private Vector abs (Vector x)
	{
		Vector absolute = MatrixFactory.createVector(x.size());
		
		for (int i=0; i<x.size(); i++)
			absolute.set(i, Math.abs(x.get(i)));
		
		return absolute;
	}	
	
	// Statistics

	public double signedrank ()
	{
		return Math.abs(signrank.sum()); 
		
		// MATLAB (one-tailed, negative signranks only):
		// return signrank.sum();
	}
	
	public int n ()
	{
		return n;
	}

	public int nr ()
	{
		return nr;
	}
	
	/**
	 * Mean of the sampling distribution of W (=0).
	 */
	public double mu ()
	{
		return 0.0;
	}
	
	/**
	 * Standard deviation of the sampling distribution of W.
	 */
	public double sigma ()
	{
		return Math.sqrt(nr*(nr+1)*(2*nr+1)/6);
	}
	
	/**
	 * Adjusted deviation of the sampling distribution of W.
	 */
	public double adjustedSigma ()
	{
		return Math.sqrt((n*(n+1)*(2*n+1)-rank.tieAdjustment())/24.0);		
	}
	
	// Normal approximation (including -0.5 correction for continuity).
	
	public double zvalue ()
	{
		double w = signedrank();
		
		if (nr>0)
			return (w-0.5)/sigma();
		else
			return Double.POSITIVE_INFINITY; 
		
		// MATLAB z-score (one-tailed, only negative signranks):
		// w = Math.min (w, n*(n+1)/2.0 - w);
		// return (w - n*(n+1)/4.0) / adjustedSigma();
	}
	
	/**
	 * Two-tailed Wilcoxon test.
	 * 
	 * @return p-value
	 */
	public double pvalue ()
	{
		if (nr>0)
			return 2*(1-normal.cdf(Math.abs(zvalue())));
		else
			return 1.0;
		
		// MATLAB p-value (one-tailed):
		// The two-sided p-value is computed by doubling the most significant one-sided value
		// return 2*normal.cdf(zvalue());
	}
	
	/**
	 * Critical value of W (approximated using a normal distribution)
	 * @param alpha Significance level
	 * @return Estimation of W_alpha
	 */
	public double w (double alpha)
	{
		return mu()+sigma()*normal.idf(1-alpha/2);
	}
}
