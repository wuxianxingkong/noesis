package test.ikor.math.statistics.test;

import static org.junit.Assert.*;
import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.test.WilcoxonTest;

import org.junit.Test;

public class WilcoxonTestTest 
{

	// Two-sided test for the median of a single population
	// i.e. test the hypothesis that the median mileage differs from 33.
	// (at the 5% significance level, the results indicate that the median mileage differs from 33)
	
	double mileage[] = new double[] { 34.5, 34.8, 33.8, 33.4, 33.7, 33.9 };
	
	// [p,h,stats] = signrank(mileage(:,2),33,'method','approximate')
	
	@Test
	public void testMATLABexample ()
	{
		Vector x = MatrixFactory.createVector(mileage);
		
		WilcoxonTest test = new WilcoxonTest(x,33.0);
		
		assertEquals ( 6, test.n() );
		assertEquals ( 6, test.nr() );
		assertEquals ( 21, test.signedrank(), 0.0001 );
		assertEquals ( 2.1490, test.zvalue(), 0.0001 ); // vs. -2.2014
		assertEquals ( 0.0316, test.pvalue(), 0.0001 ); // vs.  0.0277
	}
	
	// [p,h,stats] = signrank([1 2 2 4 4 5],0,'method','approximate')
	// [p,h,stats] = signrank([1 2 2 4 4 5],3,'method','approximate')
	
	@Test
	public void testMATLABtie ()
	{
		Vector x = MatrixFactory.createVector(new double[]{1, 2, 2, 4, 4, 5});
		
		WilcoxonTest test = new WilcoxonTest(x);
		
		assertEquals ( 6, test.n() );
		assertEquals ( 6, test.nr() );
		assertEquals ( 21, test.signedrank(), 0.0001 );
		assertEquals ( 2.1490, test.zvalue(), 0.0001 ); // vs. -2.2136
		assertEquals ( 0.0316, test.pvalue(), 0.0001 ); // vs. 0.0269
		
		WilcoxonTest test3 = new WilcoxonTest(x,3);
		
		assertEquals ( 6, test3.n() );
		assertEquals ( 6, test3.nr() );
		assertEquals ( 0, test3.signedrank(), 0.0001 );
		assertEquals ( -0.0524, test3.zvalue(), 0.0001 ); // vs. 0
		assertEquals ( 0.9582, test3.pvalue(), 0.0001 );  // vs. 1
		
	}

	// [p,h,stats] = signrank([1 1 1 4 4 5],3,'method','approximate')
	
	@Test
	public void testMATLABtie3 ()
	{
		Vector x = MatrixFactory.createVector(new double[]{1, 1, 1, 4, 4, 5});
		
		WilcoxonTest test = new WilcoxonTest(x,3);
		
		assertEquals ( 6, test.n() );
		assertEquals ( 6, test.nr() );
		assertEquals ( 6, test.signedrank(), 0.0001 );
		assertEquals ( 0.5766, test.zvalue(), 0.0001 ); // vs. -0.6489
		assertEquals ( 0.5642, test.pvalue(), 0.0001 ); // vs.  0.5164
	}

	
	// [p,h,stats] = signrank([3 3 3 3 3 3],3,'method','approximate')
	
	@Test
	public void testMATLABdegenerate ()
	{
		Vector x = MatrixFactory.createVector(new double[]{3, 3, 3, 3, 3, 3});
		
		WilcoxonTest test = new WilcoxonTest(x,3);
		
		assertEquals ( 6, test.n() );
		assertEquals ( 0, test.nr() );
		assertEquals ( 0, test.signedrank(), 0.0001 );
		assertEquals ( Double.POSITIVE_INFINITY, test.zvalue(), 0.0001 ); // vs. -0.6489
		assertEquals ( 1.0, test.pvalue(), 0.0001 ); // vs.  0.5164
	}

	
	// Wilcoxon paired difference test
	
	// Example from Wikipedia, http://en.wikipedia.org/wiki/Wilcoxon_signed-rank_test

	double wikipediaX[] = new double[]{ 125, 115, 130, 140, 140, 115, 140, 125, 140, 135 };
	double wikipediaY[] = new double[]{ 110, 122, 125, 120, 140, 124, 123, 137, 135, 145 };

	@Test
	public void testWikipedia ()
	{
		Vector x = MatrixFactory.createVector(wikipediaX);
		Vector y = MatrixFactory.createVector(wikipediaY);
		
		WilcoxonTest test = new WilcoxonTest(x,y);
		
		assertEquals ( 10, test.n() );
		assertEquals ( 9, test.nr() );
		assertEquals ( 9, test.signedrank(), 0.0001 ); 
		assertEquals ( 0.5035, test.zvalue(), 0.0001 );
		assertEquals ( 0.6146, test.pvalue(), 0.0001 );
	}
	
	// Example from Richard Lowry, http://vassarstats.net/textbook/ch12a.html

	double lowryX[] = new double[]{ 78, 24, 64, 45, 64, 52, 30, 50, 64, 50, 78, 22, 84, 40, 90, 72 };
	double lowryY[] = new double[]{ 78, 24, 62, 48, 68, 56, 25, 44, 56, 40, 68, 36, 68, 20, 58, 32 };
	
	@Test
	public void testLowry ()
	{
		Vector x = MatrixFactory.createVector(lowryX);
		Vector y = MatrixFactory.createVector(lowryY);
		
		WilcoxonTest test = new WilcoxonTest(x,y);
		
		assertEquals ( 16, test.n() );
		assertEquals ( 14, test.nr() );
		assertEquals ( 67, test.signedrank(), 0.0001 );
		assertEquals ( 0.0, test.mu(), 0.0001 );
		assertEquals ( 31.8591, test.sigma(), 0.0001 );
		assertEquals ( 2.0873, test.zvalue(), 0.0001 );
		assertEquals ( 0.0369, test.pvalue(), 0.0001 );	
		
		// MATLAB
		// * "most extreme" signed rank statistic = 19 => z-value = -2.1040 => p-value = 0.0342
		// Lowry's:
		// * correction for continuity: z-score = (W-0.5)/sigmaW = +2.09 => 0.02 < p-value < 0.05
	}
	
}
