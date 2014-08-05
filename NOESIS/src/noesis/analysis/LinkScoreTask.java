package noesis.analysis;

import ikor.parallel.Task;
import noesis.Network;
import noesis.network.LinkIndex;
import noesis.network.LinkIndexer;

/**
 * Task for computing link scores.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class LinkScoreTask extends Task<LinkScore>
{
	private Network     network;
	private LinkIndexer index;
	
	
	// Constructors
	
	public LinkScoreTask (Network network, LinkIndexer index)
	{
		this.network = network;
		this.index = index;
	}

	public LinkScoreTask (Network network)
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
	
	public void checkDone ()
	{
		if (getResult()==null)
			compute();
	}
	
	public void compute ()
	{
		int size = network.size();
		int pos = 0;
		
		LinkScore result = new LinkScore(this,network,index);
	
		for (int node=0; node<size; node++) {
			for (int link=0; link<network.outDegree(node); link++) {
				result.set (pos, compute(node, network.outLink(node,link)));
				pos++;
			}
		}
		
		setResult(result);
	}

	
	@Override
	public LinkScore call() 
	{
		compute();
		
		return getResult();
	}
	
	public abstract double compute (int source, int destination);
	
}
