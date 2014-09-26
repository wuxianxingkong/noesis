package noesis.analysis.structure.communities;

// Title:       Modularity Coefficient, between -1 and 1
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
 * Modularity Coefficient, between -1 and 1
 *
 * @author Aaron Rosas (aarr90@gmail.com) & Fco. Javier Gijon (fcojaviergijon@gmail.com)
 */
@Label("Modularity")
@Description("Modularity")
public class ModularityCoefficient extends ClusterScoreTask 
{
    /**
     * Constructor
     * @param network Network to compute the coefficient
     * @param clusters Cluster assignment
     */
    public ModularityCoefficient(Network network, NodeScore clusters) 
    {
        super(network, clusters);
    }

    @Override
    public double compute(int node) 
    {
        double sum = 0.0;
        Network net = getNetwork();
        int m = net.links();
        int degree = net.degree(node);

        // List nodes with common cluster
        int cluster = (int) getAssignment().get(node);
        List<Integer> nodes = getClusters().get(cluster);

        for (int i = 0; i < nodes.size(); ++i) {
            double v = 0;
            int node2 = nodes.get(i);
            if (net.get(node, node2) != null) {
                v = 1;
            }
            double kk = ((double) (degree * net.degree(node2))) / m;
            sum += (v - kk);

        }

        return sum/m;
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