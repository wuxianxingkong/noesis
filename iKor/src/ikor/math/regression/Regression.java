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
public abstract class Regression extends Task<RegressionModel> 
{
	Vector[] x;
	Vector   y;
	
	// Constructors
	
	public Regression (Vector[] x, Vector y)
	{
		this.x = x;
		this.y = y;

	}

	public Regression (Matrix x, Vector y)
	{
		this.y = y;
		this.x = new Vector[x.columns()];
		
		for (int i=0; i<x.columns(); i++)
			this.x[i] = x.getColumn(i);
	}
	
	public Regression (double[][] x, double[] y)
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
	
	public Vector[] getX ()
	{
		return x;
	}

	public Vector getX (int j)
	{
		return x[j-1];
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
	
	
	// Model fit
	
	public void fit (RegressionModel model)
	{
		model.setN ( instances() );
		model.setSSE( getSSE(model) ); 
		model.setSST( getSST(model) );
		model.setDW ( getDW(model) );
	}
	
	// Total sum of squares = explained sum of squares + residual sum of squares
	
	public double getSST(RegressionModel model)
	{
		int    m = y.size();
		double mean = y.average();
		double sst = 0;
		double d;
		
		for (int i=0; i<m; i++) {
			d = getY(i) - mean;
			sst += d*d; 
		}
		
		return sst;		
	}	
	
	// Explained sum of squares (ESS), a.k.a. model sum of squares or sum of squares due to regression ("SSR")
	
	public double getSSR (RegressionModel model)
	{
		int    m = y.size();
		double mean = y.average();
		double ssr = 0;
		double d;
		
		for (int i=0; i<m; i++) {
			d = getPrediction(model,i) - mean;
			ssr += d*d; 
		}
		
		return ssr;		
	}
	
	// Residual sum of squares (RSS), a.k.a. sum of squared residuals (SSR) or sum of squared errors of prediction (SSE).
	
	public double getSSE (RegressionModel model)
	{
		int    m = y.size();
		double sse = 0;
		double d;
		
		for (int i=0; i<m; i++) {
			d = getResidual(model,i);
			sse += d*d; 
		}
		
		return sse;		
	}
	
	// Durbin-Watson statistic
	
	public double getDW (RegressionModel model)
	{
		int    m = y.size();
		double sum = 0;
		double d;
		
		for (int i=1; i<m; i++) {
			d = getResidual(model,i)-getResidual(model,i-1);
			sum += d*d; 
		}
		
		return sum / getSSE(model);		
	}

	// Residual
	
	public double getResidual (RegressionModel model, int i)
	{
		return getPrediction(model,i) - getY(i);
	}

	// Prediction
	
	public double getPrediction (RegressionModel model, int i)
	{
		double d[] = new double[variables()];

		for (int j=0; j<variables(); j++) {
			d[j] = x[j].get(i);
		}

		return model.predict(d);
	}

	// Task interface
	
	@Override
	public abstract RegressionModel call(); 

}
