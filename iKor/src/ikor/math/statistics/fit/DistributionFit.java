package ikor.math.statistics.fit;

import ikor.math.statistics.Distribution;

/**
 * Distribution fitting.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 * @param <T> Probability distribution type
 */

public interface DistributionFit<T extends Distribution> 
{
	/*
	 *  Fit a probability distribution
	 */
	
	public abstract T fit ();
}
