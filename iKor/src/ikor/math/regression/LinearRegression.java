package ikor.math.regression;

// Title:       Linear regression estimation methods
// Version:     2.0
// Copyright:   2012-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.Matrix;

import ikor.parallel.Task;

/**
 * Base class for linear regression estimation methods
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public abstract class LinearRegression extends Task<LinearRegressionModel> 
{
	Vector[] x;
	Vector   y;
	
	// Constructors
	
	public LinearRegression (Vector[] x, Vector y)
	{
		this.x = x;
		this.y = y;

	}

	public LinearRegression (Matrix x, Vector y)
	{
		this.y = y;
		this.x = new Vector[x.columns()];
		
		for (int i=0; i<x.columns(); i++)
			this.x[i] = x.getColumn(i);
	}
	
	public LinearRegression (double[][] x, double[] y)
	{
		this.x = new Vector[x.length];
		
		for (int i=0; i<x.length; i++)
			this.x[i] = MatrixFactory.createVector(x[i]);
		
		this.y = MatrixFactory.createVector(y);
	}

	
	// Training set
	
	public int variables ()
	{
		return x.length;
	}
	
	public int instances ()
	{
		return y.size();
	}
	
		
	public double getX (int j, int i)
	{
		if (j>0)
			return x[j-1].get(i);
		else
			return 1; // X[0,:] = 1
	}
	
	public Vector getY ()
	{
		return y;
	}

	public double getY (int i)
	{
		return y.get(i);
	}
	

	// Task interface
	
	@Override
	public abstract LinearRegressionModel call(); 

}
