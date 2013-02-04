package test.ikor.math.statistics;

import static org.junit.Assert.*;
import ikor.math.statistics.Distribution;
import ikor.math.statistics.RayleighDistribution;

import org.junit.Before;
import org.junit.Test;

public class RayleighDistributionTest 
{
	public static double ERROR = 1e-7;

	@Before
	public void setUp() throws Exception 
	{
	}

	// exponential(1)

	@Test
	public void testStandardPDF() 
	{
		Distribution standard = new RayleighDistribution(1);

		assertEquals( 0.0, standard.pdf( 0.0), ERROR );
		assertEquals( Math.exp(-0.5), standard.pdf( 1.0), ERROR );
		assertEquals( 0.0, standard.pdf(+1e6), ERROR );
	}

	@Test
	public void testStandardCDF() 
	{
		Distribution standard = new RayleighDistribution(1);

		assertEquals( 0.0, standard.cdf(-1e6), ERROR );
		assertEquals( 0.0, standard.cdf( 0.0), ERROR );
		assertEquals( 1.0, standard.cdf(+1e6), ERROR );
	}

	@Test
	public void testStandardIDF() 
	{
		Distribution standard = new RayleighDistribution(1);

		assertEquals( 0.0, standard.idf(0.0), ERROR );
		assertEquals( 1*Math.sqrt(Math.log(4)), standard.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, standard.idf(1.0), ERROR );
	}	

	// exponential(0.5)

	@Test
	public void test05PDF() 
	{
		Distribution distribution = new RayleighDistribution(0.5);

		assertEquals( 0.0, distribution.pdf(0.0), ERROR );
		assertEquals( Math.exp(-0.5)/0.5, distribution.pdf(0.5), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e6), ERROR );
	}

	@Test
	public void test05CDF() 
	{
		Distribution distribution = new RayleighDistribution(0.5);

		assertEquals( 0.0, distribution.cdf(-1e6), ERROR );
		assertEquals( 0.0, distribution.cdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e6), ERROR );
	}

	@Test
	public void test05IDF() 
	{
		Distribution distribution = new RayleighDistribution(0.5);

		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( 0.5*Math.sqrt(Math.log(4)), distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	// exponential(2)

	@Test
	public void test20PDF() 
	{
		Distribution distribution = new RayleighDistribution(2);

		assertEquals( 0.0, distribution.pdf(0.0), ERROR );
		assertEquals( Math.exp(-0.5)/2.0, distribution.pdf(2.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e6), ERROR );
	}

	@Test
	public void test20CDF() 
	{
		Distribution distribution = new RayleighDistribution(2.0);

		assertEquals( 0.0, distribution.cdf(-1e6), ERROR );
		assertEquals( 0.0, distribution.cdf( 0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e6), ERROR );
	}

	@Test
	public void test20IDF() 
	{
		Distribution distribution = new RayleighDistribution(2.0);

		assertEquals( 0.0, distribution.idf(0.0), ERROR );
		assertEquals( 2.0*Math.sqrt(Math.log(4)), distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}


	// Tolerance intervals

	@Test
	public void testIntervals ()
	{
		Distribution distribution = new RayleighDistribution(1.0);

		assertEquals( 1-Math.exp(-0.5), distribution.cdf(1), ERROR);
		assertEquals( 1-Math.exp(-2), distribution.cdf(2), ERROR);
		assertEquals( 1-Math.exp(-4.5), distribution.cdf(3), ERROR);
		assertEquals( 1-Math.exp(-8), distribution.cdf(4), ERROR);
		assertEquals( 1-Math.exp(-12.5), distribution.cdf(5), ERROR);
		assertEquals( 1-Math.exp(-18), distribution.cdf(6), ERROR);
	}

	// Quantile function: z_p

	@Test
	public void testQuantiles ()
	{
		Distribution distribution = new RayleighDistribution(1.0);

		assertEquals( Math.sqrt(-Math.log(0.50*0.50)), distribution.idf(0.50), ERROR);
		assertEquals( Math.sqrt(-Math.log(0.25*0.25)), distribution.idf(0.75), ERROR);
		assertEquals( Math.sqrt(-Math.log(0.10*0.10)), distribution.idf(0.90), ERROR);
		assertEquals( Math.sqrt(-Math.log(0.05*0.05)), distribution.idf(0.95), ERROR);
		
		assertEquals( 0.50, distribution.cdf(Math.sqrt(-Math.log(0.50*0.50))), ERROR);
		assertEquals( 0.75, distribution.cdf(Math.sqrt(-Math.log(0.25*0.25))), ERROR);
		assertEquals( 0.90, distribution.cdf(Math.sqrt(-Math.log(0.10*0.10))), ERROR);
		assertEquals( 0.99, distribution.cdf(Math.sqrt(-Math.log(0.01*0.01))), ERROR);	
	}

}
