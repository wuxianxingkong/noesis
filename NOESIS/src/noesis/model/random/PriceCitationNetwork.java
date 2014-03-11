package noesis.model.random;

// Title:       Price's directed citation network
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.random.Random;

/**
 * Price's model.
 * Price's model (named after the physicist Derek J. de Solla Price) was the first model which generalized the Simon model
 * to be used for networks, especially for growing networks. Price's model belongs to the  class of network growing models
 * whose primary target is to explain the origination of networks with strongly skewed degree distributions.
 * 
 * ref. http://en.wikipedia.org/wiki/Price's_model
 * 
 * - Derek J. de Solla Price: "A general theory of bibliometric and other cumulative advantage processes," 
 * Journal of the American Society for Information Science, 27(5):292-306, September 1976, DOI 10.1002/asi.4630270505
 * 
 * - P. L. Krapivsky and S. Redner: "Organization of growing random networks," 
 * Physical Review E - Statistical, Nonlinear, and Soft Matter Physics, 63:066123, May 2001  DOI 10.1103/PhysRevE.63.066123
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class PriceCitationNetwork extends RandomNetwork 
{
	private int citations;
	private int free;
	
	public PriceCitationNetwork (int nodes, int citations, int free)
	{
		this.citations = citations;
		this.free = free;
		
		setID("PRICE'S CITATION NETWORK (n="+nodes+", c="+citations+", a="+free+")");
		
		setSize(nodes);

		int    target[] = new int[nodes*citations];
		int    link = 0;
		double probability = citations / (double) (citations+free); 
		
		// Initialization
		
		for (int i=1; i<=citations; i++) {
			for (int j=0; j<i; j++) {
				target[link] = j;
				add(i,target[link]);
				link++;
			}
		}
		
		// Network formation
		
		for (int i=citations+1; i<nodes; i++) {
			for (int j=0; j<citations; j++) {
				
				if (Random.random()<probability) {
					
					// Choose an element uniformly at random from the list of targets
					
					do {
						target[link] = target[Random.random(link)];
					} while ( contains(i,target[link]) );
					
				} else {
					
					// Choose a vertex uniformly at random from the set of nodes
					
					do {
						target[link] = Random.random(i);
					} while ( contains(i,target[link]) );
				}
				
				add(i, target[link]);
				link++;
			}
		}
		
		target = null;
	}
	
	
	public int getCitationsPerPaper ()
	{
		return citations;
	}
	
	public int getFreeCitationsPerPaper ()
	{
		return free;
	}
}
