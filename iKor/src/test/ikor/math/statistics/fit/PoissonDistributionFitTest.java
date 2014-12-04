package test.ikor.math.statistics.fit;

import static org.junit.Assert.assertEquals;
import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.PoissonDistribution;
import ikor.math.statistics.fit.ConfidenceInterval;
import ikor.math.statistics.fit.PoissonDistributionFit;

import org.junit.Test;

public class PoissonDistributionFitTest 
{
	public static double EPSILON = 1e-4;
	
	@Test
	public void testDataArray ()
	{
		double[] data = {15, 20, 35, 45, 50};
		
		PoissonDistributionFit fit = new PoissonDistributionFit(data);
		
		PoissonDistribution distribution = fit.fit();
		
		assertEquals( 33.0, fit.mleLambda(), EPSILON );

		assertEquals( 33.0, distribution.mean(), EPSILON );
		assertEquals( 33.0, distribution.variance(), EPSILON );
	}
	
	@Test
	public void testDataVector ()
	{
		Vector data = MatrixFactory.createVector(5);
		data.set(0, 1);
		data.set(1, 2);
		data.set(2, 3);
		data.set(3, 4);
		data.set(4, 5);
		
		PoissonDistributionFit fit = new PoissonDistributionFit(data);
		
		PoissonDistribution distribution = fit.fit();
		
		assertEquals( 3.0, fit.mleLambda(), EPSILON );

		assertEquals( 3.0, distribution.mean(), EPSILON );
		assertEquals( 3.0, distribution.variance(), EPSILON );
	}
	
	// MATLAB TEST
	// Note: MATLAB resorts to a normal approximation for the confidence intervals when n>=100
	// >> data = poissrnd(5,100,1);
	// >> [l,lci] = poissfit(data)

	private double data[] = new double[] {
		 7,  5, 10,  6,  5,  4,  6,  7,  6,  5,  
		 6,  5,  8,  9,  4,  4,  6,  7, 10,  7,  
		 6,  7,  6,  7,  6,  6,  7,  5,  7,  5,  
		 4,  4,  7,  2,  7,  3,  6,  6,  5,  3,  
		 3,  3,  5,  4,  7,  7,  8,  4,  2,  4,  
		 4,  5,  3, 11,  9,  6,  6,  2,  6,  8,  
		 4,  7,  4,  6,  8,  4,  4,  5,  3,  4,  
		 4,  7,  7,  2,  2,  3,  1,  6,  4,  4,  
		 6,  6,  3,  1,  2,  3,  8,  7,  8,  7,  
		 4,  8,  5,  3,  5,  7,  7,  7,  7,  7			
	};
	
	@Test
	public void testMatlab ()
	{
		PoissonDistributionFit fit = new PoissonDistributionFit(data);

		PoissonDistribution distribution = fit.fit();
		
		assertEquals( 5.43, distribution.mean(), EPSILON );
		assertEquals( 5.43, distribution.variance(), EPSILON );
		
		ConfidenceInterval ci = fit.lambdaNormalApproximation(0.05);
		
		assertEquals( 4.9733, ci.min(), EPSILON);
		assertEquals( 5.8867, ci.max(), EPSILON);
		assertEquals( 0.05,   ci.alpha(), EPSILON);
		assertEquals( 0.95,   ci.confidenceLevel(), EPSILON);
	}
	
	@Test
	public void testMatlabExact ()
	{
		double data[] = new double[] { 3, 4, 2, 7, 5, 2, 7, 4, 6, 5 };
		PoissonDistributionFit fit = new PoissonDistributionFit(data);

		PoissonDistribution distribution = fit.fit();
		
		assertEquals( 4.5, distribution.mean(), EPSILON );
		assertEquals( 4.5, distribution.variance(), EPSILON );
		
		ConfidenceInterval ci = fit.lambda(0.05);
		
		assertEquals( 3.2823, ci.min(), EPSILON);
		assertEquals( 6.0214, ci.max(), EPSILON);
		assertEquals( 0.05,   ci.alpha(), EPSILON);
		assertEquals( 0.95,   ci.confidenceLevel(), EPSILON);
	}	
}