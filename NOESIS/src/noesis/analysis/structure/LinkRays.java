package noesis.analysis.structure;

import ikor.model.data.annotations.Label;

import noesis.Network;

@Label("Link rays")
public class LinkRays extends LinkMeasureTask
{
	public LinkRays(Network network) 
	{
		super(network);
	}

	@Override
	public double compute(int source, int destination) 
	{
		Network net = getNetwork();
		
		return net.inDegree(source)*net.outDegree(destination);
	}

}
