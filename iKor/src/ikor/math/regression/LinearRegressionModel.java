package ikor.math.regression;

// Title:       Linear regression model
// Version:     2.0
// Copyright:   2012-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.statistics.FDistribution;
import ikor.math.statistics.StudentTDistribution;

/**
 * Linear regression model
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class LinearRegressionModel extends RegressionModel
{
	public LinearRegressionModel (int variables)
	{
		super(variables);
	}
	
	public LinearRegressionModel (double[] coefficients)
	{
		super(coefficients);
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
		return (ssr()/(parameters()-1)) / ((sst()-ssr())/(n()-parameters()));
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
		FDistribution fdist = new FDistribution( parameters()-1, n()-parameters() );
		
		return 1.0 - fdist.cdf( FStatistic() );
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
		StudentTDistribution tdist = new StudentTDistribution(dof());
		
		return 2*Math.min( tdist.cdf(t), 1.0-tdist.cdf(t) );
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
		return - n()/2.0 * ( Math.log(2*Math.PI/n()) + Math.log(sse()) + 1); 
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
		
		return (2*k - 2*logLikelihood())/n();
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
		
		return AIC() + 2*k*(k+1)/(n()-k-1);
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
		
		return ( k * Math.log(n()) - 2*logLikelihood() ) / n();
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
