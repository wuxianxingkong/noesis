package noesis.analysis.structure;

// Title:       Link betweenness centrality
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;

import noesis.algorithms.LinkVisitor;
import noesis.algorithms.traversal.NetworkBFS;
import noesis.analysis.structure.LinkMeasure;
import noesis.network.LinkIndexer;

/**
 * Link betweenness score
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
@Label("Link betweenness score")
@Description("Link betweenness score")
public class LinkBetweennessScore extends LinkMeasureTask
{
	private int distance[];
	private int geodesics[];
	private int visits[];
    private double betweenness[];
    
	private int node;
	private int visited;	 
    
    /**
     * Constructor
     * 
     * @param network Input network
     * @param linkIndexer Link indexer
     * @param node Source node
     */
    public LinkBetweennessScore(Network network, LinkIndexer linkIndexer, int node) 
    {
        super(network, linkIndexer);

		this.distance    = new int[network.size()];
		this.geodesics   = new int[network.size()];
		this.visits      = new int[network.size()];
		this.betweenness = new double[network.size()];
		this.node        = node;
		this.visited     = 0;
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
	
	public int visited ()
	{
		return visited;
	}
	
	public double betweenness (int node)
	{
		return betweenness[node];
	}

    @Override
    public double compute(int source, int destination) 
    {
    	checkDone();
    	
    	return measure.get(source,destination);
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

		measure = new LinkMeasure(this,net,getLinkIndex());
			
		for (int i=visited-1; i>=0; i--) {
			
			int currentNode = visits[i];
			int currentDistance = distance(currentNode);
			double currentGeodesics = geodesics(currentNode);
			
			// Node betweenness 
			
			double currentScore = computeNodeBetweennessScore(currentNode);
			
			// Share node betweenness score among incoming links
			
			int indegree = net.inDegree(currentNode);
			int inlinks[] = net.inLinks(currentNode);

			for (int j=0; j<indegree; j++) {
				if ( distance(inlinks[j])==currentDistance-1 )
					measure.set(inlinks[j],currentNode, measure.get(inlinks[j],currentNode) + geodesics(inlinks[j])*currentScore/currentGeodesics);
			}
		}
    }

	private double computeNodeBetweennessScore (int node)
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
					score += betweenness[links[j]] * geodesics / geodesics(links[j]);
				}
			}
		}	
		
		betweenness[node] = score;
		
		return score;
	}
    

    /**
     * Link visitor
     */
	
	private class GeodesicVisitor extends LinkVisitor
	{
		private LinkBetweennessScore data;
		
		public GeodesicVisitor (LinkBetweennessScore data)
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
