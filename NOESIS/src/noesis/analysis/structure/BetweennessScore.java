package noesis.analysis.structure;

// Title:       Betweenness centrality
// Version:     1.0
// Copyright:   2013
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.Network;

import noesis.algorithms.LinkVisitor;
import noesis.algorithms.traversal.NetworkBFS;

/**
 * Betweenness score 
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

@Label("betweenness-score")
@Description("Betweenness score")
public class BetweennessScore extends NodeMeasureTask
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
	
	// Getters

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
	
	public int visit (int pos)
	{
		return visits[pos];
	}
	
	public int visited ()
	{
		return visited;
	}
	
	public double betweenness (int node)
	{
		if (measure!=null)
			return measure.get(node);
		else
			return 0.0;
	}
	
	// Betweenness score computation
	
	@Override
	public void compute() 
	{
		Network    net = getNetwork();
		NetworkBFS bfs = new NetworkBFS(net);
	
		measure = new NodeMeasure(this,net);
		
		// 1 path to source node (at distance 0)
		
		geodesics[node] = 1;	
		
		// BFS
		
		visits[0] = node;
		visited   = 1;
		
		bfs.setLinkVisitor(new GeodesicVisitor(this));
		bfs.traverse(node);
		
		// Betweenness from shortest paths (reverse order)
		
		for (int i=visited-1; i>=0; i--) {
			measure.set (visits[i], nodeScore(visits[i]) );
		}
	}

	
	public double compute (int node) 
	{
		checkDone();		
		
		return measure.get(node);
	}
	
	
	// Node betweenness score
	
	private double nodeScore (int node)
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
					score += measure.get(links[j]) * geodesics / geodesics(links[j]);
				}
			}
		}	
		
		return score;
	}
		
	
	// Link visitor for network traversal
	
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
