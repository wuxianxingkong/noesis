package noesis.algorithms.paths;

import ikor.collection.Evaluator;
import noesis.Network;

public abstract class SingleSourceShortestPathFinder<V, E> extends SingleSourcePathFinder<V, E> 
{
	protected Evaluator<E> linkEvaluator;
	
	protected double[] distance;

	
	public SingleSourceShortestPathFinder(Network net, int origin, Evaluator<E> linkEvaluator) 
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