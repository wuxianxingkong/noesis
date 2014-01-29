package noesis.analysis.structure;


import noesis.Network;

import ikor.math.Matrix;
import ikor.math.Vector;
import ikor.model.data.DataModel;

public class NodeMultiMeasure extends Matrix
{
	private Network network;
	private int measures;
	private String id[];
	private String description[];
	private DataModel model[];
	
	// Constructors
	
	protected NodeMultiMeasure (NodeMultiMeasureTask creator, Network network, int measures)
	{
		super(measures, network.size());
		
		this.network = network;
		this.measures = measures;
		this.id = creator.getNames();
		this.description = creator.getDescriptions();
		this.model = creator.getModels();
	}
	

	// Getters
	
	public final Network getNetwork ()
	{
		return network;
	}
	
	public final int getMeasureCount ()
	{
		return measures;
	}

	
	// Measure metadata
	
	public String getName (int measure)
	{
		return id[measure];
	}
	
	public String getDescription (int measure)
	{
		return description[measure];
	}
	
	public DataModel getModel (int measure)
	{
		return model[measure]; 
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
