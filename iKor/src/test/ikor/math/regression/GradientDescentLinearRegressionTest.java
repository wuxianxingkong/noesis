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

		reg.setLearningRate(0.03);
		reg.setMaxIterations(2000);
		reg.setMinCostImprovement(0.0);   // i.e. perform the specified number of iterations
		
		return (LinearRegressionModel) reg.getResult();
	}

	@Override
	public double EPSILON() 
	{
		return GRADIENT_DESCENT_EPSILON;
	}

}
