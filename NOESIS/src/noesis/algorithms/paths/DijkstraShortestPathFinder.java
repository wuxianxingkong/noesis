package noesis.algorithms.paths;

import ikor.collection.Evaluator;
import ikor.collection.EvaluatorComparator;
import ikor.collection.IndexedPriorityQueue;
import ikor.collection.Indexer;
import ikor.collection.PriorityQueue;

import noesis.ArrayNetwork;
import noesis.Network;

public class DijkstraShortestPathFinder<V,E> implements PathFinder<V, E>
{
	private Network<V,E> network;
	private int          origin;
	private Evaluator<E> linkEvaluator;
	
	private int[]        predecessor;
	private double[]     distance;
	
	public DijkstraShortestPathFinder (Network<V,E> net, int origin, Evaluator<E> linkEvaluator)
	{
		this.network       = net;
		this.origin        = origin;
		this.linkEvaluator = linkEvaluator;
	}
	
	/* (non-Javadoc)
	 * @see noesis.algorithms.paths.PathFinder#network()
	 */
	@Override
	public Network<V,E> network ()
	{
		return network;
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
		
		predecessor     = new int[size];
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
		EvaluatorComparator<Integer> nodeComparator;
		PriorityQueue<Integer>       queue;

		nodeIndexer = new DijkstraNodeIndexer();
		nodeEvaluator = new DijkstraNodeEvaluator(distance);
		nodeComparator = new EvaluatorComparator(nodeEvaluator);
		queue = new IndexedPriorityQueue<Integer>(size,nodeComparator,nodeIndexer);
		
		return queue;
	}
	
	// Shortest path tree
	
	/* (non-Javadoc)
	 * @see noesis.algorithms.paths.PathFinder#paths()
	 */
	@Override
	public final Network<V,E> paths ()
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
	public final int[] pathTo (int destination)
	{
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

