package noesis.analysis.structure;

import ikor.model.data.DataModel;
import noesis.Network;

public class OutDegree extends NodeMeasure 
{
		
	public OutDegree (Network network)
	{
		super(network);
	}
	
	@Override
	public String getName() 
	{
		return "out-degree";
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

		return net.outDegree(node);
	}

}
