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

	
	// Task methods
	
	@Override
	public LinkMeasure call() 
	{
		int         size = network.size();
		int         pos = 0;
		LinkMeasure measure = new LinkMeasure(this,network,index);
	
		for (int node=0; node<size; node++) {
			for (int link=0; link<network.outDegree(node); link++) {
				measure.set (pos, compute(node, network.outLink(node,link)));
				pos++;
			}
		}
		
		return measure;
	}
	
	public abstract double compute (int source, int destination);
	

}
