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

		reg.setErrorMargin(1e-10);  
		reg.setLearningRate(1.0);
		reg.setIterations(100);
			
		return (LogisticRegressionModel) reg.getResult();		
	}	
	
}
