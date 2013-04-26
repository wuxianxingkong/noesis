package noesis.analysis.structure;

import noesis.Network;

/**
 * Decay centrality (weighs distance exponentially).
 * 
 * NOTE: Delta near 1 becomes component size, delta near 0 becomes degree.
 * 
 * decay = sum { delta ^ (path length) }
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class Decay extends NodeMeasure 
{
	public static final double DEFAULT_DECAY = 0.5;
	
	private double delta;
	
	
	public Decay (Network network)
	{
		this(network, DEFAULT_DECAY);
	}
	
	public Decay (Network network, double delta)
	{
		super(network);
		
		this.delta = delta;
	}	

	public double getDelta ()
	{
		return delta;
	}
	
	@Override
	public String getName() 
	{
		return "decay-"+Math.round(100*delta);
	}	

	@Override
	public double compute(int node) 
	{
		Network    network = getNetwork();
		PathLength paths = new PathLength(network, node);
		
		paths.compute();
		
		return paths.decay(delta);
	}	
}
