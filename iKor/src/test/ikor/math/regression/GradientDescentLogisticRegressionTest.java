package test.ikor.math.regression;

import ikor.math.regression.GradientDescentLogisticRegression;
import ikor.math.regression.LogisticRegressionModel;


public class GradientDescentLogisticRegressionTest extends LogisticRegressionTest
{
	// Epsilon
	
	public double EPSILON ()
	{
		return 0.001;
	}
	
	
	// Logistic regression model
	
	@Override
	public LogisticRegressionModel createLogisticRegressionModel ( double x[][], double y[])
	{
		GradientDescentLogisticRegression reg = new GradientDescentLogisticRegression (x,y);

		reg.setErrorMargin(1e-12);  
		reg.setLearningRate(0.5);   
		reg.setIterations(10000);   
			
		return (LogisticRegressionModel) reg.getResult();		
	}	
	
}
