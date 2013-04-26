package noesis.analysis.structure;

import ikor.model.data.DataModel;
import noesis.Network;
import noesis.algorithms.traversal.StronglyConnectedComponents;

/**
 * Strongly-connected components in a directed network
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class ConnectedComponents extends NodeMultiMeasure 
{
	public static final int COMPONENT = 0;
	public static final int COMPONENT_SIZE = 1;
	public static final int REACHABLE_NODES = 2;
	// TODO REACHED_BY
	
	public ConnectedComponents (Network network)
	{
		super(network,3);
	}	


	private static final String[] names = { "component", "scc-size", "reaches" };
	private static final String[] descriptions = { "Strongly connected component", "Connected component size", "Reachable nodes" };
	
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
	public DataModel getModel (int measure)
	{
		return INTEGER_MODEL; 
	}	

	@Override
	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
		
		StronglyConnectedComponents scc = new StronglyConnectedComponents( net );
		
		scc.compute();
		
		for (int node=0; node<size; node++) {
			set (COMPONENT, node, scc.component(node));
			set (COMPONENT_SIZE, node, scc.componentSize(node));
			
			PathLength paths = new PathLength(net, node);
			paths.compute();
			set (REACHABLE_NODES, node, paths.reachableNodes() );
		}
	}	
	
	
	
	public double[] compute(int node) 
	{
		checkDone();		
		return new double[] { get(COMPONENT,node), get(COMPONENT_SIZE,node), get(REACHABLE_NODES,node) };
	}	
	
}
