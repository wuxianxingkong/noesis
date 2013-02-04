package sandbox.math;

import static org.junit.Assert.*;
import ikor.math.Functions;

import org.junit.Before;
import org.junit.Test;

public class HalfNormalDistributionTest 
{
	public static double ERROR = 1e-7;

	@Before
	public void setUp() throws Exception 
	{
	}

	// HN(0,1)
	
	@Test
	public void testStandardPDF() 
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(0,1);
		
		assertEquals( 0.0, normal.pdf(-8.0), ERROR );
		assertEquals( 2/Math.sqrt(2*Math.PI), normal.pdf( 0.0), ERROR );
		assertEquals( 0.0, normal.pdf(+8.0), ERROR );
	}
	
	@Test
	public void testStandardCDF() 
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(0,1);
		
		assertEquals( 0.0, normal.cdf( -8.0), ERROR );
		assertEquals( 0.0, normal.cdf(  0.0), ERROR );
		assertEquals( 1.0, normal.cdf(+16.0), ERROR );
	}
	
	@Test
	public void testStandardIDF() 
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(0,1);
		
		assertEquals( 0.0, normal.idf(0.0), ERROR );
		assertEquals( Math.sqrt(2)*Functions.erfinv(0.5), normal.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, normal.idf(1.0), ERROR );
	}	
	
	// HN(1,1)

	@Test
	public void test11PDF() 
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(1,1);
		
		assertEquals( 0.0, normal.pdf(-0.0), ERROR );
		assertEquals( 2/Math.sqrt(2*Math.PI), normal.pdf( 1.0), ERROR );
		assertEquals( 0.0, normal.pdf(+9.0), ERROR );
	}
	
	@Test
	public void test11CDF() 
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(1,1);
		
		assertEquals( 0.0, normal.cdf( -7.0), ERROR );
		assertEquals( 0.0, normal.cdf(  1.0), ERROR );
		assertEquals( 1.0, normal.cdf(+17.0), ERROR );
	}
	
	@Test
	public void test11IDF() 
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(1,1);
		
		assertEquals( 1, normal.idf(0.0), ERROR );
		assertEquals( 1 + Math.sqrt(2)*Functions.erfinv(0.5), normal.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, normal.idf(1.0), ERROR );
	}

	// HN(0,2)

	@Test
	public void test02PDF() 
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(0,2);
		
		assertEquals( 0.0, normal.pdf(-16.0), ERROR );
		assertEquals( 2/(2*Math.sqrt(2*Math.PI)), normal.pdf(0.0), ERROR );
		assertEquals( 0.0, normal.pdf(+16.0), ERROR );
	}
	
	@Test
	public void test02CDF() 
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(0,2);
		
		assertEquals( 0.0, normal.cdf(-16.0), ERROR );
		assertEquals( 0.0, normal.cdf(  0.0), ERROR );
		assertEquals( 1.0, normal.cdf(+16.0), ERROR );
	}
	
	@Test
	public void test02IDF() 
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(0,2);
		
		assertEquals( 0, normal.idf(0.0), ERROR );
		assertEquals( 2*Math.sqrt(2)*Functions.erfinv(0.5), normal.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, normal.idf(1.0), ERROR );
	}
	
	// HN(5,2)
	
	@Test
	public void test52PDF() 
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(5,2);
		
		assertEquals( 0.0, normal.pdf(-11.0), ERROR );
		assertEquals( 2/(2*Math.sqrt(2*Math.PI)), normal.pdf(5.0), ERROR );
		assertEquals( 0.0, normal.pdf(+21.0), ERROR );
	}
	
	
	@Test
	public void test52CDF() 
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(5,2);
		
		assertEquals( 0.0, normal.cdf(-11.0), ERROR );
		assertEquals( 0.0, normal.cdf(  5.0), ERROR );
		assertEquals( 1.0, normal.cdf(+21.0), ERROR );
	}
	
	@Test
	public void test52IDF() 
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(5,2);
		
		assertEquals( 5, normal.idf(0.0), ERROR );
		assertEquals( 5 + 2*Math.sqrt(2)*Functions.erfinv(0.5), normal.idf(0.5), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, normal.idf(1.0), ERROR );
	}
	
	// Tolerance intervals
	
	@Test
	public void testToleranceIntervals ()
	{
		HalfNormalDistribution normal = new HalfNormalDistribution(0,1);
		
		assertEquals( 0.682689492137, normal.cdf(1)-normal.cdf(-1), ERROR);
		assertEquals( 0.954499736104, normal.cdf(2)-normal.cdf(-2), ERROR);
		assertEquals( 0.997300203937, normal.cdf(3)-normal.cdf(-3), ERROR); // Three sigma
		assertEquals( 0.999936657516, normal.cdf(4)-normal.cdf(-4), ERROR);
		assertEquals( 0.999999426697, normal.cdf(5)-normal.cdf(-5), ERROR);
		assertEquals( 0.999999998027, normal.cdf(6)-normal.cdf(-6), ERROR); // Six sigma
	}
	
	// Quantile function: z_p
	// a.k.a probit function for N(0,1)
	
	@Test
	public void testQuantiles ()
	{
		HalfNormalDistribution halfnormal = new HalfNormalDistribution(0,1);
		NormalDistribution     normal = new NormalDistribution(0,1);

		assertEquals( normal.idf(0.95),   halfnormal.idf(0.90), ERROR);
		assertEquals( normal.idf(0.975),  halfnormal.idf(0.95), ERROR);
		assertEquals( normal.idf(0.995),  halfnormal.idf(0.99), ERROR);
		assertEquals( normal.idf(0.9975), halfnormal.idf(0.995), ERROR);
		assertEquals( normal.idf(0.9995), halfnormal.idf(0.999), ERROR);

		assertEquals( normal.idf(0.99995), halfnormal.idf(0.9999), ERROR);
		assertEquals( normal.idf(0.999995), halfnormal.idf(0.99999), ERROR);
		assertEquals( normal.idf(0.9999995), halfnormal.idf(0.999999), ERROR);
		assertEquals( normal.idf(0.99999995), halfnormal.idf(0.9999999), ERROR);
		assertEquals( normal.idf(0.999999995), halfnormal.idf(0.99999999), ERROR);
	}
}