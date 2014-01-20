package noesis.analysis.structure;

import noesis.Network;

public abstract class NodeMeasure extends Measure
{
	private Network network;
	
	protected NodeMeasure (Network network)
	{
		super(network.size());
		
		this.network = network;
	}
	
	public final Network getNetwork ()
	{
		return network;
	}
	

	// Computation template method
	
	protected boolean done = false;
	
	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
	
		for (int node=0; node<size; node++)
			set (node, compute(node));
		
		done = true;
	}
	
	protected final boolean checkDone ()
	{
		if (!done)
			compute();
		
		return done;
	}
	
	public abstract double compute (int node);
}
