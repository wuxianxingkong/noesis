package sandbox.math;

import static org.junit.Assert.*;

import ikor.math.Configuration;

import org.junit.Before;
import org.junit.Test;

public class WaldDistributionTest 
{
	public static double ERROR = Configuration.EPSILON;

	@Before
	public void setUp() throws Exception 
	{
	}

	// IG(1,1)
	
	@Test
	public void test11PDF() 
	{
		WaldDistribution distribution = new WaldDistribution(1,1);
		
		assertEquals( 0.0, distribution.pdf(0.0), ERROR );
		assertEquals( 1/Math.sqrt(2*Math.PI), distribution.pdf( 1.0), ERROR );
		assertEquals( 0.0, distribution.pdf(1e6), ERROR );
	}
	
	@Test
	public void test11CDF() 
	{
		WaldDistribution distribution = new WaldDistribution(1,1);
		
		assertEquals( 0.0, distribution.cdf(0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(1e6), ERROR );
	}
	
	@Test
	public void test11IDF() 
	{
		WaldDistribution distribution = new WaldDistribution(1,1);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}	

	@Test
	public void test11Statistics() 
	{
		WaldDistribution distribution = new WaldDistribution(1,1);
		
		assertEquals(  1.0, distribution.mean(), ERROR );
		assertEquals(  1.0, distribution.variance(), ERROR );
		assertEquals(  3.0, distribution.skewness(), ERROR );
		assertEquals( 15.0, distribution.kurtosis(), ERROR );
	}
	
	// IG(1,2)

	@Test
	public void test12PDF() 
	{
		WaldDistribution distribution = new WaldDistribution(1,2);
		
		assertEquals( 0.0, distribution.pdf(0.0), ERROR );
		assertEquals( 1/Math.sqrt(Math.PI), distribution.pdf( 1.0), ERROR );
		assertEquals( 0.0, distribution.pdf(1e6), ERROR );
	}
	
	@Test
	public void test12CDF() 
	{
		WaldDistribution distribution = new WaldDistribution(1,2);
		
		assertEquals( 0.0, distribution.cdf(0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(1e6), ERROR );
	}
	
	@Test
	public void test12IDF() 
	{
		WaldDistribution distribution = new WaldDistribution(1,2);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	@Test
	public void test12Statistics() 
	{
		WaldDistribution distribution = new WaldDistribution(1,2);
		
		assertEquals(  1.0, distribution.mean(), ERROR );
		assertEquals(  0.5, distribution.variance(), ERROR );
		assertEquals(  3.0*Math.sqrt(0.5), distribution.skewness(), ERROR );
		assertEquals( 15.0*0.5, distribution.kurtosis(), ERROR );
	}
	
	// IG(2,1)

	@Test
	public void test21PDF() 
	{
		WaldDistribution distribution = new WaldDistribution(2,1);
		
		assertEquals( 0.0, distribution.pdf(0.0), ERROR );
		assertEquals( 1/Math.sqrt(2*Math.PI*8), distribution.pdf(2.0), ERROR );
		assertEquals( 0.0, distribution.pdf(1e6), ERROR );
	}
	
	@Test
	public void test21CDF() 
	{
		WaldDistribution distribution = new WaldDistribution(2,1);
		
		assertEquals( 0.0, distribution.cdf(0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(1e6), ERROR );
	}
	
	@Test
	public void test21IDF() 
	{
		WaldDistribution distribution = new WaldDistribution(2,1);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	@Test
	public void test21Statistics() 
	{
		WaldDistribution distribution = new WaldDistribution(2,1);
		
		assertEquals(  2, distribution.mean(), ERROR );
		assertEquals(  8, distribution.variance(), ERROR );
		assertEquals(  3*Math.sqrt(2), distribution.skewness(), ERROR );
		assertEquals( 15*2, distribution.kurtosis(), ERROR );
	}
	
	// IG(5,2)
	
	@Test
	public void test52PDF() 
	{
		WaldDistribution distribution = new WaldDistribution(5,2);
		
		assertEquals( 0.0, distribution.pdf(0), ERROR );
		assertEquals( 1/Math.sqrt(Math.PI*5*5*5), distribution.pdf(5.0), ERROR );
		assertEquals( 0.0, distribution.pdf(1e6), ERROR );
	}
	
	
	@Test
	public void test52CDF() 
	{
		WaldDistribution distribution = new WaldDistribution(5,2);
		
		assertEquals( 0.0, distribution.cdf(0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(1e6), ERROR );
	}
	
	@Test
	public void test52IDF() 
	{
		WaldDistribution distribution = new WaldDistribution(5,2);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	@Test
	public void test52Statistics() 
	{
		WaldDistribution distribution = new WaldDistribution(5,2);
		
		assertEquals( 5, distribution.mean(), ERROR );
		assertEquals( 125.0/2.0, distribution.variance(), ERROR );
		assertEquals( 3*Math.sqrt(2.5), distribution.skewness(), ERROR );
		assertEquals( 15*2.5, distribution.kurtosis(), ERROR );
	}

	// Intervals
	
	@Test
	public void testIntervals ()
	{
		WaldDistribution distribution = new WaldDistribution(1,1);
		
		assertEquals( 0.668102001223, distribution.cdf(1), ERROR);
		assertEquals( 0.885475429860, distribution.cdf(2), ERROR);
		assertEquals( 0.953187920743, distribution.cdf(3), ERROR); // Three sigma
		assertEquals( 0.979076364179, distribution.cdf(4), ERROR);
		assertEquals( 0.990115297400, distribution.cdf(5), ERROR);
		assertEquals( 0.995150117866, distribution.cdf(6), ERROR); // Six sigma
	}
	
	// Quantile function: z_p
	
	@Test
	public void testQuantiles ()
	{
		WaldDistribution distribution = new WaldDistribution(1,1);

		assertEquals( 0.675841249631, distribution.idf(0.50), ERROR);
		assertEquals( 1.244059774308, distribution.idf(0.75), ERROR);
		assertEquals( 2.143033896118, distribution.idf(0.90), ERROR);
		assertEquals( 2.922076021150, distribution.idf(0.95), ERROR);
		assertEquals( 4.984094800875, distribution.idf(0.99), ERROR);
		assertEquals( 8.354864974081, distribution.idf(0.999), ERROR);

		assertEquals( 0.50, distribution.cdf(0.675841249631), ERROR);
		assertEquals( 0.75, distribution.cdf(1.244059774308), ERROR);
		assertEquals( 0.90, distribution.cdf(2.143033896118), ERROR);
		assertEquals( 0.95, distribution.cdf(2.922076021150), ERROR);
		assertEquals( 0.99, distribution.cdf(4.984094800875), ERROR);
		assertEquals( 0.999,distribution.cdf(8.354864974081), ERROR);
	}
}

