package noesis.analysis;

import ikor.collection.CollectionFactory;
import ikor.collection.List;
import ikor.model.data.DataModel;
import ikor.parallel.Task;
import noesis.Network;

/**
 * Task for computing a set of node scores.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class NodeScoreGroupTask extends Task<List<NodeScore>>
{
	private Network     network;
	
	public NodeScoreGroupTask (Network network)
	{
		this.network = network;
	}

	
	public final Network getNetwork ()
	{
		return network;
	}
	
	
	
	protected final List<NodeScore> createMeasures (Network network)
	{
		String[]    names = getNames();
		String[]    descriptions = getDescriptions();
		DataModel[] models = getModels();
		
		List<NodeScore> measures = CollectionFactory.createList();
		
		for (int i=0; i<names.length; i++) {
			NodeScore measure = new NodeScore(names[i], network);
			measure.setMetadata(this);
			measure.setName(names[i]);
			measure.setDescription(descriptions[i]);
			measure.setModel(models[i]);
			measures.add(measure);
		}
		
		return measures;
	}
	
	protected abstract String[] getNames ();
	
	protected abstract String[] getDescriptions();
	
	protected abstract DataModel[] getModels ();
	
	
	// Computation template method
	
	protected List<NodeScore> measures = null;

	@Override
	public List<NodeScore> call() 
	{
		compute();
		
		return measures;
	}

	public void checkDone ()
	{
		if (measures==null)
			compute();
	}
	
	public abstract void compute ();	

	public abstract double[] compute (int node);
}
