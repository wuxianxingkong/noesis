package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;

/**
 * Eigenvector centrality.
 * 
 * Right eigenvector centrality for directed networks.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

@Label("eigenvector")
@Description("Eigenvector centrality")
public class EigenvectorCentrality  extends NodeMeasureTask
{
	public static int MAX_ITERATIONS = 100;
	public static double EPSILON = 1e-6;
	
	public EigenvectorCentrality (Network network)
	{
		super(network);
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

		measure = new NodeMeasure(this,net);
				
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
	
				// Left eigenvector (alternative for directed networks)
				//
				// for (int i=0; i<net.outDegree(v); i++) {
				//     centrality[v] += old[ net.outLink(v,i) ];
				// }
				
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

		measure.set(centrality);	    
	}	
	
	
	
	public double compute(int node) 
	{
		checkDone();		
		return measure.get(node);
	}	
	
}
