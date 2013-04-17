package noesis.analysis.structure;

import noesis.Network;

import noesis.algorithms.LinkVisitor;
import noesis.algorithms.traversal.NetworkBFS;
import noesis.algorithms.traversal.NetworkTraversal;

public class PathLength extends NodeMetrics 
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
		return sum() / (size()-1);
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
