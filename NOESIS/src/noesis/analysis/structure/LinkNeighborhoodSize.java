package noesis.analysis.structure;

// Title:       Link neighborhood size
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.Set;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.Network;

/**
* Link neighborhood size, i.e. total number of neighbors.
* 
* @author Fernando Berzal (berzal@acm.org)
*/

@Label("neighborhood-size")
@Description("Link neighborhood size")
public class LinkNeighborhoodSize extends LinkEmbeddedness
{
	public LinkNeighborhoodSize(Network network) 
	{
		super(network);
	}

	@Override
	public double compute(int source, int destination) 
	{
		Set<Integer> sourceNeighborhood = neighborhood(source);
		Set<Integer> destinationNeighborhood = neighborhood(destination);
		Set<Integer> linkNeighborhood = sourceNeighborhood.union(destinationNeighborhood);
		
		linkNeighborhood.remove(source);
		linkNeighborhood.remove(destination);
		
		return linkNeighborhood.size();
	}

}