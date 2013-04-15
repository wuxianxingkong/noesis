package noesis.algorithms.visualization;

import noesis.Attribute;
import noesis.AttributeNetwork;

/**
 * Iterative network layout algorithm
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class IterativeNetworkLayout extends NetworkLayout 
{
	protected AttributeNetwork network;
	protected Attribute<Double> x;
	protected Attribute<Double> y;

	@Override
	public final void layout(AttributeNetwork network, Attribute<Double> x, Attribute<Double> y) 
	{
		this.network = network;
		this.x = x;
		this.y = y;
		
		init();
		
		while (!stop()) {
			iterate();
		}
		
		end();
	}

	
	public abstract void init ();
	
	public abstract boolean stop ();
	
	public abstract void iterate ();

	public abstract void end ();
}
