package noesis.analysis.structure;

// Title:       Link embeddedness
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.CollectionFactory;
import ikor.collection.Set;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.LinkScoreTask;

/**
 * Link embeddedness, i.e. number of shared neighbors.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

@Label("embeddedness")
@Description("Link embeddedness, a.k.a. shared neighbors")
public class LinkEmbeddedness extends LinkScoreTask
{
	public LinkEmbeddedness(Network network) 
	{
		super(network);
	}
	
	protected Set<Integer> neighborhood (int node)
	{
		Network      net = getNetwork();
		Set<Integer> neighbors = CollectionFactory.createSet();
		
		for (int i=0; i<net.inDegree(node); i++)
			neighbors.add(net.inLink(node,i));
		
		for (int i=0; i<net.outDegree(node); i++)
			neighbors.add(net.outLink(node,i));

		return neighbors;
	}

	@Override
	public double compute(int source, int destination) 
	{
		Set<Integer> sourceNeighborhood = neighborhood(source);
		Set<Integer> destinationNeighborhood = neighborhood(destination);
		Set<Integer> sharedNeighborhood = sourceNeighborhood.intersection(destinationNeighborhood);
		
		return sharedNeighborhood.size();
	}

}
