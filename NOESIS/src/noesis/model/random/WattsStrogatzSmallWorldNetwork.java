package noesis.model.random;

import ikor.math.random.Random;

/**
 * Watts-Strogatz random small world network.
 * 
 * ref. http://en.wikipedia.org/wiki/Watts_and_Strogatz_model
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class WattsStrogatzSmallWorldNetwork extends RandomNetwork 
{
	private double probability;
	
	public WattsStrogatzSmallWorldNetwork (int nodes, int neighbors, double rewiringProbability)
	{
		this.probability = rewiringProbability;
		
		setID("W-S NETWORK (n="+nodes+", k="+neighbors+", beta="+probability+")");
		
		setSize(nodes);

		int k = neighbors/2;
		
		// Regular link lattice
		
		for (int i=0; i<nodes; i++) {
			for (int j=1; j<=k; j++) {
				add2(i, (i+j)%nodes);
			}
		}
		
		// Link rewiring
		
		int target;
		
		for (int i=0; i<nodes; i++) {
			for (int j=1; j<=k; j++) {
				if (Random.random()<probability) {
					remove2(i, (i+j)%nodes);
					
					do {
						target = Random.random(nodes);
					} while ( (target==i) || contains(i,target) );
					
					add2(i, target);
				}
			}
		}
	}
	
	public double getRewiringProbability()
	{
		return probability;
	}
	
}
