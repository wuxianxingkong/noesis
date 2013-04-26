package noesis.analysis.structure;

import noesis.Network;

public class ClusteringCoefficient extends NodeMeasure 
{
	public ClusteringCoefficient (Network network)
	{
		super(network);
	}
	
	@Override
	public String getName() 
	{
		return "cc";
	}	
	
	@Override
	public String getDescription() 
	{
		return "Clustering coefficient";
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
				
					if (net.get(links[i],links[j])!=null)
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
