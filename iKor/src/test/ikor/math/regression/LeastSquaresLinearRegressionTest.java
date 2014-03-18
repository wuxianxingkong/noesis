package test.ikor.math.regression;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ikor.math.regression.LinearRegressionModel;
import ikor.math.regression.LeastSquaresLinearRegression;

public class LeastSquaresLinearRegressionTest extends LinearRegressionTest 
{
	private static final double NORMAL_EQUATION_EPSILON  = 1e-14;

	@Override
	public LinearRegressionModel createLinearRegressionModel (double[][] x, double[] y) 
	{
		LeastSquaresLinearRegression reg = new LeastSquaresLinearRegression (x,y);
		
		return (LinearRegressionModel) reg.call();
		
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
	
	@Test
	public void testWeightLinearRegression ()
	{
		LinearRegressionModel model = createLinearRegressionModel(weightFeatures(), weights);

		assertEquals ( 3, model.parameters());
		assertEquals (  128.8128, model.getParameter(0), 0.0001);
		assertEquals ( -143.1620, model.getParameter(1), 0.0001);
		assertEquals (   61.9603, model.getParameter(2), 0.0001);
		
		assertEquals ( 15, model.n() );
		
		// Error

		assertEquals ( 0.7595, model.sse(), 0.0001);
		assertEquals ( 692.61, model.ssr(), 0.01);
		assertEquals ( 693.37, model.sst(), 0.01);		
		assertEquals ( 0.9989, model.R2(), 0.0001);		
		assertEquals ( 0.9987, model.adjustedR2(), 0.0001);	
		
		assertEquals ( 0.2516, model.rmse(), 0.0001);
		
		assertEquals ( 16.3083, model.standardError(0), 0.0001);
		assertEquals ( 19.8332, model.standardError(1), 0.0001);
		assertEquals (  6.0084, model.standardError(2), 0.0001);
				
		// Statistics
		
		assertEquals ( 5471.2, model.FStatistic(), 0.1);
		assertEquals ( 0.0000, model.pValueF(), 0.0001);
		
		assertEquals ( 7.8986, model.tStatistic(0), 0.0001);
		assertEquals (-7.2183, model.tStatistic(1), 0.0001);
		assertEquals (10.3122, model.tStatistic(2), 0.0001);
		
		assertEquals ( 0.0000, model.pValue(0), 0.0001);
		assertEquals ( 0.0000, model.pValue(1), 0.0001);
		assertEquals ( 0.0000, model.pValue(2), 0.0001);
		
		assertEquals ( 2.1013, model.DurbinWatsonStatistic(), 0.0001 );
		
		assertEquals ( 1.0890, model.logLikelihood(), 0.0001 );
		assertEquals ( 0.2548, model.AIC(), 0.0001 );
		assertEquals ( 0.3964, model.BIC(), 0.0001 );
	}
	

	// Ambiguous dataset: y3 = 2 + 3*x

	double x3[][] = new double[][] { {0, 1, 1} };
	double y3[]   = new double[]     {2, 2, 8};

	@Test
	public void testSimpleLinearRegression ()
	{
		LinearRegressionModel model = createLinearRegressionModel(x3, y3);

		assertEquals ( 2, model.parameters());
		assertEquals ( 2.0, model.getParameter(0), 0.0001);
		assertEquals ( 3.0, model.getParameter(1), 0.0001);
		
		assertEquals ( 3, model.n() );
		
		// Error

		assertEquals ( 0*0+3*3+3*3, model.sse(), 0.0001);
		assertEquals ( 1*1+1*1+2*2, model.ssr(), 0.01);
		assertEquals ( 2*2+2*2+4*4, model.sst(), 0.01);		
		assertEquals ( 0.25, model.R2(), 0.0001);		
		assertEquals ( -0.5, model.adjustedR2(), 0.0001);	
		
		assertEquals ( 4.243, model.rmse(), 0.001);
		
		assertEquals ( 4.243, model.standardError(0), 0.001);
		assertEquals ( 5.196, model.standardError(1), 0.001);
				
		// Statistics
		
		assertEquals ( 0.3333, model.FStatistic(), 0.0001);
		assertEquals ( 0.6667, model.pValueF(), 0.0001);
		
		assertEquals ( 0.471, model.tStatistic(0), 0.001);
		assertEquals ( 0.577, model.tStatistic(1), 0.001);
		
		assertEquals ( 0.720, model.pValue(0), 0.001);
		assertEquals ( 0.667, model.pValue(1), 0.001);
	}
	
}
