package noesis.algorithms.paths;

import noesis.Network;
import ikor.collection.Evaluator;
import ikor.collection.IndexedPriorityQueue;
import ikor.collection.Indexer;
import ikor.collection.PriorityQueue;

public class AStarPathFinder<V, E> extends PredecessorPathFinder<V,E> implements PathFinder<V, E>
{
	private Evaluator<E> linkEvaluator;
	private Evaluator<V> heuristicEvaluator;
	
	private double[] g;
	private double[] h;
	
	public AStarPathFinder (Network<V,E> net, int origin, Evaluator<E> linkEvaluator, Evaluator<V> heuristicEvaluator)
	{
		super(net,origin);

		this.linkEvaluator = linkEvaluator;
		this.heuristicEvaluator = heuristicEvaluator;
	}
	
	/* (non-Javadoc)
	 * @see noesis.algorithms.paths.PathFinder#run()
	 */	
	@Override
	public void run()
	{
		PriorityQueue<Integer> open;
		boolean[]              closed;
		double                 linkValue;
		double                 nodeValue;
		int                    size = network.size();
		
		// Initialization
		
		predecessor = new int[size];
		g = new double[size];
		h = new double[size];
		
		open = createPriorityQueue(size);
		closed = new boolean[network.size()];
		
		for (int i=0; i<size; i++) {
			predecessor[i] = -1;
			closed[i] = false;
			g[i] = Double.POSITIVE_INFINITY;
			h[i] = Double.POSITIVE_INFINITY;
		}
		
		g[origin] = 0;
		h[origin] = heuristicEvaluator.evaluate(network.get(origin));
		open.add(origin);
	
		// Search
		
        while (open.size()>0) {
            
        	int vertex = open.get();
        	
        	closed[vertex] = true;
        	
        	int[] links = network.outLinks(vertex);
        	
        	if (links!=null)
        		for (int j=0; j<links.length; j++) {
        			
        			if (!closed[links[j]]) {
        				
        				linkValue = linkEvaluator.evaluate( network.get(vertex, links[j]) );
        				nodeValue = g[vertex] + linkValue;
        				
        				if (nodeValue < g[links[j]]) {
        					g[links[j]] = nodeValue;
        					h[links[j]] = heuristicEvaluator.evaluate(network.get(links[j]));
        					predecessor[links[j]] = vertex;
        					open.add(links[j]);
        				}        				
        			}
        		}
        }		
	}
	
	
	private PriorityQueue<Integer> createPriorityQueue (int size)
	{
		Indexer<Integer>             nodeIndexer;
		Evaluator<Integer>           nodeEvaluator;
		PriorityQueue<Integer>       queue;

		nodeIndexer = new AStarNodeIndexer();
		nodeEvaluator = new AStarNodeEvaluator(g,h);
		queue = new IndexedPriorityQueue<Integer>(size,nodeEvaluator,nodeIndexer);
		
		return queue;
	}
	

	// Distances
	
	public final double[] cost ()
	{
		return g;
	}
	
	public final double cost (int node)
	{
		return g[node];
	}
	
	// Ancillary classes
	
	private class AStarNodeEvaluator implements Evaluator<Integer>
	{
		double[] g;
		double[] h;
		
		public AStarNodeEvaluator (double[] g, double[] h)
		{
			this.g = g;
			this.h = h;
		}
		
		@Override
		public double evaluate(Integer object) 
		{
			return g[object] + h[object];
		}
	}
	
	private class AStarNodeIndexer implements Indexer<Integer>
	{
		@Override
		public int index(Integer object) 
		{
			return object;
		}
		
	}	

}
