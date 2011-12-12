package noesis.algorithms.paths;

import noesis.Network;
import noesis.algorithms.LinkVisitor;
import noesis.algorithms.traversal.NetworkBFS;


public class BreadthFirstPathFinder<V, E> extends PredecessorPathFinder<V, E> implements PathFinder<V, E> 
{
	public BreadthFirstPathFinder (Network<V,E> net, int origin)
	{
		super(net,origin);
	}	
	
	@Override
	public void run() 
	{
		NetworkBFS<V,E> bfs;
		
		// Initialization
		
		predecessor = new int[network.size()];
		
		for (int i=0; i<predecessor.length; i++)
			predecessor[i] = -1;		
		
		// DFS
		
		bfs = new NetworkBFS(network);
		bfs.setLinkVisitor(new BFSVisitor(predecessor));
		bfs.traverse(origin);
	}
	
	// Ancillary visitor class

	class BFSVisitor extends LinkVisitor
	{
		private int[] predecessor;
		
		public BFSVisitor (int[] predecessor)
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
