package noesis.analysis.structure;

import ikor.model.data.DataModel;

import ikor.parallel.Task;

import noesis.Network;

public abstract class NodeMultiMeasureTask extends Task<NodeMultiMeasure>
{
	private Network     network;
	
	public NodeMultiMeasureTask (Network network)
	{
		this.network = network;
	}

	
	public final Network getNetwork ()
	{
		return network;
	}
	
	public abstract String[] getNames ();
	
	public abstract String[] getDescriptions();
	
	public abstract DataModel[] getModels ();
	
	
	// Computation template method
	
	protected NodeMultiMeasure measure = null;

	@Override
	public NodeMultiMeasure call() 
	{
		compute();
		
		return measure;
	}

	public void checkDone ()
	{
		if (measure==null)
			compute();
	}
	
	public abstract void compute ();	

	public abstract double[] compute (int node);
}
