package ikor.math.statistics.fit;

import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.ChiSquaredDistribution;
import ikor.math.statistics.NormalDistribution;
import ikor.math.statistics.StudentTDistribution;

/**
 * Fit a normal distribution 
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class NormalDistributionFit implements DistributionFit<NormalDistribution>
{
	private Vector data;
	
	// Constructors
	
	public NormalDistributionFit (Vector data)
	{
		this.data = data;
	}
	
	public NormalDistributionFit (double[] data) 
	{
		this.data = MatrixFactory.createVector(data);
	}	
	
	// Distribution fitting interface

	@Override
	public NormalDistribution fit() 
	{
		return new NormalDistribution( sampleMean(), sampleDeviation() );
	}
	
	// Maximum likelihood estimates
	
	public double mleMean ()
	{
		return data.average();
	}
	
	public double mleVariance ()
	{
		return data.variance();
	}
	
	public double mleDeviation ()
	{
		return data.deviation();
	}
	
	// Unbiased estimators
	
	public double sampleMean ()
	{
		return data.average();
	}
	
	public double sampleVariance ()
	{
		return data.sampleVariance();
	}
	
	public double sampleDeviation ()
	{
		return data.sampleDeviation();
	}
	
	// Confidence intervals
	
	public ConfidenceInterval mean (double alpha)
	{
		int    n = data.size();
		double mu = sampleMean();
		double s = Math.sqrt(sampleVariance());
		
		StudentTDistribution t = new StudentTDistribution(n-1);
		
		double min = mu + t.idf(alpha/2)*s/Math.sqrt(n);
		double max = mu + t.idf(1-alpha/2)*s/Math.sqrt(n);
		
		return new ConfidenceInterval(min,max,alpha);		
	}
	
	public ConfidenceInterval variance (double alpha)
	{
		int    n = data.size();
		double s2 = sampleVariance();
		
		ChiSquaredDistribution chi2 = new ChiSquaredDistribution(n-1);
		
		double min = (n-1)*s2 / chi2.idf(1-alpha/2);
		double max = (n-1)*s2 / chi2.idf(alpha/2);

		return new ConfidenceInterval(min,max,alpha);		
	}
	
	public ConfidenceInterval deviation (double alpha)
	{
		ConfidenceInterval variance = variance(alpha);
		
		return new ConfidenceInterval ( Math.sqrt(variance.min()), Math.sqrt(variance.max()), alpha);
	}

}
