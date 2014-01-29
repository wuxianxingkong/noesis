package noesis.analysis.structure;

import noesis.Network;

public class NodeMeasure extends Measure
{
	private Network network;
	
	// Constructor
	
	protected NodeMeasure (NodeMeasureTask creator, Network network)
	{
		super(network.size());
		
		this.network = network;
		
		setName( creator.getName() );
		setDescription (creator.getDescription() );
	}
	
	protected NodeMeasure (String id, Network network)
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
