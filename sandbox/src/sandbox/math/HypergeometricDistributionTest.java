package sandbox.math;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HypergeometricDistributionTest 
{
	public static double ERROR = 1e-10;

	@Before
	public void setUp() throws Exception 
	{
	}
	
	// HG(N=36,K=6,n=6) 
	// Probability of obtaining exactly k correct balls in a pick-n lottery from a reservoir of N balls 
	// (of which K=n are "good" and M=N-n are "bad"). For example, for K=6 and N=36, the probabilities 
	// of obtaining  correct balls are given by...
	
	@Test
	public void testHGLotteryPDF() 
	{
		Distribution distribution = new HypergeometricDistribution(36,6,6);

		assertEquals( 0.3048451785, distribution.pdf(0), ERROR );
		assertEquals( 0.4389770571, distribution.pdf(1), ERROR );
		assertEquals( 0.2110466621, distribution.pdf(2), ERROR );
		assertEquals( 0.0416882295, distribution.pdf(3), ERROR );
		assertEquals( 0.0033499470, distribution.pdf(4), ERROR );
		assertEquals( 0.0000924123, distribution.pdf(5), ERROR );
		assertEquals( 0.0000005134, distribution.pdf(6), ERROR );	
		assertEquals( 0.0000000000, distribution.pdf(7), ERROR );	
	}
	
	// HG(N=50,K=5,n=10)
	// There are 5 white and 45 black marbles in an urn. You draw 10 marbles without replacement.
	// What is the probability that exactly k of the 10 are white?
	
	@Test
	public void testHGUrnPDF() 
	{
		Distribution distribution = new HypergeometricDistribution(50,5,10);

		assertEquals( 0.3105627820, distribution.pdf(0), ERROR );
		assertEquals( 0.4313371972, distribution.pdf(1), ERROR );
		assertEquals( 0.2098397176, distribution.pdf(2), ERROR );
		assertEquals( 0.0441767826, distribution.pdf(3), ERROR );
		assertEquals( 0.0039645831, distribution.pdf(4), ERROR );
		assertEquals( 0.0001189375, distribution.pdf(5), ERROR );
		assertEquals( 0.0000000000, distribution.pdf(6), ERROR );	
	}
	
	
	// HG(10,1,1)

	@Test
	public void test1PDF() 
	{
		Distribution distribution = new HypergeometricDistribution(10,1,1);

		assertEquals( 0.0, distribution.pdf(-1), ERROR );
		assertEquals( 1.0-0.1, distribution.pdf(0), ERROR );
		assertEquals( 0.1, distribution.pdf(1), ERROR );
		assertEquals( 0.0, distribution.pdf(2), ERROR );
	}

	@Test
	public void test1CDF() 
	{
		Distribution distribution = new HypergeometricDistribution(10,1,1);

		assertEquals( 0.0, distribution.cdf(0), ERROR );
		assertEquals( 0.9, distribution.cdf(1), ERROR );
		assertEquals( 1.0, distribution.cdf(2), ERROR );
	}

	@Test
	public void test1IDF() 
	{
		Distribution distribution = new HypergeometricDistribution(10,1,1);

		assertEquals( 0, distribution.idf(0.0), ERROR );
		assertEquals( 0, distribution.idf(0.5), ERROR );
		assertEquals( 1, distribution.idf(0.9), ERROR );
		assertEquals( 2, distribution.idf(1.0), ERROR );
	}	

	// HG(10,2,2)

	@Test
	public void test2PDF() 
	{
		Distribution distribution = new HypergeometricDistribution(10,2,2);

		assertEquals( 0.0, distribution.pdf(-1), ERROR );
		assertEquals( (8./10.)*(7./9.), distribution.pdf(0), ERROR );
		assertEquals( (8./10.)*(2./9.) + (2./10.)*(8./9.), distribution.pdf(1), ERROR );
		assertEquals( (2./10.)*(1./9.), distribution.pdf(2), ERROR );
		assertEquals( 0.0, distribution.pdf(3), ERROR );
	}

	@Test
	public void test2CDF() 
	{
		Distribution distribution = new HypergeometricDistribution(10,2,2);

		assertEquals( 0.0, distribution.cdf(0), ERROR );
		assertEquals( (8./10.)*(7./9.), distribution.cdf(1), ERROR );
		assertEquals( (8./10.)*(7./9.) + (8./10.)*(2./9.) + (2./10.)*(8./9.), distribution.cdf(2), ERROR );
		assertEquals( 1.0, distribution.cdf(3), ERROR );
	}

	@Test
	public void test2IDF() 
	{
		Distribution distribution = new HypergeometricDistribution(10,2,2);

		assertEquals( 0, distribution.idf(0.0), ERROR );
		assertEquals( 1, distribution.idf((8./10.)*(7./9.)), ERROR );
		assertEquals( 2, distribution.idf((8./10.)*(7./9.) + (8./10.)*(2./9.) + (2./10.)*(8./9.)), ERROR );
		assertEquals( 3, distribution.idf(1.0), ERROR );
	}

}