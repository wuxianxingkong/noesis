package test.ikor.math;

import static org.junit.Assert.*;
import ikor.math.MatrixFactory;
import ikor.math.Vector;

import org.junit.Test;

public class VectorCovarianceTest 
{
	static final double ERROR = 0.01;
	
	// Linear correlation
	
	@Test
	public void testCorrelation ()
	{
		Vector x,y;
		
		x = MatrixFactory.createVector( new double[]{1,2,3,4} );
		
		// y = 2x
		
		y = MatrixFactory.createVector( new double[]{2,4,6,8} );
		
		assertEquals ( 1.0, x.correlation(y), ERROR );
		assertEquals ( 2.5, x.covariance(y), ERROR );
		
		// y = 2*(5-x)

		y = MatrixFactory.createVector( new double[]{8,6,4,2} );
		
		assertEquals ( -1.0, x.correlation(y), ERROR );
		assertEquals ( -2.5, x.covariance(y), ERROR );

		// y = 4

		y = MatrixFactory.createVector( new double[]{4,4,4,4} );
		
		assertEquals ( 0.0, x.correlation(y), ERROR );
		assertEquals ( 0.0, x.covariance(y), ERROR );
		
		// x = 4

		x = MatrixFactory.createVector( new double[]{4,4,4,4} );
		
		assertEquals ( 0.0, x.correlation(y), ERROR );
		assertEquals ( 0.0, x.covariance(y), ERROR );
	}
	
	
	// Anscombe's quartet
	// (often used to illustrate the importance of looking at a set of data graphically 
	// before starting to analyze according to a particular type of relationship, and 
	// the inadequacy of basic statistic properties for describing realistic datasets)
	//
	// ref. http://en.wikipedia.org/wiki/Anscombe's_quartet
	// Anscombe, Francis J. (1973) Graphs in statistical analysis. American Statistician, 27, 17–21.

	static final int DATASETS = 4;
	static final int POINTS = 11;
	
	static final double dx[][] = new double[][]
		{{10.0,  8.0, 13.0,  9.0, 11.0, 14.0,  6.0,  4.0, 12.0,  7.0,  5.0},
		 {10.0,  8.0, 13.0,  9.0, 11.0, 14.0,  6.0,  4.0, 12.0,  7.0,  5.0},
		 {10.0,  8.0, 13.0,  9.0, 11.0, 14.0,  6.0,  4.0, 12.0,  7.0,  5.0},
		 { 8.0,  8.0,  8.0,  8.0,  8.0,  8.0,  8.0, 19.0,  8.0,  8.0,  8.0}};
	  
	static final double dy[][] = new double[][]
	    {{ 8.04,  6.95,  7.58,  8.81,  8.33,  9.96,  7.24,  4.26, 10.84,  4.82,  5.68},
	     { 9.14,  8.14,  8.74,  8.77,  9.26,  8.10,  6.13,  3.10,  9.13,  7.26,  4.74},
	     { 7.46,  6.77, 12.74,  7.11,  7.81,  8.84,  6.08,  5.39,  8.15,  6.42,  5.73},
	     { 6.58,  5.76,  7.71,  8.84,  8.47,  7.04,  5.25, 12.50,  5.56,  7.91,  6.89}};
	  
	@Test
	public void testAnscombeQuartetVectors() 
	{
		Vector x,y;
		
		for (int i=0; i<DATASETS; i++) {
			x = MatrixFactory.createVector(dx[i]);
			assertEquals(  9.0, x.average(), ERROR);
			assertEquals( 10.0, x.variance(), ERROR);
			y = MatrixFactory.createVector(dy[i]);
			assertEquals(  7.5, y.average(), ERROR);
			assertEquals(  3.75, y.variance(), ERROR);
		}
	}

	@Test
	public void testAnscombeQuartetCorrelation() 
	{
		Vector x,y;
		
		for (int i=0; i<DATASETS; i++) {
			x = MatrixFactory.createVector(dx[i]);
			y = MatrixFactory.createVector(dy[i]);
			assertEquals( 0.816, x.correlation(y), ERROR);
			assertEquals( 0.816, y.correlation(x), ERROR);
		}
	}
	
	@Test
	public void testAnscombeQuartetCovariance() 
	{
		Vector x,y;
		
		for (int i=0; i<DATASETS; i++) {
			x = MatrixFactory.createVector(dx[i]);
			y = MatrixFactory.createVector(dy[i]);
			assertEquals( 5.0, x.covariance(y), ERROR);
			assertEquals( 5.0, y.covariance(x), ERROR);
		}
	}	

	@Test
	public void testAnscombeQuartetLinearRegression() 
	{
		Vector x,y;
		double slope;
		double intercept;
		
		for (int i=0; i<DATASETS; i++) {
			x = MatrixFactory.createVector(dx[i]);
			y = MatrixFactory.createVector(dy[i]);
			// y = 3.0 + 0.5x
			slope = x.covariance(y) / x.variance();
			intercept = y.average() - slope*x.average();
			assertEquals( 0.5, slope, ERROR);
			assertEquals( 3.0, intercept, ERROR);
		}
	}	
	
}
