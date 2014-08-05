package noesis.analysis.structure;

// Title:       Link betweenness centrality
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.Vector;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import ikor.parallel.*;
import ikor.parallel.combiner.VectorAccumulator;
import noesis.Network;
import noesis.analysis.LinkScoreTask;
import noesis.analysis.LinkScore;
import noesis.network.LinkIndexer;

/**
 * Link betweenness centrality
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

@Label("link-betweenness")
@Description("Link betweenness")
public class LinkBetweenness extends LinkScoreTask 
{
     /**
     * Constructor
     * 
     * @param network Input network
     */
    public LinkBetweenness(Network network)
    {
        super(network);
    }
    
    /**
     * Constructor
     *  
     * @param network Input network
     * @param index Link index
     */
    public LinkBetweenness (Network network, LinkIndexer index)
    {
    	super(network,index);
    }

    
    /**
     * Link betweenness computation
     */
    @Override
    public double compute (int source, int destination) 
    {
    	checkDone();
    	    	
    	return getResult().get(source,destination); 
    }

    /**
     *  Link betweenness computation
     */
   
    @Override
    public void compute() 
    {
    	Network net = getNetwork();
    	LinkIndexer index = getLinkIndex();
    	
        int size = net.size();
        int links = net.links();
    
        LinkScore result = new LinkScore(this, net, index);

        // Parallel algorithm 
        
        Vector score = (Vector) Parallel.reduce(new LinkBetweennessKernel(), new VectorAccumulator(links), 0, size - 1);

        for (int i = 0; i < score.size(); i++) 
            result.set(i, score.get(i));
        
        setResult(result);
    }


    /**
     * Kernel implementation
     */
    private class LinkBetweennessKernel implements Kernel<Vector> 
    {
        @Override
        public Vector call(int index) 
        {
            LinkBetweennessScore score = new LinkBetweennessScore( getNetwork(), getLinkIndex(), index);
            
            return score.call();
        }
    }

}
