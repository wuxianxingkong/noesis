package noesis.algorithms.paths;

import noesis.Network;
import noesis.algorithms.LinkVisitor;
import noesis.algorithms.traversal.NetworkDFS;

public class DepthFirstPathFinder<V, E> extends PredecessorPathFinder<V, E> implements PathFinder<V, E> 
{
	public DepthFirstPathFinder (Network<V,E> net, int origin)
	{
		super(net,origin);
	}	
	
	@Override
	public void run() 
	{
		NetworkDFS<V,E> dfs;
		
		// Initialization
		
		predecessor = new int[network.size()];
		
		for (int i=0; i<predecessor.length; i++)
			predecessor[i] = -1;		
		
		// DFS
		
		dfs = new NetworkDFS(network);
		dfs.setLinkVisitor(new DFSVisitor(predecessor));
		dfs.traverse(origin);
	}
	
	// Ancillary visitor class

	class DFSVisitor extends LinkVisitor
	{
		private int[] predecessor;
		
		public DFSVisitor (int[] predecessor)
		{
			this.predecessor = predecessor;
		}
		
		@Override
		public void visit (int source, int destination) 
		{
			if (predecessor[destination]==-1)
				predecessor[destination] = source;
		}
	}
}
