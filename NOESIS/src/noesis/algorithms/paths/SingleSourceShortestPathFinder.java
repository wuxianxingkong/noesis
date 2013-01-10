package noesis.algorithms.paths;

import noesis.Network;
import noesis.LinkEvaluator;

public abstract class SingleSourceShortestPathFinder<V, E> extends SingleSourcePathFinder<V, E> 
{
	protected LinkEvaluator linkEvaluator;

	protected double[] distance;

	
	public SingleSourceShortestPathFinder(Network net, int origin, LinkEvaluator linkEvaluator) 
	{
		super(net, origin);
		
		this.linkEvaluator = linkEvaluator;
	}

	
	public final double[] distance() 
	{
		return distance;
	}

	public final double distance(int node) 
	{
		return distance[node];
	}

}