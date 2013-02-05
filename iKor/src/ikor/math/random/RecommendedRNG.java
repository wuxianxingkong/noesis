package ikor.math.random;

/**
 * Recommended pseudorandom number generator.
 * 
 * ref. Numerical Recipes: The Art of Scientific Computing [3rd edition], 2007, pp. 341-343
 * 
 * - Never use a generator based on a linear congruential generator (LCG) or a multiplicative linear congruential generator (MLCG).
 * - Never use a generator with a period less than 2^64 (or any generator whose period is undisclosed).
 * - Never use a generator that warns against using its low-order bits as being completely random (usually, a LCG).
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class RecommendedRNG implements RNG
{
	long u,v,w;
	
	public RecommendedRNG (long seed)
	{
		v = 4101842887655102017L;
		w = 1;
		
		u = seed^v;
		integer();
		v = u; 
		integer();
		w = v; 
		integer();
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
		u = u * 2862933555777941757L + 7046029254386353087L;
		v ^= v >> 17; 
		v ^= v << 31; 
		v ^= v >> 8;
		w = 4294957665L*(w & 0xffffffff) + (w >> 32);
		
		long x = u ^ (u << 21); 
		x ^= x >> 35; 
		x ^= x << 4;
		
		return (x + v) ^ w;
	}
	
}
