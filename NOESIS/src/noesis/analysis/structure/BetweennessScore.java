package noesis.analysis.structure;

import noesis.Network;

import noesis.algorithms.LinkVisitor;
import noesis.algorithms.traversal.NetworkBFS;

public class BetweennessScore extends NodeMetrics 
{
	private int distance[];
	private int geodesics[];
	private int visits[];
	
	private int node;
	private int visited;
	
	public BetweennessScore (Network network, int node)
	{
		super(network);
		
		this.distance  = new int[network.size()];
		this.geodesics = new int[network.size()];
		this.visits    = new int[network.size()];
		this.node      = node;
	}
	
	
	public int node()
	{
		return node;
	}
	
	public int distance (int node)
	{
		return distance[node];
	}
	
	public int geodesics (int node)
	{
		return geodesics[node];
	}
	
	
	@Override
	public void compute() 
	{
		Network    net = getNetwork();
		NetworkBFS bfs = new NetworkBFS(net);
	
		// 1 path to source node (at distance 0)
		
		geodesics[node] = 1;	
		
		// BFS
		
		visits[0] = node;
		visited   = 1;
		
		bfs.setLinkVisitor(new GeodesicVisitor(this));
		bfs.traverse(node);
		
		// Betweenness from shortest paths (reverse order)
		
		for (int i=visited-1; i>=0; i--) {
			set (visits[i], computeScore(visits[i]));
		}

		done = true;
	}

	public double compute (int node) 
	{
		checkDone();		
		return get(node);
	}
	
	private double computeScore (int node)
	{
		Network network = getNetwork();
		int degree  = network.outDegree(node);
		int links[] = network.outLinks(node);
		
		double distance  = distance(node);
		double geodesics = geodesics(node);
		double score     = 1;

		if (links!=null) {			
			for (int j=0; j<degree; j++) {
				
				if (distance(links[j])==distance+1) {
					// score[node] += score[j]*geodesics[node]/geodesics[j]  
					score += get(links[j]) * geodesics / geodesics(links[j]);
				}
			}
		}	
		
		return score;
	}
		
	// Visitor
	
	private class GeodesicVisitor extends LinkVisitor
	{
		private BetweennessScore data;
		
		public GeodesicVisitor (BetweennessScore data)
		{
			this.data = data;
		}

		@Override
		public void visit(int source, int destination) 
		{
			int d = data.distance[source];
			
			if ( destination!=data.node ) {		
			
				if (data.distance[destination]==0) {
					
					// Visited node
					data.visits[data.visited] = destination;
					data.visited++;
					
					// d[destination] = d[source] + 1
					data.distance[destination]= d+1;
					
					// w[destination] = w[source]
					data.geodesics[destination] = data.geodesics[source];
					
				} else if ( data.distance[destination]==d+1) {
					
					// w[destination] += d[source]
					data.geodesics[destination] += data.geodesics[source];
				}
			}
		}
	}
	
}
