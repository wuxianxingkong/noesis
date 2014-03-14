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
public class BaselineRegression extends LinearRegression 
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
	
	// SST
	
	public double getSST ()
	{
		int    m = y.size();
		double base = getBaseline();
		double sse = 0;
		double d;

		for (int i=0; i<m; i++) {
			d = base - getY(i);
			sse += d*d; 
		}

		return sse;
	}
	
	// Task interface
	
	@Override
	public LinearRegressionModel call() 
	{
		LinearRegressionModel model = new LinearRegressionModel(variables());
		
		model.setParameter(0, getBaseline());
		model.setSSE( getSST() );
		
		return model;
	}

}
