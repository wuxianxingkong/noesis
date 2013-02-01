package sandbox.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NormalDistributionTest 
{
	public static double ERROR = 1e-7;

	@Before
	public void setUp() throws Exception 
	{
	}

	// N(0,1)
	
	@Test
	public void testStandardNormalPDF() 
	{
		NormalDistribution normal = new NormalDistribution(0,1);
		
		assertEquals( 0.0, normal.pdf(-8.0), ERROR );
		assertEquals( 1/Math.sqrt(2*Math.PI), normal.pdf( 0.0), ERROR );
		assertEquals( 0.0, normal.pdf(+8.0), ERROR );
	}
	
	@Test
	public void testStandardNormalCDF() 
	{
		NormalDistribution normal = new NormalDistribution(0,1);
		
		assertEquals( 0.0, normal.cdf(-8.0), ERROR );
		assertEquals( 0.5, normal.cdf( 0.0), ERROR );
		assertEquals( 1.0, normal.cdf(+8.0), ERROR );
	}
	
	@Test
	public void testStandardNormalIDF() 
	{
		NormalDistribution normal = new NormalDistribution(0,1);
		
		assertEquals( -8.0, normal.idf(0.0), ERROR );
		assertEquals(  0.0, normal.idf(0.5), ERROR );
		assertEquals( +8.0, normal.idf(1.0), ERROR );
	}	
	
	// N(1,1)

	@Test
	public void test11NormalPDF() 
	{
		NormalDistribution normal = new NormalDistribution(1,1);
		
		assertEquals( 0.0, normal.pdf(-7.0), ERROR );
		assertEquals( 1/Math.sqrt(2*Math.PI), normal.pdf( 1.0), ERROR );
		assertEquals( 0.0, normal.pdf(+9.0), ERROR );
	}
	
	@Test
	public void test11NormalCDF() 
	{
		NormalDistribution normal = new NormalDistribution(1,1);
		
		assertEquals( 0.0, normal.cdf(-7.0), ERROR );
		assertEquals( 0.5, normal.cdf( 1.0), ERROR );
		assertEquals( 1.0, normal.cdf(+9.0), ERROR );
	}
	
	@Test
	public void test11NormalIDF() 
	{
		NormalDistribution normal = new NormalDistribution(1,1);
		
		assertEquals( -7.0, normal.idf(0.0), ERROR );
		assertEquals(  1.0, normal.idf(0.5), ERROR );
		assertEquals( +9.0, normal.idf(1.0), ERROR );
	}

	// N(0,2)

	@Test
	public void test02NormalPDF() 
	{
		NormalDistribution normal = new NormalDistribution(0,2);
		
		assertEquals( 0.0, normal.pdf(-16.0), ERROR );
		assertEquals( 1/(2*Math.sqrt(2*Math.PI)), normal.pdf(0.0), ERROR );
		assertEquals( 0.0, normal.pdf(+16.0), ERROR );
	}
	
	@Test
	public void test02NormalCDF() 
	{
		NormalDistribution normal = new NormalDistribution(0,2);
		
		assertEquals( 0.0, normal.cdf(-16.0), ERROR );
		assertEquals( 0.5, normal.cdf(  0.0), ERROR );
		assertEquals( 1.0, normal.cdf(+16.0), ERROR );
	}
	
	@Test
	public void test02NormalIDF() 
	{
		NormalDistribution normal = new NormalDistribution(0,2);
		
		assertEquals( -16.0, normal.idf(0.0), ERROR );
		assertEquals(   0.0, normal.idf(0.5), ERROR );
		assertEquals( +16.0, normal.idf(1.0), ERROR );
	}
	
	// N(5,2)
	
	@Test
	public void test52NormalPDF() 
	{
		NormalDistribution normal = new NormalDistribution(5,2);
		
		assertEquals( 0.0, normal.pdf(-11.0), ERROR );
		assertEquals( 1/(2*Math.sqrt(2*Math.PI)), normal.pdf(5.0), ERROR );
		assertEquals( 0.0, normal.pdf(+21.0), ERROR );
	}
	
	
	@Test
	public void test52NormalCDF() 
	{
		NormalDistribution normal = new NormalDistribution(5,2);
		
		assertEquals( 0.0, normal.cdf(-11.0), ERROR );
		assertEquals( 0.5, normal.cdf(  5.0), ERROR );
		assertEquals( 1.0, normal.cdf(+21.0), ERROR );
	}
	
	@Test
	public void test52NormalIDF() 
	{
		NormalDistribution normal = new NormalDistribution(5,2);
		
		assertEquals( -11.0, normal.idf(0.0), ERROR );
		assertEquals(   5.0, normal.idf(0.5), ERROR );
		assertEquals( +21.0, normal.idf(1.0), ERROR );
	}
	
	// Tolerance intervals
	
	@Test
	public void testToleranceIntervals ()
	{
		NormalDistribution normal = new NormalDistribution(0,1);
		
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
		NormalDistribution normal = new NormalDistribution(0,1);

		assertEquals( 1.281551565545, normal.idf(0.90), ERROR);
		assertEquals( 1.644853626951, normal.idf(0.95), ERROR);
		assertEquals( 1.959963984540, normal.idf(0.975), ERROR);
		assertEquals( 2.326347874041, normal.idf(0.99), ERROR);
		assertEquals( 2.575829303549, normal.idf(0.995), ERROR);
		
		assertEquals( 2.807033768344, normal.idf(0.9975), ERROR);
		assertEquals( 3.090232306168, normal.idf(0.999), ERROR);
		
		assertEquals( 3.290526731492, normal.idf(0.9995), ERROR);
		assertEquals( 3.890591886413, normal.idf(0.99995), ERROR);
		assertEquals( 4.417173413469, normal.idf(0.999995), ERROR);
		assertEquals( 4.891638475699, normal.idf(0.9999995), ERROR);
		assertEquals( 5.326723886384, normal.idf(0.99999995), ERROR);
		assertEquals( 5.730728868236, normal.idf(0.999999995), ERROR);
		assertEquals( 6.109410204869, normal.idf(0.9999999995), ERROR);
	}
}
