package noesis.analysis;

import noesis.Network;

/**
 * Node score.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class NodeScore extends Score
{
	private Network network;
	
	// Constructor
	
	public NodeScore (NodeScoreTask creator, Network network)
	{
		super(network.size());
		
		this.network = network;
		
		setName( creator.getName() );
		setDescription (creator.getDescription() );
	}
	
	public NodeScore (String id, Network network)
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
