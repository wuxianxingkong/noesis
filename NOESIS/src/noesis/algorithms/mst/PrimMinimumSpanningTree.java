package noesis.algorithms.mst;

import ikor.collection.Evaluator;
import ikor.collection.EvaluatorComparator;
import ikor.collection.Indexer;
import ikor.collection.PriorityQueue;
import ikor.collection.IndexedPriorityQueue;

import noesis.ArrayNetwork;
import noesis.Network;

/**
 * Minimum Spanning Trees (for undirected networks): Prim's algorithm. 
 *  
 * Efficient implementation using an indexed priority queue, O(m log n).
 *  
 * @author Fernando Berzal
 */
public class PrimMinimumSpanningTree<V,E>
{
	private Network<V,E> network;
	private Evaluator<E> linkEvaluator;

	private double       weight;
	private int[]        parent;
	
	
	public PrimMinimumSpanningTree (Network<V,E> net, Evaluator<E> linkEvaluator)
	{
		this.network       = net;
		this.linkEvaluator = linkEvaluator;
	}
	
	
	public void run()
	{
		PriorityQueue<Integer> queue;
		boolean[]              visited;  
		double[]               cost;		
		double                 linkValue;
		int                    size = network.size();
		
		// Initialization
		
		parent = new int[size];
		cost = new double[size];
		visited = new boolean[size];
		queue = createPriorityQueue(size,cost);
		
		for (int i=0; i<size; i++) {
			parent[i] = -1;
			cost[i] = Double.POSITIVE_INFINITY;
			queue.add(i);
		}
		
		updateCost(queue, cost, 0, 0);
	
		// Greedy algorithm
		
        while (queue.size()>0) {
            
        	int vertex = queue.get();
        	
        	visited[vertex] = true;
        	
        	int[] links = network.outLinks(vertex);
        	
        	if (links!=null)
        		for (int j=0; j<links.length; j++) {
        			
        			if (!visited[links[j]]) {
        				
            			linkValue = linkEvaluator.evaluate( network.get(vertex, links[j]) );
        				
        				if (linkValue<cost[links[j]]) {
        					parent[links[j]] = vertex;
        					updateCost(queue, cost, links[j], linkValue);
        				}
        			}
        		}
        }		
		
		
		// Result
		
		this.weight = 0;	
		
		for (int i=0; i<network.size(); i++) {
			this.weight += cost[i];

			// Alternative (slower) implementation:
			//
			// if (parent[i]!=-1) {
			//	  E link = network.get(parent[i],i);
			//	  weight += linkEvaluator.evaluate(link);
			//}
		}
	}
	
	private void updateCost (PriorityQueue queue, double cost[], int node, double newCost)
	{
		queue.remove(node);
		
		cost[node] = newCost;

		queue.add(node);
	}
	
	private PriorityQueue<Integer> createPriorityQueue (int size, double cost[])
	{
		Indexer<Integer>             nodeIndexer;
		Evaluator<Integer>           nodeEvaluator;
		EvaluatorComparator<Integer> nodeComparator;
		PriorityQueue<Integer>       queue;

		nodeIndexer = new PrimNodeIndexer();
		nodeEvaluator = new PrimNodeEvaluator(cost);
		nodeComparator = new EvaluatorComparator(nodeEvaluator);
		queue = new IndexedPriorityQueue<Integer>(size,nodeComparator,nodeIndexer);
		
		return queue;
	}
	
	// Minimum spanning tree
	
	public Network<V,E> MST ()
	{
		ArrayNetwork<V,E> mst = new ArrayNetwork<V,E>();
		
		for (int i=0; i<network.size(); i++)
			mst.add ( network.get(i) );
		
		for (int i=0; i<network.size(); i++)
			if (parent[i]!=-1) {
				E link = network.get(parent[i],i);
				mst.add(parent[i], i, link);
			}
		
		return mst;
	}
	
	
	public int parent (int node)
	{
		return parent[node];
	}
	
	// Tree weight
	
	public double weight ()
	{
		return weight;
	}
	
	
	
	
	// Ancillary classes
	
	class PrimNodeEvaluator implements Evaluator<Integer>
	{
		double[] cost;
		
		public PrimNodeEvaluator (double[] cost)
		{
			this.cost = cost;
		}
		
		@Override
		public double evaluate(Integer object) 
		{
			return cost[object];
		}
	}
	
	class PrimNodeIndexer implements Indexer<Integer>
	{
		@Override
		public int index(Integer object) 
		{
			return object;
		}
		
	}
}
