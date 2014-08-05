package noesis.analysis.structure;

// Title:       Clustering coefficient
// Version:     1.0
// Copyright:   2013
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.NodeScoreTask;

/**
 * Clustering coefficient, between 0 and 1.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

@Label("cc")
@Description("Clustering coefficient")
public class ClusteringCoefficient extends NodeScoreTask
{
	public ClusteringCoefficient (Network network)
	{
		super(network);
	}
	
	public double compute (int node) 
	{
		Network net = getNetwork();
		int triangles = 0;
		int degree  = net.outDegree(node);
		int links[] = net.outLinks(node);
		
		if (links!=null) {			
			for (int i=0; i<degree; i++) {
				for (int j=0; j<degree; j++) {				
				
					if (net.contains(links[i],links[j]))
						triangles++;
				}
			}
		}
		
		if (degree>1)
			return ((double)triangles)/(degree*(degree-1));
		else
			return 0;
	}

}
