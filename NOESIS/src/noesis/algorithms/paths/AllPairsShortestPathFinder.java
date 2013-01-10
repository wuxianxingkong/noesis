package noesis.algorithms.paths;

import noesis.Network;
import noesis.LinkEvaluator;

public abstract class AllPairsShortestPathFinder<V, E> implements PathFinder<V, E> 
{
	protected Network<V,E>  network;
	protected LinkEvaluator linkEvaluator;
	
	protected double[][] distance;

	
	public AllPairsShortestPathFinder (Network<V,E> net, LinkEvaluator linkEvaluator) 
	{
		this.network = net;		
		this.linkEvaluator = linkEvaluator;
	}

	@Override
	public final Network<V, E> network() 
	{
		return network;
	}
	
	public final double[][] distance() 
	{
		return distance;
	}

	public final double distance(int source, int target) 
	{
		return distance[source][target];
	}

	
	public boolean negativeCycleDetected ()
	{
		return false;
	}
	

	public int[] path(int source, int target) 
	{
		// TODO Auto-generated method stub
		return null;
	}

	public int predecessor(int source, int target) 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	
}