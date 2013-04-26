package noesis.analysis.structure;


import noesis.Network;

import ikor.math.Vector;
import ikor.model.data.DataModel;
import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;

public abstract class NodeMeasure extends Vector
{
	private Network network;
	
	protected NodeMeasure (Network network)
	{
		super(network.size());
		
		this.network = network;
	}
	
	public final Network getNetwork ()
	{
		return network;
	}
	

	// Computation template method
	
	protected boolean done = false;
	
	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
	
		for (int node=0; node<size; node++)
			set (node, compute(node));
		
		done = true;
	}
	
	public final boolean checkDone ()
	{
		if (!done)
			compute();
		
		return done;
	}
	
	public abstract double compute (int node);


	// Measure metadata
	
	public abstract String getName ();
	
	public String getDescription ()
	{
		return getName();
	}
	
	protected static final DataModel INTEGER_MODEL = new IntegerModel();
	protected static final DataModel REAL_MODEL = new RealModel();
	
	public DataModel getModel ()
	{
		return REAL_MODEL; 
	}
	
	// Standard output
	
	public String toString ()
	{
		return getName() + ": " + toStringSummary();
	}
}
