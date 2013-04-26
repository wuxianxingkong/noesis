package noesis.analysis.structure;

import ikor.model.data.DataModel;
import noesis.Network;

public class InDegree extends NodeMeasure 
{
		
	public InDegree (Network network)
	{
		super(network);
	}
	
	@Override
	public String getName() 
	{
		return "in-degree";
	}	
	
	@Override
	public DataModel getModel()
	{
		return INTEGER_MODEL;
	}
	
	@Override
	public double compute (int node) 
	{
		Network net = getNetwork();
		
		return net.inDegree(node);
	}



}