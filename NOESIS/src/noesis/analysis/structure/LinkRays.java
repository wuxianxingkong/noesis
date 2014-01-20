package noesis.analysis.structure;

import noesis.Network;

public class LinkRays extends LinkMeasure 
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

	@Override
	public String getName() 
	{
		return "Link rays";
	}

}
