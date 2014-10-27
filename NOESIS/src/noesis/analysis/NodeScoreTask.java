package noesis.analysis;

import ikor.model.data.DataModel;
import noesis.Network;
import noesis.NoesisTask;

/**
 * Task for computing node scores.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class NodeScoreTask extends NoesisTask<NodeScore>
{
	private DataModel   model;
	private Network     network;

	// Constructors
	
	public NodeScoreTask (DataModel model, Network network)
	{
		this.model = model;
		this.network = network;
	}

	public NodeScoreTask (Network network)
	{
		this(NodeScore.REAL_MODEL, network);
	}

	// Getters
		
	public final DataModel getModel ()
	{
		return model;
	}
	
	public final Network getNetwork ()
	{
		return network;
	}
	
	// Task result
	
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

	public void checkDone ()
	{
		if (getResult()==null)
			compute();
	}

	@Override
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
