package ikor.math.random;

/**
 * Random number generation.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class Random 
{
	private static RNG rng = new JavaRNG();
	
	// Current RNG
	
	public static RNG get ()
	{
		return rng;
	}
	
	public static void set (RNG newRandomNumberGenerator)
	{
		rng = newRandomNumberGenerator;
	}
	
	
	// Public interface

	/**
	 * Uniform(0,1) random number
	 */
	
	public static double random ()
	{
		return rng.random();
	}


	
	/**
	 * Normal(0,1) random number:
	 * 
	 * Gaussian ("normally") distributed double value 
	 * with mean 0.0 and standard deviation 1.0 using the current random number generator.
	 * 
	 * @JDK7: Polar method by G. E. P. Box, M. E. Muller, and G. Marsaglia, 
	 * as described by Donald E. Knuth in The Art of Computer Programming, Volume 3: 
	 * Seminumerical Algorithms, section 3.4.1, subsection C, algorithm P.
	 */	
	
	public static double gaussian() 
	{
		double v1, v2, s;

		do {
			v1 = 2 * random() - 1;   // between -1.0 and 1.0
			v2 = 2 * random() - 1;   // between -1.0 and 1.0
			s = v1 * v1 + v2 * v2;
		} while (s >= 1 || s == 0);

		double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s)/s);

		// nextGaussian = v2 * multiplier; // Actually, two N(0,1) random values per call

		return v1 * multiplier;
	}
}	
