package test.ikor.math.statistics;

import static org.junit.Assert.*;
import ikor.math.statistics.DiscreteDistribution;
import ikor.math.statistics.Distribution;
import ikor.math.statistics.PoissonDistribution;

import org.junit.Before;
import org.junit.Test;

public class PoissonDistributionTest 
{
	public static double ERROR = 1e-7;

	@Before
	public void setUp() throws Exception 
	{
	}

	// poisson(1)

	@Test
	public void test1PDF() 
	{
		Distribution distribution = new PoissonDistribution(1);

		assertEquals( 0.0, distribution.pdf(-1), ERROR );
		assertEquals( 1/Math.E, distribution.pdf(0.0), ERROR );
		assertEquals( 1/Math.E, distribution.pdf(1.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e6), ERROR );
	}

	@Test
	public void test1CDF() 
	{
		Distribution distribution = new PoissonDistribution(1);

		assertEquals( 0.0, distribution.cdf(-1e6), ERROR );
		assertEquals( 0.0, distribution.cdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e6), ERROR );
	}

	@Test
	public void test1IDF() 
	{
		Distribution distribution = new PoissonDistribution(1);

		assertEquals( 0, distribution.idf(0.0), ERROR );
		assertEquals( 1, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}	

	@Test
	public void test1Statistics() 
	{
		DiscreteDistribution distribution = new PoissonDistribution(1);

		assertEquals( 1, distribution.mean(), ERROR );
		assertEquals( 1, distribution.variance(), ERROR );
		assertEquals( 1, distribution.skewness(), ERROR );
		assertEquals( 1, distribution.kurtosis(), ERROR );
	}	
	
	// poisson(2)

	@Test
	public void test2PDF() 
	{
		Distribution distribution = new PoissonDistribution(2);

		assertEquals( 0.0, distribution.pdf(-1), ERROR );
		assertEquals( 1/(Math.E*Math.E), distribution.pdf(0.0), ERROR );
		assertEquals( 2/(Math.E*Math.E), distribution.pdf(1.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e6), ERROR );
	}

	@Test
	public void test2CDF() 
	{
		Distribution distribution = new PoissonDistribution(2);

		assertEquals( 0.0, distribution.cdf(-1e6), ERROR );
		assertEquals( 0.0, distribution.cdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e6), ERROR );
	}

	@Test
	public void test2IDF() 
	{
		Distribution distribution = new PoissonDistribution(2);

		assertEquals( 0, distribution.idf(0.0), ERROR );
		assertEquals( 2, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	@Test
	public void test2Statistics() 
	{
		DiscreteDistribution distribution = new PoissonDistribution(2);

		assertEquals( 2, distribution.mean(), ERROR );
		assertEquals( 2, distribution.variance(), ERROR );
		assertEquals( 1.0/Math.sqrt(2), distribution.skewness(), ERROR );
		assertEquals( 1.0/2.0, distribution.kurtosis(), ERROR );
	}		
	
	// poisson(5)

	@Test
	public void test5PDF() 
	{
		Distribution distribution = new PoissonDistribution(5);

		assertEquals( 0.0, distribution.pdf(-1), ERROR );
		assertEquals( 1.0/Math.exp(5), distribution.pdf(0), ERROR );
		assertEquals( 5.0/Math.exp(5), distribution.pdf(1), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e6), ERROR );
	}

	@Test
	public void test5CDF() 
	{
		Distribution distribution = new PoissonDistribution(5);

		assertEquals( 0.0, distribution.cdf(-1e6), ERROR );
		assertEquals( 0.0, distribution.cdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e6), ERROR );
	}

	@Test
	public void test5IDF() 
	{
		Distribution distribution = new PoissonDistribution(5);

		assertEquals( 0, distribution.idf(0.0), ERROR );
		assertEquals( 5, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	@Test
	public void test5Statistics() 
	{
		DiscreteDistribution distribution = new PoissonDistribution(5);

		assertEquals( 5, distribution.mean(), ERROR );
		assertEquals( 5, distribution.variance(), ERROR );
		assertEquals( 1.0/Math.sqrt(5), distribution.skewness(), ERROR );
		assertEquals( 1.0/5.0, distribution.kurtosis(), ERROR );
	}		


	// Tolerance intervals

	@Test
	public void testIntervals ()
	{
		Distribution distribution = new PoissonDistribution(1);

		assertEquals( 0.36787944, distribution.cdf(1), ERROR);
		assertEquals( 0.73575888, distribution.cdf(2), ERROR);
		assertEquals( 0.91969860, distribution.cdf(3), ERROR);
		assertEquals( 0.98101184, distribution.cdf(4), ERROR);
		assertEquals( 0.99634015, distribution.cdf(5), ERROR);
		assertEquals( 0.99940582, distribution.cdf(6), ERROR);
	}

	// Quantile function: z_p

	@Test
	public void testQuantiles ()
	{
		Distribution distribution = new PoissonDistribution(1);

		assertEquals( 0, distribution.idf(0.00), ERROR);
		assertEquals( 0, distribution.idf(0.36), ERROR);
		assertEquals( 1, distribution.idf(0.37), ERROR);
		assertEquals( 1, distribution.idf(0.73), ERROR);
		assertEquals( 2, distribution.idf(0.74), ERROR);
		assertEquals( 2, distribution.idf(0.91), ERROR);
		assertEquals( 3, distribution.idf(0.92), ERROR);
		assertEquals( 3, distribution.idf(0.98), ERROR);
		assertEquals( 4, distribution.idf(0.99), ERROR);	
		assertEquals( 4, distribution.idf(0.996), ERROR);	
		assertEquals( 5, distribution.idf(0.997), ERROR);	
		assertEquals( 5, distribution.idf(0.999), ERROR);	
		assertEquals( 6, distribution.idf(0.9995), ERROR);	
	}

}
