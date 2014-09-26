package noesis.algorithms.communities.modularity;

// Title:       Generic Modular Community Detection Algorithm
// Version:     1.0
// Copyright:   2014
// Author:      Fco. Javier Gijon & Aaron Rosas
// E-mail:      fcojaviergijon@gmail.com & aarr90@gmail.com

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import java.util.ArrayList;

import noesis.AttributeNetwork;
import noesis.DynamicNetwork;
import noesis.Link;
import noesis.algorithms.communities.CommunityDetector;
import noesis.algorithms.traversal.ConnectedComponents;
import noesis.analysis.NodeScore;
import noesis.analysis.structure.communities.ModularityCoefficient;

/**
 * Generic Modularity Community Detection Algorithm
 *
 * @author Aaron Rosas (aarr90@gmail.com) & Fco. Javier Gijon (fcojaviergijon@gmail.com)
 */
@Label("Modularity Community Detector")
@Description("Modularity Community Detector")
public abstract class ModularityCommunityDetector extends CommunityDetector 
{
    /**
     * The remaining links to add in each time
     */
    protected ArrayList<Link<Double>> links;
    protected DynamicNetwork dn;

    public ModularityCommunityDetector(AttributeNetwork network) 
    {
        super(network);
        dn = new DynamicNetwork(network);
    }

    /**
     * Calculate the modularity of the network
     * @return the modularity value
     */
    protected double computeModularity() 
    {
        ConnectedComponents cc = new ConnectedComponents(dn);
        cc.compute();

        // Get assignment
        NodeScore dv = new NodeScore("cluster assignment",an);
        for (int i = 0; i < results.columns(); ++i) {
            dv.set(i, cc.component(i));
        }

        ModularityCoefficient md = new ModularityCoefficient(an, dv);

        return md.overallValue();
    }

    /**
     * Apply algorithm and save results.
     */
    @Override
    public void compute() 
    {
        preProcess();
                
        while (improveModularity()) {
        }

        // Compute connected components
        ConnectedComponents cc = new ConnectedComponents(dn);
        cc.compute();

        // Save results
        for (int i = 0; i < results.columns(); ++i) {
            results.set(0, i, cc.component(i));
        }
    }

    /**
     * @brief Alter links to improve modularity
     * @return true if improved modularity is achieved
     */
    protected abstract boolean improveModularity();
    
    
    /**
     * Preprocces the net to apply the algorithm
     */
    protected abstract void preProcess();
}
