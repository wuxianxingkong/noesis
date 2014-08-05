package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.NodeScoreTask;
import noesis.analysis.NodeScore;

/**
 * Node eccentricity.
 * 
 * The eccentricity of a vertex  is the greatest geodesic distance between it and any other vertex. 
 * (i.e. how far a node is from the node most distant from it in the network).
 * 
 * - The radius of a network is the minimum eccentricity of any vertex.
 * - The diameter of a graph is the maximum eccentricity of any vertex.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

@Label("eccentricity")
@Description("Node eccentricity")
public class Eccentricity extends NodeScoreTask 
{
	public Eccentricity (Network network)
	{
		super(NodeScore.INTEGER_MODEL,network);
	}	

	
	
	public double compute(int node) 
	{
		PathLength paths = new PathLength(getNetwork(), node);
		
		paths.compute();
		
		return paths.getResult().max();
	}	
	
	public int diameter ()
	{
		checkDone();
		
		return (int) getResult().max();
	}

}
