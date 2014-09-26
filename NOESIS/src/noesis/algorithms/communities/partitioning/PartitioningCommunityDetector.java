package noesis.algorithms.communities.partitioning;

// Title:       Generic Partitioning Community Detection Algorithm
// Version:     1.0
// Copyright:   2014
// Author:      Fco. Javier Gijon & Aaron Rosas
// E-mail:      fcojaviergijon@gmail.com & aarr90@gmail.com

import ikor.model.data.annotations.Description;
import ikor.model.data.annotations.Label;

import noesis.AttributeNetwork;
import noesis.algorithms.communities.CommunityDetector;

/**
 * Generic Partitioning Community Detectrion Algorithm
 *
 * @author Aaron Rosas (aarr90@gmail.com) & Fco. Javier Gijon (fcojaviergijon@gmail.com)
 */
@Label("Partitioning Community Detector")
@Description("Generic Partitioning Community Detection Algorithm")
public abstract class PartitioningCommunityDetector extends CommunityDetector 
{
    public PartitioningCommunityDetector(AttributeNetwork network) 
    {
        super(network);
    }
}
