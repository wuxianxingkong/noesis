package sandbox.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class UniformDistributionTest 
{
	public static double ERROR = 1e-7;

	@Before
	public void setUp() throws Exception 
	{
	}

	// U(0,1)
	
	@Test
	public void testStandardPDF() 
	{
		UniformDistribution uniform = new UniformDistribution(0,1);
		
		assertEquals( 0.0, uniform.pdf(-1.0), ERROR );
		assertEquals( 1.0, uniform.pdf( 0.0), ERROR );
		assertEquals( 1.0, uniform.pdf( 0.5), ERROR );
		assertEquals( 1.0, uniform.pdf( 1.0), ERROR );
		assertEquals( 0.0, uniform.pdf(+2.0), ERROR );
	}
	
	@Test
	public void testStandardCDF() 
	{
		UniformDistribution uniform = new UniformDistribution(0,1);
		
		assertEquals( 0.0, uniform.cdf(0.0), ERROR );
		assertEquals( 0.5, uniform.cdf(0.5), ERROR );
		assertEquals( 1.0, uniform.cdf(1.0), ERROR );
	}
	
	@Test
	public void testStandardIDF() 
	{
		UniformDistribution uniform = new UniformDistribution(0,1);
		
		assertEquals( 0.0, uniform.idf(0.0), ERROR );
		assertEquals( 0.5, uniform.idf(0.5), ERROR );
		assertEquals( 1.0, uniform.idf(1.0), ERROR );
	}	

	@Test
	public void testStandardStatistics() 
	{
		UniformDistribution uniform = new UniformDistribution(0,1);
		
		assertEquals( 0.5, uniform.mean(), ERROR );
		assertEquals( 1.0/12.0, uniform.variance(), ERROR );
		assertEquals( 0.0, uniform.skewness(), ERROR );
		assertEquals(-1.2, uniform.kurtosis(), ERROR );
	}
	
	// U(1,1)

	@Test
	public void test11PDF() 
	{
		UniformDistribution uniform = new UniformDistribution(1,1);
		
		assertEquals( 0.0, uniform.pdf(0.9), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, uniform.pdf( 1.0), ERROR );
		assertEquals( 0.0, uniform.pdf(1.1), ERROR );
	}
	
	@Test
	public void test11CDF() 
	{
		UniformDistribution uniform = new UniformDistribution(1,1);
		
		assertEquals( 0.0, uniform.cdf(0.9), ERROR );
		assertEquals( 0.0, uniform.cdf(1.0), ERROR );
		assertEquals( 1.0, uniform.cdf(1.1), ERROR );
	}
	
	@Test
	public void test11IDF() 
	{
		UniformDistribution uniform = new UniformDistribution(1,1);
		
		assertEquals( 1.0, uniform.idf(0.0), ERROR );
		assertEquals( 1.0, uniform.idf(0.5), ERROR );
		assertEquals( 1.0, uniform.idf(1.0), ERROR );
	}

	@Test
	public void test11Statistics() 
	{
		UniformDistribution uniform = new UniformDistribution(1,1);
		
		assertEquals( 1.0, uniform.mean(), ERROR );
		assertEquals( 0.0, uniform.variance(), ERROR );
		assertEquals( 0.0, uniform.skewness(), ERROR );
		assertEquals(-1.2, uniform.kurtosis(), ERROR );
	}

	// U(0,2)

	@Test
	public void test02PDF() 
	{
		UniformDistribution uniform = new UniformDistribution(0,2);
		
		assertEquals( 0.0, uniform.pdf(-1.0), ERROR );
		assertEquals( 0.5, uniform.pdf(0.0), ERROR );
		assertEquals( 0.5, uniform.pdf(1.0), ERROR );
		assertEquals( 0.5, uniform.pdf(2.0), ERROR );
		assertEquals( 0.0, uniform.pdf(3.0), ERROR );
	}
	
	@Test
	public void test02CDF() 
	{
		UniformDistribution uniform = new UniformDistribution(0,2);
		
		assertEquals( 0.0, uniform.cdf(0.0), ERROR );
		assertEquals( 0.5, uniform.cdf(1.0), ERROR );
		assertEquals( 1.0, uniform.cdf(2.0), ERROR );
	}
	
	@Test
	public void test02IDF() 
	{
		UniformDistribution uniform = new UniformDistribution(0,2);
		
		assertEquals( 0.0, uniform.idf(0.0), ERROR );
		assertEquals( 1.0, uniform.idf(0.5), ERROR );
		assertEquals( 2.0, uniform.idf(1.0), ERROR );
	}

	@Test
	public void test02Statistics() 
	{
		UniformDistribution uniform = new UniformDistribution(0,2);
		
		assertEquals( 1.0, uniform.mean(), ERROR );
		assertEquals( 4.0/12.00, uniform.variance(), ERROR );
		assertEquals( 0.0, uniform.skewness(), ERROR );
		assertEquals(-1.2, uniform.kurtosis(), ERROR );
	}
	
	// U(2,5)
	
	@Test
	public void test25PDF() 
	{
		UniformDistribution uniform = new UniformDistribution(2,5);
		
		assertEquals( 0.0, uniform.pdf(1.0), ERROR );
		assertEquals( 1.0/3.0, uniform.pdf(2.0), ERROR );
		assertEquals( 1.0/3.0, uniform.pdf(3.0), ERROR );
		assertEquals( 1.0/3.0, uniform.pdf(4.0), ERROR );
		assertEquals( 1.0/3.0, uniform.pdf(5.0), ERROR );
		assertEquals( 0.0, uniform.pdf(6.0), ERROR );
	}
	
	
	@Test
	public void test25CDF() 
	{
		UniformDistribution uniform = new UniformDistribution(2,5);
		
		assertEquals( 0.0, uniform.cdf(2.0), ERROR );
		assertEquals( 0.5, uniform.cdf(3.5), ERROR );
		assertEquals( 1.0, uniform.cdf(5.0), ERROR );
	}
	
	@Test
	public void test25IDF() 
	{
		UniformDistribution uniform = new UniformDistribution(2,5);
		
		assertEquals( 2.0, uniform.idf(0.0), ERROR );
		assertEquals( 3.5, uniform.idf(0.5), ERROR );
		assertEquals( 5.0, uniform.idf(1.0), ERROR );
	}

	@Test
	public void test25Statistics() 
	{
		UniformDistribution uniform = new UniformDistribution(2,5);
		
		assertEquals( 3.5, uniform.mean(), ERROR );
		assertEquals( 9.0/12.00, uniform.variance(), ERROR );
		assertEquals( 0.0, uniform.skewness(), ERROR );
		assertEquals(-1.2, uniform.kurtosis(), ERROR );
	}
	
	// Tolerance intervals
	
	@Test
	public void testIntervals ()
	{
		UniformDistribution uniform = new UniformDistribution(0,1);
		
		assertEquals( 0.0, uniform.cdf(0.0), ERROR);
		assertEquals( 0.2, uniform.cdf(0.2), ERROR);
		assertEquals( 0.4, uniform.cdf(0.4), ERROR);
		assertEquals( 0.6, uniform.cdf(0.6), ERROR);
		assertEquals( 0.8, uniform.cdf(0.8), ERROR);
		assertEquals( 1.0, uniform.cdf(1.0), ERROR);
	}
	
	// Quantile function: z_p
	// a.k.a probit function for N(0,1)
	
	@Test
	public void testQuantiles ()
	{
		UniformDistribution uniform = new UniformDistribution(0,1);

		assertEquals( 0.90, uniform.idf(0.90), ERROR);
		assertEquals( 0.95, uniform.idf(0.95), ERROR);
		assertEquals( 0.99, uniform.idf(0.99), ERROR);
		
		assertEquals( 0.999000000, uniform.idf(0.999), ERROR);
		assertEquals( 0.999900000, uniform.idf(0.9999), ERROR);
		assertEquals( 0.999990000, uniform.idf(0.99999), ERROR);
		assertEquals( 0.999999000, uniform.idf(0.999999), ERROR);
		assertEquals( 0.999999900, uniform.idf(0.9999999), ERROR);
		assertEquals( 0.999999990, uniform.idf(0.99999999), ERROR);
		assertEquals( 0.999999999, uniform.idf(0.999999999), ERROR);
	}

}
