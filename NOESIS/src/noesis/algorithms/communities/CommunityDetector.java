package noesis.algorithms.communities;

// Title:       Community Detection Algorithm
// Version:     1.0
// Copyright:   2014
// Author:      Fco. Javier Gijon & Aaron Rosas
// E-mail:      fcojaviergijon@gmail.com & aarr90@gmail.com

import ikor.math.DenseMatrix;
import ikor.math.DenseVector;
import ikor.math.Matrix;
import ikor.math.Vector;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.AttributeNetwork;
import noesis.analysis.NodeScore;
import noesis.analysis.structure.communities.ModularityCoefficient;

/**
 * Community Detection Algorithm
 *
 * @author Aaron Rosas (aarr90@gmail.com) & Fco. Javier Gijon (fcojaviergijon@gmail.com)
 */
@Label("CommunityDetector")
@Description("Community detection algorithm")
public abstract class CommunityDetector 
{
    protected AttributeNetwork network;
    protected Matrix results;

    /**
     * Constructor.
     *
     * @param network Network to apply Community detecction
     */
    public CommunityDetector (AttributeNetwork network) 
    {
        this.network = network;
        this.results = new DenseMatrix(1, network.nodes(), -1);
    }

    /**
     * Apply algorithm and save results.
     */
    public abstract void compute();

    /**
     * Return attribute 'results'.
     *
     * @return results (all results are -1 unless compute method is called)
     */
    public Matrix getResults() 
    {
        return results;
    }

    /**
     * Return the best assignment of clusters using modularity
     *
     * @return Vector with best assignement
     */
    public Vector getBest() 
    {
        double bm = Double.NEGATIVE_INFINITY;
        DenseVector dv = new DenseVector(network.nodes());

        // Compute best assignment
        for (int k = 0; k < results.rows(); ++k) {
            NodeScore cl = new NodeScore("cluster assignment",network);
            cl.set(results.getRow(k));
            // Compute modularity
            ModularityCoefficient mod = new ModularityCoefficient(network, cl);
            double m = mod.overallValue();
            if (m > bm) {
                bm = m;
                dv = new DenseVector(results.getRow(k));
            }
        }

        return dv;
    }
}
