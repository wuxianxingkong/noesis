package test.ikor.math.regression;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ikor.math.regression.LeastSquaresRegularizedLinearRegression;
import ikor.math.regression.LinearRegressionModel;

public class RegularizedLinearRegressionTest 
{
	// DATA 
	// From Andrew Ng's Machine Learning course 
	// http://openclassroom.stanford.edu/MainFolder/CoursePage.php?course=MachineLearning
	
	double x[] = new double[] {-0.99768, -0.69574, -0.40373, -0.10236, 0.22024, 0.47742, 0.822291};
	double y[] = new double[] { 2.0885,   1.1646,   0.3287,   0.46013, 0.44808, 0.10013,-0.329520};
	
	double[][] createPowers ()
	{
		double[][] data = new double[5][x.length];
		
		for (int i=0; i<5; i++)
			for (int j=0; j<x.length; j++)
				data[i][j] = Math.pow(x[j], i+1);
		
		return data;
	}
	
	// REGRESSION MODEL 
	
	public LinearRegressionModel createRegularizedRegressionModel (double lambda, double x[][], double y[])
	{
		LeastSquaresRegularizedLinearRegression regression = new LeastSquaresRegularizedLinearRegression (x,y);
		
		regression.setRegularizationFactor(lambda);
		
		return (LinearRegressionModel) regression.getResult();
	}

	public double EPSILON ()
	{
		return 0.0001;
	}
	
	// TEST CASES
	
	@Before
	public void setUp() throws Exception 
	{	

	}
	
	
	@Test
	public void testNonregularizedRegression ()
	{
		double x[][] = createPowers();
		LinearRegressionModel model = createRegularizedRegressionModel(0.0, x, y);
		
		assertEquals ( 6, model.parameters());
		assertEquals ( 0.4725, model.getParameter(0), EPSILON());
		assertEquals ( 0.6814, model.getParameter(1), EPSILON());
		assertEquals (-1.3801, model.getParameter(2), EPSILON());
		assertEquals (-5.9777, model.getParameter(3), EPSILON());
		assertEquals ( 2.4418, model.getParameter(4), EPSILON());
		assertEquals ( 4.7371, model.getParameter(5), EPSILON());
	}
	
	@Test
	public void testRegularizedRegression1 ()
	{
		double x[][] = createPowers();
		LinearRegressionModel model = createRegularizedRegressionModel(1.0, x, y);
		
		assertEquals ( 6, model.parameters());
		assertEquals ( 0.3976, model.getParameter(0), EPSILON());
		assertEquals (-0.4207, model.getParameter(1), EPSILON());
		assertEquals ( 0.1296, model.getParameter(2), EPSILON());
		assertEquals (-0.3975, model.getParameter(3), EPSILON());
		assertEquals ( 0.1753, model.getParameter(4), EPSILON());
		assertEquals (-0.3394, model.getParameter(5), EPSILON());
	}

	@Test
	public void testRegularizedRegression10 ()
	{
		double x[][] = createPowers();
		LinearRegressionModel model = createRegularizedRegressionModel(10.0, x, y);
		
		assertEquals ( 6, model.parameters());
		assertEquals ( 0.5205, model.getParameter(0), EPSILON());
		assertEquals (-0.1825, model.getParameter(1), EPSILON());
		assertEquals ( 0.0606, model.getParameter(2), EPSILON());
		assertEquals (-0.1482, model.getParameter(3), EPSILON());
		assertEquals ( 0.0743, model.getParameter(4), EPSILON());
		assertEquals (-0.1280, model.getParameter(5), EPSILON());
	}	
}
