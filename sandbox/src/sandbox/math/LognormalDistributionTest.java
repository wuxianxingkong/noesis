package sandbox.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LognormalDistributionTest 
{
	public static double ERROR = 1e-7;

	@Before
	public void setUp() throws Exception 
	{
	}

	// LN(0,1)
	
	@Test
	public void testStandardPDF() 
	{
		LognormalDistribution distribution = new LognormalDistribution(0,1);
		
		assertEquals( 0.0, distribution.pdf(-1.0), ERROR );
		assertEquals( 0.0, distribution.pdf( 0.0), ERROR );
		assertEquals( 1/Math.sqrt(2*Math.PI), distribution.pdf(1.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e6), ERROR );
	}
	
	@Test
	public void testStandardCDF() 
	{
		LognormalDistribution distribution = new LognormalDistribution(0,1);
		
		assertEquals( 0.0, distribution.cdf(-1.0), ERROR );
		assertEquals( 0.0, distribution.cdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e6), ERROR );
	}
	
	@Test
	public void testStandardIDF() 
	{
		LognormalDistribution distribution = new LognormalDistribution(0,1);
		
		assertEquals(  0.0, distribution.idf(0.0), ERROR );
		assertEquals(  1.0, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}	
	
	// LN(1,1)

	@Test
	public void test11PDF() 
	{
		LognormalDistribution distribution = new LognormalDistribution(1,1);
		
		assertEquals( 0.0, distribution.pdf(-1.0), ERROR );
		assertEquals( 0.0, distribution.pdf( 0.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e6), ERROR );
	}
	
	@Test
	public void test11CDF() 
	{
		LognormalDistribution distribution = new LognormalDistribution(1,1);
		
		assertEquals( 0.0, distribution.cdf(-1.0), ERROR );
		assertEquals( 0.0, distribution.cdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e6), ERROR );
	}
	
	@Test
	public void test11IDF() 
	{
		LognormalDistribution distribution = new LognormalDistribution(1,1);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( Math.E, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	// LN(0,2)

	@Test
	public void test02PDF() 
	{
		LognormalDistribution distribution = new LognormalDistribution(0,2);
		
		assertEquals( 0.0, distribution.pdf(-1.0), ERROR );
		assertEquals( 0.0, distribution.pdf( 0.0), ERROR );
		assertEquals( 1/(2*Math.sqrt(2*Math.PI)), distribution.pdf(1.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e6), ERROR );
	}
	
	@Test
	public void test02CDF() 
	{
		LognormalDistribution distribution = new LognormalDistribution(0,2);
		
		assertEquals( 0.0, distribution.cdf(-1.0), ERROR );
		assertEquals( 0.0, distribution.cdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e6), ERROR );
	}
	
	@Test
	public void test02IDF() 
	{
		LognormalDistribution distribution = new LognormalDistribution(0,2);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( 1.0, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}
	
	// LN(5,2)
	
	@Test
	public void test52PDF() 
	{
		LognormalDistribution distribution = new LognormalDistribution(5,2);
		
		assertEquals( 0.0, distribution.pdf(-1.0), ERROR );
		assertEquals( 0.0, distribution.pdf( 0.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e6), ERROR );
	}
	
	
	@Test
	public void test52CDF() 
	{
		LognormalDistribution distribution = new LognormalDistribution(5,2);
		
		assertEquals( 0.0, distribution.cdf(-1.0), ERROR );
		assertEquals( 0.0, distribution.cdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e8), ERROR );
	}
	
	@Test
	public void test52IDF() 
	{
		LognormalDistribution distribution = new LognormalDistribution(5,2);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( Math.exp(5), distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}
	
	// Tolerance intervals
	
	@Test
	public void testIntervals ()
	{
		LognormalDistribution distribution = new LognormalDistribution(0,1);
		
		assertEquals( 0.500000000000, distribution.cdf(1), ERROR);
		assertEquals( 0.755891404196, distribution.cdf(2), ERROR);
		assertEquals( 0.864031392238, distribution.cdf(3), ERROR); // Three sigma
		assertEquals( 0.917171480925, distribution.cdf(4), ERROR);
		assertEquals( 0.946239689334, distribution.cdf(5), ERROR);
		assertEquals( 0.963414248448, distribution.cdf(6), ERROR); // Six sigma		
	}

	
	// Quantile function: z_p
	
	@Test
	public void testQuantiles ()
	{
		LognormalDistribution distribution = new LognormalDistribution(0,1);

		assertEquals(  1.000000000000, distribution.idf(0.50), ERROR);
		assertEquals(  1.963031086696, distribution.idf(0.75), ERROR);
		assertEquals(  3.602224470023, distribution.idf(0.90), ERROR);
		assertEquals(  5.180251602337, distribution.idf(0.95), ERROR);
		assertEquals( 10.240473676273, distribution.idf(0.99), ERROR);
	}
	
}

