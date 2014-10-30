package ikor.math.regression;

import ikor.math.statistics.NormalDistribution;

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
	
	
	// Wald statistic
	
	public double waldStatistic (int p)
	{
		double beta = getParameter(p);
		double stderr = standardError(p);
		
		return (beta*beta) / (stderr*stderr);
	}
	
	// p-value (Wald statistic)

	public double pValue (int p)
	{
		double t = tStatistic(p);
		NormalDistribution norm = new NormalDistribution(0,1);
		
		return 2*norm.cdf(-Math.abs(t));    // MATLAB
		
		// StudentTDistribution tdist = new StudentTDistribution(dof());
		// return 2*tdist.cdf(t);
		
		// double w = waldStatistic(p);
		// ChiSquaredDistribution chi2 = new ChiSquaredDistribution(dof());
		// return 2*Math.min( chi2.cdf(w), 1.0-chi2.cdf(w) );
	}
	
}
