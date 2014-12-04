package ikor.math.statistics.fit;

import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.ChiSquaredDistribution;
import ikor.math.statistics.NormalDistribution;
import ikor.math.statistics.PoissonDistribution;

/**
 * Fit a Poisson distribution 
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class PoissonDistributionFit implements DistributionFit<PoissonDistribution> 
{
	private Vector data;
	
	// Constructors
	
	public PoissonDistributionFit (Vector data)
	{
		this.data = data;
	}
	
	public PoissonDistributionFit (double[] data) 
	{
		this.data = MatrixFactory.createVector(data);
	}	
	
	// Distribution fitting interface

	@Override
	public PoissonDistribution fit() 
	{
		return new PoissonDistribution( mleLambda() );
	}
	
	// Estimators
	
	public double mleLambda ()
	{
		return data.average();
	}
	
	// Confidence intervals
	
	public ConfidenceInterval lambda (double alpha)
	{
		int n = data.size();
		int k = (int) data.sum();
		
		ChiSquaredDistribution chi2min = new ChiSquaredDistribution(2*k);
		
		double min = chi2min.idf(alpha/2) / (2*n);
		
		ChiSquaredDistribution chi2max = new ChiSquaredDistribution(2*k+2);
		
		double max = chi2max.idf(1-alpha/2) / (2*n);
		
		return new ConfidenceInterval(min,max,alpha);
	}

	public ConfidenceInterval lambdaNormalApproximation (double alpha)
	{
		int n = data.size();
		double mu = data.sum();
		double sigma = Math.sqrt(mu);
		
		NormalDistribution normal = new NormalDistribution(mu,sigma);
		
		double min = normal.idf(alpha/2) / n;
		double max = normal.idf(1-alpha/2) / n;
		
		return new ConfidenceInterval(min,max,alpha);
	}
	
}
