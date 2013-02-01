package sandbox.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StudentTDistributionTest {

	public static double ERROR = 1e-7;

	@Before
	public void setUp() throws Exception 
	{
	}

	// Standard (0,1)-distribution with 1 degree of freedom == Cauchy distribution
	
	@Test
	public void testStandardPDF() 
	{
		StudentTDistribution standard = new StudentTDistribution(1,0,1);
		
		assertEquals( 0.0, standard.pdf(-1e10), ERROR );
		assertEquals( 1/Math.PI, standard.pdf( 0.0), ERROR );
		assertEquals( 0.0, standard.pdf(+1e10), ERROR );
	}
	
	@Test
	public void testStandardCDF() 
	{
		StudentTDistribution standard = new StudentTDistribution(1,0,1);
		
		assertEquals( 0.0, standard.cdf(-1e16), ERROR );
		assertEquals( 0.5, standard.cdf( 0.0), ERROR );
		assertEquals( 1.0, standard.cdf(+1e16), ERROR );
	}
	
	@Test
	public void testStandardIDF() 
	{
		StudentTDistribution standard = new StudentTDistribution(1,0,1);
		
		assertTrue( -1e16 > standard.idf(0.0) );
		assertEquals(  0.0, standard.idf(0.5), ERROR );
		assertTrue( +1e16 < standard.idf(1.0) );
	}	
	
	// C(1,1)

	@Test
	public void test11CauchyPDF() 
	{
		StudentTDistribution cauchy = new StudentTDistribution(1,1,1);
		
		assertEquals( 0.0, cauchy.pdf(-1e16), ERROR );
		assertEquals( 1/Math.PI, cauchy.pdf(1.0), ERROR );
		assertEquals( 0.0, cauchy.pdf(+1e16), ERROR );
	}
	
	@Test
	public void test11CauchyCDF() 
	{
		StudentTDistribution cauchy = new StudentTDistribution(1,1,1);
		
		assertEquals( 0.0, cauchy.cdf(-1e16), ERROR );
		assertEquals( 0.5, cauchy.cdf( 1.0), ERROR );
		assertEquals( 1.0, cauchy.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test11CauchyIDF() 
	{
		StudentTDistribution cauchy = new StudentTDistribution(1,1,1);
		
		assertTrue( -1e16 > cauchy.idf(0.0) );
		assertEquals(  1.0, cauchy.idf(0.5), ERROR );
		assertTrue( +1e16 < cauchy.idf(1.0) );
	}

	// C(0,2)

	@Test
	public void test02CauchyPDF() 
	{
		StudentTDistribution cauchy = new StudentTDistribution(1,0,2);
		
		assertEquals( 0.0, cauchy.pdf(-1e16), ERROR );
		assertEquals( 1/(2*Math.PI), cauchy.pdf(0.0), ERROR );
		assertEquals( 0.0, cauchy.pdf(+1e16), ERROR );
	}
	
	@Test
	public void test02CauchyCDF() 
	{
		StudentTDistribution cauchy = new StudentTDistribution(1,0,2);
		
		assertEquals( 0.0, cauchy.cdf(-1e16), ERROR );
		assertEquals( 0.5, cauchy.cdf(  0.0), ERROR );
		assertEquals( 1.0, cauchy.cdf(+1e16), ERROR );
	}
	
	@Test
	public void test02CauchyIDF() 
	{
		StudentTDistribution cauchy = new StudentTDistribution(1,0,2);
		
		assertTrue( -1e16 > cauchy.idf(0.0) );
		assertEquals(  0.0, cauchy.idf(0.5), ERROR );
		assertTrue( +1e16 < cauchy.idf(1.0) );
	}
	
	// C(5,2)
	
	@Test
	public void test52CauchyPDF() 
	{
		StudentTDistribution cauchy = new StudentTDistribution(1,5,2);
		
		assertEquals( 0.0, cauchy.pdf(-1e16), ERROR );
		assertEquals( 1/(2*Math.PI), cauchy.pdf(5.0), ERROR );
		assertEquals( 0.0, cauchy.pdf(+1e16), ERROR );
	}
	
	
	@Test
	public void test52CauchyCDF() 
	{
		StudentTDistribution cauchy = new StudentTDistribution(1,5,2);
				
		assertTrue( -1e16 > cauchy.idf(0.0) );
		assertEquals( 0.5, cauchy.cdf(  5.0), ERROR );
		assertTrue( +1e16 < cauchy.idf(1.0) );
	}
	
	@Test
	public void test52CauchyIDF() 
	{
		StudentTDistribution cauchy = new StudentTDistribution(1,5,2);
		
		assertTrue( -1e16 > cauchy.idf(0.0) );
		assertEquals(  5.0, cauchy.idf(0.5), ERROR );
		assertTrue( +1e16 < cauchy.idf(1.0) );
	}
	
	// Tolerance intervals
	
	@Test
	public void testToleranceIntervals ()
	{
		StudentTDistribution cauchy = new StudentTDistribution(1,0,1);
		
		assertEquals( 1.0/3.0, cauchy.cdf(Math.sqrt(3)/3) - cauchy.cdf(-Math.sqrt(3)/3), ERROR); 
		assertEquals( 0.5,     cauchy.cdf(1) - cauchy.cdf(-1), ERROR);
		assertEquals( 2.0/3.0, cauchy.cdf(Math.sqrt(3)) - cauchy.cdf(-Math.sqrt(3)), ERROR); 
		assertEquals( 1.0,     cauchy.cdf(1e10) - cauchy.cdf(-1e10), ERROR);
	}
	
	// Quantile function: z_p
	
	@Test
	public void testQuantiles ()
	{
		StudentTDistribution cauchy = new StudentTDistribution(1,0,1);

		assertEquals( Math.tan((0.8-0.5)*Math.PI), cauchy.idf(0.80), ERROR);
		assertEquals( Math.tan((0.9-0.5)*Math.PI), cauchy.idf(0.90), ERROR);

		assertEquals( Math.tan((0.95-0.5)*Math.PI), cauchy.idf(0.95), ERROR);
		assertEquals( Math.tan((0.98-0.5)*Math.PI), cauchy.idf(0.98), ERROR);
		assertEquals( Math.tan((0.99-0.5)*Math.PI), cauchy.idf(0.99), ERROR);
	}

	
	// t tables (data from Wikipedia, http://en.wikipedia.org/wiki/Student%27s_t_distribution#Table_of_selected_values)
	
	private static final double TERROR = 0.001;
	
	private static final double oneSided[] = { 0.75, 0.90, 0.95, 0.975, 0.99, 0.995, 0.999, 0.9995 };
	private static final double twoSided[] = { 0.50, 0.80, 0.90, 0.95,  0.98, 0.99,  0.998, 0.999  };

	private static final double degrees[] =     {     1,     5,    10,   100 }; // Degrees of freedom vs. one-tailed/two-tailed
	private static final double tvalues[][] = { { 1.000, 0.727, 0.700, 0.677 }, // 75% / 50%
		                                        { 3.078, 1.476, 1.372, 1.290 }, // 90% / 80%
		                                        { 6.314, 2.015, 1.812, 1.660 }, // 95% / 90%
		                                        { 12.71, 2.571, 2.228, 1.984 }, // 97.5% / 95%
		                                        { 31.82, 3.365, 2.764, 2.364 }, // 99% / 98%
		                                        { 63.66, 4.032, 3.169, 2.626 }, // 99.5% / 99%
		                                        { 318.3, 5.893, 4.144, 3.174 }, // 99.9% / 99.8%
		                                        { 636.6, 6.869, 4.587, 3.390 }  // 99.95% / 99.9%
	};
	
	@Test
	public void testOneSided ()
	{
		StudentTDistribution t[] = new StudentTDistribution[degrees.length];
		
		for (int j=0; j<t.length; j++)
			t[j] =  new StudentTDistribution(degrees[j],0,1);
		
		for (int i=0; i<tvalues.length; i++) {
			for (int j=0; j<t.length; j++) {
			    assertEquals ( "CDF error @ ("+oneSided[i]+","+tvalues[i][j]+") with "+degrees[j]+" degrees of freedom",
			    		       oneSided[i], t[j].cdf(tvalues[i][j]), oneSided[i]*TERROR );	
			}
		}
	}

	@Test
	public void testOneSidedInverse ()
	{
		StudentTDistribution t[] = new StudentTDistribution[degrees.length];
		
		for (int j=0; j<t.length; j++)
			t[j] =  new StudentTDistribution(degrees[j],0,1);
		
		for (int i=0; i<tvalues.length; i++) {
			for (int j=0; j<t.length; j++) {
			    assertEquals ( "IDF error @ ("+oneSided[i]+","+tvalues[i][j]+") with "+degrees[j]+" degrees of freedom",
			    		      tvalues[i][j], t[j].idf(oneSided[i]), tvalues[i][j]*TERROR );	
			}
		}
	}
	
	@Test
	public void testTwoSided ()
	{
		StudentTDistribution t[] = new StudentTwoTailedTDistribution[degrees.length];
		
		for (int j=0; j<t.length; j++)
			t[j] =  new StudentTwoTailedTDistribution(degrees[j]);
		
		for (int i=0; i<tvalues.length; i++) {
			for (int j=0; j<t.length; j++) {
			    assertEquals ( "CDF error @ ("+twoSided[i]+","+tvalues[i][j]+") with "+degrees[j]+" degrees of freedom",
			    		       twoSided[i], t[j].cdf(tvalues[i][j]), twoSided[i]*TERROR );	
			}
		}
	}

	@Test
	public void testTwoSidedInverse ()
	{
		StudentTDistribution t[] = new StudentTwoTailedTDistribution[degrees.length];
		
		for (int j=0; j<t.length; j++)
			t[j] =  new StudentTwoTailedTDistribution(degrees[j]);
		
		for (int i=0; i<tvalues.length; i++) {
			for (int j=0; j<t.length; j++) {
			    assertEquals ( "IDF error @ ("+twoSided[i]+","+tvalues[i][j]+") with "+degrees[j]+" degrees of freedom",
			    		       tvalues[i][j], t[j].idf(twoSided[i]), tvalues[i][j]*TERROR );	
			}
		}
	}

}
