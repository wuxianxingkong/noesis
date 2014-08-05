package noesis.analysis.structure;

// Title:       Link rays
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.model.data.annotations.Label;
import ikor.model.data.annotations.Description;
import noesis.Network;
import noesis.analysis.LinkScoreTask;

/**
 * Link rays, i.e. number of different paths (x,i,j,y) through the (i,j) link.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

@Label("link-rays")
@Description("Link rays")
public class LinkRays extends LinkScoreTask
{
	public LinkRays(Network network) 
	{
		super(network);
	}

	@Override
	public double compute(int source, int destination) 
	{
		Network net = getNetwork();
		
		return net.inDegree(source)*net.outDegree(destination);
	}

}
