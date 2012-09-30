package noesis.analysis.structure;

import noesis.Network;

// PageRank

public class PageRank  extends NodeMetrics 
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

	double  pagerank[];
	double  weight[];
	boolean dangling[];
	
	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
		boolean changes;
		double  old;
		
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
			

		// Iterative algorithm
		
	    do {	
	    	
	    	changes = false;
			pagerank = new double[size];
	    	
		    for (int node=0; node<size; node++) {
		    	
		    	old = get(node);
		    	pagerank[node] = pagerank(net,node);
		    	
		    	if (!changes && (Math.abs(pagerank[node]-old)>EPSILON) ) {
		    		changes = true;
		    	}

		    	System.out.println(node+ " " + pagerank[node]);
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
	
	
	private double pagerank (Network net, int node)
	{
		int    size    = net.size();
		int    degree  = net.inDegree(node);
		int    links[] = net.inLinks(node);
		double rank;
		double danglingRank;
		double randomWalk;

		// Network structure
		
		rank = 0;
		
		if (links!=null) {			
			for (int i=0; i<degree; i++) {
				rank += get(links[i])*weight[links[i]];
			}
		}
		
		// Dangling nodes
		
		danglingRank = 0;
		
		for (int i=0; i<size; i++)
			if (dangling[i])
				danglingRank += get(i);
		
		danglingRank /= size;
		
		// Random walk
		
		randomWalk = 1.0/size;
		
		return theta*(rank + danglingRank) + (1-theta)*randomWalk;
	}
	
	
	public double compute(int node) 
	{
		checkDone();		
		return get(node);
	}	
	
}
