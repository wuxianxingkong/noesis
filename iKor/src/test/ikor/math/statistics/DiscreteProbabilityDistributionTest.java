package test.ikor.math.statistics;

import static org.junit.Assert.assertEquals;

import ikor.math.statistics.DiscreteProbabilityDistribution;

import org.junit.Test;
import org.junit.Before;
/**
 * Discrete probability distribution test.
 * 
 * @author Victor Martinez & Fernando Berzal (berzal@acm.org)
 */
public class DiscreteProbabilityDistributionTest 
{
	public static double ERROR = 1e-9;

	// "Squared" dice example taken from http://www.cimt.plymouth.ac.uk/projects/mepres/alevel/stats_ch4.pdf
	private static double[] values1 = {1, 4, 9, 16, 25, 36};
	private static double[] probabilities1 = {1.0/6.0, 1.0/6.0, 1.0/6.0, 1.0/6.0, 1.0/6.0, 1.0/6.0};
	
	// Example taken from http://www.stat.ucla.edu/~dinov/courses_students.dir/04/Spring/Stat110A.dir/STAT110A_notes.dir/PPCh03.pdf
	private static double[] values2 = {-8, -3, -1, 0, 1, 4, 6};
	private static double[] probabilities2 = {0.13, 0.15, 0.17, 0.20, 0.15, 0.11, 0.09};
	
	
	private DiscreteProbabilityDistribution distribution1;
	private DiscreteProbabilityDistribution distribution2;
	
	@Before
	public void setup ()
	{
		distribution1 = new DiscreteProbabilityDistribution(values1, probabilities1);
		distribution2 = new DiscreteProbabilityDistribution(values2, probabilities2);
	}
	
	@Test
	public void testMean() 
	{
		assertEquals( 91.0/6.0, distribution1.mean(), ERROR);
		assertEquals( -0.53, distribution2.mean(), ERROR);
	}
	
	@Test
	public void testVariance() 
	{
		assertEquals( 5369.0/(6.0*6.0), distribution1.variance(), ERROR);
		assertEquals( 14.7091, distribution2.variance(), ERROR);
	}

	@Test
	public void testSkewness() 
	{
		assertEquals( 1193088.0/(6*Math.pow(5369,1.5)), distribution1.skewness(), ERROR );
		assertEquals( -20.613654/Math.pow(14.7091,1.5), distribution2.skewness(), ERROR );
	}
	
	@Test
	public void testKurtosis() 
	{
		assertEquals( 330484518.0/(6*5369*5369), distribution1.kurtosis(), ERROR);
		assertEquals( 621.17943157/(14.7091*14.7091), distribution2.kurtosis(), ERROR);
	}
	
	@Test
	public void testCDF() 
	{
		assertEquals( 0.0/6.0, distribution1.cdf(0.0), ERROR);
		assertEquals( 1.0/6.0, distribution1.cdf(1.0), ERROR);
		assertEquals( 1.0/6.0, distribution1.cdf(2.0), ERROR);
		assertEquals( 2.0/6.0, distribution1.cdf(4.0), ERROR);
		assertEquals( 2.0/6.0, distribution1.cdf(5.0), ERROR);
		assertEquals( 3.0/6.0, distribution1.cdf(9.0), ERROR);
		assertEquals( 4.0/6.0, distribution1.cdf(16.0), ERROR);
		assertEquals( 5.0/6.0, distribution1.cdf(25.0), ERROR);
		assertEquals( 6.0/6.0, distribution1.cdf(36.0), ERROR);
		assertEquals( 6.0/6.0, distribution1.cdf(49.0), ERROR);

		assertEquals( 0.13, distribution2.cdf(-4), ERROR);
		assertEquals( 0.65, distribution2.cdf(0.0), ERROR);
		assertEquals( 0.80, distribution2.cdf(2.0), ERROR);
		assertEquals( 0.91, distribution2.cdf(5.0), ERROR);
		assertEquals( 1.00, distribution2.cdf(7.0), ERROR);
	}
	
	@Test
	public void testIDF() 
	{
		assertEquals(  1, distribution1.idf(0.0/6.0), ERROR);
		assertEquals(  4, distribution1.idf(1.0/6.0), ERROR);
		assertEquals(  9, distribution1.idf(2.0/6.0), ERROR);
		assertEquals( 16, distribution1.idf(3.0/6.0), ERROR);
		assertEquals( 25, distribution1.idf(4.0/6.0), ERROR);
		assertEquals( 36, distribution1.idf(5.0/6.0), ERROR);
		assertEquals( 36, distribution1.idf(6.0/6.0), ERROR);

		assertEquals( -8, distribution2.idf(0.00), ERROR);
		assertEquals( -8, distribution2.idf(0.12), ERROR);
		assertEquals( -3, distribution2.idf(0.13), ERROR);
		assertEquals( -3, distribution2.idf(0.14), ERROR);
		assertEquals( -1, distribution2.idf(0.30), ERROR);
		assertEquals(  4, distribution2.idf(0.80), ERROR);
		assertEquals(  4, distribution2.idf(0.82), ERROR);
		assertEquals(  6, distribution2.idf(0.91), ERROR);
		assertEquals(  6, distribution2.idf(1.00), ERROR);
	}

	@Test
	public void testPDF() 
	{
		assertEquals( 1.0/6.0, distribution1.pdf(1), ERROR);
		assertEquals( 1.0/6.0, distribution1.pdf(4), ERROR);
		assertEquals( 1.0/6.0, distribution1.pdf(9), ERROR);
		assertEquals( 1.0/6.0, distribution1.pdf(16), ERROR);
		assertEquals( 1.0/6.0, distribution1.pdf(25), ERROR);
		assertEquals( 1.0/6.0, distribution1.pdf(36), ERROR);
		assertEquals( 0.0, distribution1.pdf(0), ERROR);
		assertEquals( 0.0, distribution1.pdf(2), ERROR);
		assertEquals( 0.0, distribution1.pdf(3), ERROR);
		assertEquals( 0.0, distribution1.pdf(5), ERROR);
		assertEquals( 0.0, distribution1.pdf(35), ERROR);
		assertEquals( 0.0, distribution1.pdf(37), ERROR);
		assertEquals( 0.0, distribution1.pdf(49), ERROR);
		
		assertEquals( 0.13, distribution2.pdf(-8), ERROR);
		assertEquals( 0.17, distribution2.pdf(-1), ERROR);
		assertEquals( 0.09, distribution2.pdf(6), ERROR);		
		assertEquals( 0.0, distribution2.pdf(-2), ERROR);
		assertEquals( 0.0, distribution2.pdf(7), ERROR);
	}	
	
	
	@Test
	public void testIncrementalDice()
	{
		DiscreteProbabilityDistribution distribution = new DiscreteProbabilityDistribution();
		
		assertEquals( 0.0, distribution.mean(), ERROR);
		
		distribution.add(1.0, 1.0);
		
		assertEquals( 1.0, distribution.mean(), ERROR);
		assertEquals( 0.0, distribution.variance(), ERROR);
		
		distribution.add(2.0, 1.0);
		
		assertEquals( 1.5, distribution.mean(), ERROR);
		assertEquals( 1.0/4.0, distribution.variance(), ERROR);

		distribution.add(3.0, 1.0);
		distribution.add(4.0, 1.0);
		distribution.add(5.0, 1.0);
		distribution.add(6.0, 1.0);

		assertEquals( 3.5, distribution.mean(), ERROR);
		assertEquals( 105.0/36.0, distribution.variance(), ERROR);		
	}
	
	@Test
	public void testIncrementalBiasedDice()
	{
		DiscreteProbabilityDistribution distribution = new DiscreteProbabilityDistribution();
		
		assertEquals( 0.0, distribution.mean(), ERROR);
		
		distribution.add(1.0, 3.0);
		
		assertEquals( 1.0, distribution.mean(), ERROR);
		assertEquals( 0.0, distribution.variance(), ERROR);
		
		distribution.add(2.0, 2.0);
		
		assertEquals( 1.4, distribution.mean(), ERROR);
		assertEquals( (3*0.4*0.4+2*0.6*0.6)/5.0, distribution.variance(), ERROR);

		distribution.add(3.0, 1.0);

		assertEquals( 5.0/3.0, distribution.mean(), ERROR);
		assertEquals( 5.0/9.0, distribution.variance(), ERROR);	
	}	
}
