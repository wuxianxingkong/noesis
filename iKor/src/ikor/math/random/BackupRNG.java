package ikor.math.random;

/**
 * Backup pseudorandom number generator.
 * 
 * ref. Numerical Recipes: The Art of Scientific Computing [3rd edition], 2007, p. 352
 * 
 * Replacement for FastestRNG with a 8.5e37 period.
 *  
 * @author Fernando Berzal (berzal@acm.org)
 */

public class BackupRNG implements RNG
{
	long v;
	long w;
	
	
	public BackupRNG (long seed)
	{
		v = 4101842887655102017L;
		w = 1;
		v ^= seed;
		w = integer();
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
		v ^= v >> 17; 
		v ^= v << 31; 
		v ^= v >> 8;
		w = 4294957665L*(w & 0xffffffff) + (w >> 32);
		return v ^ w;
	}

}
