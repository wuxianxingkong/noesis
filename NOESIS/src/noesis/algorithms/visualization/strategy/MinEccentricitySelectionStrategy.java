package noesis.algorithms.visualization.strategy;

import ikor.collection.List;
import noesis.Network;
import noesis.analysis.NodeScore;
import noesis.analysis.structure.Eccentricity;

/**
 * Node selection strategy: 
 * Select the node with the lowest eccentricity.
 */
public class MinEccentricitySelectionStrategy implements SelectionStrategy 
{
	@Override
	public int select (Network network, List<Integer> candidateNodes) 
	{
		NodeScore eccentricity = (new Eccentricity(network)).getResult();
		
		int minIndex = -1;
		double minValue = Double.MAX_VALUE;
		
		for (int node: candidateNodes) {
			if (eccentricity.get(node) < minValue) {
				minValue = eccentricity.get(node);
				minIndex = node;
			}
		}
		
		return minIndex;
	}
}
