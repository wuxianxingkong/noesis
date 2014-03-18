package test.ikor.math.regression;

import ikor.math.regression.LogisticRegressionModel;
import ikor.math.regression.NewtonMethodLogisticRegression;


public class NewtonMethodLogisticRegressionTest extends LogisticRegressionTest
{
	
	// Logistic regression model
	
	@Override
	public LogisticRegressionModel createLogisticRegressionModel ( double x[][], double y[])
	{
		NewtonMethodLogisticRegression reg = new NewtonMethodLogisticRegression (x,y);

		reg.setLearningRate(0.5);   // GradientDescentRegression.DEFAULT_LEARNING_RATE);
		reg.setIterations(100);     // GradientDescentRegression.DEFAULT_MAX_ITERATIONS);
			
		return (LogisticRegressionModel) reg.getResult();		
	}	
	
}
