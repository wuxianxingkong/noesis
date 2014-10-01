package noesis.algorithms.communities.partitioning;

// Title:       K-Means Community Detection Algorithm
// Version:     1.0
// Copyright:   2014
// Author:      Fco. Javier Gijon & Aaron Rosas
// E-mail:      fcojaviergijon@gmail.com & aarr90@gmail.com

import ikor.math.DenseMatrix;
import ikor.math.DenseVector;
import ikor.math.random.Random;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.AttributeNetwork;
import noesis.algorithms.visualization.FruchtermanReingoldLayout;
import static noesis.algorithms.communities.spectral.SpectralCommunityDetector.kMeans;
import static noesis.algorithms.visualization.NetworkLayout.MARGIN;

/**
 * K-Means Algorithm
 *
 * @author Aaron Rosas (aarr90@gmail.com) & Fco. Javier Gijon (fcojaviergijon@gmail.com)
 */
@Label("KMeans Community Detection")
@Description("KMeans Community Detection Algorithm")
public class KMeansCommunityDetector extends PartitioningCommunityDetector 
{
    private int K;

    public KMeansCommunityDetector(AttributeNetwork network) 
    {
        super(network);
        K = 3;
    }

    public KMeansCommunityDetector(AttributeNetwork network, int K) 
    {
        super(network);
        this.K = K;
    }

    @Override
    public void compute() 
    {
        // Init with Fruchterman-Reingold for distribute the nodes in the space
        for (int i = 0; i < network.nodes(); ++i) {
            double x = MARGIN + (1 - 2 * MARGIN) * Random.random();
            double y = MARGIN + (1 - 2 * MARGIN) * Random.random();
            network.setNodeAttribute("x", i, x + "");
            network.setNodeAttribute("y", i, y + "");
        }
        FruchtermanReingoldLayout frl = new FruchtermanReingoldLayout();
        frl.layout(network, network.getNodeAttribute("x"), network.getNodeAttribute("y"));

        // Create matrix with results of Fruchterman Reingold Layout
        DenseMatrix F = new DenseMatrix(network.nodes(), 2);
        for (int i = 0; i < F.rows(); ++i) {
            F.set(i, 0, (double) network.getNodeAttribute("x").get(i));
            F.set(i, 1, (double) network.getNodeAttribute("y").get(i));
        }

        // K-means algorithm 
        DenseVector CL = kMeans(F, K, 1e10);

        // Save results
        for (int i = 0; i < results.columns(); ++i) {
            results.set(0, i, CL.get(i));
        }
    }
}
