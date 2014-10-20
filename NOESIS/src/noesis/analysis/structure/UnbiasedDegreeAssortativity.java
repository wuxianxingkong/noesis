package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;

/**
 * Unbiased local degree assortativity.
 * 
 * @author Victor Martinez
 * 
 * References:
 * 	- M. Piraveenan et al: "Classifying complex networks using unbiased local assortativity," ALIFE, 2010
 */
@Label("unbiased-degree-assortativity")
@Description("Unbiased local degree assortativity")
public class UnbiasedDegreeAssortativity extends DegreeAssortativity
{
	public UnbiasedDegreeAssortativity (Network network)
	{
		super(network);
	}

	@Override
	protected double computeAssortativity (Network network, double nodeExcessDegree, double avgNeighboursExcessDegree) 
	{
		return nodeExcessDegree
				* (nodeExcessDegree + 1)
				* (avgNeighboursExcessDegree - mean)
				/ (network.links()*variance);
	}
}
