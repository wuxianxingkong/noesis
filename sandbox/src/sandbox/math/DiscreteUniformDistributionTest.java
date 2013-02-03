package sandbox.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DiscreteUniformDistributionTest 
{
	public static double ERROR = 1e-7;

	@Before
	public void setUp() throws Exception 
	{
	}

	// DU(1,6)

	@Test
	public void testPDF() 
	{
		Distribution distribution = new DiscreteUniformDistribution(1,6);

		assertEquals( 0.0, distribution.pdf(0), ERROR );
		assertEquals( 1.0/6.0, distribution.pdf(1), ERROR );
		assertEquals( 1.0/6.0, distribution.pdf(2), ERROR );
		assertEquals( 1.0/6.0, distribution.pdf(3), ERROR );
		assertEquals( 1.0/6.0, distribution.pdf(4), ERROR );
		assertEquals( 1.0/6.0, distribution.pdf(5), ERROR );
		assertEquals( 1.0/6.0, distribution.pdf(6), ERROR );
		assertEquals( 0.0, distribution.pdf(7), ERROR );
	}

	@Test
	public void testCDF() 
	{
		Distribution distribution = new DiscreteUniformDistribution(1,6);

		assertEquals( 0.0, distribution.cdf(0), ERROR );
		assertEquals( 1.0/6.0, distribution.cdf(1), ERROR );
		assertEquals( 2.0/6.0, distribution.cdf(2), ERROR );
		assertEquals( 3.0/6.0, distribution.cdf(3), ERROR );
		assertEquals( 4.0/6.0, distribution.cdf(4), ERROR );
		assertEquals( 5.0/6.0, distribution.cdf(5), ERROR );
		assertEquals( 1.0, distribution.cdf(6), ERROR );
	}

	@Test
	public void testIDF() 
	{
		Distribution distribution = new DiscreteUniformDistribution(1,6);

		assertEquals( 0, distribution.idf(0.00), ERROR);
		assertEquals( 0, distribution.idf(0.16), ERROR);
		assertEquals( 1, distribution.idf(0.17), ERROR);
		assertEquals( 1, distribution.idf(0.33), ERROR);
		assertEquals( 2, distribution.idf(0.34), ERROR);
		assertEquals( 2, distribution.idf(0.49), ERROR);
		assertEquals( 3, distribution.idf(0.50), ERROR);
		assertEquals( 3, distribution.idf(0.66), ERROR);
		assertEquals( 4, distribution.idf(0.67), ERROR);	
		assertEquals( 4, distribution.idf(0.83), ERROR);	
		assertEquals( 5, distribution.idf(0.84), ERROR);	
		assertEquals( 5, distribution.idf(0.99), ERROR);	
		assertEquals( 6, distribution.idf(1.00), ERROR);	
	}
}