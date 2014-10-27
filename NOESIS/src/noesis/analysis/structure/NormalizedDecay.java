package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import noesis.Network;

/**
 * Normalized decay, being delta*(n-1) the lowest possible decay in a connected network.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

@Description("Normalized decay")
public class NormalizedDecay extends Decay 
{
	public NormalizedDecay (Network network)
	{
		super(network);
	}
	
	public NormalizedDecay (Network network, double delta)
	{
		super(network,delta);
	}	

	
	@Override
	public String getName() 
	{
		return super.getName()+"-norm";
	}	

	@Override
	public double compute(int node) 
	{
		int size = getNetwork().size();
		
		if ((size>1) && (getDelta()>0))
			return super.compute(node) / ( getDelta() * (size-1) );
		else
			return 0;
	}	
}
