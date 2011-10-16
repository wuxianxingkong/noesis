package test.ikor.math.regression;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import ikor.math.regression.LinearRegression;

public class LinearRegressionTest 
{
	private static final double GRADIENT_DESCENT_EPSILON = 1e-4;
	private static final double NORMAL_EQUATION_EPSILON  = 1e-14;

	LinearRegression univariate;
	LinearRegression multivariate;
	
	// y1 = -1 + x1
	
	double x1[][] = new double[][] { {1, 2, 3, 4, 5, 6, 7, 8, 9} };
	double y1[]   = new double[]     {0, 1, 2, 3, 4, 5, 6, 7, 8};

	// y2 = 2 + 0.5*x1 + 0.5*x2;
	
	double x2[][] = new double[][] { {0, 1, 3, 5, 7, 9, 1, 3, 5, 7, 9 },
			                         {0, 9, 7, 5, 3, 1, 1, 3, 5, 7, 9 } };
	double y2[]   = new double[]     {2, 7, 7, 7, 7, 7, 3, 5, 7, 9, 11};
	
	
	@Before
	public void setUp() throws Exception 
	{	
		univariate   = new LinearRegression(x1, y1);
		multivariate = new LinearRegression(x2, y2);
	}
	
	@Test
	public void testUnivariateGradientDescent ()
	{
		double[] theta;

		theta = univariate.getParameters();
		
		assertEquals ( 2, theta.length);
		assertEquals ( 0, theta[0], GRADIENT_DESCENT_EPSILON);
		assertEquals ( 0, theta[1], GRADIENT_DESCENT_EPSILON);
		assertEquals ( 204.0/(2*9), univariate.getCost(), GRADIENT_DESCENT_EPSILON);
		assertEquals ( 0, univariate.predict(new double[]{10}), GRADIENT_DESCENT_EPSILON);
		
		univariate.setLearningRate(0.05);
		univariate.setIterations(1000);
		univariate.gradientDescent();
		
		theta = univariate.getParameters();
		
		assertEquals ( -1, theta[0], GRADIENT_DESCENT_EPSILON);
		assertEquals ( +1, theta[1], GRADIENT_DESCENT_EPSILON);
		
		assertEquals ( 0, univariate.getCost(), GRADIENT_DESCENT_EPSILON);
		assertEquals ( 9, univariate.predict(new double[]{10}), GRADIENT_DESCENT_EPSILON);
	}


	@Test
	public void testMultivariateGradientDescent ()
	{
		double[] theta;

		theta = multivariate.getParameters();
		
		assertEquals ( 3, theta.length);
		assertEquals ( 0, theta[0], GRADIENT_DESCENT_EPSILON);
		assertEquals ( 0, theta[1], GRADIENT_DESCENT_EPSILON);
		assertEquals ( 0, theta[2], GRADIENT_DESCENT_EPSILON);
		assertEquals ( 0, multivariate.predict(new double[]{10,10}), GRADIENT_DESCENT_EPSILON);
		
		multivariate.setLearningRate(0.03);
		multivariate.setIterations(2000);
		multivariate.gradientDescent();
		
		theta = multivariate.getParameters();
		
		assertEquals ( 2, theta[0], GRADIENT_DESCENT_EPSILON);
		assertEquals ( 0.5, theta[1], GRADIENT_DESCENT_EPSILON);
		assertEquals ( 0.5, theta[2], GRADIENT_DESCENT_EPSILON);
		
		assertEquals ( 0, multivariate.getCost(), GRADIENT_DESCENT_EPSILON);
		assertEquals ( 12, multivariate.predict(new double[]{10,10}), GRADIENT_DESCENT_EPSILON);
		assertEquals ( 17, multivariate.predict(new double[]{10,20}), GRADIENT_DESCENT_EPSILON);
		assertEquals ( 17, multivariate.predict(new double[]{20,10}), GRADIENT_DESCENT_EPSILON);
	}

	
	@Test
	public void testUnivariateNormalEquation ()
	{
		double[] theta;

		theta = univariate.getParameters();
		
		assertEquals ( 2, theta.length);
		assertEquals ( 0, theta[0], NORMAL_EQUATION_EPSILON);
		assertEquals ( 0, theta[1], NORMAL_EQUATION_EPSILON);
		assertEquals ( 204.0/(2*9), univariate.getCost(), NORMAL_EQUATION_EPSILON);
		assertEquals ( 0, univariate.predict(new double[]{10}), NORMAL_EQUATION_EPSILON);
		
		univariate.normalEquation();
	
		theta = univariate.getParameters();
		
		assertEquals ( -1, theta[0], NORMAL_EQUATION_EPSILON);
		assertEquals ( +1, theta[1], NORMAL_EQUATION_EPSILON);
		
		assertEquals ( 0, univariate.getCost(), NORMAL_EQUATION_EPSILON);
		assertEquals ( 9, univariate.predict(new double[]{10}), NORMAL_EQUATION_EPSILON);
	}


	@Test
	public void testMultivariateNormalEquation ()
	{
		double[] theta;

		theta = multivariate.getParameters();
		
		assertEquals ( 3, theta.length);
		assertEquals ( 0, theta[0], NORMAL_EQUATION_EPSILON);
		assertEquals ( 0, theta[1], NORMAL_EQUATION_EPSILON);
		assertEquals ( 0, theta[2], NORMAL_EQUATION_EPSILON);
		assertEquals ( 0, multivariate.predict(new double[]{10,10}), NORMAL_EQUATION_EPSILON);
		
		multivariate.normalEquation();
		
		theta = multivariate.getParameters();
		
		assertEquals ( 2, theta[0], NORMAL_EQUATION_EPSILON);
		assertEquals ( 0.5, theta[1], NORMAL_EQUATION_EPSILON);
		assertEquals ( 0.5, theta[2], NORMAL_EQUATION_EPSILON);
		
		assertEquals ( 0, multivariate.getCost(), NORMAL_EQUATION_EPSILON);
		assertEquals ( 12, multivariate.predict(new double[]{10,10}), NORMAL_EQUATION_EPSILON);
		assertEquals ( 17, multivariate.predict(new double[]{10,20}), NORMAL_EQUATION_EPSILON);
		assertEquals ( 17, multivariate.predict(new double[]{20,10}), NORMAL_EQUATION_EPSILON);
	}
	
}
