package ikor.math.random;

/**
 * Fastest pseudorandom number generator.
 * 
 * ref. Numerical Recipes: The Art of Scientific Computing [3rd edition], 2007, p. 351
 * 
 * Warning! 1.8e19 period (should not be used by an application that makes more than 10e12 calls)
 *  
 * @author Fernando Berzal (berzal@acm.org)
 */

public class FastestRNG implements RNG
{
	long v;
	
	public FastestRNG (long seed)
	{
		v = 4101842887655102017L;
		v ^= seed;
		v = integer();
	}
		
	
	@Override
	public double random() 
	{
		// 2^-64 = 5.4210108624275221700372640043497e-20 ==> int*(2^-64) in [-0.5, 0.5) @ Java, in [0,1) for unsigned long @ C/C++
		
		return 0.5 + 5.4210108624275221700372640043497e-20 * integer(); 
	}
	
	
	@Override
	public long integer() 
	{
		v ^= v >> 21; 
		v ^= v << 35; 
		v ^= v >> 4;
		return v * 2685821657736338717L;
	}
	
}
