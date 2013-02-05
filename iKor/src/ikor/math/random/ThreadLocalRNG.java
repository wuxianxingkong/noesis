package ikor.math.random;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Standard Java pseudorandom number generator isolated to the current thread.
 * 
 * @JDK7: 48-bit seed modified using a linear congruential formula. 
 * (See Donald Knuth, The Art of Computer Programming, Volume 2, Section 3.2.1.)
 *  
 * @author Fernando Berzal (berzal@acm.org)
 */

public class ThreadLocalRNG implements RNG
{
	public ThreadLocalRNG (long seed)
	{
		ThreadLocalRandom.current().setSeed(seed);
	}


	@Override
	public double random() 
	{		
		return ThreadLocalRandom.current().nextDouble();
	}

	@Override
	public long integer() 
	{
		return ThreadLocalRandom.current().nextLong();
	}

}
