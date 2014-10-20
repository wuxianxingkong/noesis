package noesis.analysis.structure;

import ikor.math.Vector;
import ikor.math.statistics.DiscreteProbabilityDistribution;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.Network;
import noesis.analysis.NodeScoreTask;

/**
 * Local degree assortativity.
 * 
 * @author Victor Martinez
 * 
 * References:
 * 	- M. Piraveenan et al: "Local assortativeness in scale-free networks," EPL, 84 (2008)
 * 	- M. Piraveenan et al: "Classifying complex networks using unbiased local assortativity," ALIFE, 2010
 */
@Label("degree-assortativity")
@Description("Degree assortativity")
public class DegreeAssortativity extends NodeScoreTask 
{
	private DiscreteProbabilityDistribution distribution;
	
	protected double mean;
	protected double variance;
	
	public DegreeAssortativity (Network network)
	{
		super(network);
	}
	
	@Override
	public void compute ()
	{
		Network network = getNetwork();
		
		// Degree distribution
		Vector degree = (new OutDegree(network)).call();
		int maxDegree = (int) degree.max();
		double degreeAverage = degree.average();
		double[] degreeFraction = new double[maxDegree+1];
		
		for (int node=0; node<network.nodes(); node++)
			degreeFraction[network.outDegree(node)]++;
		
		for (int i=0; i<degreeFraction.length; i++)
			degreeFraction[i] /= network.nodes();
		
		// Excess degree distribution
		double[] degreeValues = new double[maxDegree];
		double[] degreeProbabilities = new double[maxDegree];
		
		for (int i=0; i<maxDegree; i++) {
			degreeValues[i] = i;
			degreeProbabilities[i] = (i+1)*degreeFraction[i+1]/degreeAverage;
		}
		
		this.distribution = new DiscreteProbabilityDistribution(degreeValues, degreeProbabilities); 
		this.mean = distribution.mean();
		this.variance = distribution.variance();

		super.compute();
	}
	
	@Override
	public double compute (int node) 
	{
		Network network = getNetwork();
		
		double nodeExcessDegree = network.outDegree(node)-1;
		double avgNeighboursExcessDegree = 0;
		int neighbors = network.outDegree(node);
		
		for (int i=0; i<neighbors; i++)
			avgNeighboursExcessDegree += network.outDegree(network.outLink(node, i))-1;
		
		if (neighbors>0)
			avgNeighboursExcessDegree /= neighbors;
		
		if (variance==0.0)
			return 1.0/network.size();
		else
			return computeAssortativity(network, nodeExcessDegree, avgNeighboursExcessDegree);
	}
	
	
	protected double computeAssortativity (Network network, double nodeExcessDegree, double avgNeighboursExcessDegree) 
	{
		return (nodeExcessDegree + 1)
				* (nodeExcessDegree*avgNeighboursExcessDegree - mean*mean)
				/ (network.links()*variance);
	}
}
