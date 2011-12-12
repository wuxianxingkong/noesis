package noesis.algorithms.paths;

import noesis.ArrayNetwork;
import noesis.Network;

public abstract class PredecessorPathFinder<V, E> implements PathFinder<V, E>
{
	protected Network<V,E> network;
	protected int          origin;
	protected int[]        predecessor;

	public PredecessorPathFinder(Network<V,E> net, int origin)
	{
		this.network     = net;
		this.origin      = origin;
	}	

	@Override
	public final Network<V, E> network() 
	{
		return network;
	}

	/* (non-Javadoc)
	 * @see noesis.algorithms.paths.PathFinder#paths()
	 */	
	@Override
	public final Network<V, E> paths() 
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

	/* (non-Javadoc)
	 * @see noesis.algorithms.paths.PathFinder#pathTo(int)
	 */	
	@Override
	public final int[] pathTo(int destination) 
	{
		int[] path = null;
		int   position;
		int   length = pathLengthTo(destination);
		
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

	public final int pathLengthTo(int destination) 
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

}