package test.ikor.math.regression;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ikor.math.regression.LinearRegressionModel;
import ikor.math.regression.OLSLinearRegression;

public class OLSLinearRegressionTest extends LinearRegressionTest 
{
	private static final double NORMAL_EQUATION_EPSILON  = 1e-14;

	@Override
	public LinearRegressionModel createLinearRegressionModel (double[][] x, double[] y) 
	{
		OLSLinearRegression reg = new OLSLinearRegression (x,y);
		
		return reg.call();
		
	}

	@Override
	public double EPSILON() 
	{
		return NORMAL_EQUATION_EPSILON;
	}
	
	
	// Wikipedia example
	// -----------------
	// weight = a+b*height+c*height^2
	// ref. Wikipedia http://en.wikipedia.org/wiki/Ordinary_least_squares
	
	double heights[] = new double[] {  1.47,  1.50,  1.52,  1.55,  1.57,  1.60,  1.63,
			                           1.65,  1.68,  1.70,  1.73,  1.75,  1.78,  1.80, 1.83 };
	double weights[] = new double[] { 52.21, 53.12, 54.48, 55.84, 57.20, 58.57, 59.93, 
			                          61.29, 63.11, 64.47, 66.28, 68.10, 69.92, 72.19, 74.46 };
	
	double[][] weightFeatures ()
	{
		double x[][] = new double[2][weights.length];
		
		for (int i=0; i<weights.length; i++) {
			x[0][i] = heights[i];
			x[1][i] = heights[i]*heights[i];
		}
			
		return x;
	}

	// Wikipedia example
	
	@Test
	public void testWeightLinearRegression ()
	{
		LinearRegressionModel model = createLinearRegressionModel(weightFeatures(), weights);

		assertEquals ( 3, model.parameters());
		assertEquals (  128.8128, model.getParameter(0), 0.0001);
		assertEquals ( -143.1620, model.getParameter(1), 0.0001);
		assertEquals (   61.9603, model.getParameter(2), 0.0001);
		
		assertEquals ( 15, model.n() );

		assertEquals ( 0.7595, model.sse(), 0.0001);
		assertEquals ( 692.61, model.ssr(), 0.01);
		assertEquals ( 693.37, model.sst(), 0.01);		
		assertEquals ( 0.9989, model.r2(), 0.0001);		
		assertEquals ( 0.9987, model.adjustedR2(), 0.0001);	
		
		assertEquals ( 0.2516, model.stdError(), 0.0001);
	}
	

}
