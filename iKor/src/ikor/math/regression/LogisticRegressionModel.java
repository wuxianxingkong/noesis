package ikor.math.regression;

// Title:       Logistic regression model
// Version:     2.0
// Copyright:   2012-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Logistic regression model
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class LogisticRegressionModel extends RegressionModel
{
	public LogisticRegressionModel (int variables)
	{
		super(variables);
	}
	
	public LogisticRegressionModel (double[] coefficients)
	{
		super(coefficients);
	}

	// Prediction
	
	/**
	 * Prediction. Use of the linear regression model to predict value.
	 * 
	 * @param x Input vector
	 * @return Prediction y
	 */
	
	public double predict (double[] x)
	{
		double h = getParameter(0);
		
		for (int j=0; j<x.length; j++) {
			h += x[j]*getParameter(j+1);
		}
		
		return 1.0 / (1.0 + Math.exp(-h));
	}	
	
}
