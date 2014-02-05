package noesis.analysis.structure;

import ikor.parallel.Task;

import noesis.Network;
import noesis.network.LinkIndex;
import noesis.network.LinkIndexer;

public abstract class LinkMeasureTask extends Task<LinkMeasure>
{
	private Network     network;
	private LinkIndexer index;
	
	
	// Constructors
	
	public LinkMeasureTask (Network network, LinkIndexer index)
	{
		this.network = network;
		this.index = index;
	}

	public LinkMeasureTask (Network network)
	{
		this (network, new LinkIndex(network));
	}


	// Getters
	
	public final Network getNetwork ()
	{
		return network;
	}
	
	public final LinkIndexer getLinkIndex ()
	{
		return index;
	}

	
	// Computation template method
	
	protected LinkMeasure measure = null;
	
	public void checkDone ()
	{
		if (measure==null)
			compute();
	}
	
	public void compute ()
	{
		int size = network.size();
		int pos = 0;
		
		measure = new LinkMeasure(this,network,index);
	
		for (int node=0; node<size; node++) {
			for (int link=0; link<network.outDegree(node); link++) {
				measure.set (pos, compute(node, network.outLink(node,link)));
				pos++;
			}
		}
	}

	
	@Override
	public LinkMeasure call() 
	{
		compute();
		
		return measure;
	}
	
	public abstract double compute (int source, int destination);
	
}
