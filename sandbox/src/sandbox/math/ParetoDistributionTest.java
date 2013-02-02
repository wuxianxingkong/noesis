package sandbox.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ParetoDistributionTest 
{
	public static double ERROR = 1e-6;

	@Before
	public void setUp() throws Exception 
	{
	}

	// Pareto(1)
	
	@Test
	public void test1PDF() 
	{
		ParetoDistribution distribution = new ParetoDistribution(1);
		
		assertEquals( 0.0, distribution.pdf(0), ERROR );
		assertEquals( 0.0, distribution.pdf(1), ERROR );
		assertEquals( 0.25, distribution.pdf(2), ERROR );
		assertEquals( 0.04, distribution.pdf(5), ERROR );
		assertEquals( 0.00, distribution.pdf(+1e10), ERROR );
	}
	
	@Test
	public void test1CDF() 
	{
		ParetoDistribution distribution = new ParetoDistribution(1);
		
		assertEquals( 0.0, distribution.cdf(1), ERROR );
		assertEquals( 0.5, distribution.cdf(2), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test1IDF() 
	{
		ParetoDistribution distribution = new ParetoDistribution(1);
		
		assertEquals( 1.0, distribution.idf(0.0), ERROR );
		assertEquals( 2.0, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}	
	
	// Pareto(2)

	@Test
	public void test2PDF() 
	{
		ParetoDistribution distribution = new ParetoDistribution(2);
		
		assertEquals( 0.0, distribution.pdf(0), ERROR );
		assertEquals( 0.0, distribution.pdf(1), ERROR );
		assertEquals( 0.25, distribution.pdf(2), ERROR );
		assertEquals( 0.016, distribution.pdf(5), ERROR );
		assertEquals( 0.00, distribution.pdf(+1e10), ERROR );
	}
	
	@Test
	public void test2CDF() 
	{
		ParetoDistribution distribution = new ParetoDistribution(2);
		
		assertEquals( 0.0, distribution.cdf(1), ERROR );
		assertEquals( 0.75, distribution.cdf(2), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test2IDF() 
	{
		ParetoDistribution distribution = new ParetoDistribution(2);
		
		assertEquals( 1.0, distribution.idf(0.0), ERROR );
		assertEquals( 2.0, distribution.idf(0.75), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	// Pareto(2,2)

	@Test
	public void test22PDF() 
	{
		ParetoDistribution distribution = new ParetoDistribution(2,2);
		
		assertEquals( 0.0, distribution.pdf(0), ERROR );
		assertEquals( 0.0, distribution.pdf(2), ERROR );
		assertEquals( 0.125, distribution.pdf(4), ERROR );
		assertEquals( 0.008, distribution.pdf(10), ERROR );
		assertEquals( 0.00, distribution.pdf(+1e10), ERROR );
	}
	
	@Test
	public void test22CDF() 
	{
		ParetoDistribution distribution = new ParetoDistribution(2,2);
		
		assertEquals( 0.0, distribution.cdf(2), ERROR );
		assertEquals( 0.75, distribution.cdf(4), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test22IDF() 
	{
		ParetoDistribution distribution = new ParetoDistribution(2,2);
		
		assertEquals( 2.0, distribution.idf(0.0), ERROR );
		assertEquals( 4.0, distribution.idf(0.75), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}
	
	
	// Quantile function: z_p
	
	@Test
	public void testQuantiles ()
	{
		ParetoDistribution distribution = new ParetoDistribution(1);

		assertEquals( 1.0, distribution.idf(0.00), ERROR);
		assertEquals( 1.0/(1.0-0.01), distribution.idf(0.01), ERROR);
		assertEquals( 1.0/(1.0-0.05), distribution.idf(0.05), ERROR);
		assertEquals( 1.0/(1.0-0.10), distribution.idf(0.10), ERROR);
		assertEquals( 1.0/(1.0-0.50), distribution.idf(0.50), ERROR);
		assertEquals( 1.0/(1.0-0.90), distribution.idf(0.90), ERROR);
		assertEquals( 1.0/(1.0-0.95), distribution.idf(0.95), ERROR);
		assertEquals( 1.0/(1.0-0.99), distribution.idf(0.99), ERROR);
	}

}
