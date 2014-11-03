package noesis.analysis.structure;

import ikor.math.Vector;
import ikor.math.statistics.DiscreteProbabilityDistribution;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.NodeScore;
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
		int size = network.size();
		
		// Degree distribution
		Vector degree = (new OutDegree(network)).call();
		int maxDegree = (int) degree.max();
		double degreeAverage = degree.average();
		double[] degreeFraction = new double[maxDegree+1];
		
		for (int node=0; node<size; node++)
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
		
		// Node degree assortativities
		
		NodeScore score = new NodeScore(this,network);
	
		for (int node=0; node<size; node++)
			score.set (node, computeAssortativity(network,node));
		
		setResult(score);		
	}
	
	
	@Override
	public double compute (int node) 
	{
		checkDone();	
		
		return getResult(node);
	}
	
	
	private double computeAssortativity (Network network, int node) 
	{
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
			return assortativity(network, nodeExcessDegree, avgNeighboursExcessDegree);
	}
	
	
	protected double assortativity (Network network, double nodeExcessDegree, double avgNeighboursExcessDegree) 
	{
		return (nodeExcessDegree + 1)
				* (nodeExcessDegree*avgNeighboursExcessDegree - mean*mean)
				/ (network.links()*variance);
	}
}
