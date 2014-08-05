package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.NodeScoreTask;

@Label("out-degree-norm")
@Description("Normalized out-degree")
public class NormalizedOutDegree extends NodeScoreTask
{
		
	public NormalizedOutDegree (Network network)
	{
		super(network);
	}
	
	
	@Override
	public double compute (int node) 
	{
		Network net = getNetwork();
		
		if (net.size()>1)
			return ((double)net.outDegree(node))/(net.size()-1);
		else
			return 0;
	}

}