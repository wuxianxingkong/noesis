package test.ikor.math.statistics;

import static org.junit.Assert.*;
import ikor.math.statistics.LaplaceDistribution;

import org.junit.Before;
import org.junit.Test;

public class LaplaceDistributionTest 
{
	public static double ERROR = 1e-6;

	@Before
	public void setUp() throws Exception 
	{
	}

	// Laplace(0,1)
	
	@Test
	public void test01PDF() 
	{
		LaplaceDistribution distribution = new LaplaceDistribution(0,1);
		
		assertEquals( 0.0, distribution.pdf(-1e10), ERROR );
		assertEquals( 1.0/(2*Math.E), distribution.pdf(-1.0), ERROR );
		assertEquals( 0.5, distribution.pdf( 0.0), ERROR );
		assertEquals( 1.0/(2*Math.E), distribution.pdf( 1.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e10), ERROR );
	}
	
	@Test
	public void test01CDF() 
	{
		LaplaceDistribution distribution = new LaplaceDistribution(0,1);
		
		assertEquals( 0.0, distribution.cdf(-1e16), ERROR );
		assertEquals( 0.5, distribution.cdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test01IDF() 
	{
		LaplaceDistribution distribution = new LaplaceDistribution(0,1);
		
		assertEquals( Double.NEGATIVE_INFINITY, distribution.idf(0.0), ERROR );
		assertEquals( 0.0, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}	
	
	// Laplace(0,2)

	@Test
	public void test02PDF() 
	{
		LaplaceDistribution distribution = new LaplaceDistribution(0,2);
		
		assertEquals( 0.0, distribution.pdf(-1e10), ERROR );
		assertEquals( 1.0/(4*Math.E), distribution.pdf(-2.0), ERROR );
		assertEquals( 1.0/4, distribution.pdf( 0.0), ERROR );
		assertEquals( 1.0/(4*Math.E), distribution.pdf( 2.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e10), ERROR );		
	}
	
	@Test
	public void test02CDF() 
	{
		LaplaceDistribution distribution = new LaplaceDistribution(0,2);
		
		assertEquals( 0.0, distribution.cdf(-1e16), ERROR );
		assertEquals( 0.5, distribution.cdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test02IDF() 
	{
		LaplaceDistribution distribution = new LaplaceDistribution(0,2);
		
		assertEquals( Double.NEGATIVE_INFINITY, distribution.idf(0.0), ERROR );
		assertEquals( 0.0, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	// Laplace(2,2)

	@Test
	public void test22PDF() 
	{
		LaplaceDistribution distribution = new LaplaceDistribution(2,2);
		
		assertEquals( 0.0, distribution.pdf(-1e10), ERROR );
		assertEquals( 1.0/(4*Math.E), distribution.pdf(0.0), ERROR );
		assertEquals( 1.0/4, distribution.pdf(2.0), ERROR );
		assertEquals( 1.0/(4*Math.E), distribution.pdf(4.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e10), ERROR );		
	}
	
	@Test
	public void test22CDF() 
	{
		LaplaceDistribution distribution = new LaplaceDistribution(2,2);
		
		assertEquals( 0.0, distribution.cdf(-1e16), ERROR );
		assertEquals( 0.5, distribution.cdf( 2.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test22IDF() 
	{
		LaplaceDistribution distribution = new LaplaceDistribution(2,2);
		
		assertEquals( Double.NEGATIVE_INFINITY, distribution.idf(0.0), ERROR );
		assertEquals( 2.0, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}
	
	
	// Quantile function: z_p
	
	@Test
	public void testQuantiles ()
	{
		LaplaceDistribution distribution = new LaplaceDistribution(0,1);

		assertEquals( +Math.log(0.02), distribution.idf(0.01), ERROR);
		assertEquals( +Math.log(0.10), distribution.idf(0.05), ERROR);
		assertEquals( +Math.log(0.20), distribution.idf(0.10), ERROR);
		assertEquals( 0.00, distribution.idf(0.50), ERROR);
		assertEquals( -Math.log(0.20), distribution.idf(0.90), ERROR);
		assertEquals( -Math.log(0.10), distribution.idf(0.95), ERROR);
		assertEquals( -Math.log(0.02), distribution.idf(0.99), ERROR);
	}

}