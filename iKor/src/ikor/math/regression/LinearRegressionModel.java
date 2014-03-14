package ikor.math.regression;

// Title:       Linear regression model
// Version:     2.0
// Copyright:   2012-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.FDistribution;
import ikor.math.statistics.StudentTDistribution;

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
	
	// Statistics
	
	/**
	 * F-statistic tries to test the hypothesis that all coefficients (except the intercept) are equal to zero. 
	 * This statistic has F(p–1,n–p) distribution under the null hypothesis and normality assumption, and its 
	 * p-value indicates probability that the hypothesis is indeed true. Note that when errors are not normal 
	 * this statistic becomes invalid, and other tests such as for example Wald test or LR test should be used.
	 * 
	 * @return F-statistic value
	 */
	public double FStatistic ()
	{
		return (ssr()/(parameters()-1)) / ((sst()-ssr())/(n-parameters()));
	}
	
	/**
	 * F-statistic tries to test the hypothesis that all coefficients (except the intercept) are equal to zero. 
	 * This statistic has F(p–1,n–p) distribution under the null hypothesis and normality assumption, and its 
	 * p-value indicates probability that the hypothesis is indeed true. Note that when errors are not normal 
	 * this statistic becomes invalid, and other tests such as for example Wald test or LR test should be used.
	 * 
	 * @return F-statistic p-value
	 */
	
	public double pValueF ()
	{
		FDistribution fdist = new FDistribution( parameters()-1, n-parameters() );
		
		return 1.0 - fdist.cdf( FStatistic() );
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
	
	/**
	 * The t-statistic tests whether any of the regression coefficients might be equal to zero. Its p-value 
	 * expresses the results of the hypothesis test as a significance level. Conventionally, p-values smaller 
	 * than 0.05 are taken as evidence that the population coefficient is nonzero.
	 *  
	 * @param p Linear regression coefficient
	 * @return t-statistic p-value
	 */
	public double pValue (int p)
	{
		double t = tStatistic(p);
		StudentTDistribution tdist = new StudentTDistribution(n-parameters());
		
		return 2*Math.min( tdist.cdf(t), 1.0-tdist.cdf(t) );
	}
	
	 
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
	
	// Model likelihood
	
	/**
	 * Likelihood: The likelihood of a set of parameter values given outcomes is equal to the 
	 * probability of those observed outcomes given those parameter values. 
	 * ref. http://en.wikipedia.org/wiki/Likelihood_function
	 * 
	 * @return
	 */
	public double logLikelihood ()
	{
		return - n/2.0 * ( Math.log(2*Math.PI/n) + Math.log(sse) + 1); 
	}

	
	/**
	 * Akaike information criterion (AIC). A measure of the relative quality of a statistical model for a given set of data.
	 * ref. http://en.wikipedia.org/wiki/Akaike_information_criterion
	 * 
	 * @return AIC
	 */
	public double AIC ()
	{
		int k = parameters();
		
		return (2*k - 2*logLikelihood())/n;
	}

	/**
	 * Corrected Akaike information criterion (AICc). A measure of the relative quality of a statistical model for a given set of data.
	 * ref. http://en.wikipedia.org/wiki/Akaike_information_criterion
	 * 
	 * @return AICc
	 */
	public double AICc ()
	{
		double k = parameters();
		
		return AIC() + 2*k*(k+1)/(n-k-1);
	}

	/**
	 * Bayesian information criterion (BIC) or Schwarz criterion (also SBC, SBIC)
	 * ref. http://en.wikipedia.org/wiki/Schwarz_criterion
	 * 
	 * return BIC 
	 */
	public double BIC ()
	{
		int k = parameters();
		
		return ( k * Math.log(n) - 2*logLikelihood() ) / n;
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
		
		return h;
	}	
	
}
