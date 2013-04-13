package noesis.algorithms.visualization;

import noesis.Attribute;
import noesis.AttributeNetwork;

/**
 * Network layout
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class NetworkLayout 
{	
	protected static final double MARGIN = 0.05;
	
	public void layout (AttributeNetwork network)
	{
		layout(network, network.getNodeAttribute("x"), network.getNodeAttribute("y"));
	}
	
	public abstract void layout (AttributeNetwork network, Attribute<Double> x, Attribute<Double> y);	
}
