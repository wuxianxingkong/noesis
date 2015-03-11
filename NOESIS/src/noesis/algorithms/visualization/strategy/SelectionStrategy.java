package noesis.algorithms.visualization.strategy;

import ikor.collection.List;
import noesis.Network;

/**
 * Node selection strategy interface
 */

public interface SelectionStrategy 
{
	public int select (Network network, List<Integer> candidateNodes);
}

