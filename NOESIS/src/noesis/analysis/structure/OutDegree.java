package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;

@Label("out-degree")
@Description("Out-degree")
public class OutDegree extends NodeMeasureTask
{
		
	public OutDegree (Network network)
	{
		super(NodeMeasure.INTEGER_MODEL, network);
	}

		
	
	@Override
	public double compute (int node) 
	{
		Network net = getNetwork();

		return net.outDegree(node);
	}

}
