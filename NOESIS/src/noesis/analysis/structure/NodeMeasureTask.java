package noesis.analysis.structure;

import ikor.model.data.DataModel;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import ikor.parallel.Task;

import noesis.Network;

public abstract class NodeMeasureTask extends Task<NodeMeasure>
{
	private DataModel   model;
	private Network     network;

	
	public NodeMeasureTask (DataModel model, Network network)
	{
		this.model = model;
		this.network = network;
	}

	public NodeMeasureTask (Network network)
	{
		this(NodeMeasure.REAL_MODEL, network);
	}

		
	public final DataModel getModel ()
	{
		return model;
	}
	
	public final Network getNetwork ()
	{
		return network;
	}
	
	public String getName ()
	{
		Class type = this.getClass();
		Label label = (Label) type.getAnnotation(Label.class);
				
		if (label!=null)
			return label.value();
		else
			return null;
	}
	
	public String getDescription()
	{
		Class type = this.getClass();
		Description description = (Description) type.getAnnotation(Description.class);
		
		if (description!=null)
			return description.value();
		else
			return null;
	}
	


	// Computation template method
	
	protected NodeMeasure measure = null;
	
	@Override
	public NodeMeasure call() 
	{
		compute();
		
		return measure;
	}

	public void checkDone ()
	{
		if (measure==null)
			compute();
	}
	
	public void compute ()
	{
		int size = network.size();
		
		measure = new NodeMeasure(this,network);
	
		for (int node=0; node<size; node++)
			measure.set (node, compute(node));
	}
	
	public abstract double compute (int node);
	

}
