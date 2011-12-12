package noesis.algorithms.paths;


import ikor.collection.Visitor;
import ikor.collection.util.Pair;

import noesis.Network;
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
		bfs.setLinkIndexVisitor(new IndexVisitor(predecessor));
		bfs.traverse(origin);
	}
	
	// Ancillary visitor class

	class IndexVisitor implements Visitor<Pair<Integer,Integer>>
	{
		private int[] predecessor;
		
		public IndexVisitor (int[] predecessor)
		{
			this.predecessor = predecessor;
		}
		
		@Override
		public void visit(Pair<Integer,Integer> link) 
		{
			int source = link.first();
			int destination = link.second();
			
			if (predecessor[destination]==-1)
				predecessor[destination] = source;
		}
	}
}
