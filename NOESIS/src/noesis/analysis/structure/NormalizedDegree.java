package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.Network;

@Label("in-degree-norm")
@Description("Normalized in-degree")
public class NormalizedDegree extends NodeMeasureTask 
{
		
	public NormalizedDegree (Network network)
	{
		super(network);
	}

	
	@Override
	public double compute (int node) 
	{
		Network net = getNetwork();
		
		if (net.size()>1)
			return ((double) net.inDegree(node) + net.outDegree(node))/(2.0*(net.size()-1));
		else
			return 0;
	}

}