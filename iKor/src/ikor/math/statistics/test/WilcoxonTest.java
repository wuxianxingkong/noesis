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
	private double tieadjustment;
	
	private static NormalDistribution normal = new NormalDistribution(0.0,1.0);
	
	public WilcoxonTest (Vector x)
	{
		Vector reduced = reduce(x);
		Vector sign = sign(reduced);
		Vector absolute = abs(reduced);

		sort(sign,absolute);
		
		Vector rank = tiedrank (absolute);
		
		this.n = x.size();
		this.nr = reduced.size();
		this.signrank = sign.arrayMultiply(rank);
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
	
	
	// Exclude pairs with 0 difference.
	
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
	
	// Sort
	
	private void sort (Vector sign, Vector absolute)
	{
		// Selection sort

		double tmp;
		int min;
		int n = absolute.size();

		for (int i=0; i<n-1; i++) {

			// Minimum
			
			min = i;

			for (int j=i+1; j<n; j++)
				if (absolute.get(j)<absolute.get(min))
					min = j;

			// Set minimum at its correct position

			tmp = sign.get(i);
			sign.set(i, sign.get(min));
			sign.set(min, tmp);
			
			tmp = absolute.get(i);
			absolute.set(i, absolute.get(min));
			absolute.set(min, tmp);
		}
	}
	
	// Rank
	
	private Vector tiedrank (Vector sorted)
	{
		int n = sorted.size();
		int current, next, ntied;
		double rank;
		Vector ranked = MatrixFactory.createVector(n);
		
		current = 0;
		tieadjustment = 0;
		
		while (current<n) {
			
			next = current+1;
			rank = next;
			
			while ( (next<n) && sorted.get(current)==sorted.get(next) ) {
				next++;
				rank += next;
			}
			
			rank /= (next-current);
			
			ntied = next-current;
			tieadjustment += ntied * (ntied-1) * (ntied+1) / 2;
			
			for (int j=current; j<next; j++)
				ranked.set(j, rank);
			
			current = next;
		}
		
		return ranked;
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
		return Math.sqrt((n*(n+1)*(2*n+1)-tieadjustment)/24.0);		
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
