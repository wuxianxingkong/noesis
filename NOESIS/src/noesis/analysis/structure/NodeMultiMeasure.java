package noesis.analysis.structure;


import noesis.Network;

import ikor.math.Matrix;
import ikor.math.Vector;
import ikor.model.data.DataModel;
import ikor.model.data.IntegerModel;
import ikor.model.data.RealModel;

public abstract class NodeMultiMeasure extends Matrix
{
	private Network network;
	private int measures;
	
	protected NodeMultiMeasure (Network network, int measures)
	{
		super(measures, network.size());
		
		this.network = network;
		this.measures = measures;
	}
	
	public final Network getNetwork ()
	{
		return network;
	}
	
	public final int getMeasureCount ()
	{
		return measures;
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
	
	public abstract double[] compute (int node);


	// Measure metadata
	
	public abstract String getName (int measure);
	
	public String getDescription (int measure)
	{
		return getName(measure);
	}
	
	protected static final DataModel INTEGER_MODEL = new IntegerModel();
	protected static final DataModel REAL_MODEL = new RealModel();
	
	public DataModel getModel (int measure)
	{
		return REAL_MODEL; 
	}
	
	// Standard output
	
	public String toString ()
	{
		String result = "";
		Vector measure;
		
		for (int i=0; i<measures; i++) {
			measure = new Vector(this,i);
			result += "[" + getName(i) + ": " + measure.toStringSummary() + "]";
		}
		
		return result;
	}
}
