package noesis.model.random;

// Title:       Barabasi-Albert's preferential attachment network model
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.random.Random;

/**
 * Barabási–Albert model.
 * An algorithm for generating random scale-free networks using a preferential attachment mechanism.
 * 
 * ref. http://en.wikipedia.org/wiki/Barab%C3%A1si%E2%80%93Albert_model
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class BarabasiAlbertNetwork extends RandomNetwork 
{
	private int links;
	
	public BarabasiAlbertNetwork (int nodes, int links)
	{
		this.links = links;
		
		setID("BARABASI-ALBERT NETWORK (n="+nodes+", c="+links+")");
		
		setSize(nodes);

		int    target[] = new int[2*nodes*links];
		int    link = 0;
		
		// Initialization: (1,0) (2,0) (3,0) (3,1) (4,0) (4,1) (4,2) (5,0) ...
		
		for (int i=1; i<=links; i++) {
			for (int j=0; (j==0) || (j<i-1); j++) {
				add2(i,j);
				target[link] = i;
				link++;
				target[link] = j;
				link++;
			}
		}
		
		// Network formation: Preferential attachment
		
		for (int i=links+1; i<nodes; i++) {
			for (int n=0; n<links; n++) {
				
				// Choose an element uniformly at random from the list of targets
				// i.e. with a probability that is proportional to the number of links that the existing nodes already have.

				int j;
				
				do {
					j = target[Random.random(link)];
				} while ( contains(i,j) );
				
				add2(i,j);
				
				target[link] = i;
				link++;
				target[link] = j;
				link++;
			}
		}
		
		target = null;
	}
	
	
	public int getLinksPerNewNode ()
	{
		return links;
	}
}
