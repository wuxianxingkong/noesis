package noesis.analysis.structure.communities;

// Title:       Task for computing node scores using clusters assignment
// Version:     1.0
// Copyright:   2014
// Author:      Fco. Javier Gijon & Aaron Rosas
// E-mail:      fcojaviergijon@gmail.com & aarr90@gmail.com

import ikor.collection.CollectionFactory;
import ikor.collection.Dictionary;
import ikor.collection.List;
import ikor.math.DenseVector;
import ikor.math.Vector;
import ikor.model.data.DataModel;
import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;
import noesis.Network;
import noesis.analysis.NodeScore;
import noesis.analysis.NodeScoreTask;

/**
 * Task for computing node scores given a cluster assignment.
 *
 * @author Aaron Rosas (aarr90@gmail.com) & Fco. Javier Gijon (fcojaviergijon@gmail.com)
 */
@Label("ClusterScoreTask")
@Description("Cluster score task")
public abstract class ClusterScoreTask extends NodeScoreTask 
{
    private NodeScore assignment;
    private Dictionary<Integer, List<Integer>> clusters;

    // Constructors

    public ClusterScoreTask(Network network, NodeScore assignment) 
    {
        this(NodeScore.REAL_MODEL, network, assignment);
    }
    
    public ClusterScoreTask(DataModel model, Network network, NodeScore assignment) 
    {
        super(model,network);
        this.assignment = assignment;
        clusters = CollectionFactory.createDictionary();
        
        // Create a list of nodes for each cluster
        for (int i = 0; i < assignment.size(); ++i) {
            // Cluster
            int c = (int) assignment.get(i);
            // First time
            if (!clusters.contains(c)) {
                clusters.set(c,CollectionFactory.createList());
            }
            // Add node to cluster c
            clusters.get(c).add(i);
        }
        
    }

    
    // Accessors
    
    /**
     * Clusters
     * @return number of clusters/communities
     */
    public int clusters() 
    {
        return clusters.size();
    }
     
    /**
     * Cluster assignment
     * @return the current cluster assignment (as a dictionary)
     */
    public final Dictionary<Integer, List<Integer>> getClusters() 
    {
        return clusters;
    }

    /**
     * Cluster assignment
     * @return the current cluster assignment (as a vector)
     */
    public NodeScore getAssignment() 
    {
        return assignment;
    }
   
    /**
     * Node score
     * @param node Node ID
     * @return Score for the specified node
     */
    public double nodeValue(int node)
    {
        return getResult(node);
    }
    
    /**
     * Cluster score
     * @param cluster Cluster ID
     * @return Combined score for the nodes in the specified cluster 
     */
    public abstract double clusterValue(int cluster);
    
    
    /**
     * Cluster scores
     * @param cluster Cluster ID
     * @return Scores for the nodes in the specified cluster 
     */
    public final Vector clusterValues (int cluster)
    {
        List<Integer> nodes = clusters.get(cluster);
        Vector vector = new DenseVector(nodes.size());

        for (int i = 0; i < nodes.size(); ++i)
            vector.set(i, nodeValue(nodes.get(i)));
        
        return vector;
    }
    
    /**
     * Overall score
     * @return Combined value of the node scores
     */
    public abstract double overallValue();
}
