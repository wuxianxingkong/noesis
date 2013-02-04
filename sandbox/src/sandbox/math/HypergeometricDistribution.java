package sandbox.math;

import ikor.math.Functions;

/**
 * Hypergeometric distribution: HG(N,K,n)
 * 
 * discrete probability distribution that describes the probability of k successes in n draws 
 * from a finite population of size N containing K successes without replacement. 
 * 
 * NOTE: The binomial distribution describes the probability of k successes in n draws WITH replacement.
 * 
 * http://en.wikipedia.org/wiki/Hypergeometric_distribution
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class HypergeometricDistribution extends DiscreteDistribution implements Distribution 
{
	private int N;
	private int K;
	private int n;
	
	/**
	 * Hypergeometric distribution
	 * @param N Population size
	 * @param K Number of success states in the population (<=N)
	 * @param n Number of draws (<=N)
	 */
	public HypergeometricDistribution (int N, int K, int n)
	{
		this.N = N;
		this.K = K;
		this.n = n;
	}
	
	// positive when max{0,n+K-N} <= k <= min{K,n}
	
	@Override
	public double pdf (double x) 
	{
		int k = (int) x;
		
		if (k<0)
			return 0;
		else
			return Math.exp ( Functions.logBinomial(K,k) + Functions.logBinomial(N-K,n-k) - Functions.logBinomial(N,n) );
	}

	@Override
	public double cdf (double x) 
	{
		int k = (int) x;
		
		if (k < 0)
			return 0.0;
		else if (k > Math.min(K,n) )
			return 1.0;
		else {
			double total = 0;
			
			for (int i=Math.max(0,n+K-N); i<k; i++)
				total += pdf(i);
			
			return total;
		}
	}

	@Override
	public double idf (double p) 
	{
		if (p==0.0)
			return 0;
		else if (p==1.0)
			return Math.min(K,n)+1;
		else {
			int k = 1; // (p*r/(1-p)) mean value
			return idfsearch (p, k, 0, Math.min(K,n)+1);
		}
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
		return ((double)(n*K)) / (double)N;
	}

	@Override
	public double variance() 
	{
		return mean()*(N-K)*(N-n)/(N*(N-1));
	}

	@Override
	public double skewness() 
	{
		return (N-2*K)*Math.sqrt(N-1)*(N-2*n) / ( (N-2)*Math.sqrt(n*K*(N-K)*(N-n)) );
	}

	@Override
	public double kurtosis() 
	{
		return ( (N-1.0)*N*N*( N*(N+1.0)-6.0*K*(N-K)-6.0*n*(N-n) ) + 6.0*n*K*(N-K)*(N-n)*(5.0*N-6.0) )
			 / ( n*K*(N-K)*(N-n)*(N-2.0)*(N-3.0));
	}

}
