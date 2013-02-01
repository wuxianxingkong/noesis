package sandbox.math;

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

}
