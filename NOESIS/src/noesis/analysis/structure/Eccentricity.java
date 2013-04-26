package noesis.analysis.structure;

import ikor.model.data.DataModel;
import noesis.Network;

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
public class Eccentricity extends NodeMeasure 
{
	public Eccentricity (Network network)
	{
		super(network);
	}	

	@Override
	public String getName() 
	{
		return "radius";
	}	
	
	@Override
	public DataModel getModel()
	{
		return INTEGER_MODEL;
	}
	
	public double compute(int node) 
	{
		PathLength paths = new PathLength(getNetwork(), node);
		
		paths.compute();
		
		return paths.max();
	}	
	
	public int diameter ()
	{
		checkDone();
		
		return (int) max();
	}

}
