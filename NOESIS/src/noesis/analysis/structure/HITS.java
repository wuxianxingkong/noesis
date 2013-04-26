package noesis.analysis.structure;

import noesis.Network;

/**
 * HITS: Hubs & Authorities.
 * 
 * Jon M. Kleinberg: "Authoritative Sources in a Hyperlinked Environment," Journal of the ACM 46(5):604–632 (1999)
 * 
 * - The hub centrality of a vertex is proportional to the sum of the authority centralities of the vertices it points to.
 * - The authority centrality of a vertex is proportional to the sum of the hub centralities of the vertices that point to it.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class HITS extends NodeMultiMeasure 
{
	public static final int HUB = 0;
	public static final int AUTHORITY = 1;
	
	public static int MAX_ITERATIONS = 100;
	public static double EPSILON = 1e-6;
	
	private double alpha;
	private double beta;
	
	public HITS (Network network)
	{
		this(network,1,1);
	}
	
	public HITS (Network network, double alpha, double beta)
	{
		super(network,2);
		
		this.alpha = alpha;
		this.beta = beta;
	}	

	public double getAlpha ()
	{
		return alpha;
	}
	
	public double getBeta ()
	{
		return beta;
	}	
	
	
	private static final String[] names = { "hub", "authority" };
	private static final String[] descriptions = { "Hub centrality", "Authority centrality" };
	
	@Override
	public String getName (int measure) 
	{
		return names[measure];
	}	

	@Override
	public String getDescription (int measure) 
	{
		return descriptions[measure];
	}	

	

	@Override
	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
		double  hub[], authority[];
		double  oldHub[], oldAuthority[];
		double  tmp[];
		double  change;
		double  sumHub, sumAuthority;
		double  normHub, normAuthority;
		int     iteration;
		
		// Initialization: 1/sqrt(N)
		
		hub = new double[size];
		authority = new double[size];
		oldHub = new double[size];
		oldAuthority = new double[size];
		
		for (int i=0; i<size; i++) {
			hub[i] = 1.0/Math.sqrt(size);
			oldHub[i] = hub[i];
			authority[i] = 1.0/Math.sqrt(size);
			oldAuthority[i] = authority[i];
		}
				
		
		// Iterative algorithm
		
		change = Double.MAX_VALUE;
		
		for (iteration=0; (iteration<MAX_ITERATIONS) && (change>EPSILON); iteration++) {

			// Swap centrality vectors
			
			tmp = oldHub;         
			oldHub = hub;
			hub = tmp;

			tmp = oldHub;         
			oldHub = hub;
			hub = tmp;

			sumHub = 0;
			sumAuthority = 0;
			
			// Update hubs & authorities
			
			for (int v=0; v<size; v++) {
				
				hub[v] = 0.0;
				authority[v] = 0.0;
				
			    for (int i=0; i<net.outDegree(v); i++) {
				    hub[v] += oldAuthority[ net.outLink(v,i) ];
				}
				
				for (int i=0; i<net.inDegree(v); i++) {
				 	authority[v] += oldHub[ net.inLink(v,i) ];
				}
	
			    hub[v] *= beta;
			    authority[v] *= alpha;
			    
				sumHub += hub[v];
				sumAuthority += authority[v];
			}
		    
		    // Normalize centrality vectors so that they sum 1
			
			normHub = sumHub;
			normAuthority = sumAuthority;
			
			change = 0;
			
			for (int v=0; v<size; v++) {
				hub[v] /= normHub;
				authority[v] /= normAuthority;

				if ( Math.abs(hub[v]-oldHub[v]) > change )
					change = Math.abs(hub[v]-oldHub[v]);
				
				if ( Math.abs(authority[v]-oldAuthority[v]) > change )
					change = Math.abs(authority[v]-oldAuthority[v]);
			}
		}

		set(HUB, hub);
		set(AUTHORITY, authority);
	}	
	
	
	
	public double[] compute(int node) 
	{
		checkDone();		
		return new double[] { get(HUB,node), get(AUTHORITY,node) };
	}	
	
}
