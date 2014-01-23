package noesis.analysis.structure;

import noesis.Network;
import noesis.network.LinkIndex;

public abstract class LinkMeasure extends Measure
{
	private Network network;
	private LinkIndex index;

	protected LinkMeasure (Network network, LinkIndex index)
	{
		super(network.links());
		
		this.network = network;
		this.index = index;
	}

	protected LinkMeasure (Network network)
	{
		this (network, new LinkIndex(network));
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
		int     pos = 0;
	
		for (int node=0; node<size; node++) {
			for (int link=0; link<net.outDegree(node); link++) {
				set (pos, compute(node, net.outLink(node,link)));
				pos++;
			}
		}
		
		done = true;
	}
	
	protected final boolean checkDone ()
	{
		if (!done)
			compute();
		
		return done;
	}
	
	public abstract double compute (int source, int destination);
	
	// Getter method
	
	public double get (int source, int destination)
	{
		int link = index.index(source, destination);
		
		if (link!=-1)
			return get(link);
		else
			return Double.NaN;
	}
}
