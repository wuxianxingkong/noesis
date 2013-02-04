package ikor.math.random;

/**
 * Random number generator interface
 *  
 * @author Fernando Berzal (berzal@acm.org)
 */
public interface RNG 
{
	/**
	 * Returns a random number in U(0,1)
	 * 
	 * @return A random number in the interval [0,1) 
	 */
	public double random();
}
