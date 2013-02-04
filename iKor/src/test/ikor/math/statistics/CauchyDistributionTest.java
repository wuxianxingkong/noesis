package test.ikor.math.statistics;

import static org.junit.Assert.*;
import ikor.math.statistics.CauchyDistribution;

import org.junit.Before;
import org.junit.Test;

public class CauchyDistributionTest 
{
	public static double ERROR = 1e-7;

	@Before
	public void setUp() throws Exception 
	{
	}

	// Standard (0,1)-distribution
	
	@Test
	public void testStandardCauchyPDF() 
	{
		CauchyDistribution standard = new CauchyDistribution(0,1);
		
		assertEquals( 0.0, standard.pdf(-1e10), ERROR );
		assertEquals( 1/Math.PI, standard.pdf( 0.0), ERROR );
		assertEquals( 0.0, standard.pdf(+1e10), ERROR );
	}
	
	@Test
	public void testStandardCauchyCDF() 
	{
		CauchyDistribution standard = new CauchyDistribution(0,1);
		
		assertEquals( 0.0, standard.cdf(-1e16), ERROR );
		assertEquals( 0.5, standard.cdf( 0.0), ERROR );
		assertEquals( 1.0, standard.cdf(+1e16), ERROR );
	}
	
	@Test
	public void testStandardCauchyIDF() 
	{
		CauchyDistribution standard = new CauchyDistribution(0,1);
		
		assertEquals( Double.NEGATIVE_INFINITY, standard.idf(0.0), ERROR );
		assertEquals(  0.0, standard.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, standard.idf(1.0), ERROR );
	}	
	
	// C(1,1)

	@Test
	public void test11CauchyPDF() 
	{
		CauchyDistribution cauchy = new CauchyDistribution(1,1);
		
		assertEquals( 0.0, cauchy.pdf(-1e16), ERROR );
		assertEquals( 1/Math.PI, cauchy.pdf(1.0), ERROR );
		assertEquals( 0.0, cauchy.pdf(+1e16), ERROR );
	}
	
	@Test
	public void test11CauchyCDF() 
	{
		CauchyDistribution cauchy = new CauchyDistribution(1,1);
		
		assertEquals( 0.0, cauchy.cdf(-1e16), ERROR );
		assertEquals( 0.5, cauchy.cdf( 1.0), ERROR );
		assertEquals( 1.0, cauchy.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test11CauchyIDF() 
	{
		CauchyDistribution cauchy = new CauchyDistribution(1,1);
		
		assertEquals( Double.NEGATIVE_INFINITY, cauchy.idf(0.0), ERROR );
		assertEquals( 1.0, cauchy.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, cauchy.idf(1.0), ERROR );
	}

	// C(0,2)

	@Test
	public void test02CauchyPDF() 
	{
		CauchyDistribution cauchy = new CauchyDistribution(0,2);
		
		assertEquals( 0.0, cauchy.pdf(-1e16), ERROR );
		assertEquals( 1/(2*Math.PI), cauchy.pdf(0.0), ERROR );
		assertEquals( 0.0, cauchy.pdf(+1e16), ERROR );
	}
	
	@Test
	public void test02CauchyCDF() 
	{
		CauchyDistribution cauchy = new CauchyDistribution(0,2);
		
		assertEquals( 0.0, cauchy.cdf(-1e16), ERROR );
		assertEquals( 0.5, cauchy.cdf(  0.0), ERROR );
		assertEquals( 1.0, cauchy.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test02CauchyIDF() 
	{
		CauchyDistribution cauchy = new CauchyDistribution(0,2);
		
		assertEquals( Double.NEGATIVE_INFINITY, cauchy.idf(0.0), ERROR );
		assertEquals( 0.0, cauchy.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, cauchy.idf(1.0), ERROR );
	}
	
	// C(5,2)
	
	@Test
	public void test52CauchyPDF() 
	{
		CauchyDistribution cauchy = new CauchyDistribution(5,2);
		
		assertEquals( 0.0, cauchy.pdf(-1e16), ERROR );
		assertEquals( 1/(2*Math.PI), cauchy.pdf(5.0), ERROR );
		assertEquals( 0.0, cauchy.pdf(+1e16), ERROR );
	}
	
	
	@Test
	public void test52CauchyCDF() 
	{
		CauchyDistribution cauchy = new CauchyDistribution(5,2);
				
		assertEquals( 0.0, cauchy.cdf(-1e16), ERROR );
		assertEquals( 0.5, cauchy.cdf(  5.0), ERROR );
		assertEquals( 1.0, cauchy.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test52CauchyIDF() 
	{
		CauchyDistribution cauchy = new CauchyDistribution(5,2);
		
		assertEquals( Double.NEGATIVE_INFINITY, cauchy.idf(0.0), ERROR );
		assertEquals( 5.0, cauchy.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, cauchy.idf(1.0), ERROR );
	}
	
	// Tolerance intervals
	
	@Test
	public void testToleranceIntervals ()
	{
		CauchyDistribution cauchy = new CauchyDistribution(0,1);
		
		assertEquals( 1.0/3.0, cauchy.cdf(Math.sqrt(3)/3) - cauchy.cdf(-Math.sqrt(3)/3), ERROR); 
		assertEquals( 0.5,     cauchy.cdf(1) - cauchy.cdf(-1), ERROR);
		assertEquals( 2.0/3.0, cauchy.cdf(Math.sqrt(3)) - cauchy.cdf(-Math.sqrt(3)), ERROR); 
		assertEquals( 1.0,     cauchy.cdf(1e10) - cauchy.cdf(-1e10), ERROR);
	}
	
	// Quantile function: z_p
	
	@Test
	public void testQuantiles ()
	{
		CauchyDistribution cauchy = new CauchyDistribution(0,1);

		assertEquals( Math.tan((0.8-0.5)*Math.PI), cauchy.idf(0.80), ERROR);
		assertEquals( Math.tan((0.9-0.5)*Math.PI), cauchy.idf(0.90), ERROR);

		assertEquals( Math.tan((0.95-0.5)*Math.PI), cauchy.idf(0.95), ERROR);
		assertEquals( Math.tan((0.98-0.5)*Math.PI), cauchy.idf(0.98), ERROR);
		assertEquals( Math.tan((0.99-0.5)*Math.PI), cauchy.idf(0.99), ERROR);
	}


}
