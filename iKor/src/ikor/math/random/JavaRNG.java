package ikor.math.random;

/**
 * Standard Java pseudorandom number generator.
 * 
 * @JDK7: 48-bit seed modified using a linear congruential formula. 
 * (See Donald Knuth, The Art of Computer Programming, Volume 2, Section 3.2.1.)
 *  
 * @author Fernando Berzal (berzal@acm.org)
 */

public class JavaRNG implements RNG 
{
	java.util.Random rng;
	
	public JavaRNG (long seed)
	{
		this.rng = new java.util.Random(seed);
	}

	public JavaRNG ()
	{
		this.rng = null;
	}


	@Override
	public double random() 
	{
		if (rng!=null)
			return rng.nextDouble();
		else
			return Math.random();
	}

}
