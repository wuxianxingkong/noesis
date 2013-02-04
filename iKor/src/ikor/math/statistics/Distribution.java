package ikor.math.statistics;

/**
 * Probability distribution interface
 * 
 * @author Fernando Berzal
 */
public interface Distribution 
{
	/**
	 * Probability distribution function
	 */
	public double pdf (double x);

	/**
	 * Cumulative distribution function
	 */
	public double cdf (double x);
	
	/**
	 * Inverse cumulative distribution function (a.k.a quantile function)
	 */
	public double idf (double p);
		
	/**
	 * Random number generation
	 */
	public double random ();

	/**
	 * Expected value (a.k.a. expectation, mathematical expectation, EV, mean, or the first moment) of a random variable:
	 * The weighted average of all possible values that this random variable can take on.
	 */
	public abstract double mean();
	
	/**
	 * Variance (a.k.a. the second central moment):
	 * A measure of how far values lie from the mean (expected value).
	 * = the expected value of the squared difference between the variable's realization and the variable's mean. 
	 */
	public abstract double variance();
	
	/**
	 * Skewness (a.k.a. the third standardized moment): 
	 * A measure of the asymmetry of the probability distribution.
	 * 
	 * - A negative skew indicates that the tail on the left side of the probability density function 
	 * is longer than the right side and the bulk of the values (possibly including the median) lie
	 * to the right of the mean. 
	 * - A positive skew indicates that the tail on the right side is longer than the left side 
	 * and the bulk of the values lie to the left of the mean. 
	 * - A zero value indicates that the values are relatively evenly distributed on both sides of the mean, 
	 * typically (but not necessarily) implying a symmetric distribution.
	 */
	public abstract double skewness();
	
	/**
	 * Excess kurtosis (adjusted version of Pearson's kurtosis) 
	 * to provide a comparison of the shape of a given distribution to that of the normal distribution: 
	 * A scaled version of the fourth moment that measures the "peakedness" of a probability distribution).
	 * 
	 *  Distributions with negative or positive excess kurtosis are called 
	 *  platykurtic distributions or leptokurtic distributions, respectively.
	 */
	public abstract double kurtosis();
	
}
