package test.ikor.math.statistics.fit;

import static org.junit.Assert.assertEquals;
import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.ParetoDistribution;
import ikor.math.statistics.fit.ConfidenceInterval;
import ikor.math.statistics.fit.ParetoDistributionFit;

import org.junit.Test;

public class ParetoDistributionFitTest 
{
	public static double EPSILON = 1e-4;
	
	double[] sample = {1, 1, 1, 1, 2, 2, 2, 3, 3, 4};

	@Test
	public void testDataArray ()
	{
		ParetoDistributionFit fit = new ParetoDistributionFit(sample);
		
		ParetoDistribution distribution = fit.fit();
		
		assertEquals( 2.0, fit.mleScale(), EPSILON );
		assertEquals( 4.9891, fit.mleShape(), EPSILON );

		assertEquals( 2.5014, distribution.mean(), EPSILON );
		assertEquals( 0.4195, distribution.variance(), EPSILON );
	}
	
	@Test
	public void testDataVector ()
	{
		Vector data = MatrixFactory.createVector(sample.length);
		
		for (int i=0; i<data.size(); i++)
			data.set(i, sample[i]);
		
		ParetoDistributionFit fit = new ParetoDistributionFit(data);
		
		assertEquals( 2.0, fit.mleScale(), EPSILON );
		assertEquals( 4.9891, fit.mleShape(), EPSILON );
		
		assertEquals( 2.0, data.average(), EPSILON );
		assertEquals( 1.0, data.variance(), EPSILON );
	}
	
	// MATLAB TEST
	// -----------
	// Note: The Generalized Pareto distribution with shape parameter k (<>0), 
	// scale parameter sigma, and threshold parameter theta, is equivalent to the 
	// Pareto distribution with scale x_m=sigma/k and shape alpha=1/k 
	// when k > 0 and theta = sigma/k. 
	// 
	// >> k=2
	// >> sigma = 10
	// >> theta = sigma/k
	// >> data = gprnd(2,10,5,100,1);
    // >> data = round(data)
	// >> [parmhat, patmci] = gpfit(x)
	//     -> index (shape) parameter, k
	//     -> scale parameter, sigma
    // >> [alpha, xmin, L]=plfit(data)   
	//     % from http://tuvalu.santafe.edu/~aaronc/powerlaws/
	//     % (estimates alpha via direct maximization of likelihood function)
	//     % to fit the power-law distribution p(x) ~ x^-alpha for x >= xmin.
	//     -> 'alpha' is the maximum likelihood estimate of the scaling exponent, 
	//     -> 'xmin' is the estimate of the lower bound of the power-law behavior, 
	//     -> L is the log-likelihood of the data x>=xmin under the fitted power law.
	
	private double data[] = new double[] {
		7, 41, 25, 1700, 159, 11, 46, 6, 358, 5, 17, 10, 5, 60, 29, 23, 9, 7, 498, 158, 39, 1555, 
		18, 44, 162, 115, 6, 11, 23, 6, 462, 9, 9, 16, 147, 14, 56, 278, 111, 6, 979, 85, 1730, 
		26, 28338, 6, 129, 574, 53, 24, 484, 5, 45, 57, 1299, 56, 2327, 20, 9, 13, 619, 765, 8, 
		6, 18, 420, 7, 44, 58, 9, 46796, 2130, 11, 14, 18, 9, 10, 8, 60, 10, 16, 32, 1318, 8, 44, 
		14, 9, 455, 306, 17, 21, 6, 8, 9, 1898, 941, 638, 8, 6, 11 
	};
	
	@Test
	public void testMatlab ()
	{
		ParetoDistributionFit fit = new ParetoDistributionFit(data);

		ParetoDistribution distribution = fit.fit();
		
		// TODO
		// Clauset's plfit: alpha=1.5, xmin=6, L=-562.7879
		// MATLAB's gpfit: k=-1.1067, sigma=4.4268
		//    Warning! Maximum likelihood has converged to a boundary point of the parameter space.
		//    Confidence intervals and standard errors can not be computed reliably.
		
		assertEquals(   1.6, fit.mleShape(), 0.1 );
		assertEquals( 216.3, fit.mleScale(), 0.1 );
		
		assertEquals( 568.4, distribution.mean(), 0.1 );
		assertEquals( Double.POSITIVE_INFINITY, distribution.variance(), EPSILON );

		ConfidenceInterval ci = fit.shapeNormalApproximation(0.05);
		
		assertEquals( 1.3686, ci.min(), EPSILON);
		assertEquals( 1.8603, ci.max(), EPSILON);
		assertEquals( 0.05, ci.alpha(), EPSILON);
		assertEquals( 0.95, ci.confidenceLevel(), EPSILON);

	}

}