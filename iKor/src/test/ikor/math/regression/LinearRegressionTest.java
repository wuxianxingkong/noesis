package test.ikor.math.regression;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import ikor.math.regression.BaselineRegression;
import ikor.math.regression.LinearRegressionModel;

public abstract class LinearRegressionTest 
{
	// DATA
	
	// y1 = -1 + x1
	
	double x1[][] = new double[][] { {1, 2, 3, 4, 5, 6, 7, 8, 9} };
	double y1[]   = new double[]     {0, 1, 2, 3, 4, 5, 6, 7, 8};

	// y2 = 2 + 0.5*x1 + 0.5*x2;
	
	double x2[][] = new double[][] { {0, 1, 1, 3, 5, 7, 9, 1, 3, 5, 7, 9 },
			                         {0, 1, 9, 7, 5, 3, 1, 1, 3, 5, 7, 9 } };
	double y2[]   = new double[]     {2, 3, 7, 7, 7, 7, 7, 3, 5, 7, 9, 11};
	
	
	
	// GENERIC INTERFACE FOR ESTIMATION METHODS 
	
	public abstract LinearRegressionModel createLinearRegressionModel (double x[][], double y[]);
	
	public abstract double EPSILON ();

	
	// TEST CASES
	
	@Before
	public void setUp() throws Exception 
	{	

	}
	
	
	@Test
	public void testUnivariateBaselineRegression ()
	{
		LinearRegressionModel model = (new BaselineRegression(x1, y1)).call();
		
		assertEquals ( 2, model.parameters());
		assertEquals ( 4, model.getParameter(0), EPSILON());
		assertEquals ( 0, model.getParameter(1), EPSILON());
		
		assertEquals ( 2*(4*4+3*3+2*2+1*1), model.getSSE(), EPSILON());
		
		assertEquals ( 4.0, model.predict(new double[]{0}), EPSILON());
		assertEquals ( 4.0, model.predict(new double[]{5}), EPSILON());
		assertEquals ( 4.0, model.predict(new double[]{10}), EPSILON());
	}	
	
	@Test
	public void testUnivariateLinearRegression ()
	{
		LinearRegressionModel model = createLinearRegressionModel(x1, y1);
		
		assertEquals ( 2, model.parameters());
		assertEquals ( -1, model.getParameter(0), EPSILON());
		assertEquals ( +1, model.getParameter(1), EPSILON());
		
		assertEquals ( 0.0, model.getSSE(), EPSILON());
		
		assertEquals ( -1, model.predict(new double[]{0}), EPSILON());
		assertEquals (  4, model.predict(new double[]{5}), EPSILON());
		assertEquals (  9, model.predict(new double[]{10}), EPSILON());
	}


	
	@Test
	public void testMultivariateBaselineRegression ()
	{
		LinearRegressionModel model = (new BaselineRegression(x2, y2)).call();
		
		assertEquals ( 3, model.parameters());
		assertEquals ( 6.25, model.getParameter(0), EPSILON());
		assertEquals ( 0, model.getParameter(1), EPSILON());
		assertEquals ( 0, model.getParameter(2), EPSILON());
		
		assertEquals ( 74.25, model.getSSE(), EPSILON());
		
		assertEquals ( 6.25, model.predict(new double[]{0,0}), EPSILON());
		assertEquals ( 6.25, model.predict(new double[]{5,0}), EPSILON());
		assertEquals ( 6.25, model.predict(new double[]{0,5}), EPSILON());
		assertEquals ( 6.25, model.predict(new double[]{5,5}), EPSILON());
		assertEquals ( 6.25, model.predict(new double[]{10,10}), EPSILON());
	}	
	
	@Test
	public void testMultivariateGradientDescent ()
	{
		LinearRegressionModel model = createLinearRegressionModel(x2, y2);
		
		assertEquals ( 3, model.parameters());
		assertEquals ( 2.0, model.getParameter(0), EPSILON());
		assertEquals ( 0.5, model.getParameter(1), EPSILON());
		assertEquals ( 0.5, model.getParameter(2), EPSILON());
		
		assertEquals ( 0.0, model.getSSE(), EPSILON());
				
		assertEquals ( 2, model.predict(new double[]{0,0}), EPSILON());
		assertEquals ( 7, model.predict(new double[]{10,0}), EPSILON());
		assertEquals ( 7, model.predict(new double[]{0,10}), EPSILON());
		assertEquals ( 12, model.predict(new double[]{10,10}), EPSILON());
		assertEquals ( 17, model.predict(new double[]{10,20}), EPSILON());
		assertEquals ( 17, model.predict(new double[]{20,10}), EPSILON());
	}

	
}
