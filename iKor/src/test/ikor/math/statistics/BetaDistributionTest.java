package test.ikor.math.statistics;

import static org.junit.Assert.*;
import ikor.math.statistics.BetaDistribution;

import org.junit.Before;
import org.junit.Test;

public class BetaDistributionTest 
{
	public static double ERROR = 1e-6;

	@Before
	public void setUp() throws Exception 
	{
	}

	// Beta(1,1) == Uniform(0,1)
	
	@Test
	public void test11PDF() 
	{
		BetaDistribution distribution = new BetaDistribution(1,1);
		
		assertEquals( 0.0, distribution.pdf(-1e10), ERROR );
		assertEquals( 0.0, distribution.pdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.pdf( 0.2), ERROR );
		assertEquals( 1.0, distribution.pdf( 0.5), ERROR );
		assertEquals( 1.0, distribution.pdf( 0.8), ERROR );
		assertEquals( 0.0, distribution.pdf( 1.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e10), ERROR );
	}
	
	@Test
	public void test11CDF() 
	{
		BetaDistribution distribution = new BetaDistribution(1,1);
		
		assertEquals( 0.0, distribution.cdf(-1e16), ERROR );
		assertEquals( 0.0, distribution.cdf( 0.0), ERROR );
		assertEquals( 0.2, distribution.cdf( 0.2), ERROR );
		assertEquals( 0.5, distribution.cdf( 0.5), ERROR );
		assertEquals( 0.8, distribution.cdf( 0.8), ERROR );
		assertEquals( 1.0, distribution.cdf( 1.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test11IDF() 
	{
		BetaDistribution distribution = new BetaDistribution(1,1);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( 0.2, distribution.idf(0.2), ERROR );
		assertEquals( 0.5, distribution.idf(0.5), ERROR );
		assertEquals( 0.8, distribution.idf(0.8), ERROR );
		assertEquals( 1.0, distribution.idf(1.0), ERROR );
	}	
	
	// Beta(2,2)

	@Test
	public void test22PDF() 
	{
		BetaDistribution distribution = new BetaDistribution(2,2);
		
		assertEquals( 0.0, distribution.pdf(-1e16), ERROR );
		assertEquals( 0.0, distribution.pdf( 0.0), ERROR );
		assertEquals( 1.5, distribution.pdf( 0.5), ERROR );
		assertEquals( 0.0, distribution.pdf( 1.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e16), ERROR );
	}
	
	@Test
	public void test22CDF() 
	{
		BetaDistribution distribution = new BetaDistribution(2,2);
		
		assertEquals( 0.0, distribution.cdf(-1e16), ERROR );
		assertEquals( 0.0, distribution.cdf(  0.0), ERROR );
		assertEquals( 0.5, distribution.cdf(  0.5), ERROR );
		assertEquals( 1.0, distribution.cdf(  1.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test22IDF() 
	{
		BetaDistribution distribution = new BetaDistribution(2,2);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( 0.5, distribution.idf(0.5), ERROR );
		assertEquals( 1.0, distribution.idf(1.0), ERROR );
	}

	// Beta(2,5)

	@Test
	public void test25PDF() 
	{
		BetaDistribution distribution = new BetaDistribution(2,5);
		
		assertEquals( 0.0, distribution.pdf(-1e16), ERROR );
		assertEquals( 0.0, distribution.pdf( 0.0), ERROR );
		assertEquals( 0.0, distribution.pdf( 1.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e16), ERROR );
	}
	
	@Test
	public void test25CDF() 
	{
		BetaDistribution distribution = new BetaDistribution(2,5);
		
		assertEquals( 0.0, distribution.cdf(-1e16), ERROR );
		assertEquals( 0.0, distribution.cdf(  0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(  1.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test25IDF() 
	{
		BetaDistribution distribution = new BetaDistribution(2,5);
		
		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( 1.0, distribution.idf(1.0), ERROR );
	}
	
	
	// Quantile function: z_p
	
	@Test
	public void testQuantiles1 ()
	{
		BetaDistribution distribution = new BetaDistribution(1,1);

		assertEquals( 0.00, distribution.idf(0.00), ERROR);
		assertEquals( 0.50, distribution.idf(0.50), ERROR);
		assertEquals( 0.90, distribution.idf(0.90), ERROR);
		assertEquals( 0.95, distribution.idf(0.95), ERROR);
		assertEquals( 0.99, distribution.idf(0.99), ERROR);
	}

	@Test
	public void testQuantiles2 ()
	{
		BetaDistribution distribution = new BetaDistribution(2,2);

		assertEquals( 0.00, distribution.idf(0.00), ERROR);
		assertEquals( 0.50, distribution.idf(0.50), ERROR);
		assertEquals( 0.804200, distribution.idf(0.90), ERROR);
		assertEquals( 0.864650, distribution.idf(0.95), ERROR);
		assertEquals( 0.941097, distribution.idf(0.99), ERROR);
	}

	@Test
	public void testQuantiles05 ()
	{
		BetaDistribution distribution = new BetaDistribution(0.5,0.5);

		assertEquals( 0.00, distribution.idf(0.00), ERROR);
		assertEquals( 0.50, distribution.idf(0.50), ERROR);
		assertEquals( 0.975528, distribution.idf(0.90), ERROR);
		assertEquals( 0.993844, distribution.idf(0.95), ERROR);
		assertEquals( 0.999753, distribution.idf(0.99), ERROR);
	}
	
}

