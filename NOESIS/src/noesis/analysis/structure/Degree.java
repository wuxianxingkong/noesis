package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.Network;

@Label("degree")
@Description("Node degree")
public class Degree extends NodeMeasureTask
{
		
	public Degree (Network network)
	{
		super(NodeMeasure.INTEGER_MODEL, network);
	}
	
	
	@Override
	public double compute (int node) 
	{
		Network net = getNetwork();
		
		return net.inDegree(node) + net.outDegree(node);
	}

}