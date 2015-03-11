package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.NodeScoreTask;
import noesis.analysis.NodeScore;

@Label("degree")
@Description("Node degree")
public class Degree extends NodeScoreTask
{
		
	public Degree (Network network)
	{
		super(NodeScore.INTEGER_MODEL, network);
	}
	
	
	@Override
	public double compute (int node) 
	{
		Network net = getNetwork();
		
		return net.inDegree(node) + net.outDegree(node);
	}
	
	// Average node degree
	
	public double averageDegree ()
	{
		Network net = getNetwork();
		
		return (double) net.links() / (double) net.nodes();
	}

	// Network heterogeneity (variance of node degrees)
	
	public double heterogeinity ()
	{
		checkDone();
		
		double d;
		double s = 0;
		double s2 = 0;
		
		NodeScore degree = getResult();
		int nodes = degree.size(); 
		
		for (int i=0; i<nodes; i++) {
			d = degree.get(i)/2;
			s += d;
			s2 += d*d;
		}
		
		return s2 / (s*s) * nodes;
	}
}