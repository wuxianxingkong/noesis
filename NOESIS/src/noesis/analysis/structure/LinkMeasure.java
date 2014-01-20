package noesis.analysis.structure;

import noesis.Network;

public abstract class LinkMeasure extends Measure
{
	private Network network;
	private int[] index;
	
	protected LinkMeasure (Network network)
	{
		super(network.links());
		
		this.network = network;
		this.index = new int[network.size()];
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
			index[node] = pos;
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
		Network net = getNetwork();
		int degree = net.outDegree(source);
		int offset = 0;
		
		while ((offset<degree) && (destination!=net.outLink(source,offset)))
			offset++;
		
		if (offset<degree)
			return get(index[source]+offset);
		else
			return 0;
	}
}
