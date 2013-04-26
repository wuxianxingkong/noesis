package noesis.analysis.structure;

import noesis.Network;

/**
 * Katz centrality: x_i = alpha * sum_j ( A_ij * x_j ) + beta
 * 
 * - Equal to eigenvector centrality when beta = 0.
 * - Constant when alpha = 0.
 * 
 * NOTE: Beta is adjusted by the number of nodes since the centrality vector is normalized to be a unit vector.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class KatzCentrality  extends NodeMetrics 
{
	public static int MAX_ITERATIONS = 100;
	public static double EPSILON = 1e-6;

	private double alpha;
	private double beta;

	public KatzCentrality (Network network)
	{
		this(network,1.0,1.0);
	}
	
	public KatzCentrality (Network network, double alpha)
	{
		this(network,alpha,1.0);
	}
	
	public KatzCentrality (Network network, double alpha, double beta)
	{
		super(network);
		this.alpha = alpha;
		this.beta = beta;
	}	

	
	@Override
	public String getName() 
	{
		return "katz("+alpha+","+beta+")";
	}	

	@Override
	public String getDescription() 
	{
		return "Katz centrality (alpha="+alpha+"; beta="+beta+")";
	}	

	

	@Override
	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
		double  centrality[];
		double  old[];
		double  tmp[];
		double  change;
		double  sum2;
		double  norm;
		int     iteration;
		
		// Initialization: 1/N
		
		centrality = new double[size];
		old = new double[size];
	
		for (int i=0; i<size; i++) {
			centrality[i] = 1.0/size;
			old[i] = centrality[i];
		}
		
		
		// Power iteration: O(k(n+m))
		// The value of norm converges to the dominant eigenvalue, and the vector 'centrality' to an associated eigenvector
		// ref. http://en.wikipedia.org/wiki/Power_iteration
		
		change = Double.MAX_VALUE;
		
		for (iteration=0; (iteration<MAX_ITERATIONS) && (change>EPSILON); iteration++) {

			tmp = old;         // Swap old-centrality
			old = centrality;
			centrality = tmp;

			sum2 = 0;
			
			for (int v=0; v<size; v++) {
				
				centrality[v] = 0.0;
				
				// Right eigenvector
				
				for (int i=0; i<net.inDegree(v); i++) {
				 	centrality[v] += old[ net.inLink(v,i) ];
				}
				
				// Katz centrality

				centrality[v] = alpha*centrality[v] + beta/size;
				
				sum2 += centrality[v]*centrality[v];
			}
		    
		    // Normalization
			
			norm = Math.sqrt(sum2);
			change = 0;
			
			for (int v=0; v<size; v++) {
				centrality[v] /= norm;

				if ( Math.abs(centrality[v]-old[v]) > change )
					change = Math.abs(centrality[v]-old[v]);
			}
		}

		set(centrality);	    
	}	
	
	
	
	public double compute(int node) 
	{
		checkDone();		
		return get(node);
	}	
	
}
