package test.ikor.math.regression;

import ikor.math.regression.GradientDescentLogisticRegression;
import ikor.math.regression.LogisticRegressionModel;


public class GradientDescentLogisticRegressionTest extends LogisticRegressionTest
{
	
	// Logistic regression model
	
	@Override
	public LogisticRegressionModel createLogisticRegressionModel ( double x[][], double y[])
	{
		GradientDescentLogisticRegression reg = new GradientDescentLogisticRegression (x,y);

		reg.setErrorMargin(1e-10);  
		reg.setLearningRate(0.5);   
		reg.setIterations(10000);   
			
		return (LogisticRegressionModel) reg.getResult();		
	}	
	
}
