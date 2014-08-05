package noesis.analysis;

import ikor.model.data.DataModel;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import ikor.parallel.Task;
import noesis.Network;

/**
 * Task for computing node scores.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class NodeScoreTask extends Task<NodeScore>
{
	private DataModel   model;
	private Network     network;

	
	public NodeScoreTask (DataModel model, Network network)
	{
		this.model = model;
		this.network = network;
	}

	public NodeScoreTask (Network network)
	{
		this(NodeScore.REAL_MODEL, network);
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
	
	
	public final double getResult (int node)
	{
		return getResult().get(node);
	}
	
	public final void setResult (int node, double value)
	{
		getResult().set(node,value);
	}
	
	public final void setResult (double values[])
	{
		NodeScore score = new NodeScore(this,getNetwork());
		
		score.set(values);
		
		setResult(score);
	}

	// Computation template method
	
	@Override
	public NodeScore call() 
	{
		compute();
		
		return getResult();
	}

	public void checkDone ()
	{
		if (getResult()==null)
			compute();
	}
	
	public void compute ()
	{
		int size = network.size();
		
		NodeScore score = new NodeScore(this,network);
	
		for (int node=0; node<size; node++)
			score.set (node, compute(node));
		
		setResult(score);
	}
	
	public abstract double compute (int node);
	
}
