package noesis.algorithms.mst;


import ikor.collection.Evaluator;
import ikor.collection.PriorityQueue;
import ikor.collection.DynamicPriorityQueue;
import ikor.collection.util.UnionFind;

import noesis.Network;
import noesis.Link;
import noesis.LinkComparator;
import noesis.ArrayNetwork;

public class KruskalMinimumSpanningTree<V,E>
{
	Network<V,E> network;
	Evaluator    evaluator;
	Network<V,E> mst;
	double       weight;
	
	public KruskalMinimumSpanningTree (Network<V,E> net, Evaluator linkEvaluator)
	{
		this.network   = net;
		this.evaluator = linkEvaluator;
	}
	
	public Network<V,E> run()
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
		
		
		// Result
		
		return mst;
	}
	
	private PriorityQueue<Link<E>> createPriorityQueue ()
	{
		LinkComparator<E> comparator = new LinkComparator(evaluator);
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
}
