package noesis.algorithms.mst;


import ikor.collection.Evaluator;
import ikor.collection.EvaluatorComparator;
import ikor.collection.PriorityQueue;
import ikor.collection.DynamicPriorityQueue;
import ikor.collection.util.UnionFind;

import noesis.Network;
import noesis.Link;
import noesis.ArrayNetwork;

/**
 * Minimum Spanning Trees (for undirected networks): Kruskal's algorithm. 
 *  
 * Efficient implementation using union-find data structure, O(m log m).
 *  
 * @author Fernando Berzal
 */
public class KruskalMinimumSpanningTree<V,E>
{
	private Network<V,E> network;
	private Evaluator<E> evaluator;
	private Network<V,E> mst;
	private double       weight;
	
	public KruskalMinimumSpanningTree (Network<V,E> net, Evaluator<E> linkEvaluator)
	{
		this.network   = net;
		this.evaluator = linkEvaluator;
	}
	
	public void run()
	{
		PriorityQueue<Link<E>> queue;
		UnionFind              uf;
		Link<E>                link;
		
		// Initialization
		
		this.mst = new ArrayNetwork<V,E>();
		this.weight = 0;	
	
		// Nodes
		
		for (int i=0; i<network.size(); i++)
			mst.add ( network.get(i) );
	
		// Links
		
		queue = createPriorityQueue();
		
		// Greedy algorithm
		
		uf = new UnionFind(network.size());
		
        while ((queue.size()>0) && (mst.links() < network.size()-1) ) {
            
        	link = queue.get();

        	// Check that source-destination link does not create a cycle
        	
            if (!uf.inSameSet(link.getSource(), link.getDestination())) { 
            	
            	// merge source and destination components
            	
                uf.union(link.getSource(), link.getDestination());
                
                // add edge to MST
                
                mst.add(link.getSource(), link.getDestination(), link.getContent() );
                
                weight += evaluator.evaluate(link.getContent());
            }
        }		
	}
	
	private PriorityQueue<Link<E>> createPriorityQueue ()
	{
		LinkEvaluator linkEvaluator = new LinkEvaluator(evaluator);    
		EvaluatorComparator<E> comparator = new EvaluatorComparator(linkEvaluator);
		PriorityQueue<Link<E>> queue = new DynamicPriorityQueue<Link<E>>(comparator);
	
		for (int i=0; i<network.size(); i++) {
			
			int[] links = network.outLinks(i);
			
			for (int j=0; j<links.length; j++) {
				if (i<links[j]) // Add links ONLY in one direction (no loops)  
					queue.add( new Link(i,links[j],network.get(i,links[j])) );
			}
		}
	
		return queue;
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
	
	class LinkEvaluator implements Evaluator<Link<E>>
	{
		private Evaluator<E> evaluator;
		
		public LinkEvaluator(Evaluator<E> evaluator)
		{
			this.evaluator = evaluator;
		}

		@Override
		public double evaluate(Link<E> object) 
		{
			return evaluator.evaluate(object.getContent());
		}
	}
}
