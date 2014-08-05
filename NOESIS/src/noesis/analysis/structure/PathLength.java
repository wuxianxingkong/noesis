package noesis.analysis.structure;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.algorithms.LinkVisitor;
import noesis.algorithms.traversal.NetworkBFS;
import noesis.algorithms.traversal.NetworkTraversal;
import noesis.analysis.NodeScoreTask;
import noesis.analysis.NodeScore;

@Label("path-length")
@Description("Path length")
public class PathLength extends NodeScoreTask
{
	private int node;
	private NodeScore score;
	
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
		
		score = new NodeScore(this,network);

		NetworkTraversal bfs = new NetworkBFS(network);
		
		bfs.setLinkVisitor(new BFSVisitor(this));
		
		bfs.traverse(node);
		
		setResult(score);
	}

	public double compute(int node) 
	{
		checkDone();		
		
		return getResult(node);
	}
	
	
	public double averagePathLength ()
	{
		checkDone();
		
		int reachable = reachableNodes();
		
		if (reachable>0)
			return getResult().sum() / reachable;
		else
			return 0.0;
	}
	
	public double closeness ()
	{
		checkDone();
		
		int    reachable = reachableNodes();
		double sumPathLengths = getResult().sum();
		
		if (sumPathLengths>0)
			return reachable / sumPathLengths;
		else		
			return 0.0;
	}
	
	public double reachable ()
	{
		checkDone();
		
		return ((double)reachableNodes())/(getResult().size()-1); 
	}
	
	public double decay (double delta)
	{
		checkDone();
		
		int    nodes = getNetwork().size();
		double sum = 0;
		
		for (int i=0; i<nodes; i++) {
			
			if (getResult(i)>0)
				sum += Math.pow(delta, getResult(i));
		}
		
		return sum;
	}
	
	public int reachableNodes ()
	{
		int nodes = getNetwork().size();
		int total = 0;
		
		for (int i=0; i<nodes; i++)
			if (getResult(i)>0)
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
			   && (metrics.score.get(destination)==0) )
				metrics.score.set ( destination, metrics.score.get(source) + 1);		
		}
		
	}
	
}
