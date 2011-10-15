package noesis.analysis.structure;


import noesis.Network;

import ikor.math.Vector;

public abstract class NodeMetrics 
{
	private Network network;
	private Vector  metrics;
	
	protected NodeMetrics (Network network)
	{
		this.network = network;
		this.metrics = new Vector(network.size());
	}
	
	public Network getNetwork ()
	{
		return this.network;
	}
	
	public double get(int i)
	{
		return metrics.get(i);
	}
	
	protected void set (int i, double value)
	{
		metrics.set(i,value);
	}
	
	public abstract void compute ();
	
	// Standard output
	
	public String toString ()
	{
		return metrics.toStringSummary();
	}
}
