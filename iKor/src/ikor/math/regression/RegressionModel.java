package ikor.math.regression;

// Title:       Regression model
// Version:     2.0
// Copyright:   2012-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.MatrixFactory;
import ikor.math.Vector;

/**
 * Regression model
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public abstract class RegressionModel 
{
	private Vector parameters;
	
	private int    n;
	private double sse;
	private double sst;
	
	public RegressionModel (int variables)
	{
		parameters = MatrixFactory.createVector(variables+1);
	}
	
	public RegressionModel (double[] coefficients)
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

	public void setParameters (Vector parameters)
	{
		this.parameters = parameters;
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
	
	public double R2 ()
	{
		return 1 - sse/sst;
	}
	
	public double adjustedR2 ()
	{
		return 1 - (n-1)*(1-R2())/(n-parameters());
	}
	
	// Standard error of regression
	// == root-mean-square-error (RMSE)
	
	public double rmse ()
	{
		return Math.sqrt(sse/(n-parameters()));
	}
	
	public double explainedVariance ()
	{
		return ssr()/sst();
	}
	
	private Vector standardErrors;
	
	public void setStandardErrors (Vector errors)
	{
		this.standardErrors = errors;
	}
	
	public double standardError (int p)
	{
		if (standardErrors!=null)
			return standardErrors.get(p);
		else
			return Double.NaN;
	}

	/**
	 * Degrees of freedom
	 * 
	 * @return n() - parameters()
	 */
	public int dof ()
	{
		return n()-parameters();
	}
	
	/**
	 * The t-statistic tests whether any of the regression coefficients might be equal to zero. The t-statistic 
	 * is calculated simply as parameter(p)/standardError(p). If the errors follow a normal distribution, 
	 * t follows a Student-t distribution. Under weaker conditions, t is asymptotically normal. Large values of 
	 * t indicate that the null hypothesis can be rejected and that the corresponding coefficient is not zero.
	 * 
	 * @param p Linear regression coefficient
	 * @return Student t-statistic
	 */
	public double tStatistic (int p)
	{
		return getParameter(p) / standardError(p);
	}


	public abstract double pValue (int p);
	
	// Durbin–Watson statistic
	
	private double dw;
	
	protected void setDW (double dw)
	{
		this.dw = dw;
	}
	
	/**
	 * The Durbin–Watson statistic is a test statistic used to detect the presence of autocorrelation
	 * (a relationship between values separated from each other by a given time lag) in the residuals 
	 * (prediction errors) from a regression analysis.
	 * 
	 * @return Durbin-Watson statistic
	 */
	
	public double DurbinWatsonStatistic ()
	{
		return dw;
	}
	
	
	// Prediction
	
	/**
	 * Prediction. Use of the linear regression model to predict value.
	 * 
	 * @param x Input vector
	 * @return Prediction y
	 */
	
	public abstract double predict (double[] x);
	
}
