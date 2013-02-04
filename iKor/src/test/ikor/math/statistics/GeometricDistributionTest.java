package test.ikor.math.statistics;

import static org.junit.Assert.assertEquals;
import ikor.math.statistics.DiscreteDistribution;
import ikor.math.statistics.Distribution;
import ikor.math.statistics.GeometricDistribution;

import org.junit.Before;
import org.junit.Test;


public class GeometricDistributionTest 
{
	public static double ERROR = 1e-7;

	@Before
	public void setUp() throws Exception 
	{
	}

	// G(0.1) == NB(1,1-0.1)

	@Test
	public void test1PDF() 
	{
		Distribution distribution = new GeometricDistribution(0.1);

		assertEquals( 0.0, distribution.pdf(-1), ERROR );
		assertEquals( 0.1, distribution.pdf(0), ERROR );
		assertEquals( 0.1*0.9, distribution.pdf(1), ERROR );
		assertEquals( 0.1*0.9*0.9, distribution.pdf(2), ERROR );
		assertEquals( 0.1*Math.pow(0.9,10), distribution.pdf(10), ERROR );
		assertEquals( 0.0, distribution.pdf(1e6), ERROR );
	}

	@Test
	public void test1CDF() 
	{
		Distribution distribution = new GeometricDistribution(0.1);

		assertEquals( 0.0, distribution.cdf(0), ERROR );
		assertEquals( 1.0-0.9*0.9, distribution.cdf(1), ERROR );
		assertEquals( 1.0-0.9*0.9*0.9, distribution.cdf(2), ERROR );
		assertEquals( 1.0-0.9*0.9*0.9*0.9, distribution.cdf(3), ERROR );
		assertEquals( 1.0, distribution.cdf(1e6), ERROR );
	}

	@Test
	public void test1IDF() 
	{
		Distribution distribution = new GeometricDistribution(0.1);

		assertEquals(  0, distribution.idf(0.0), ERROR );
		assertEquals(  0, distribution.idf(0.1), ERROR ); 
		assertEquals(  5, distribution.idf(0.5), ERROR ); 
		assertEquals( 20, distribution.idf(0.9), ERROR ); 
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}	

	@Test
	public void test1Statistics() 
	{
		DiscreteDistribution distribution = new GeometricDistribution(0.1);

		assertEquals( 9, distribution.mean(), ERROR );
		assertEquals( 90, distribution.variance(), ERROR );
		assertEquals( 1.9/Math.sqrt(0.9), distribution.skewness(), ERROR );
		assertEquals( 6+0.01/0.9, distribution.kurtosis(), ERROR );
	}	
	
	// G(0.5) == NB(1,0.5)

	@Test
	public void test5PDF() 
	{
		Distribution distribution = new GeometricDistribution(0.5);

		assertEquals( 0.0, distribution.pdf(-1), ERROR );
		assertEquals( 0.5, distribution.pdf(0), ERROR );
		assertEquals( 0.5*0.5, distribution.pdf(1), ERROR );
		assertEquals( 0.5*0.5*0.5, distribution.pdf(2), ERROR );
		assertEquals( 0.5*Math.pow(0.5,10), distribution.pdf(10), ERROR );
		assertEquals( 0.0, distribution.pdf(1e6), ERROR );
	}

	@Test
	public void test5CDF() 
	{
		Distribution distribution = new GeometricDistribution(0.5);

		assertEquals( 0.0, distribution.cdf(0), ERROR );
		assertEquals( 1.0-0.5*0.5, distribution.cdf(1), ERROR );
		assertEquals( 1.0-0.5*0.5*0.5, distribution.cdf(2), ERROR );
		assertEquals( 1.0-0.5*0.5*0.5*0.5, distribution.cdf(3), ERROR );
		assertEquals( 1.0, distribution.cdf(1e6), ERROR );
	}

	@Test
	public void test5IDF() 
	{
		Distribution distribution = new GeometricDistribution(0.5);

		assertEquals(  0, distribution.idf(0.0), ERROR );
		assertEquals(  0, distribution.idf(0.5), ERROR ); 
		assertEquals(  1, distribution.idf(0.75), ERROR ); 
		assertEquals(  1, distribution.idf(0.76), ERROR ); 
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	@Test
	public void test5Statistics() 
	{
		DiscreteDistribution distribution = new GeometricDistribution(0.5);

		assertEquals( 1, distribution.mean(), ERROR );
		assertEquals( 2, distribution.variance(), ERROR );
		assertEquals( 1.5/Math.sqrt(0.5), distribution.skewness(), ERROR );
		assertEquals( 6+0.25/0.5, distribution.kurtosis(), ERROR );
	}	
	
	// G(0.9) == NB(1,1-0.9)

	@Test
	public void test9PDF() 
	{
		Distribution distribution = new GeometricDistribution(0.9);

		assertEquals( 0.0, distribution.pdf(-1), ERROR );
		assertEquals( 0.9, distribution.pdf(0), ERROR );
		assertEquals( 0.9*0.1, distribution.pdf(1), ERROR );
		assertEquals( 0.9*0.1*0.1, distribution.pdf(2), ERROR );
		assertEquals( 0.9*Math.pow(0.1,10), distribution.pdf(10), ERROR );
		assertEquals( 0.0, distribution.pdf(1e6), ERROR );
	}

	@Test
	public void test9CDF() 
	{
		Distribution distribution = new GeometricDistribution(0.9);

		assertEquals( 0.0, distribution.cdf(0), ERROR );
		assertEquals( 1.0-0.1*0.1, distribution.cdf(1), ERROR );
		assertEquals( 1.0-0.1*0.1*0.1, distribution.cdf(2), ERROR );
		assertEquals( 1.0-0.1*0.1*0.1*0.1, distribution.cdf(3), ERROR );
		assertEquals( 1.0, distribution.cdf(1e6), ERROR );
	}

	@Test
	public void test9IDF() 
	{
		Distribution distribution = new GeometricDistribution(0.9);

		assertEquals(  0, distribution.idf(0.0), ERROR );
		assertEquals(  0, distribution.idf(0.5), ERROR ); 
		assertEquals(  0, distribution.idf(0.9), ERROR ); 
		assertEquals(  1, distribution.idf(0.99), ERROR ); 
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	@Test
	public void test9Statistics() 
	{
		DiscreteDistribution distribution = new GeometricDistribution(0.9);

		assertEquals( 0.1/0.9, distribution.mean(), ERROR );
		assertEquals( 0.1/0.81, distribution.variance(), ERROR );
		assertEquals( 1.1/Math.sqrt(0.1), distribution.skewness(), ERROR );
		assertEquals( 6+0.81/0.1, distribution.kurtosis(), ERROR );
	}
	
	// Intervals

	@Test
	public void testIntervals ()
	{
		Distribution distribution = new GeometricDistribution(0.5);

		assertEquals( 0.0000, distribution.cdf(0), ERROR);
		assertEquals( 0.7500, distribution.cdf(1), ERROR);
		assertEquals( 0.8750, distribution.cdf(2), ERROR);
		assertEquals( 0.9375, distribution.cdf(3), ERROR);
		assertEquals( 0.96875, distribution.cdf(4), ERROR);
		assertEquals( 1.00000, distribution.cdf(1e6), ERROR);
	}

	// Quantile function: z_p

	@Test
	public void testQuantiles ()
	{
		Distribution distribution = new GeometricDistribution(0.5);

		assertEquals( 0, distribution.idf(0.00), ERROR);
		assertEquals( 0, distribution.idf(0.74), ERROR);
		assertEquals( 1, distribution.idf(0.75), ERROR);
		assertEquals( 1, distribution.idf(0.87), ERROR);
		assertEquals( 2, distribution.idf(0.88), ERROR);
		assertEquals( 2, distribution.idf(0.93), ERROR);
		assertEquals( 3, distribution.idf(0.94), ERROR);
		assertEquals( 3, distribution.idf(0.96), ERROR);
		assertEquals( 4, distribution.idf(0.97), ERROR);	
	}
}
