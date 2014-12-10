package ikor.math.statistics.fit;

import ikor.math.MatrixFactory;
import ikor.math.Vector;
import ikor.math.statistics.NormalDistribution;
import ikor.math.statistics.ParetoDistribution;

/**
 * Fit a Pareto distribution (i.e. a power law distribution) 
 * 
 * ref. "Power-law distributions in empirical data" 
 *      by Aaron Clauset, Cosma Rohilla Shalizi & Mark E.J. Newman
 *      SIAM Review 51(4), 661-703 (2009). (arXiv:0706.1062, doi:10.1137/070710111)
 *
 * @author Fernando Berzal (berzal@acm.org)
 */

public class ParetoDistributionFit implements DistributionFit<ParetoDistribution> 
{
	private Vector data;
	
	// Constructors
	
	public ParetoDistributionFit (Vector data)
	{
		this.data = data;
	}
	
	public ParetoDistributionFit (double[] data) 
	{
		this.data = MatrixFactory.createVector(data);
	}	
	
	// Distribution fitting interface

	@Override
	public ParetoDistribution fit() 
	{
		return new ParetoDistribution( mleShape(), mleScale() );
	}
	
	// Estimators
	
	public double mleShape ()
	{
		int n=0;
		double xmin = mleScale();
		double sum = 0.0;

		for (int index=0; index<data.size(); index++) {
			if (data.get(index)>=xmin) {
				n++;
				sum += Math.log(data.get(index)/xmin);         // Continuous
				//sum += Math.log(data.get(index)/(xmin-0.5)); // Discrete
			}
		}
		
		return 1+n*(1.0/sum);
	}
	
	public double mleScale ()
	{
		return Math.sqrt(data.max());
	}
	
	private int nscale ()
	{
		int    n = 0;
		double xmin = mleScale();
		
		for (int index=0; index<data.size(); index++) {
			if (data.get(index)>=xmin) {
				n++;
			}
		}
		
		return n;
	}
	
	// Confidence intervals

	public ConfidenceInterval shapeNormalApproximation (double alpha)
	{
		int n = nscale(); // vs. data.size();
		double mu = mleShape();
		double sigma = (mu-1)/Math.sqrt(n);
		
		NormalDistribution normal = new NormalDistribution(mu,sigma);
		
		double min = normal.idf(alpha/2);
		double max = normal.idf(1-alpha/2);
		
		return new ConfidenceInterval(min,max,alpha);
	}
	
}
