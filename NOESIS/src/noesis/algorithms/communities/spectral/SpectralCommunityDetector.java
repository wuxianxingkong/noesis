package noesis.algorithms.communities.spectral;

// Title:       Generic Spectral Community Detection Algorithm
// Version:     1.0
// Copyright:   2014
// Author:      Fco. Javier Gijon & Aaron Rosas
// E-mail:      fcojaviergijon@gmail.com & aarr90@gmail.com

import ikor.math.*;
import ikor.math.random.Random;

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.AttributeNetwork;
import noesis.Network;
import noesis.algorithms.communities.CommunityDetector;

/**
 * Generic Spectral Algorithm
 *
 * @author Aaron Rosas (aarr90@gmail.com) & Fco. Javier Gijon (fcojaviergijon@gmail.com)
 */
@Label("Spectral Community Detector")
@Description("Generic Spectral Community Detection Algorithm")
public abstract class SpectralCommunityDetector extends CommunityDetector 
{
    public enum Normalized {
        // No normalized
        NO, 
        // Symetric normalization
        SYMETRIC,
        // Asymetric normalization
        ASYMETRIC
    };

    /**
     * Constructor
     * @param network Network
     */
    public SpectralCommunityDetector(AttributeNetwork network) 
    {
        super(network);
    }

    /**
     * Return the adjacency matrix of our net.
     *
     * @param net Network to represent
     * @return Adjacency matrix
     */
    public static DenseMatrix AdjacencyMatrix(Network net) 
    {
        double value = 1;

        int size = net.nodes();
        // Adjacency matrix
        DenseMatrix W = new DenseMatrix(size, size, 0);
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) // If link exists
            {
                if (net.contains(i, j)) {
                    W.set(i, j, value);
                }
            }
        }
        return W;
    }
    

    /**
     * Return the degree matrix of our net.
     *
     * @param net Network to represent
     * @return Diagonal degree matrix (Adjacency matrix)
     */
    public static DenseMatrix DegreeMatrix(Network net) 
    {
        int size = net.nodes();
        // Diagonal degree matrix (Dii -> degree of node i)
        DenseMatrix D = new DenseMatrix(size, size, 0);
        for (int i = 0; i < size; ++i) {
            D.set(i, i, net.degree(i));
        }

        return D;
    }

    /**
     * Return the Laplacian matrix (normalized or not).
     *
     * @param W Similarity matrix of our graph
     * @param D Diagonal degree matrix of our graph
     * @param normalized Normalized type
     *
     * @return Laplacian matrix (Normalized or no)
     */
    public static DenseMatrix LaplacianMatrix(DenseMatrix W, DenseMatrix D, Normalized norm) 
    {
        int size = W.rows();
        // Laplacian matrix
        DenseMatrix L = (DenseMatrix) D.subtract(W);

        // Normalization
        switch (norm) {
            // Asymetric normalization
            case ASYMETRIC:
                L = (DenseMatrix) D.inverse().multiply(L);
                // If determinant = 0, L = null
                if (L == null) {
                    L = (DenseMatrix) D.subtract(W);
                }
                break;
            // Symetric normalization
            case SYMETRIC:
                // Inverse square-root degree matrix
                DenseMatrix ID12 = new DenseMatrix(size, size, 0);
                // Square root elements, its a diagonal matrix
                for (int i = 0; i < size; ++i) {
                    ID12.set(i, i, Math.sqrt(D.get(i, i)));
                }
                // Inverse
                ID12 = (DenseMatrix) ID12.inverse();
                // If determinant = 0, ID12 = null
                if (ID12 == null) {
                    L = (DenseMatrix) D.subtract(W);
                } else {
                    L = (DenseMatrix) ID12.multiply(L).multiply(ID12);
                }
                break;
            // No normalization
            case NO:
            	break;
              
        }

        return L;
    }

    /**
     * Apply eigenvectors descomposition to laplacian matrix and return the k
     * first eigenvectors in a matrix.
     *
     * @pre k > 0 && k < Columns(L) 
     * @param L Laplacian Matrix
     * @param k Number of eigenvectors
     * @return Eigenvector matrix
     */
    public static DenseMatrix EigenvectorsMatrix(DenseMatrix L, int k) 
    {
        // Eigenvectors descomposition
        EigenvectorDecomposition ED = new EigenvectorDecomposition(L);
        // Copy k first
        DenseMatrix E = (DenseMatrix) ED.getEigenvectorMatrix();
        DenseMatrix V = new DenseMatrix(L.rows(), k);
        for (int i = 0; i < V.rows() && i < E.rows(); ++i) {
            for (int j = 0; j < V.columns() && j < E.columns(); ++j) {
                V.set(i, j, E.get(i, j));
            }
        }

        return V;
    }

    /**
     * Apply eigenvectors descomposition to laplacian matrix and return all the
     * eigenvalues.
     *
     * @param L Laplacian Matrix
     * @return Eigenvalues vector
     */
    public static DenseVector EigenvaluesVector(DenseMatrix L) 
    {
        // Eigenvectors descomposition
        EigenvectorDecomposition ED = new EigenvectorDecomposition(L);
        return (DenseVector) ED.getRealEigenvalues();
    }

    /**
     * Apply eigenvectors descomposition to laplacian matrix and return second
     * eigenvector associated to second smallest eigenvalue (Fiedler vector).
     *
     * @param L Laplacian Matrix
     * @return Fiedler eigenvector
     */
    public static DenseVector FiedlerEigenvector(DenseMatrix L) 
    {
        // Eigenvectors descomposition
        EigenvectorDecomposition ED = new EigenvectorDecomposition(L);
        // Eigenvector matrix
        DenseMatrix E = (DenseMatrix) ED.getEigenvectorMatrix();
        DenseVector V = new DenseVector(E.rows());
        // Copy second eigenvector
        for (int i = 0; i < E.rows(); ++i) {
            V.set(i, E.get(i, 1));
        }
        return V;
    }
    
    /**
     * Estimate the number of clusters in our net using eigenvalues of laplacian
     * matrix.
     *
     * @param net Network
     * @return Estimated number of clusters
     */
    public static long EstimateClusters(Network net) 
    {
        // Adjacency matrix
        DenseMatrix W = AdjacencyMatrix(net);
        // Diagonal degree matrix (Dii -> degree of node i)
        DenseMatrix D = DegreeMatrix(net);
        // Laplacian matrix no normalized
        DenseMatrix L = LaplacianMatrix(W, D, Normalized.NO);
        return EstimateClusters(L);
    }

    /**
     * Estimate the number of clusters in our net using eigenvalues of laplacian
     * matrix.
     *
     * @param L Laplacian matrix of our Network
     * @return Estimated number of clusters
     */
    public static long EstimateClusters(DenseMatrix L) 
    {
        long k = 0;
        // Compute eigenvalues of laplacian matrix
        DenseVector E = EigenvaluesVector(L);

        // compute the gap between each pair of eigenvalues
        double gap = Double.MIN_VALUE;
        for (int i = 0; i < E.size() - 1; ++i) {
            if (Math.abs(E.get(i) - E.get(i + 1)) > gap) {
                gap = Math.abs(E.get(i) - E.get(i + 1));
                k = i+1;
            }
        }
        
        return k;
    }


    /**
     * Return a euclidean distance of element i and centroid j.
     *
     * @param NA elements attributes
     * @param element element i
     * @param CA centroids attributes
     * @param centroid centroid j
     * @return Distance
     */
    public static double Distance(DenseMatrix NA, int element, DenseMatrix CA, int centroid) 
    {
        double dist = 0;
        for (int value = 0; value < NA.columns() && value < CA.columns(); ++value) {
            dist += (CA.get(centroid, value) - NA.get(element, value)) * (CA.get(centroid, value) - NA.get(element, value));
        }

        return Math.sqrt(dist);
    }

    /**
     * Apply k-means algorithm to grouping E elements in k clusters.
     *
     * @param E Elements attributes
     * @param K Number of clusters
     * @param msse Minimum change in SSE in two consecutive iterations, stop
     * criteria
     * @return Vector where position i correspond to cluster of element i
     */
    public static DenseVector KMeans(DenseMatrix E, int K, double msse) 
    {
        int elements = E.rows();
        int attrs = E.columns();
        double max = E.max();
        double min = E.min();

        // Init centroids
        DenseMatrix CE = new DenseMatrix(K, attrs, 0);
        DenseMatrix NCE = new DenseMatrix(K, attrs, 0);
        for (int i = 0; i < CE.rows(); ++i) {
            for (int j = 0; j < CE.columns(); ++j) {
                // Random initialization
                CE.set(i, j, min + Random.random() * (max - min));
            }
        }

        // Assign elements to cluster
        DenseVector CL = new DenseVector(elements);
        DenseVector NCL = new DenseVector(elements);
        // No elements in clusters
        DenseVector NIC = new DenseVector(K);
        // Stop criteria
        Double minSSE = Double.MAX_VALUE;
        Boolean salir = false;
        while (!salir) {
            // Init vars for recalculate centroides
            for (int i = 0; i < NCE.rows(); ++i) {
                for (int j = 0; j < NCE.columns(); ++j) {
                    NCE.set(i, j, 0);
                }
                NIC.set(i, 0);
            }

            int centroideAsigned = -1;
            double SSE = 0;
            // Foreach element, asign to the cluster centroide which is nearest
            for (int element = 0; element < elements; ++element) {
                Double distMin = Double.MAX_VALUE;
                for (int centroid = 0; centroid < CE.rows(); ++centroid) {
                    // Distance between element and centroid c
                    double dist = Distance(E, element, CE, centroid);
                    if (dist < distMin) {
                        // Keep minimun distance and asign the element to cluster
                        distMin = dist;
                        NCL.set(element, centroid);
                        centroideAsigned = centroid;
                    }
                }
                // For recalculate centroides
                for (int i = 0; i < NCE.columns(); ++i) {
                    NCE.set(centroideAsigned, i, NCE.get(centroideAsigned, i) + E.get(centroideAsigned, i));
                }
                NIC.set(centroideAsigned, NIC.get(centroideAsigned) + 1);
                // Calculating SSE
                SSE += distMin * distMin;
            }
            // Recalculate centroides
            for (int i = 0; i < CE.rows(); ++i) {
                for (int j = 0; j < CE.columns(); ++j) {
                    if (NIC.get(i) != 0) {
                        CE.set(i, j, NCE.get(i, j) / NIC.get(i));
                    } else
	        	CE.set(i,j, min + Random.random()*(max-min)); 
                }
            }

            // Save min SSE and check stop criteria
            if (minSSE - msse > SSE) {
                minSSE = SSE;
                // Backup last asign
                CL = new DenseVector(NCL);
            } else {
                if (minSSE > SSE) // Backup last asign
                {
                    CL = new DenseVector(NCL);
                }
                salir = true;
            }

        }

        return CL;
    }

    /**
     * Sort array using the Quicksort algorithm.
     *
     * @pre array is full, all elements are non-null
     * @param array Elements to sort
     */
    public static void QuickSort(DenseVector array) 
    {
        QuickSort(array, 0, array.size() - 1);
    }

    /**
     * Sort array using quicksort algorithm .
     * (http://www.mycstutorials.com/articles/sorting/quicksort)
     *
     * @pre array is full, all elements are non-null
     * @param arrar Elements to sort
     * @param start index of first element to sort
     * @param end index of last element to sort
     * @return Sorted array
     */
    public static void QuickSort(DenseVector array, int start, int end) 
    {
        // index of left-to-right scan
        int i = start;
        // index of right-to-left scan
        int k = end;
        // check that there are at least two elements to sort
        if (end - start >= 1) {
            // set the pivot as the first element in the partition
            double pivot = array.get(start);
            // while the scan indices from left and right have not met
            while (k > i) {
				// from the left, look for the first element greater than the
                // pivot
                while (array.get(i) <= pivot && i <= end && k > i) {
                    i++;
                }
				// from the right, look for the first element not greater than
                // the pivot
                while (array.get(k) > pivot && k >= start && k >= i) {
                    k--;
                }
				// if the left seekindex is still smaller than the right index,
                // swap the corresponding elements
                if (k > i) {
                    array.swap(i, k);
                }
            }
			// after the indices have crossed, swap the last element in the left
            // partition with the pivot
            array.swap(start, k);
            // quicksort the left partition
            QuickSort(array, start, k - 1);
            // quicksort the right partition
            QuickSort(array, k + 1, end);
        }
    }
}
