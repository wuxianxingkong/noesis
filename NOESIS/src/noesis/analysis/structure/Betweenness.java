package noesis.analysis.structure;

import ikor.math.Vector;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import ikor.parallel.*;
import ikor.parallel.combiner.VectorAccumulator;

import noesis.Network;


// Betweenness centrality, between (2n-1) and n^2-(n-1)

@Label("betweenness")
@Description("Node betweenness")
public class Betweenness extends NodeMeasureTask 
{
	public Betweenness (Network network)
	{
		super(network);
	}	

	
	@Override
	public double compute(int node) 
	{
		checkDone();	
		
		return measure.get(node);
	}	

	@Override
	public void compute ()
	{
		Network net = getNetwork();
		int     size = net.size();
		
		measure = new NodeMeasure(this,net);
	
		// Iterative algorithm
        /*
		BetweennessScore score;
		
		for (int node=0; node<size; node++) {
			
			score = new BetweennessScore(net,node);
			score.compute();
			
			for (int i=0; i<size; i++) {
			    measure.set (i, measure.get(i) + score.get(i) );
			}
		}
		*/
		
		// Parallel algorithm
		
		Vector score = (Vector) Parallel.reduce( new BetweennessKernel(net), new VectorAccumulator(size), 0, size-1);

		for (int i=0; i<size; i++) {
		    measure.set (i, score.get(i) );
		}
	}	
	
	
	class BetweennessKernel implements Kernel<Vector>
	{
		private Network net;
		
		public BetweennessKernel (Network net)
		{
			this.net = net;
		}
		
		@Override
		public Vector call (int index) 
		{
			BetweennessScore score = new BetweennessScore(net, index);
			
			score.compute();
			
			return score.measure;
		}
	}		
	
}
