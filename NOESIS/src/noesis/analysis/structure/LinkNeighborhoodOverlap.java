package noesis.analysis.structure;

// Title:       Link neighborhood overlap
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.Set;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.Network;

/**
* Link neighborhood overlap, i.e. number of shared neighbors / total number of neighbors.
* 
* @author Fernando Berzal (berzal@acm.org)
*/

@Label("Neighborhood overlap")
@Description("Link neighborhood overlap")
public class LinkNeighborhoodOverlap extends LinkEmbeddedness
{
	public LinkNeighborhoodOverlap(Network network) 
	{
		super(network);
	}

	@Override
	public double compute(int source, int destination) 
	{
		Set<Integer> sourceNeighborhood = neighborhood(source);
		Set<Integer> destinationNeighborhood = neighborhood(destination);
		Set<Integer> sharedNeighborhood = sourceNeighborhood.intersection(destinationNeighborhood);
		Set<Integer> linkNeighborhood = sourceNeighborhood.union(destinationNeighborhood);
		
		linkNeighborhood.remove(source);
		linkNeighborhood.remove(destination);
		
		return sharedNeighborhood.size() / (double) linkNeighborhood.size();
	}

}