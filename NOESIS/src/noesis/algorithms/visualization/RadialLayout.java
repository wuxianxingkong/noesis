package noesis.algorithms.visualization;

import ikor.collection.DynamicList;
import ikor.collection.DynamicSet;
import ikor.collection.List;
import ikor.collection.Set;
import noesis.Attribute;
import noesis.AttributeNetwork;
import noesis.Network;
import noesis.algorithms.LinkVisitor;
import noesis.algorithms.traversal.ConnectedComponents;
import noesis.algorithms.traversal.NetworkBFS;
import noesis.analysis.NodeScore;
import noesis.analysis.structure.AveragePathLength;

/**
 * Radial layout algorithm
 * with 'fake' root node for multiple component networks.
 * 
 * References: 
 * - Graham J. Wills (1999):
 *   "NicheWorks — Interactive visualization of very large graphs"
 *   Journal of Computational and Graphical Statistics 8(2):190-212, 1999
 *   DOI 10.1080/10618600.1999.10474810
 * - Graham J. Wills (1999):
 *   "NicheWorks — Interactive visualization of very large graphs"
 *   5th International Symposium on Graph Drawing, GD '97, 
 *   Rome, Italy, September 18–20, 1997, pp. 403-414
 *   DOI 10.1007/3-540-63938-1_85
 *   
 * @author Victor Martinez (victormg@acm.org)
 */

public class RadialLayout extends NetworkLayout 
{

	/**
	 * Component's root selection strategy
	 */
	private RootSelectionStrategy rootSelector;
	
	/**
	 * Constructor with default strategy
	 */
	public RadialLayout () 
	{
		this.rootSelector = new AveragePathLengthSelectionStrategy();
	}
	
	/**
	 * Constructor with custom strategy
	 * @param rootSelector Root selection strategy
	 */
	public RadialLayout (RootSelectionStrategy rootSelector) 
	{
		this.rootSelector = rootSelector;
	}

	/**
	 * Layout method
	 */
	@Override
	public void layout (AttributeNetwork network, Attribute<Double> x, Attribute<Double> y) 
	{
		if (network.size() == 0)
			return;
		
		// Create undirected version of the network
		
		AttributeNetwork undirectedNetwork = createUndirectedNetwork(network);

		// Compute components and get root node for each one
		
		ConnectedComponents components = new ConnectedComponents(undirectedNetwork);
		components.compute();

		List<Integer>[] componentList = new List[components.components()-1];
		
		for (int component=0; component < componentList.length; component++)
			componentList[component] = new DynamicList<Integer>();
		
		for (int node=0; node < network.nodes(); node++)
			componentList[components.component(node) - 1].add(node);
		
		int rootNode = setGlobalRootNode(undirectedNetwork, componentList);
		
		// Compute levels, weights, and node angles
		
		Set<Integer>[] children = new Set[undirectedNetwork.size()];
		int[] predecessor = new int[undirectedNetwork.size()];
		boolean[] visited = new boolean[undirectedNetwork.size()];
		
		for (int i=0; i<predecessor.length; i++)
			predecessor[i] = -1;
		
		NetworkBFS<Integer,Integer> BFS = new NetworkBFS<Integer,Integer>(undirectedNetwork);
		BFSLinkVisitor visitor = new BFSLinkVisitor(predecessor, children, rootNode, visited);
		BFS.setLinkVisitor(visitor);
		BFS.traverse(rootNode);
		
		List<List<Integer>> levels = getLevelNodes(rootNode, children);
		double[] weights = computeNodeWeights(levels, predecessor);
		NodeInfo[] nodeInfo = computePositionInfo(rootNode, weights, predecessor, children);

		// Compute final positions
		
		double correctionFactor = (0.5-MARGIN)/0.5;
		
		for (int node = 0; node < network.size(); node++) {
			double angle = nodeInfo[node].initialAngle + (nodeInfo[node].finalAngle - nodeInfo[node].initialAngle) / 2.0;
			double amplitude = correctionFactor * (0.5 * nodeInfo[node].level / Math.max(levels.size()-1, 1.0));
			x.set(node, 0.5 + amplitude * Math.cos(angle * 2 * Math.PI));
			y.set(node, 0.5 + amplitude * Math.sin(angle * 2 * Math.PI));
		}
	}

	
	// Compute global root node
	
	private int setGlobalRootNode (AttributeNetwork undirectedNetwork, List<Integer>[] componentList) 
	{
		int[] rootNodes = new int[componentList.length];
		
		for ( int component = 0 ; component < componentList.length ; component++ )
			rootNodes[component] = rootSelector.selectRootNode(undirectedNetwork, componentList[component]);
		
		// Set 'false' global root node when more than one component is present
		
		int rootNode = rootNodes[0];
		
		if (componentList.length > 1) {
			for (int root: rootNodes)
				undirectedNetwork.add2(undirectedNetwork.size() - 1, root);
			rootNode = undirectedNetwork.size() - 1;
		}
		
		return rootNode;
	}
	
	
	// Compute initial and final angle information
	
	private NodeInfo[] computePositionInfo (int rootNode, double[] weight, int[] predecessor, Set<Integer>[] children) 
	{
		NodeInfo[] nodeInfo = new NodeInfo[predecessor.length];
		int currentLevel = 0;
		Set<Integer> nodesCurrentLevel = new DynamicSet<Integer>();
		nodesCurrentLevel.add(rootNode);

		while (nodesCurrentLevel.size() > 0) {
			
			Set<Integer> nextLevelNodes = new DynamicSet<Integer>();
			
			for (int node : nodesCurrentLevel) {
				
				double currentAngle = 0;
				double finalAngle = 1;

				if (predecessor[node] != -1) {
					currentAngle = nodeInfo[predecessor[node]].childrenAngle;
					double weightRatio = (weight[node] + 1.0) / weight[predecessor[node]];
					double predecessorAngle = nodeInfo[predecessor[node]].finalAngle - nodeInfo[predecessor[node]].initialAngle;
					finalAngle = currentAngle + predecessorAngle * weightRatio;
					nodeInfo[predecessor[node]].childrenAngle = finalAngle;
				}

				nodeInfo[node] = new NodeInfo(currentLevel, currentAngle, finalAngle, currentAngle);

				if (children[node] != null)
					for (int child : children[node])
						nextLevelNodes.add(child);
				
				nodesCurrentLevel = nextLevelNodes;
			}
			
			currentLevel++;
		}

		return nodeInfo;
	}
	
	
	// Get nodes in each level
	
	private List<List<Integer>> getLevelNodes (int rootNode, Set<Integer>[] children) 
	{
		List<List<Integer>> nodesPerLevel = new DynamicList<List<Integer>>();
		List<Integer> currentNodes = new DynamicList<Integer>();
		currentNodes.add(rootNode);
	
		while (currentNodes.size() > 0) {
			
			nodesPerLevel.add(currentNodes);
			List<Integer> nextNodes = new DynamicList<Integer>();
			
			for (int n: currentNodes) {
				if (children[n] != null)
					for (int c: children[n])
						nextNodes.add(c);
			}
			
			currentNodes = nextNodes;
		}
		
		return nodesPerLevel;
	}

	
	// Propagate node weights
	
	private double[] computeNodeWeights (List<List<Integer>> levels, int[] predecessor) 
	{
		double[] weights = new double[predecessor.length];
	
		for (int level=levels.size()-1; level >= 1 ; level--)
			for (int node: levels.get(level))
				weights[predecessor[node]] = weights[predecessor[node]] + weights[node] + 1.0;
		
		return weights;
	}

	
	// Force undirected network
	
	private AttributeNetwork createUndirectedNetwork (AttributeNetwork network) 
	{
		AttributeNetwork undirectedNetwork = new AttributeNetwork();
		undirectedNetwork.setSize(network.size() + 1);
		
		for (int node = 0; node < network.size(); node++) {
			for (int link = 0; link < network.outDegree(node); link++) {
				int target = network.outLink(node, link);
				if (!undirectedNetwork.contains(node, target))
					undirectedNetwork.add2(node, target);
			}
		}

		return undirectedNetwork;
	}
	
	
	
	/**
	 * Root selection strategy interface (for connected components in undirected networks).
	 */
	
	public interface RootSelectionStrategy 
	{
		public int selectRootNode (Network undirectedNetwork, List<Integer> componentNodes);
	}

	
	/**
	 * Default root selection strategy (for connected components in undirected networks):
	 * Select node with lowest ASP to all the other nodes.
	 */
	public class AveragePathLengthSelectionStrategy implements RootSelectionStrategy 
	{
		@Override
		public int selectRootNode (Network undirectedNetwork, List<Integer> componentNodes) 
		{
			AveragePathLength path = new AveragePathLength(undirectedNetwork);
			
			path.compute();
			
			NodeScore pathLength = path.getResult();
			int minIndex = -1;
			double pLength = Double.MAX_VALUE;
			
			for (int node: componentNodes) {
				if (pathLength.get(node) < pLength) {
					pLength = pathLength.get(node);
					minIndex = node;
				}
			}
			
			return minIndex;
		}
	}
	
	
	// Internal node information
	
	private class NodeInfo 
	{
		public int level;
		private double initialAngle;
		private double finalAngle;
		private double childrenAngle;

		public NodeInfo (int level, double initialAngle, double finalAngle, double childrenAngle) 
		{
			this.level = level;
			this.initialAngle = initialAngle;
			this.finalAngle = finalAngle;
			this.childrenAngle = childrenAngle;
		}
	}

	
	// Link visitor class
	
	class BFSLinkVisitor extends LinkVisitor 
	{
		private Set<Integer>[] children;
		private int[] predecessor;
		private boolean[] visited;

		public BFSLinkVisitor (int[] predecessor, Set<Integer>[] children, int rootNode, boolean[] visited) 
		{
			this.predecessor = predecessor;
			this.children = children;
			this.visited = visited;
			visited[rootNode] = true;
		}

		@Override
		public void visit (int source, int destination) 
		{
			if (!visited[destination]) {
				visited[destination] = true;
				predecessor[destination] = source;
				if (children[source] == null)
					children[source] = new DynamicSet<Integer>();
				children[source].add(destination);
			}
		}
	}
}