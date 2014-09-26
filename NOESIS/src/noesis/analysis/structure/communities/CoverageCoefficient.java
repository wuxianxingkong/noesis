package noesis.analysis.structure.communities;

// Title:       Coverage Coefficient, between 0 and 1
// Version:     1.0
// Copyright:   2014
// Author:      Fco. Javier Gijon & Aaron Rosas
// E-mail:      fcojaviergijon@gmail.com & aarr90@gmail.com

import ikor.collection.List;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.Network;
import noesis.analysis.NodeScore;

/**
 * Coverage Coefficient, between 0 and 1
 *
 * @author Aaron Rosas (aarr90@gmail.com) & Fco. Javier Gijon (fcojaviergijon@gmail.com)
 */
@Label("Coverage Coefficient")
@Description("Coverage Coefficient")
public class CoverageCoefficient extends ClusterScoreTask 
{
    /**
     * Constructor
     * @param network Network to compute the coefficient
     * @param clusters Cluster assignment
     */
    public CoverageCoefficient(Network network, NodeScore clusters) 
    {
        super(network, clusters);
    }

    @Override
    public double compute(int node) 
    {
        Network net = getNetwork();
        
        // Common cluster
        int cluster = (int) getAssignment().get(node);
        List<Integer> nodes = getClusters().get(cluster);
        // Undirected network has 2 links per link
        double m = net.links()/2, links = 0;
        int size = nodes.size();
        
        // Count links
        for (int i = 0; i < size; ++i)
            for (int j = i+1; j < size; ++j)
                if (net.contains(nodes.get(i), nodes.get(j)))
                    links++;
        
        return (links/m)/size;
    }

    @Override
    public double overallValue() 
    {        
        return getResult().sum();
    }
    
    @Override
    public double clusterValue(int cluster) 
    {
        if (getClusters().contains(cluster)) {
            checkDone();
            return clusterValues(cluster).sum();
        } else
            return Double.NaN;
    }
}
