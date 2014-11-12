package ikor.math.statistics.test;

import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.ChiSquaredDistribution;

/**
 * Friedman test.
 * 
 * Non-parametric statistical test (tests only for column effects after adjusting for possible row effects)
 * 
 * http://en.wikipedia.org/wiki/Friedman_test
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class FriedmanTest 
{
	int    n;
	int    nc;
	Vector data[];
	Vector rank[];

	public FriedmanTest ( Vector columns[] )
	{
		this.n = columns[0].size();
		this.nc = columns.length;
		this.data = columns;
		this.rank = rank(data);
	}
	
	private Vector[] rank (Vector columns[])
	{
		Vector row = MatrixFactory.createVector(nc);
		Rank   rankedRow;
		
		rank = new Vector[nc];
		
		for (int j=0; j<nc; j++)
			rank[j] = MatrixFactory.createVector(n);
		
		for (int i=0; i<n; i++) {
			
			for (int j=0; j<nc; j++)
				row.set(j, columns[j].get(i));
		
			rankedRow = new Rank(row);
			
			for (int j=0; j<nc; j++)
				rank[j].set(i, rankedRow.rank(j));
		}
		
		return rank;
	}
	
	// Statistics
	
	public int n ()
	{
		return n;
	}
	
	public int nc ()
	{
		return nc;
	}
	
	public int df ()
	{
		return nc-1;
	}
	
	
	public Vector rank (int i)
	{
		return rank[i];
	}

	/**
	 * Mean rank for columns..
	 * @param i column index
	 * @return Mean rank for the ith column 
	 */
	public double meanrank (int i)
	{
		return rank[i].average();
	}
	
	/**
	 * Overall mean rank.
	 */
	public double meanrank ()
	{
		double sum = 0;
		
		for (int i=0; i<nc; i++)
			sum += meanrank(i);
		
		return sum/nc;
	}
	
	/**
	 * Sum of squares (variation between groups)
	 */
	public double sst ()
	{
		double meanrank = meanrank();
		double diff;
		double sum2 = 0;
		
		for (int i=0; i<nc; i++) {
			diff = meanrank(i)-meanrank;
			sum2 += diff*diff;
		}
		
		return n*sum2;
	}
	
	/**
	 * Mean squares (variation between groups): SSt/df
	 */
	public double mst ()
	{
		return sst()/df();
	}
	
	/**
	 * Sum of squares error (variation within groups)
	 */	
	public double sse ()
	{
		double meanrank = meanrank();
		double diff;
		double sum2 = 0;

		for (int i=0; i<n; i++) {
			for (int j=0; j<nc; j++) {
				diff = rank[j].get(i)-meanrank;
				sum2 += diff*diff;
			}
		}
		
		return sum2/(n*(nc-1));
	}
	
	/**
	 * Degrees of freedom error: (n-1)*(nc-1)
	 */
	public int dfe ()
	{
		return (n-1)*(nc-1);
	}
	
	/**
	 * Mean sum of squares error
	 */
	public double mse ()
	{
		return sse()/dfe();
	}
	
	/**
	 * Friedman test statistic: Q = SSt/SSe
	 */	
	public double q ()
	{
		return sst()/sse();
	}
	
	/**
	 * Friedman test p-value: P(Chi2>=Q)
	 * 
	 * When n or k is large (i.e. n > 15 or nc > 4), the probability distribution of Q 
	 * can be approximated by a chi-squared distribution with (nc-1) degrees of freedom.
	 * If the p-value is significant, appropriate post-hoc multiple comparisons tests 
	 * should be performed.
	 */
	public double pvalue ()
	{
		ChiSquaredDistribution chi2 = new ChiSquaredDistribution(df());
		
		return 1 - chi2.cdf(q());
	}
}
