package noesis.analysis.structure;

import noesis.Network;

import noesis.algorithms.LinkVisitor;
import noesis.algorithms.traversal.NetworkBFS;
import noesis.algorithms.traversal.NetworkTraversal;

public class PathLength extends NodeMeasure 
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
	public String getName() 
	{
		return "path-length";
	}	

	
	@Override
	public void compute() 
	{
		NetworkTraversal bfs = new NetworkBFS(getNetwork());
		
		bfs.setLinkVisitor(new BFSVisitor(this));
		
		bfs.traverse(node);

		done = true;
	}

	public double compute(int node) 
	{
		checkDone();		
		return get(node);
	}
	
	
	public double averagePathLength ()
	{
		checkDone();
		
		int reachable = reachableNodes();
		
		if (reachable>0)
			return sum() / reachable;
		else
			return 0.0;
	}
	
	public double closeness ()
	{
		checkDone();
		
		int    reachable = reachableNodes();
		double sumPathLengths = sum();
		
		if (sumPathLengths>0)
			return reachable / sumPathLengths;
		else		
			return 0.0;
	}
	
	public double reachable ()
	{
		checkDone();
		
		return ((double)reachableNodes())/(size()-1); 
	}
	
	public double decay (double delta)
	{
		checkDone();
		
		double sum = 0;
		
		for (int i=0; i<size(); i++) {
			
			if (get(i)>0)
				sum += Math.pow(delta, get(i));
		}
		
		return sum;
	}
	
	public int reachableNodes ()
	{
		int total = 0;
		
		for (int i=0; i<size(); i++)
			if (get(i)>0)
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
			   && (metrics.get(destination)==0) )
				metrics.set ( destination, metrics.get(source) + 1);		
		}
		
	}
	
}
