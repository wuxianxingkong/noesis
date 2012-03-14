package noesis.analysis.structure;


import noesis.Network;

import ikor.math.Vector;

public abstract class NodeMetrics extends Vector
{
	private Network network;
	
	protected NodeMetrics (Network network)
	{
		super(network.size());
		
		this.network = network;
	}
	
	public final Network getNetwork ()
	{
		return this.network;
	}
	

	// Computation
	
	protected boolean done = false;
	
	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
	
		for (int node=0; node<size; node++)
			set (node, compute(node));
		
		done = true;
	}
	
	public final boolean checkDone ()
	{
		if (!done)
			compute();
		
		return done;
	}
	
	public abstract double compute (int node);
	
	// Standard output
	
	public String toString ()
	{
		return toStringSummary();
	}
}
