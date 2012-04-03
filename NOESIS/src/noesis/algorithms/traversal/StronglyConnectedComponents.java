package noesis.algorithms.traversal;

import noesis.*;
import noesis.algorithms.NodeVisitor;

// Kosaraju's two pass O(m+n) algorithm

public class StronglyConnectedComponents 
{
	private Network network;
	private int index[];
	private int components; 

	private int order[];
	
	public StronglyConnectedComponents (Network network)
	{
		this.network    = network;
		this.index      = new int[network.size()];
		this.components = 0;
	}
	
	public final Network getNetwork ()
	{
		return this.network;
	}
	
	/**
	 * Number of strongly connected components.
	 * @return The number of strongly connected components in the network
	 */
	
	public final int components ()
	{
		return components;
	}
	
	/**
	 * Component index.
	 * @return An integer indicating the strongly connected component for the specified node.
	 */
	
	public final int component (int node)
	{
		return index[node];
	}
	
	/**
	 * Component index.
	 * @return An array indicating the strongly connected component for each node.
	 */
	
	public final int[] componentIndex ()
	{
		return index;
	}
	
	/**
	 * Strongly-connected component sizes
	 * @return An array containing the number of nodes within each strongly connected component.
	 */
	
	public final int[] componentSizes ()
	{
		int[] sizes = new int[components];
		
		for (int i=0; i<network.size(); i++)
			sizes[ index[i]-1 ]++;
		
		return sizes;
	}
	
	
	private Network reverseNetwork (Network net)
	{
		Network reverse = new BasicNetwork();
		int[]   links;
		
		reverse.setSize(net.size());
		
		for (int i=0; i<net.size(); i++) {
			links = net.outLinks(i);
			
			if (links!=null)
				for (int j=0; j<links.length; j++)
			    	reverse.add( links[j], i);
		}
		
		return reverse;
	}
	
	public final void compute ()
	{
		// Topological sort on reverse network
		// (finishing time for each node)
		
		Network reverse = reverseNetwork(network);
		
		TopologicalSort ts = new TopologicalSort(reverse);
		
		ts.sort();
		
		order = ts.nodes();
		
		// DFS on original network
		// (in decreasing order of finishing time)
		
		NetworkDFS dfs = new NetworkDFS(network);
	
		dfs.setNodeVisitor(new StrongComponentVisitor(this));
		
		for (int i=0; i<network.size(); i++) {
			if (index[order[i]]==0) {
				components++;
				dfs.traverse(order[i]);
			}
		}
	}

	// Visitor
	
	private class StrongComponentVisitor extends NodeVisitor
	{
		private StronglyConnectedComponents scc;
		
		public StrongComponentVisitor (StronglyConnectedComponents scc)
		{
			this.scc = scc;
		}

		@Override
		public void visit(int node) 
		{
			scc.index[node] = scc.components;
		}	
	}

}
