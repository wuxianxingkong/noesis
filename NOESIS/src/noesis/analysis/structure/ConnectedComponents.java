package noesis.analysis.structure;

import ikor.model.data.DataModel;

import noesis.Network;
import noesis.algorithms.traversal.StronglyConnectedComponents;

/**
 * Strongly-connected components in a directed network
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class ConnectedComponents extends NodeMeasureMultiTask
{
	public static final int COMPONENT = 0;
	public static final int COMPONENT_SIZE = 1;
	public static final int REACHABLE_NODES = 2;
	// TODO REACHED_BY

	/**
	 * Constructor
	 * 
	 * @param network Network
	 */
	public ConnectedComponents (Network network)
	{
		super(network);
	}	

	// Metadata
	
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

	// Computation
	
	@Override
	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
		
		measures = createMeasures(net);
		
		
		StronglyConnectedComponents scc = new StronglyConnectedComponents( net );
		
		scc.compute();
		
		for (int node=0; node<size; node++) {
			measures.get(COMPONENT).set(node, scc.component(node));
			measures.get(COMPONENT_SIZE).set(node, scc.componentSize(node));
			
			PathLength paths = new PathLength(net, node);
			paths.compute();
			measures.get(REACHABLE_NODES).set(node, paths.reachableNodes() );
		}
	}	
	
	
	
	public double[] compute(int node) 
	{
		checkDone();		
		return new double[] { measures.get(COMPONENT).get(node), 
				              measures.get(COMPONENT_SIZE).get(node), 
				              measures.get(REACHABLE_NODES).get(node) };
	}	
	
}
