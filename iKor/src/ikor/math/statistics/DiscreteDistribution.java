package ikor.math.statistics;

public abstract class DiscreteDistribution implements Distribution 
{
	
	// integer k in [min,max] such that cdf(<k) <= p < cdf(<=k)

	protected int idfsearch (double p, int k, int min, int max)
	{
		int kl,ku;  // [kl,ku] interval 
		int inc = 1;
		
		if (p < cdf(k)) {
			do {
				k = (int) Math.max(k-inc,min);
				inc *= 2;
			} while (p < cdf(k));
			kl = k; 
			ku = k + inc/2;
		} else {
			do {
				k = (int) Math.min(k+inc,max);
				inc *= 2;
			} while (p > cdf(k));
			ku = k; 
			kl = k - inc/2;
		}
		
		while (ku-kl>1) { // Bisection
			k = (kl+ku)/2;
			if (p < cdf(k)) 
				ku = k;
			else 
				kl = k;
		}

		return kl;		
	}
}
