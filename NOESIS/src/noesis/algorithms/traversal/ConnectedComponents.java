package noesis.algorithms.traversal;

import noesis.Network;
import noesis.algorithms.NodeVisitor;

// O(n+m) connected components

public class ConnectedComponents 
{
	private Network network;
	private int index[];
	private int components; 
	
	public ConnectedComponents (Network network)
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
	 * Number of connected components.
	 * @return The number of connected components in the network
	 */
	
	public final int components ()
	{
		return components;
	}
	
	/**
	 * Component index.
	 * @return An array indicating the connected component for each node.
	 */
	
	public final int[] componentIndex ()
	{
		return index;
	}
	
	/**
	 * Component sizes
	 * @return An array containing the number of nodes within each connected component.
	 */
	
	public final int[] componentSizes ()
	{
		int[] sizes = new int[components];
		
		for (int i=0; i<network.size(); i++)
			sizes[ index[i]-1 ]++;
		
		return sizes;
	}
	
	public final void compute ()
	{
		NetworkDFS dfs = new NetworkDFS(network);
		
		// DFS
	
		dfs.setNodeVisitor(new ComponentVisitor(this));
		
		for (int i=0; i<network.size(); i++) {
			if (index[i]==0) {
				components++;
				dfs.traverse(i);
			}
		}
	}

	// Visitor
	
	private class ComponentVisitor extends NodeVisitor
	{
		private ConnectedComponents cc;
		
		public ComponentVisitor (ConnectedComponents cc)
		{
			this.cc = cc;
		}

		@Override
		public void visit(int node) 
		{
			cc.index[node] = cc.components;
		}	
	}

}
