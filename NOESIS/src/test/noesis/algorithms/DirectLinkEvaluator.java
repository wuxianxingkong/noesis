package test.noesis.algorithms;

import noesis.LinkEvaluator;
import noesis.Network;

// Ancillary class

public class DirectLinkEvaluator implements LinkEvaluator
{
	private Network net;
	
	public DirectLinkEvaluator (Network net)
	{
		this.net = net;
	}
	
	@Override
	public double evaluate (int source, int destination) 
	{
		return (Integer) net.get(source,destination);
	}
}
