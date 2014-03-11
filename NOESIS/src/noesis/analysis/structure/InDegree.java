package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.Network;

@Label("in-degree")
@Description("In-degree")
public class InDegree extends NodeMeasureTask
{
		
	public InDegree (Network network)
	{
		super(NodeMeasure.INTEGER_MODEL, network);
	}

	
	@Override
	public double compute (int node) 
	{
		Network net = getNetwork();
		
		return net.inDegree(node);
	}
}