package ikor.math.random;

/**
 * 32-bit pseudorandom number generator.
 * 
 * ref. Numerical Recipes: The Art of Scientific Computing [3rd edition], 2007, p. 355-357
 * 
 * Using only 32-bit arithmetic: 3.11e37 period.
 *  
 * @author Fernando Berzal (berzal@acm.org)
 */

public class RNG32 implements RNG
{
	int u,v,w1,w2;
	
	public RNG32 (long seed)
	{
		v = (int) 2244614371L;
		w1 = 521288629;
		w2 = 362436069;
		
		u = ((int)seed) ^ v; 
		int32();
		v = u; 
		int32();
	}
		
	
	@Override
	public double random() 
	{
		// 2^32 = 0.00000000023283064365386962890625
		
		return 0.5 + 2.3283064365386962890625E-10 * ( int32() + 2.3283064365386962890625E-10 * int32() );  
		// vs. return 0.5 + 2.3283064365386962890625E-10 * int32();
	}
	
	
	@Override
	public long integer() 
	{
		return ((long)int32())<<32 + int32();
	}
	

	public int int32() 
	{
		int x,y;
		
		u = u * ((int)2891336453L) + 1640531513;
		v ^= v >> 13; 
		v ^= v << 17; 
		v ^= v >> 5;
		
		w1 = 33378 * (w1 & 0xffff) + (w1 >> 16);
		w2 = 57225 * (w2 & 0xffff) + (w2 >> 16);
		
		x = u ^ (u << 9); 
		x ^= x >> 17; 
		x ^= x << 6;
		
		y = w1 ^ (w1 << 17); 
		y ^= y >> 15; 
		y ^= y << 5;
		
		return (x + v) ^ (y + w2);
	}
}
