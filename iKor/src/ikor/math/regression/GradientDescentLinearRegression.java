package ikor.math.regression;

// Title:       Gradient descent linear regression
// Version:     2.0
// Copyright:   2012-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.Vector;

/**
 * Linear regression using the gradient descent method for parameter estimation
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class GradientDescentLinearRegression extends GradientDescentRegression 
{
	// Constructors
	
	public GradientDescentLinearRegression (Vector[] x, Vector y)
	{
		super(x,y);
	}

	public GradientDescentLinearRegression (double[][] x, double[] y)
	{
		super(x,y);
	}
	
	// Regression model
	
	public RegressionModel createModel ()
	{
		return new LinearRegressionModel(variables());
	}
	
	// Cost function
	
	public double cost()
	{
		RegressionModel model = getModel();
		
		return getSSE(model)/ (2*instances());
	}

}
