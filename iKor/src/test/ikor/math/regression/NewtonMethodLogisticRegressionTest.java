package test.ikor.math.regression;

import ikor.math.regression.LogisticRegressionModel;
import ikor.math.regression.NewtonMethodLogisticRegression;


public class NewtonMethodLogisticRegressionTest extends LogisticRegressionTest
{
	// Epsilon
	
	public double EPSILON ()
	{
		return 0.0001;
	}
	
	// Logistic regression model
	
	@Override
	public LogisticRegressionModel createLogisticRegressionModel ( double x[][], double y[])
	{
		NewtonMethodLogisticRegression reg = new NewtonMethodLogisticRegression (x,y);

		reg.setLearningRate(1.0);
		reg.setMaxIterations(100);
		reg.setMinCostImprovement(1e-10);  
			
		return (LogisticRegressionModel) reg.getResult();		
	}	
	
}
