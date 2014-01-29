package noesis.analysis.structure;

import ikor.model.data.DataModel;
import noesis.Network;
import noesis.algorithms.traversal.StronglyConnectedComponents;

/**
 * Strongly-connected components in a directed network
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class ConnectedComponents extends NodeMultiMeasureTask
{
	public static final int COMPONENT = 0;
	public static final int COMPONENT_SIZE = 1;
	public static final int REACHABLE_NODES = 2;
	// TODO REACHED_BY
	
	public ConnectedComponents (Network network)
	{
		super(network);
	}	


	private static final String[] names = { "component", "scc-size", "reaches" };
	private static final String[] descriptions = { "Strongly connected component", "Connected component size", "Reachable nodes" };
	private static final DataModel[] models = new DataModel[] { NodeMeasure.INTEGER_MODEL, NodeMeasure.INTEGER_MODEL, NodeMeasure.INTEGER_MODEL };
	
	@Override
	public String[] getNames () 
	{
		return names;
	}	

	@Override
	public String[] getDescriptions () 
	{
		return descriptions;
	}	

	@Override
	public DataModel[] getModels ()
	{
		return models;  
	}	

	@Override
	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
		
		measure = new NodeMultiMeasure(this,net,3);
		
		StronglyConnectedComponents scc = new StronglyConnectedComponents( net );
		
		scc.compute();
		
		for (int node=0; node<size; node++) {
			measure.set (COMPONENT, node, scc.component(node));
			measure.set (COMPONENT_SIZE, node, scc.componentSize(node));
			
			PathLength paths = new PathLength(net, node);
			paths.compute();
			measure.set (REACHABLE_NODES, node, paths.reachableNodes() );
		}
	}	
	
	
	
	public double[] compute(int node) 
	{
		checkDone();		
		return new double[] { measure.get(COMPONENT,node), measure.get(COMPONENT_SIZE,node), measure.get(REACHABLE_NODES,node) };
	}	
	
}
