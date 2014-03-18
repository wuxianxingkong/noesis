package ikor.math.regression;

// Title:       Baseline regression
// Version:     2.0
// Copyright:   1998-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.Vector;

/**
 * Baseline predictor
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class BaselineRegression extends Regression 
{
	// Constructors
	
	public BaselineRegression (Vector[] x, Vector y)
	{
		super(x,y);
	}

	public BaselineRegression (double[][] x, double[] y)
	{
		super(x,y);
	}
	
	// Baseline
	
	public double getBaseline ()
	{
		return getY().average();
	}

	
	// Task interface
	
	@Override
	public LinearRegressionModel call() 
	{
		LinearRegressionModel model = new LinearRegressionModel(variables());
		
		model.setParameter(0, getBaseline());
		
		fit(model);
		
		return model;
	}

}
