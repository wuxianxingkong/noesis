package sandbox.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ChiSquaredDistributionTest 
{
	public static double ERROR = 1e-6;

	@Before
	public void setUp() throws Exception 
	{
	}

	// 1 degree of freedom 
	
	@Test
	public void test1PDF() 
	{
		ChiSquaredDistribution standard = new ChiSquaredDistribution(1);
		
		assertEquals( 0.0, standard.pdf(-1e10), ERROR );
		assertEquals( 0.0, standard.pdf( 0.0), ERROR );
		assertEquals( 0.0, standard.pdf(+1e10), ERROR );
	}
	
	@Test
	public void test1CDF() 
	{
		ChiSquaredDistribution standard = new ChiSquaredDistribution(1);
		
		assertEquals( 0.0, standard.cdf(-1e16), ERROR );
		assertEquals( 0.0, standard.cdf( 0.0), ERROR );
		assertEquals( 1.0, standard.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test1IDF() 
	{
		ChiSquaredDistribution standard = new ChiSquaredDistribution(1);
		
		assertEquals( 0.0, standard.idf(0.0), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, standard.idf(1.0), ERROR );
	}	
	
	// 2 degrees of freedom

	@Test
	public void test2PDF() 
	{
		ChiSquaredDistribution chi = new ChiSquaredDistribution(2);
		
		assertEquals( 0.0, chi.pdf(-1e16), ERROR );
		assertEquals( 0.0, chi.pdf(  0.0), ERROR );
		assertEquals( 0.0, chi.pdf(+1e16), ERROR );
	}
	
	@Test
	public void test2CDF() 
	{
		ChiSquaredDistribution chi = new ChiSquaredDistribution(2);
		
		assertEquals( 0.0, chi.cdf(-1e16), ERROR );
		assertEquals( 0.0, chi.cdf(  0.0), ERROR );
		assertEquals( 1.0, chi.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test2IDF() 
	{
		ChiSquaredDistribution chi = new ChiSquaredDistribution(2);
		
		assertEquals( 0.0, chi.idf(0.0), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, chi.idf(1.0), ERROR );
	}

	// 10 degrees of freedom

	@Test
	public void test10PDF() 
	{
		ChiSquaredDistribution chi = new ChiSquaredDistribution(10);
		
		assertEquals( 0.0, chi.pdf(-1e16), ERROR );
		assertEquals( 0.0, chi.pdf(  0.0), ERROR );
		assertEquals( 0.0, chi.pdf(+1e16), ERROR );
	}
	
	@Test
	public void test10CDF() 
	{
		ChiSquaredDistribution chi = new ChiSquaredDistribution(10);
		
		assertEquals( 0.0, chi.cdf(-1e16), ERROR );
		assertEquals( 0.0, chi.cdf(  0.0), ERROR );
		assertEquals( 1.0, chi.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test10IDF() 
	{
		ChiSquaredDistribution chi = new ChiSquaredDistribution(10);
		
		assertEquals( 0.0, chi.idf(0.0), ERROR );
		assertEquals( Double.POSITIVE_INFINITY, chi.idf(1.0), ERROR );
	}
	
	
	// Quantile function: z_p
	
	@Test
	public void testQuantiles ()
	{
		ChiSquaredDistribution chi = new ChiSquaredDistribution(1);

		assertEquals( 0.000000, chi.idf(0.00), ERROR);
		assertEquals( 0.003932, chi.idf(0.05), ERROR);
		assertEquals( 0.015790, chi.idf(0.10), ERROR);
		assertEquals( 2.705543, chi.idf(0.90), ERROR);
		assertEquals( 3.841459, chi.idf(0.95), ERROR);
		assertEquals( 5.023886, chi.idf(0.975), ERROR);
		assertEquals( 6.634897, chi.idf(0.99), ERROR);
		assertEquals( 7.879439, chi.idf(0.995), ERROR);
	}

	
	// p values (data from Wikipedia, http://en.wikipedia.org/wiki/Chi-squared_distribution#Table_of_.CF.872_value_vs_p-value)
	
	private static final double CHIERROR = 0.01;
	
	private static final double degrees[] = { 1, 2, 5, 10 }; // Degrees of freedom
	
	private static final double pvalues[] =    {   0.95,   0.90, 0.80, 0.70, 0.50,  0.30,  0.20,  0.10,  0.05,  0.01, 0.001 };
	private static final double values[][] = { { 0.0039, 0.0158, 0.06, 0.15, 0.46,  1.07,  1.64,  2.71,  3.84,  6.64, 10.83 }, // df=1
		                                       { 0.1026, 0.2107, 0.45, 0.71, 1.39,  2.41,  3.22,  4.60,  5.99,  9.21, 13.82 }, // df=2
		                                       {   1.14,   1.61, 2.34, 3.00, 4.35,  6.06,  7.29,  9.24, 11.07, 15.09, 20.52 }, // df=5
		                                       {   3.94,   4.86, 6.18, 7.27, 9.34, 11.78, 13.44, 15.99, 18.31, 23.21, 29.59 }  // df=10
	};
	
	@Test
	public void testPValues ()
	{
		ChiSquaredDistribution chi[] = new ChiSquaredDistribution[degrees.length];
		
		for (int j=0; j<chi.length; j++)
			chi[j] =  new ChiSquaredDistribution(degrees[j]);
		
		for (int i=0; i<values.length; i++) {
			for (int j=0; j<chi.length; j++) {
			    assertEquals ( "CDF error @ ("+pvalues[i]+","+values[i][j]+") with "+degrees[j]+" degrees of freedom",
			    		       pvalues[i], chi[j].pvalue(values[j][i]), CHIERROR );	
			}
		}
	}

	@Test
	public void testChiSquared ()
	{
		ChiSquaredDistribution chi[] = new ChiSquaredDistribution[degrees.length];
		
		for (int j=0; j<chi.length; j++)
			chi[j] =  new ChiSquaredDistribution(degrees[j]);
		
		for (int i=0; i<values.length; i++) {
			for (int j=0; j<chi.length; j++) {
			    assertEquals ( "IDF error @ ("+pvalues[i]+","+values[i][j]+") with "+degrees[j]+" degrees of freedom",
			    		      values[j][i], chi[j].idf(1-pvalues[i]), CHIERROR );	
			}
		}
	}
	

}

