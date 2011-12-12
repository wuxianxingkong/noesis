package noesis.algorithms.paths;

import ikor.collection.Evaluator;
import ikor.collection.IndexedPriorityQueue;
import ikor.collection.Indexer;
import ikor.collection.PriorityQueue;

import noesis.Network;

public class DijkstraShortestPathFinder<V,E> extends PredecessorPathFinder<V,E> implements PathFinder<V, E>
{
	private Evaluator<E> linkEvaluator;
	
	private double[]     distance;
	
	public DijkstraShortestPathFinder (Network<V,E> net, int origin, Evaluator<E> linkEvaluator)
	{
		super(net,origin);

		this.linkEvaluator = linkEvaluator;
	}

	
	/* (non-Javadoc)
	 * @see noesis.algorithms.paths.PathFinder#run()
	 */
	@Override
	public void run()
	{
		PriorityQueue<Integer> queue; 
		double                 linkValue;
		int                    size = network.size();
		
		// Initialization
		
		predecessor = new int[size];
		distance = new double[size];
		queue    = createPriorityQueue(size);
		
		for (int i=0; i<size; i++) {
			predecessor[i] = -1;
			distance[i] = Double.POSITIVE_INFINITY;
			queue.add(i);
		}
		
		updateDistance(queue, origin, 0);
	
		// Greedy algorithm
		
        while (queue.size()>0) {
            
        	int vertex = queue.get();
        	
        	int[] links = network.outLinks(vertex);
        	
        	if (links!=null)
        		for (int j=0; j<links.length; j++) {
        				
       				linkValue = linkEvaluator.evaluate( network.get(vertex, links[j]) );
        				
       				if (distance[links[j]] > distance[vertex] + linkValue) {
       					predecessor[links[j]] = vertex;
       					updateDistance(queue, links[j], distance[vertex] + linkValue);
       				}
        		}
        }		
	}
	
	
	private void updateDistance (PriorityQueue queue, int node, double newDistance)
	{
		queue.remove(node);
		distance[node] = newDistance;
		queue.add(node);
	}
	
	private PriorityQueue<Integer> createPriorityQueue (int size)
	{
		Indexer<Integer>             nodeIndexer;
		Evaluator<Integer>           nodeEvaluator;
		PriorityQueue<Integer>       queue;

		nodeIndexer = new DijkstraNodeIndexer();
		nodeEvaluator = new DijkstraNodeEvaluator(distance);
		queue = new IndexedPriorityQueue<Integer>(size,nodeEvaluator,nodeIndexer);
		
		return queue;
	}
	

	// Distances
	
	public final double[] distance ()
	{
		return distance;
	}
	
	public final double distance (int node)
	{
		return distance[node];
	}
		
	
	
	// Ancillary classes
	
	private class DijkstraNodeEvaluator implements Evaluator<Integer>
	{
		double[] distance;
		
		public DijkstraNodeEvaluator (double[] distance)
		{
			this.distance = distance;
		}
		
		@Override
		public double evaluate(Integer object) 
		{
			return distance[object];
		}
	}
	
	private class DijkstraNodeIndexer implements Indexer<Integer>
	{
		@Override
		public int index(Integer object) 
		{
			return object;
		}
		
	}
}

