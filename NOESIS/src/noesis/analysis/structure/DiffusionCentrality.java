package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.Parameter;
import noesis.analysis.NodeScoreTask;

/**
 * Diffusion Centrality
 * 
 * @author Victor Martinez (victormg@acm.org)
 */

@Label("diffusion")
@Description("Diffusion centrality")
public class DiffusionCentrality extends NodeScoreTask
{
	public static final double DEFAULT_PASSING_PROBABILITY = 0.1;
	public static final int DEFAULT_PATH_LENGTH = 3;
	
	@Label("passing probability")
	@Parameter(min=0.0, max=1.0, defaultValue=DEFAULT_PASSING_PROBABILITY)
	private double passingProbability;

	@Label("path length")
	@Parameter(min=1, max=Integer.MAX_VALUE, defaultValue=DEFAULT_PATH_LENGTH)
	private int pathLength;

	// Constructors
	
	public DiffusionCentrality (Network network)
	{
		this (network, DEFAULT_PASSING_PROBABILITY, DEFAULT_PATH_LENGTH);
	}
	
	public DiffusionCentrality (Network network, double passingProbability, int iterations) 
	{
		super(network);
		this.passingProbability = passingProbability;
		this.pathLength = iterations;
	}

	// Metadata
	
	@Override
	public String getName() 
	{
		return "diffusion("+passingProbability+","+pathLength+")";
	}	
	
	
	// Computation
	
	@Override
	public double compute (int node) 
	{
		double value = 0;
		double poweredPassingProbability = passingProbability;
		
		for (int t=1 ; t<=pathLength ; t++) {
			value += poweredPassingProbability * computePathCount(node, 1, t);
			poweredPassingProbability *= passingProbability;
		}
		
		return value;
	}

	/* Compute the count of all possible paths of length maxDepth-currentDepth from currentNode */
	
	private int computePathCount (int currentNode, int currentDepth, int maxDepth) 
	{
		Network network = getNetwork();

		if(currentDepth<maxDepth) {

			int value = 0;
			
			for (int i=0; i<network.outDegree(currentNode); i++) 
				value += computePathCount(network.outLink(currentNode, i), currentDepth+1, maxDepth);
			
			return value;

		} else if (currentDepth==maxDepth) {

			return network.outDegree(currentNode);

		} else {

			return 0;
		} 
	}
}