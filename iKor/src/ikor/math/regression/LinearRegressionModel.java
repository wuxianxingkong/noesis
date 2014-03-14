package ikor.math.regression;

// Title:       Linear regression model
// Version:     2.0
// Copyright:   2012-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.MatrixFactory;
import ikor.math.Vector;

/**
 * Linear regression model
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class LinearRegressionModel 
{
	private Vector parameters;
	private double sse;
	
	public LinearRegressionModel (int variables)
	{
		parameters = MatrixFactory.createVector(variables+1);
	}
	
	public LinearRegressionModel (double[] coefficients)
	{
		parameters =  MatrixFactory.createVector(coefficients.length);
		
		for (int i=0; i<coefficients.length; i++)
			setParameter(i, coefficients[i]);
	}
	
	// Model parameters
	
	public int parameters ()
	{
		return parameters.size();
	}
	
	public double getParameter (int i)
	{
		return parameters.get(i);
	}
	
	public void setParameter (int i, double value)
	{
		parameters.set(i,value);
	}
	
	public void setParameters (double values[])
	{
		for (int i=0; i<values.length; i++)
			setParameter(i, values[i]);
	}
	
	// Error measures
	
	public double getSSE ()
	{
		return sse;
	}
	
	public void setSSE (double sse)
	{
		this.sse = sse;
	}
	
	// Prediction
	
	public double predict (double[] x)
	{
		double h = getParameter(0);
		
		for (int j=0; j<x.length; j++) {
			h += x[j]*getParameter(j+1);
		}
		
		return h;
	}	
	
}
