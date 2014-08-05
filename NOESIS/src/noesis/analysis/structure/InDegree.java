package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.NodeScoreTask;
import noesis.analysis.NodeScore;

@Label("in-degree")
@Description("In-degree")
public class InDegree extends NodeScoreTask
{
		
	public InDegree (Network network)
	{
		super(NodeScore.INTEGER_MODEL, network);
	}

	
	@Override
	public double compute (int node) 
	{
		Network net = getNetwork();
		
		return net.inDegree(node);
	}
}