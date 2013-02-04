package test.ikor.math.statistics;

import static org.junit.Assert.*;
import ikor.math.statistics.FDistribution;

import org.junit.Before;
import org.junit.Test;

public class FDistributionTest 
{
	public static double ERROR = 1e-6;

	@Before
	public void setUp() throws Exception 
	{
	}

	// F(1,1)
	
	@Test
	public void test11PDF() 
	{
		FDistribution distribution = new FDistribution(1,1);
		
		assertEquals( 0.0, distribution.pdf(-1e10), ERROR );
		assertEquals( 0.0, distribution.pdf( 0.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e10), ERROR );
	}
	
	@Test
	public void test11CDF() 
	{
		FDistribution distribution = new FDistribution(1,1);
		
		assertEquals( 0.0, distribution.cdf(-1e16), ERROR );
		assertEquals( 0.0, distribution.cdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test11IDF() 
	{
		FDistribution distribution = new FDistribution(1,1);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}	
	
	// F(2,1)

	@Test
	public void test21PDF() 
	{
		FDistribution distribution = new FDistribution(2,1);
		
		assertEquals( 0.0, distribution.pdf(-1e16), ERROR );
		assertEquals( 0.0, distribution.pdf(  0.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e16), ERROR );
	}
	
	@Test
	public void test21CDF() 
	{
		FDistribution distribution = new FDistribution(2,1);
		
		assertEquals( 0.0, distribution.cdf(-1e16), ERROR );
		assertEquals( 0.0, distribution.cdf(  0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test21IDF() 
	{
		FDistribution distribution = new FDistribution(2,1);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	// F(5,2)

	@Test
	public void test52PDF() 
	{
		FDistribution distribution = new FDistribution(5,2);
		
		assertEquals( 0.0, distribution.pdf(-1e16), ERROR );
		assertEquals( 0.0, distribution.pdf(  0.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e16), ERROR );
	}
	
	@Test
	public void test52CDF() 
	{
		FDistribution distribution = new FDistribution(5,2);
		
		assertEquals( 0.0, distribution.cdf(-1e16), ERROR );
		assertEquals( 0.0, distribution.cdf(  0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test52IDF() 
	{
		FDistribution distribution = new FDistribution(5,2);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}
	
	
	// Quantile function: z_p
	
	@Test
	public void testQuantiles ()
	{
		FDistribution distribution = new FDistribution(1,1);

		assertEquals(   0.000000, distribution.idf(0.00), ERROR);
		assertEquals(   1.000000, distribution.idf(0.50), ERROR);
		assertEquals(  39.863458, distribution.idf(0.90), ERROR);
		assertEquals( 161.447639, distribution.idf(0.95), ERROR);
		assertEquals(4052.180695, distribution.idf(0.99), ERROR);
	}

}
