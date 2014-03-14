package test.ikor.math.regression;

import ikor.math.regression.GradientDescentLinearRegression;
import ikor.math.regression.LinearRegressionModel;


public class GradientDescentLinearRegressionTest extends LinearRegressionTest 
{
	private static final double GRADIENT_DESCENT_EPSILON = 1e-4;
	

	@Override
	public LinearRegressionModel createLinearRegressionModel (double x[][], double y[]) 
	{
		GradientDescentLinearRegression reg = new GradientDescentLinearRegression (x,y);

		reg.setLearningRate(0.03); // GradientDescentLinearRegression.DEFAULT_LEARNING_RATE);
		reg.setIterations(2000);   // GradientDescentLinearRegression.DEFAULT_MAX_ITERATIONS);
		
		return reg.call();
	}

	@Override
	public double EPSILON() 
	{
		return GRADIENT_DESCENT_EPSILON;
	}

}
