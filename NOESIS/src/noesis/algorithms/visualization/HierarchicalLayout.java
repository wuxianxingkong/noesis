package noesis.algorithms.visualization;

import ikor.collection.Dictionary;
import ikor.collection.DynamicDictionary;
import ikor.collection.DynamicList;
import ikor.collection.DynamicSet;
import ikor.collection.List;
import ikor.collection.Set;

import java.util.Iterator;

import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.Network;
import noesis.network.FilteredNetwork;
import noesis.network.filter.LinkFilter;

/**
 * Hierarchical graph layout algorithm
 * 
 * Tamassia, R. (2011):
 * "Handbook of graph drawing and visualization"
 * AMC, 10, 12
 * 
 * @author Victor Martinez (victormg@acm.org)
 */
public class HierarchicalLayout extends NetworkLayout 
{
	private Network acyclicNetwork;
	private Dictionary<Integer, Integer>[] layerAssignment; 

	@Override
	public void layout (AttributeNetwork network, Attribute<Double> x, Attribute<Double> y) 
	{
		// Step 1: Compute acyclic graph
		acyclicNetwork = greedyCycleRemoval(network);

		// Step 2: Compute layer assignment
		layerAssignment = layerAssignmentByLongestPath(acyclicNetwork);

		// Step 3: Optimize crosses
		if (layerAssignment.length > 1)
			reduceCrossings(network);

		// Compute coordinates
		double[] px = new double[network.nodes()];
		double[] py = new double[network.nodes()];

		for ( int layer = 0 ; layer < layerAssignment.length ; layer++ ) {

			Iterator<Integer> iterator = layerAssignment[layer].iterator();
			while (iterator.hasNext()) {
				int nodeIndex = iterator.next();

				px[nodeIndex] = (1.0 + layerAssignment[layer].get(nodeIndex))
						/ (1.0 + layerAssignment[layer].size());
				py[nodeIndex] = layer;
			}
		}

		// Normalize coordinates
		double minX = 1;
		double maxX = -1;
		double minY = 1;
		double maxY = -1;

		for (int i = 0; i < network.size(); i++) {

			if (px[i] < minX)
				minX = px[i];

			if (px[i] > maxX)
				maxX = px[i];

			if (py[i] < minY)
				minY = py[i];

			if (py[i] > maxY)
				maxY = py[i];
		}

		double scaleX;
		double scaleY;

		if (maxX > minX)
			scaleX = (1 - 2 * MARGIN) / (maxX - minX);
		else
			scaleX = 1.0;

		if (maxY > minY)
			scaleY = (1 - 2 * MARGIN) / (maxY - minY);
		else
			scaleY = 1.0;

		// Final (x,y) coordinates
		for (int i = 0; i < network.size(); i++) {
			x.set(i, MARGIN + scaleX * (px[i] - minX));
			y.set(i, MARGIN + scaleY * (py[i] - minY));
		}
	}

	// Step 1: Cycle Removal
	// ---------------------
	 
	private Network greedyCycleRemoval(AttributeNetwork network) 
	{
		List<Integer> sl = new DynamicList<Integer>();
		List<Integer> sr = new DynamicList<Integer>();
		Set<Integer> removedNodes = new DynamicSet<Integer>();

		while (removedNodes.size() < network.nodes()) {

			// Find sink nodes
			int sinkNodeIndex = findFirstByType(network, removedNodes, NodeType.SINK);
			while (sinkNodeIndex != -1) {
				sr.add(sinkNodeIndex);
				removedNodes.add(sinkNodeIndex);
				sinkNodeIndex = findFirstByType(network, removedNodes, NodeType.SINK);
			}

			// Find source nodes
			int sourceNodeIndex = findFirstByType(network, removedNodes, NodeType.SOURCE);
			while (sourceNodeIndex != -1) {
				sl.add(sourceNodeIndex);
				removedNodes.add(sourceNodeIndex);
				sourceNodeIndex = findFirstByType(network, removedNodes, NodeType.SOURCE);
			}

			if (removedNodes.size() < network.nodes()) {
				// Find maximal out-in degree difference
				int w = findMaximalDifference(network, removedNodes);
				removedNodes.add(w);
				sl.add(w);
			}
		}

		// Combine sl and sr
		List<Integer> nodesOrder = new DynamicList<Integer>();
		for ( int i = 0 ; i < sl.size() ; i++ )
			nodesOrder.add(sl.get(i));
		for ( int i = sr.size()-1 ; i >= 0 ; i-- )
			nodesOrder.add(sr.get(i));

		// Build acyclic graph reversing links
		AttributeNetwork acyclicNetwork = new AttributeNetwork(network);
		LinkFilter filter = new LinkFilter(acyclicNetwork);
		for ( int sourceIndex = 0 ; sourceIndex < network.nodes() ; sourceIndex++ ) {

			filter.removeLink(sourceIndex, sourceIndex);
			int[] targetIndices = network.outLinks(sourceIndex);
			if (targetIndices != null) {
				for ( int targetIndex : targetIndices ) {

					if (nodesOrder.get(sourceIndex) > nodesOrder.get(targetIndex)) {
						filter.removeLink(sourceIndex, targetIndex);
						if (!acyclicNetwork.contains(targetIndex, sourceIndex))
							acyclicNetwork.add(targetIndex, sourceIndex);
					}
				}
			}
		}

		acyclicNetwork = new FilteredNetwork(acyclicNetwork, filter);

		return acyclicNetwork;
	}

	private static enum NodeType { SINK, SOURCE };

	private int findFirstByType(AttributeNetwork network,
			Set<Integer> removedNodes, NodeType nodeType) 
	{
		for ( int nodeIndex = 0 ; nodeIndex < network.nodes() ; nodeIndex++ ) {
			if (!removedNodes.contains(nodeIndex)) {
				if ((nodeType == NodeType.SINK &&
						actualDegree(nodeIndex, network.outLinks(nodeIndex), removedNodes) == 0)
				|| (nodeType == NodeType.SOURCE &&
						actualDegree(nodeIndex, network.inLinks(nodeIndex), removedNodes) == 0))
					return nodeIndex;
			}
		}
		
		return -1;
	}

	private int findMaximalDifference(AttributeNetwork network, Set<Integer> removedNodes) 
	{
		int index = -1;
		double maxValue = -Double.MAX_VALUE;

		for ( int nodeIndex = 0 ; nodeIndex < network.nodes() ; nodeIndex++ ) {
			if (!removedNodes.contains(nodeIndex)) {
				int currentValue = 
						actualDegree(nodeIndex, network.outLinks(nodeIndex), removedNodes)
						- actualDegree(nodeIndex, network.inLinks(nodeIndex), removedNodes);
				if (currentValue > maxValue) {
					maxValue = currentValue;
					index = nodeIndex;
				}
			}
		}

		return index;
	}

	private int actualDegree(int nodeIndex, int[] links, Set<Integer> removedNodes) 
	{
		int degree = 0;
		if (links != null) {
			for (int node : links) {
				if (!removedNodes.contains(node))
					degree++;
			}
		}
		
		return degree;
	}

	// Step 2: Layer Assignment
	// ------------------------
	
	private Dictionary<Integer, Integer>[] layerAssignmentByLongestPath (Network acyclicNetwork) 
	{
		int[] nodeToLayer = new int[acyclicNetwork.nodes()];
		Set<Integer> U = new DynamicSet<Integer>();
		Set<Integer> Z = new DynamicSet<Integer>();
		int currentLayer = 0;

		// Compute layer assignment
		while (U.size() < acyclicNetwork.nodes()) {
			int vertex = -1;
			boolean vertexSelected = false;
			for ( int i = 0 ; i < acyclicNetwork.nodes() && !vertexSelected ; i++ ) {
				if (!U.contains(i)) {
					vertexSelected = true;
					int[] outNodes = acyclicNetwork.outLinks(i);
					if (outNodes != null) {
						for (int out : outNodes) {
							if (!Z.contains(out))
								vertexSelected = false;
						}
					}
					if (vertexSelected) {
						vertex = i;
						vertexSelected = true;
					}
				}
			}

			if (vertex != -1) {
				nodeToLayer[vertex] = currentLayer;
				U.add(vertex);
			} else {
				currentLayer++;
				Iterator<Integer> iter = U.iterator();
				while (iter.hasNext()) {
					Z.add(iter.next());
				}
			}

		}

		// Set structure
		int numLayers = currentLayer + 1;

		Dictionary<Integer, Integer>[] assignment = new Dictionary[numLayers];
		for ( int layer = 0 ; layer < numLayers ; layer++ )
			assignment[layer] = new DynamicDictionary<Integer, Integer>();

		int[] nodesAssignedToLayer = new int[numLayers];
		for ( int node = 0; node < nodeToLayer.length ; node++ ) {
			int layer = nodeToLayer[node];
			assignment[layer].set(node, nodesAssignedToLayer[layer]);
			nodesAssignedToLayer[layer] += 1;
		}

		return assignment;
	}

	// Step 3: Vertex ordering
	// -----------------------
	
	private void reduceCrossings (Network network) 
	{
		// Get links between layers
		int[][][] linksPerLayer = new int[layerAssignment.length - 1][][];
		for ( int layer = 0 ; layer < layerAssignment.length - 1 ; layer++ ) {
			List<int[]> links = new DynamicList<int[]>();
			Iterator<Integer> iter1 = layerAssignment[layer].iterator();
			while (iter1.hasNext()) {
				int u = iter1.next();

				Iterator<Integer> iter2 = layerAssignment[layer + 1].iterator();
				while (iter2.hasNext()) {
					int w = iter2.next();
					if (network.contains(u, w) || network.contains(w, u)) {
						links.add(new int[] { u, w });
					}
				}
			}

			linksPerLayer[layer] = new int[links.size()][2];
			for ( int linkIndex = 0 ; linkIndex < links.size() ; linkIndex++)
				linksPerLayer[layer][linkIndex] = links.get(linkIndex);
		}

		for ( int layer = layerAssignment.length - 2 ; layer >= 0 ; layer-- ) {
			int currentCrosses = countCrosses(network, layer, linksPerLayer);
			boolean restartLayer;
			do {
				restartLayer = false;
				Iterator<Integer> iter1 = layerAssignment[layer].iterator();
				Iterator<Integer> iter2 = layerAssignment[layer].iterator();
				while (iter1.hasNext() && !restartLayer) {
					
					int node1 = iter1.next();
					while (iter2.hasNext() && !restartLayer) {
						
						int node2 = iter2.next();

						// Swap nodes and check crosses
						swapNodePositions(node1, node2, layer);
						int nextCrosses = countCrosses(network, layer, linksPerLayer);
						if (nextCrosses < currentCrosses) {
							currentCrosses = nextCrosses;
							restartLayer = true;
						} else {
							// Revert
							swapNodePositions(node1, node2, layer);
						}
					}					
				}
			} while (restartLayer);
		}
	}

	private void swapNodePositions (int node1, int node2, int layer) 
	{
		int temp = layerAssignment[layer].get(node1);
		layerAssignment[layer].set(node1, layerAssignment[layer].get(node2));
		layerAssignment[layer].set(node2, temp);
	}
	
	private int countCrosses(Network network, int layer, int[][][] linksPerLayer) 
	{
		int crosses = 0;
		for (int i = 0; i < linksPerLayer[layer].length; i++) {
			
			for (int j = i + 1; j < linksPerLayer[layer].length; j++) {
				
				int[] linkI = {
						layerAssignment[layer].get(linksPerLayer[layer][i][0]),
						layerAssignment[layer + 1].get(linksPerLayer[layer][i][1]) };
				int[] linkJ = {
						layerAssignment[layer].get(linksPerLayer[layer][j][0]),
						layerAssignment[layer + 1].get(linksPerLayer[layer][j][1]) };
				
				if ((linkI[0] > linkJ[0] && linkI[1] < linkJ[1])
					|| (linkI[0] < linkJ[0] && linkI[1] > linkJ[1])) {
					crosses++;
				}
			}			
		}

		return crosses;
	}
}
