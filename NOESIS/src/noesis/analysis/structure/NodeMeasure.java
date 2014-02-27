package noesis.analysis.structure;

import noesis.Network;

public class NodeMeasure extends Measure
{
	private Network network;
	
	// Constructor
	
	public NodeMeasure (NodeMeasureTask creator, Network network)
	{
		super(network.size());
		
		this.network = network;
		
		setName( creator.getName() );
		setDescription (creator.getDescription() );
	}
	
	public NodeMeasure (String id, Network network)
	{
		super(network.size());
		
		this.network = network;
		
		setName(id);
	}
	
	// Getter
	
	public final Network getNetwork ()
	{
		return network;
	}
	
}
