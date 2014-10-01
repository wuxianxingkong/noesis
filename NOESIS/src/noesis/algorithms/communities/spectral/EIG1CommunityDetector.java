package noesis.algorithms.communities.spectral;

// Title:       Bi-Partitioning Spectral Community Detection Algorithm
// Version:     1.0
// Copyright:   2014
// Author:      Fco. Javier Gijon & Aaron Rosas
// E-mail:      fcojaviergijon@gmail.com & aarr90@gmail.com

import ikor.math.Matrix;
import ikor.math.Vector;
import ikor.math.DenseVector;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.AttributeNetwork;

/**
 * Bi-Partitioning Spectral Community Detection Algorithm
 *
 * L. Hagen and A. B. Kahng: 
 * "New Spectral Methods for Ratio Cut Partitioning and Clustering", 
 * IEEE Transactions on Computer-Aided Design of Integrated Circuits and Systems, 
 * Volume 11, issue 9, September 1992, pp. 1074-1085.
 * DOI http://dx.doi.org/10.1109/43.159993
 * 
 * @author Aaron Rosas (aarr90@gmail.com) & Fco. Javier Gijon (fcojaviergijon@gmail.com)
 */
@Label("EIG1")
@Description("EIG1 spectral community detection algorithm")
public class EIG1CommunityDetector extends SpectralCommunityDetector 
{
    public enum ThresholdType {
        // 0.0
        ZERO, 
        // Fiedler Eigenvector average
        AVG, 
        // Fiedler Eigenvector median
        MEDIAN,
        // Max distance of elements in the Fiedler Eigenvector
        GAP
    };
 
    ThresholdType threshold;

    // Constructors
    
    public EIG1CommunityDetector(AttributeNetwork network) 
    {
        super(network);
        threshold = ThresholdType.ZERO;
    }

    public EIG1CommunityDetector(AttributeNetwork network, ThresholdType th) 
    {
        super(network);
        threshold = th;
    }

    // Computation

    @Override
    public void compute() 
    {
        // Laplacian matrix no normalized
        Matrix L = laplacian(network, Normalization.NONE);
        // Get Fiedler eigenvector
        Vector E = FiedlerEigenvector(L);
        // Partitioning of the net using 2nd eigenvector (Fiedler vector) with threshold th
        int c1 = 0, c2 = 1;
        // Compute real threshold value
        double th = threshold(E, threshold);
        Vector CL = partitioning(E, c1, c2, th);

        // Save results
        for (int i = 0; i < results.columns(); ++i) {
            results.set(0, i, CL.get(i));
        }
    }

    /**
     * Use the second smallest eigenvector of eigenvectors matrix E, also called
     * Fiedler vector, to partition of the graph by finding the optimal
     * splitting point.
     *
     * @pre Columns(E) >= 2
     * @param E Eigenvectors Matrix
     * @param cluster1 Value of cluster 1
     * @param cluster2 Value of cluster 2
     * @param th Threshold, If value is less than threshold, asign value ->
     * cluster1, else asign -> cluster2
     * @return Vector where position i correspond to cluster of element i
     */
    private static Vector partitioning (Vector E, int cluster1, int cluster2, double th) 
    {
        int size = E.size();
        DenseVector CL = new DenseVector(size);
        // If value is less than threshold, asign cluster 1, else asign cluster 2
        for (int i = 0; i < size; ++i) {
            if (E.get(i) < th) {
                CL.set(i, cluster1);
            } else {
                CL.set(i, cluster2);
            }
        }

        return CL;
    }

    /**
     * Compute threshold to use in partitioning.
     *
     * @param E Egenvector
     * @param th Type of threshold
     * @return Threshold value
     */
    private double threshold (Vector E, ThresholdType th) 
    {
        double value = 0.0;

        // Type
        switch (th) {
            // Average value
            case AVG:
                value = E.average();
                break;
            // Median value
            case MEDIAN:
                Vector M = new DenseVector(E);
                QuickSort(M);
                value = M.get(Math.round(M.size() / 2));
                break;
            // Max gap value
            case GAP:
                Vector G = new DenseVector(E);
                QuickSort(G);
                // Find max gap
                int pos = -1;
                for (int i = 0; i < G.size() - 1; ++i) {
                    if (Math.abs(G.get(i) - G.get(i + 1)) > value) {
                        value = Math.abs(G.get(i) - G.get(i + 1));
                        pos = i + 1;
                    }
                }
                if (pos != -1) {
                    value = G.get(pos);
                }
                break;
            default:
                value = 0.0;
        }

        return value;
    }
}
