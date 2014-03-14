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
	
	private int    n;
	private double sse;
	private double sst;
	
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
	

	
	// Measures
	
	public int n ()
	{
		return n;
	}
	
	public void setN (int n)
	{
		this.n = n;
	}
	
	// Residual sum of squares (RSS), a.k.a. sum of squared residuals (SSR) or sum of squared errors of prediction (SSE).
	
	public double sse ()
	{
		return sse;
	}
	
	public void setSSE (double sse)
	{
		this.sse = sse;
	}

	// Total sum of squares = explained sum of squares + residual sum of squares
	
	public double sst ()
	{
		return sst;
	}
	
	public void setSST (double sst)
	{
		this.sst = sst;
	}
	
	// Explained sum of squares (ESS), a.k.a. model sum of squares or sum of squares due to regression (SSR)

	public double ssr ()
	{
		return sst-sse;
	}
	
	public double r2 ()
	{
		return 1 - sse/sst;
	}
	
	public double adjustedR2 ()
	{
		return 1 - (n-1)*(1-r2())/(n-parameters());
	}
	
	// Standard error of regression
	
	public double stdError ()
	{
		return Math.sqrt(sse/(n-parameters()));
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
