package noesis.algorithms.paths;

import ikor.collection.Visitor;
import ikor.collection.util.Pair;

import noesis.ArrayNetwork;
import noesis.Network;
import noesis.algorithms.traversal.NetworkDFS;

public class DepthFirstPathFinder<V, E> implements PathFinder<V, E> 
{
	private Network<V,E> network;
	private int          origin;
		
	private int[]        predecessor;
	
	// Constructor
	
	public DepthFirstPathFinder (Network<V,E> net, int origin)
	{
		this.network       = net;
		this.origin        = origin;
	}	
	
	@Override
	public Network<V, E> network() 
	{
		return network;
	}

	@Override
	public void run() 
	{
		NetworkDFS<V,E> dfs;
		
		// Initialization
		
		predecessor = new int[network.size()];
		
		for (int i=0; i<network.size(); i++) {
			predecessor[i] = -1;
		}
		
		// DFS
		
		dfs = new NetworkDFS(network);
		dfs.setLinkIndexVisitor(new IndexVisitor(predecessor));
		dfs.traverse(origin);
	}

	@Override
	public Network<V, E> paths() 
	{
		Network<V,E> paths = new ArrayNetwork<V,E>();
		
		for (int i=0; i<network.size(); i++)
			paths.add ( network.get(i) );
		
		for (int i=0; i<network.size(); i++)
			if (predecessor[i]!=-1) {
				E link = network.get(predecessor[i],i);
				paths.add(predecessor[i], i, link);
			}
		
		return paths;		
	}

	@Override
	public int[] pathTo(int destination) {
		int[] path = null;
		int   position;
		int   length = pathLength(destination);
		
		if (length>0) {
		
			path = new int[length];
			position = length-1;
		
			path[position] = destination;
		
			while (path[position]!=origin) { // == position >= 0
				position--;
				path[position] = predecessor[path[position+1]];
			} 
		}
		
		return path;
	}
	
	private int pathLength (int destination)
	{
		int length = 1;
		int node = destination;
		
		while ((node!=origin) && (predecessor[node]!=-1)) {
			length++;
			node = predecessor[node];
		}
		
		if (node==origin) 
			return length;
		else
			return 0;
	}
	
	
	// Ancillary class
	
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
