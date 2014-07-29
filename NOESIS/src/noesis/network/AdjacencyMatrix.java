package noesis.network;

// Title:       Adjacency matrix
// Version:     1.1
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import noesis.Network;
import ikor.math.SparseMatrix;

/**
 * Adjacency matrix, implemented as a sparse matrix.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class AdjacencyMatrix extends SparseMatrix 
{
	private Network net;
	
	// Constructor
	
	public AdjacencyMatrix (Network net)
	{
		super(net.size(), net.size());
		
		this.net = net;
		
		for (int i=0; i<net.size(); i++) {
			for (int k=0; k<net.outDegree(i); k++) {
				set(i, net.outLink(i,k), 1.0);
			}
		}
	}

	// Network
	
	public Network getNetwork ()
	{
		return net;
	}

}
