package noesis.algorithms.visualization.strategy;

import ikor.collection.List;
import noesis.Network;
import noesis.analysis.NodeScore;
import noesis.analysis.structure.AveragePathLength;


/**
 * Node selection strategy: 
 * Select the node with the lowest average shortest length path to all the other candidate nodes.
 */
public class MinAveragePathLengthSelectionStrategy implements SelectionStrategy 
{
	@Override
	public int select (Network network, List<Integer> candidateNodes) 
	{
		NodeScore pathLength = (new AveragePathLength(network)).getResult();
		
		int minIndex = -1;
		double minValue = Double.MAX_VALUE;
		
		for (int node: candidateNodes) {
			if (pathLength.get(node) < minValue) {
				minValue = pathLength.get(node);
				minIndex = node;
			}
		}
		
		return minIndex;
	}
}
