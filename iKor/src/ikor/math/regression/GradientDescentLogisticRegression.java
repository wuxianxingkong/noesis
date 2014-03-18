package ikor.math.regression;

// Title:       Gradient descent logistic regression
// Version:     2.0
// Copyright:   2012-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.Vector;

/**
 * Logistic regression using the gradient descent method for parameter estimation
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class GradientDescentLogisticRegression extends GradientDescentRegression 
{
	// Constructors
	
	public GradientDescentLogisticRegression (Vector[] x, Vector y)
	{
		super(x,y);
	}

	public GradientDescentLogisticRegression (double[][] x, double[] y)
	{
		super(x,y);
	}
	
	
	// Regression model
	
	public RegressionModel createModel ()
	{
		return new LogisticRegressionModel(variables());
	}
		
	
	// Cost function
	
	@Override
	public double cost()
	{
		double J = 0;
		
		for (int i=0; i<instances(); i++) {
			J += cost(i);
		}
		
		return J / instances();
	}
	
	
	public double cost (int i)
	{
		double y = getY(i);
		double h = getPrediction(getModel(),i);
		
		return -y*Math.log(h)-(1-y)*Math.log(1-h);
	}
	
	


}
