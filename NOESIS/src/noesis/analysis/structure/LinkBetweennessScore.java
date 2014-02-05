package noesis.analysis.structure;

// Title:       Link betweenness centrality
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.Network;

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
	private BetweennessScore score;
    
	private int node;
    
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

		this.node = node;
		this.score = new BetweennessScore(network,node);
    }
    
    // Getters


	public int node()
	{
		return node;
	}
	
	public int distance (int node)
	{
		return score.distance(node);
	}
	
	public int geodesics (int node)
	{
		return score.geodesics(node);
	}
	
	public int visit (int pos)
	{
		return score.visit(pos);
	}	
	
	public int visited ()
	{
		return score.visited();
	}
	
	public double betweenness (int node)
	{
		return score.betweenness(node);
	}
	
	// Link betweenness score computation

    @Override
    public double compute(int source, int destination) 
    {
    	checkDone();
    	
    	return measure.get(source,destination);
    }
	
    @Override
    public void compute() 
    {
    	Network net = getNetwork();
    	
    	// Compute node betweenness scores
    	
    	score.compute();
    			
		// Betweenness scores from shortest paths (reverse order)

		measure = new LinkMeasure(this,net,getLinkIndex());
			
		for (int i=visited()-1; i>=0; i--) {
			
			int currentNode = visit(i);
			int currentDistance = distance(currentNode);
			double currentGeodesics = geodesics(currentNode);
			
			// Current node betweenness score 
			
			double currentScore = betweenness(currentNode);
			
			// Share node betweenness score among incoming links
			
			int indegree = net.inDegree(currentNode);
			int inlinks[] = net.inLinks(currentNode);

			for (int j=0; j<indegree; j++) {
				if ( distance(inlinks[j])==currentDistance-1 )
					measure.set(inlinks[j],currentNode, measure.get(inlinks[j],currentNode) + geodesics(inlinks[j])*currentScore/currentGeodesics);
			}
		}
    }
}
