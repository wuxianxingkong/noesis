package noesis.analysis.structure;

import ikor.collection.List;
import ikor.model.data.DataModel;

import ikor.parallel.Task;

import noesis.CollectionFactory;
import noesis.Network;

public abstract class NodeMeasureMultiTask extends Task<List<NodeMeasure>>
{
	private Network     network;
	
	public NodeMeasureMultiTask (Network network)
	{
		this.network = network;
	}

	
	public final Network getNetwork ()
	{
		return network;
	}
	
	
	
	protected final List<NodeMeasure> createMeasures (Network network)
	{
		String[]    names = getNames();
		String[]    descriptions = getDescriptions();
		DataModel[] models = getModels();
		
		List<NodeMeasure> measures = CollectionFactory.createList();
		
		for (int i=0; i<names.length; i++) {
			NodeMeasure measure = new NodeMeasure(names[i], network);
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
	
	protected List<NodeMeasure> measures = null;

	@Override
	public List<NodeMeasure> call() 
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
