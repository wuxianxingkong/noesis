package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.NodeScoreTask;
import noesis.analysis.NodeScore;

@Label("out-degree")
@Description("Out-degree")
public class OutDegree extends NodeScoreTask
{
		
	public OutDegree (Network network)
	{
		super(NodeScore.INTEGER_MODEL, network);
	}

		
	
	@Override
	public double compute (int node) 
	{
		Network net = getNetwork();

		return net.outDegree(node);
	}

}
