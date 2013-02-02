package sandbox.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LogisticDistributionTest 
{

	public static double ERROR = 1e-7;

	@Before
	public void setUp() throws Exception 
	{
	}

	// logistic(0,1)
	
	@Test
	public void testStandardPDF() 
	{
		Distribution standard = new LogisticDistribution(0,1);
		
		assertEquals( 0.0, standard.pdf(-1e6), ERROR );
		assertEquals( Math.PI/(8*Math.sqrt(3)), standard.pdf( 0.0), ERROR );
		assertEquals( 0.0, standard.pdf(+1e6), ERROR );
	}
	
	@Test
	public void testStandardCDF() 
	{
		Distribution standard = new LogisticDistribution(0,1);
		
		assertEquals( 0.0, standard.cdf(-1e6), ERROR );
		assertEquals( 0.5, standard.cdf( 0.0), ERROR );
		assertEquals( 1.0, standard.cdf(+1e6), ERROR );
	}
	
	@Test
	public void testStandardIDF() 
	{
		Distribution standard = new LogisticDistribution(0,1);
		
		assertEquals( Double.NEGATIVE_INFINITY, standard.idf(0.0), ERROR );
		assertEquals(  0.0, standard.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, standard.idf(1.0), ERROR );
	}	
	
	// logistic(1,1)

	@Test
	public void test11PDF() 
	{
		Distribution distribution = new LogisticDistribution(1,1);
		
		assertEquals( 0.0, distribution.pdf(-1e6), ERROR );
		assertEquals( Math.PI/(8*Math.sqrt(3)), distribution.pdf( 1.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e6), ERROR );
	}
	
	@Test
	public void test11CDF() 
	{
		Distribution distribution = new LogisticDistribution(1,1);
		
		assertEquals( 0.0, distribution.cdf(-1e6), ERROR );
		assertEquals( 0.5, distribution.cdf( 1.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e6), ERROR );
	}
	
	@Test
	public void test11IDF() 
	{
		Distribution distribution = new LogisticDistribution(1,1);
		
		assertEquals( Double.NEGATIVE_INFINITY, distribution.idf(0.0), ERROR );
		assertEquals(  1.0, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}

	// logistic(0,2)

	@Test
	public void test02PDF() 
	{
		Distribution distribution = new LogisticDistribution(0,2);
		
		assertEquals( 0.0, distribution.pdf(-1e6), ERROR );
		assertEquals( Math.PI/(16*Math.sqrt(3)), distribution.pdf(0.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e6), ERROR );
	}
	
	@Test
	public void test02CDF() 
	{
		Distribution distribution = new LogisticDistribution(0,2);
		
		assertEquals( 0.0, distribution.cdf(-1e6), ERROR );
		assertEquals( 0.5, distribution.cdf(  0.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e6), ERROR );
	}
	
	@Test
	public void test02IDF() 
	{
		Distribution distribution = new LogisticDistribution(0,2);
		
		assertEquals( Double.NEGATIVE_INFINITY, distribution.idf(0.0), ERROR );
		assertEquals(   0.0, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}
	
	// logistic(5,2)
	
	@Test
	public void test52PDF() 
	{
		Distribution distribution = new LogisticDistribution(5,2);
		
		assertEquals( 0.0, distribution.pdf(-1e6), ERROR );
		assertEquals( Math.PI/(16*Math.sqrt(3)), distribution.pdf(5.0), ERROR );
		assertEquals( 0.0, distribution.pdf(+1e6), ERROR );
	}
	
	
	@Test
	public void test52CDF() 
	{
		Distribution distribution = new LogisticDistribution(5,2);
		
		assertEquals( 0.0, distribution.cdf(-1e6), ERROR );
		assertEquals( 0.5, distribution.cdf(  5.0), ERROR );
		assertEquals( 1.0, distribution.cdf(+1e6), ERROR );
	}
	
	@Test
	public void test52IDF() 
	{
		Distribution distribution = new LogisticDistribution(5,2);
		
		assertEquals( Double.NEGATIVE_INFINITY, distribution.idf(0.0), ERROR );
		assertEquals(   5.0, distribution.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, distribution.idf(1.0), ERROR );
	}
	
	// Tolerance intervals
	
	@Test
	public void testToleranceIntervals ()
	{
		Distribution distribution = new LogisticDistribution(0,1);
		
		assertEquals( 0.424730646198, distribution.cdf(1)-distribution.cdf(-1), ERROR);
		assertEquals( 0.719640870293, distribution.cdf(2)-distribution.cdf(-2), ERROR);
		assertEquals( 0.876474109369, distribution.cdf(3)-distribution.cdf(-3), ERROR); // Three sigma
		assertEquals( 0.948216534125, distribution.cdf(4)-distribution.cdf(-4), ERROR);
		assertEquals( 0.978763339817, distribution.cdf(5)-distribution.cdf(-5), ERROR);
		assertEquals( 0.991370553998, distribution.cdf(6)-distribution.cdf(-6), ERROR); // Six sigma
	}
	
	// Quantile function: z_p
	
	@Test
	public void testQuantiles ()
	{
		Distribution distribution = new LogisticDistribution(0,1);

		assertEquals( 0.000000000000, distribution.idf(0.50), ERROR);
		assertEquals( 0.605696699608, distribution.idf(0.75), ERROR);
		assertEquals( 1.211393399216, distribution.idf(0.90), ERROR);
		assertEquals( 1.623354290020, distribution.idf(0.95), ERROR);
		assertEquals( 2.533422351305, distribution.idf(0.99), ERROR);	
	}

}
