package noesis.analysis.structure;

import noesis.Network;

// PageRank

public class PageRank  extends NodeMeasure 
{
	public static double DEFAULT_THETA = 0.85;
	public static double EPSILON = 1e-4;
	
	private double theta = DEFAULT_THETA;
	
	public PageRank (Network network)
	{
		this(network, DEFAULT_THETA);
	}	

	public PageRank (Network network, double theta)
	{
		super(network);
		this.theta = theta;
	}	

	
	@Override
	public String getName() 
	{
		return "page-rank";
	}	

	@Override
	public String getDescription() 
	{
		return "PageRank";
	}	
	
	double  pagerank[];
	double  weight[];
	
	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
		boolean changes;
		double  old;
		boolean dangling[];
		double  danglingRank;
		double  randomRank;
		
		// Initialization: 1/N
		
		pagerank = new double[size];
		
		for (int i=0; i<size; i++)
			pagerank[i] = 1.0/size;
		
		updateRank(pagerank);
		
		// Weights: 1/outdegree(i)
		
		weight   = new double[size];
		dangling = new boolean[size];
		
		for (int i=0; i<size; i++) {
			if (net.outDegree(i)>0)
				weight[i] = 1.0 / net.outDegree(i);
			else
				dangling[i] = true;
		}
			

		// Iterative algorithm: O(k(n+m))
		
	    do {	
	    	
	    	changes = false;
			pagerank = new double[size];
	    	
			// Dangling nodes (common calculation): O(n) 
			
			danglingRank = 0;
			
			for (int i=0; i<size; i++)
				if (dangling[i])
					danglingRank += get(i);
			
			danglingRank /= size;
			
			// Random walk: O(1)
			
			randomRank = 1.0/size;
			
			// For each node: O(m)
			
		    for (int node=0; node<size; node++) {
		    	
		    	old = get(node);
		    	pagerank[node] = pagerank (net, node, danglingRank, randomRank);
		    	
		    	if (!changes && (Math.abs(pagerank[node]-old)>EPSILON) ) {
		    		changes = true;
		    	}
		    }
		    
		    updateRank(pagerank);
		    
	    } while (changes);
		
		done = true;
		weight = null;
		dangling = null;
	}	
	
	
	private void updateRank (double[] rank)
	{
	    set(rank);
	    // Normalization
	    // this.multiply(1.0/ this.sum());
	}
	
	
	private double pagerank (Network net, int node, double danglingRank, double randomRank)
	{
		int    degree  = net.inDegree(node);
		int    links[] = net.inLinks(node);
		double rank    = 0;

		// Network structure
		
		rank = 0;
		
		if (links!=null) {			
			for (int i=0; i<degree; i++) {
				rank += get(links[i])*weight[links[i]];
			}
		}
		
		// PageRank: Incoming links + Dangling nodes + Random walk
		
		return theta*(rank + danglingRank) + (1-theta)*randomRank;
	}
	
	
	public double compute(int node) 
	{
		checkDone();		
		return get(node);
	}	
	
}
