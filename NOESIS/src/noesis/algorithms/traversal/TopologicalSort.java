package noesis.algorithms.traversal;

import noesis.Network;
import noesis.algorithms.NodeVisitor;

// O(n+m) topological sort

public class TopologicalSort
{
	private Network network;
	private int index[];
	
	public TopologicalSort (Network network)
	{
		this.network = network;
		this.index   = new int[network.size()];
	}
	
	public final Network getNetwork ()
	{
		return this.network;
	}
	
	public final int[] sort ()
	{
		NetworkDFS dfs = new NetworkDFS(network);
		
		// DFS
	
		dfs.setNodePostVisitor(new TopologicalVisitor(this));
		dfs.traverse();		
		
		return this.index;
	}
	
	public final int[] nodes ()
	{
		int[] ordered = new int[network.size()];
		
		for (int i=0; i<network.size(); i++)
			ordered[index[i]] = i;
		
		return ordered;
	}

	// Visitor
	
	private class TopologicalVisitor extends NodeVisitor
	{
		private TopologicalSort sort;
		private int current;
		
		public TopologicalVisitor (TopologicalSort sort)
		{
			this.sort = sort;
			this.current = network.size()-1;
		}

		@Override
		public void visit(int node) 
		{
			sort.index[node] = current;
			current--;
		}	
	}
}
