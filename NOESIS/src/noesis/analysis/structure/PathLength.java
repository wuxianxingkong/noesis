package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.Network;

import noesis.algorithms.LinkVisitor;
import noesis.algorithms.traversal.NetworkBFS;
import noesis.algorithms.traversal.NetworkTraversal;

@Label("path-length")
@Description("Path length")
public class PathLength extends NodeMeasureTask
{
	private int node;
	
	public PathLength (Network network, int node)
	{
		super(network);
		
		this.node = node;
	}
	
	
	public int node()
	{
		return node;
	}
	

	
	@Override
	public void compute() 
	{
		Network network = getNetwork();
		
		measure = new NodeMeasure(this,network);

		NetworkTraversal bfs = new NetworkBFS(network);
		
		bfs.setLinkVisitor(new BFSVisitor(this));
		
		bfs.traverse(node);
	}

	public double compute(int node) 
	{
		checkDone();		
		return measure.get(node);
	}
	
	
	public double averagePathLength ()
	{
		checkDone();
		
		int reachable = reachableNodes();
		
		if (reachable>0)
			return measure.sum() / reachable;
		else
			return 0.0;
	}
	
	public double closeness ()
	{
		checkDone();
		
		int    reachable = reachableNodes();
		double sumPathLengths = measure.sum();
		
		if (sumPathLengths>0)
			return reachable / sumPathLengths;
		else		
			return 0.0;
	}
	
	public double reachable ()
	{
		checkDone();
		
		return ((double)reachableNodes())/(measure.size()-1); 
	}
	
	public double decay (double delta)
	{
		checkDone();
		
		double sum = 0;
		
		for (int i=0; i<measure.size(); i++) {
			
			if (measure.get(i)>0)
				sum += Math.pow(delta, measure.get(i));
		}
		
		return sum;
	}
	
	public int reachableNodes ()
	{
		int total = 0;
		
		for (int i=0; i<measure.size(); i++)
			if (measure.get(i)>0)
				total++;
		
		return total;
	}
	
	
	// Visitor
	
	private class BFSVisitor extends LinkVisitor
	{
		private PathLength metrics;
		
		public BFSVisitor (PathLength metrics)
		{
			this.metrics = metrics;
		}

		@Override
		public void visit(int source, int destination) 
		{
			if (  (destination!=metrics.node()) 
			   && (metrics.measure.get(destination)==0) )
				metrics.measure.set ( destination, metrics.measure.get(source) + 1);		
		}
		
	}
	
}
