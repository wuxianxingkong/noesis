package noesis.algorithms.mst;

import ikor.collection.DynamicPriorityQueue;
import ikor.collection.Evaluator;
import ikor.collection.EvaluatorComparator;
import ikor.collection.PriorityQueue;

import noesis.ArrayNetwork;
import noesis.Network;

/**
 * Minimum Spanning Trees (for undirected networks): Prim's algorithm. 
 *  
 * Efficient implementation using priority queue, O(m log n).
 *  
 * @author Fernando Berzal
 */
public class PrimMinimumSpanningTree<V,E>
{
	Network<V,E> network;
	Evaluator<E> linkEvaluator;
	Network<V,E> mst;
	double       weight;
	int[]        parent;
	double[]     cost;
	
	public PrimMinimumSpanningTree (Network<V,E> net, Evaluator<E> linkEvaluator)
	{
		this.network       = net;
		this.linkEvaluator = linkEvaluator;
	}
	
	public Network<V,E> run()
	{
		Evaluator<Integer>           nodeEvaluator;
		EvaluatorComparator<Integer> comparator;
		PriorityQueue<Integer>       queue;
		boolean[]                    visited;  
		double                       linkValue;

		// Initialization
		
		parent = new int[network.size()];
		cost = new double[network.size()];
		visited = new boolean[network.size()];

		nodeEvaluator = new PrimNodeEvaluator(cost);
		comparator = new EvaluatorComparator(nodeEvaluator);
		queue = new DynamicPriorityQueue<Integer>(comparator);
		
		for (int i=0; i<network.size(); i++) {
			parent[i] = -1;
			cost[i] = Double.POSITIVE_INFINITY;
			queue.add(i);
		}
		
		updateCost(queue, 0,0);
	
		// Greedy algorithm
		
        while (queue.size()>0) {
            
        	int vertex = queue.get();
        	
        	visited[vertex] = true;

        	// Check that source-destination link does not create a cycle
        	
        	int[] links = network.outLinks(vertex);
        	
        	if (links!=null)
        		for (int j=0; j<links.length; j++) {
        			
        			if (!visited[links[j]]) {
        				
        				linkValue = linkEvaluator.evaluate( network.get(vertex, links[j]) );
        				
        				if (linkValue<cost[links[j]]) {
        					parent[links[j]] = vertex;
        					updateCost(queue, links[j], linkValue);
        				}
        			}
        		}
        }		
		
		
		// Result
		
		this.mst = new ArrayNetwork<V,E>();
		this.weight = 0;	
		
		for (int i=0; i<network.size(); i++)
			mst.add ( network.get(i) );
		
		for (int i=0; i<network.size(); i++)
			if (parent[i]!=-1) {
				E link = network.get(parent[i],i);
				mst.add(parent[i], i, link);
				weight += linkEvaluator.evaluate(link);
			}
		
		return mst;
	}
	
	private void updateCost (PriorityQueue queue, int node, double newCost)
	{
		queue.remove(node);
		cost[node] = newCost;
		queue.add(node);
	}
	
	
	// Minimum spanning tree
	
	public Network<V,E> MST ()
	{
		return mst;
	}
	
	// Tree weight
	
	public double weight ()
	{
		return weight;
	}
	
	
	// Ancillary class
	
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
}
