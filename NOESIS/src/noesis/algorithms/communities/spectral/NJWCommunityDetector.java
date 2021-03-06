package noesis.algorithms.communities.spectral;

// Title:       Ng, Jordan and Weiss spectral algorithm for k-ways partitioning
// Version:     1.0
// Copyright:   2014
// Author:      Fco. Javier Gijon & Aaron Rosas
// E-mail:      fcojaviergijon@gmail.com & aarr90@gmail.com

import ikor.math.Matrix;
import ikor.math.Vector;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.AttributeNetwork;

/**
 * Efficient spectral algorithm devised by Ng, Jordan, and Weiss, a.k.a. KNSC1
 * 
 * Andrew Y. Ng, Michael I. Jordan & Yair Weiss (2002): 
 * "On spectral clustering: Analysis and an algorithm." 
 * (NIPS 2001: Vancouver, British Columbia, Canada)
 * http://www-2.cs.cmu.edu/Groups/NIPS/NIPS2001/papers/psgz/AA35.ps.gz
 * In: Dietterich, T., Becker, S., Ghahramani, Z. (eds.) 
 *    Advances in Neural Information Processing Systems 14, pp. 849-856. 
 *    MIT Press, Cambridge (2002).
 *
 * @author Aaron Rosas (aarr90@gmail.com) & Fco. Javier Gijon (fcojaviergijon@gmail.com)
 */
@Label("KNSC1")
@Description("KNSC1 spectral community detection algorithm")
public class NJWCommunityDetector extends SpectralCommunityDetector 
{
    private int K;

    public NJWCommunityDetector(AttributeNetwork network) 
    {
    	this(network, (int) Math.sqrt(network.size()));
    }
    
    public NJWCommunityDetector(AttributeNetwork network, int clusters) 
    {
        super(network);
        K = clusters > 0 ? clusters : 1;
    }

    @Override
    public void compute() 
    {
        // Laplacian matrix (symetric normalization)
        Matrix L = laplacian(network, Normalization.SYMMETRIC);
        // Create matrix with the first k Eigenvectors of the Laplacian matrix
        Matrix E = eigenvectors(L, K);

        // Normalize each row of E
        for (int i = 0; i < E.rows(); ++i) {
            double sum = 0;
            // Sum all elements of row i
            for (int j = 0; j < E.columns(); ++j) {
                sum += E.get(i, j) * E.get(i, j);
            }
            sum = Math.sqrt(sum);
            // Normalize all elements of row
            for (int j = 0; j < E.columns(); ++j) {
                E.set(i, j, E.get(i, j) / sum);
            }
        }

        // K-means algorithm
        Vector CL = kMeans(E, K, 1e10);

        // Save results
        for (int i = 0; i < results.columns(); ++i) {
            results.set(0, i, CL.get(i));
        }
    }
}
