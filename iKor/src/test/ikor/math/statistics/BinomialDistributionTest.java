package test.ikor.math.statistics;

import static org.junit.Assert.*;
import ikor.math.statistics.BinomialDistribution;
import ikor.math.statistics.DiscreteDistribution;
import ikor.math.statistics.Distribution;

import org.junit.Before;
import org.junit.Test;

public class BinomialDistributionTest 
{
	public static double ERROR = 1e-7;

	@Before
	public void setUp() throws Exception 
	{
	}

	// binomial(10,0.1)

	@Test
	public void test1PDF() 
	{
		Distribution distribution = new BinomialDistribution(10,0.1);

		assertEquals( 0.0, distribution.pdf(-1), ERROR );
		assertEquals(  1*Math.pow(0.9,10), distribution.pdf(0), ERROR );
		assertEquals( 10*0.1*Math.pow(0.9,9), distribution.pdf(1), ERROR );
		assertEquals( 252*Math.pow(0.1,5)*Math.pow(0.9,5), distribution.pdf(5), ERROR );
		assertEquals( 10*0.9*Math.pow(0.1,9), distribution.pdf(9), ERROR );
		assertEquals(  1*Math.pow(0.1,10), distribution.pdf(10), ERROR );
		assertEquals( 0.0, distribution.pdf(11), ERROR );
	}

	@Test
	public void test1CDF() 
	{
		Distribution distribution = new BinomialDistribution(10,0.1);

		assertEquals( 0.0, distribution.cdf(0), ERROR );
		assertEquals( 1.0, distribution.cdf(10), ERROR );
	}

	@Test
	public void test1IDF() 
	{
		Distribution distribution = new BinomialDistribution(10,0.1);

		assertEquals(  0, distribution.idf(0.0), ERROR );
		assertEquals(  1, distribution.idf(0.5), ERROR );
		assertEquals( 10, distribution.idf(1.0), ERROR );
	}	

	@Test
	public void test1Statistics() 
	{
		DiscreteDistribution distribution = new BinomialDistribution(10,0.1);

		assertEquals( 1, distribution.mean(), ERROR );
		assertEquals( 0.9, distribution.variance(), ERROR );
		assertEquals( 0.8/Math.sqrt(0.9), distribution.skewness(), ERROR );
		assertEquals( (1-6*0.1*0.9)/0.9, distribution.kurtosis(), ERROR );
	}	
	
	// binomial(10,0.2)

	@Test
	public void test2PDF() 
	{
		Distribution distribution = new BinomialDistribution(10,0.2);

		assertEquals( 0.0, distribution.pdf(-1), ERROR );
		assertEquals(  1*Math.pow(0.8,10), distribution.pdf(0), ERROR );
		assertEquals( 10*0.2*Math.pow(0.8,9), distribution.pdf(1), ERROR );
		assertEquals( 252*Math.pow(0.2,5)*Math.pow(0.8,5), distribution.pdf(5), ERROR );
		assertEquals( 10*0.8*Math.pow(0.2,9), distribution.pdf(9), ERROR );
		assertEquals(  1*Math.pow(0.2,10), distribution.pdf(10), ERROR );
		assertEquals( 0.0, distribution.pdf(11), ERROR );
	}

	@Test
	public void test2CDF() 
	{
		Distribution distribution = new BinomialDistribution(10,0.2);

		assertEquals( 0.0, distribution.cdf(0), ERROR );
		assertEquals( 1.0, distribution.cdf(10), ERROR );
	}

	@Test
	public void test2IDF() 
	{
		Distribution distribution = new BinomialDistribution(10,0.2);

		assertEquals(  0, distribution.idf(0.0), ERROR );
		assertEquals(  2, distribution.idf(0.5), ERROR );
		assertEquals( 10, distribution.idf(1.0), ERROR );
	}

	@Test
	public void test2Statistics() 
	{
		DiscreteDistribution distribution = new BinomialDistribution(10,0.2);

		assertEquals( 2, distribution.mean(), ERROR );
		assertEquals( 1.6, distribution.variance(), ERROR );
		assertEquals( 0.6/Math.sqrt(1.6), distribution.skewness(), ERROR );
		assertEquals( (1-6*0.2*0.8)/1.6, distribution.kurtosis(), ERROR );
	}	
	
	// binomial(10,0.5)

	@Test
	public void test5PDF() 
	{
		Distribution distribution = new BinomialDistribution(10,0.5);

		assertEquals( 0.0, distribution.pdf(-1), ERROR );
		assertEquals(   1*Math.pow(0.5,10), distribution.pdf(0), ERROR );
		assertEquals(  10*Math.pow(0.5,10), distribution.pdf(1), ERROR );
		assertEquals( 252*Math.pow(0.5,10), distribution.pdf(5), ERROR );
		assertEquals(  10*Math.pow(0.5,10), distribution.pdf(9), ERROR );
		assertEquals(   1*Math.pow(0.5,10), distribution.pdf(10), ERROR );
		assertEquals( 0.0, distribution.pdf(11), ERROR );
	}

	@Test
	public void test5CDF() 
	{
		Distribution distribution = new BinomialDistribution(10,0.5);

		assertEquals( 0.0, distribution.cdf(0), ERROR );
		assertEquals( 1.0, distribution.cdf(10), ERROR );
	}

	@Test
	public void test5IDF() 
	{
		Distribution distribution = new BinomialDistribution(10,0.5);

		assertEquals(  0, distribution.idf(0.0), ERROR );
		assertEquals(  5, distribution.idf(0.5), ERROR );
		assertEquals( 10, distribution.idf(1.0), ERROR );
	}

	@Test
	public void test5Statistics() 
	{
		DiscreteDistribution distribution = new BinomialDistribution(10,0.5);

		assertEquals( 5, distribution.mean(), ERROR );
		assertEquals( 2.5, distribution.variance(), ERROR );
		assertEquals( 0.0, distribution.skewness(), ERROR );
		assertEquals( (1-6*0.5*0.5)/2.5, distribution.kurtosis(), ERROR );
	}	

	// Tolerance intervals

	@Test
	public void testIntervals ()
	{
		Distribution distribution = new BinomialDistribution(4,0.5);

		assertEquals( 0.0000, distribution.cdf(0), ERROR);
		assertEquals( 0.0625, distribution.cdf(1), ERROR);
		assertEquals( 0.3125, distribution.cdf(2), ERROR);
		assertEquals( 0.6875, distribution.cdf(3), ERROR);
		assertEquals( 1.0000, distribution.cdf(4), ERROR);
	}

	// Quantile function: z_p

	@Test
	public void testQuantiles ()
	{
		Distribution distribution = new BinomialDistribution(4,0.5);

		assertEquals( 0, distribution.idf(0.00), ERROR);
		assertEquals( 0, distribution.idf(0.06), ERROR);
		assertEquals( 1, distribution.idf(0.07), ERROR);
		assertEquals( 1, distribution.idf(0.31), ERROR);
		assertEquals( 2, distribution.idf(0.32), ERROR);
		assertEquals( 2, distribution.idf(0.68), ERROR);
		assertEquals( 3, distribution.idf(0.69), ERROR);
		assertEquals( 3, distribution.idf(0.99), ERROR);
		assertEquals( 4, distribution.idf(1.00), ERROR);	
	}
}
