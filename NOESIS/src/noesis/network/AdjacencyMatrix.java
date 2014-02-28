package noesis.network;

// Title:       Adjacency matrix
// Version:     1.1
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import noesis.Network;
import ikor.math.Matrix;

/**
 * Adjacency matrix, implemented as a Network decorator.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class AdjacencyMatrix extends Matrix 
{
	private Network net;
	
	// Constructor
	
	public AdjacencyMatrix (Network net)
	{
		this.net = net;
	}

	// Matrix interface
	
	@Override
	public int rows() 
	{
		return net.size();
	}

	@Override
	public int columns() 
	{
		return net.size();
	}

	@Override
	public double get(int i, int j) 
	{
		if (net.contains(i,j))
			return 1;
		else
			return 0;
	}

	@Override
	public void set(int i, int j, double v) 
	{
		throw new UnsupportedOperationException("Networks cannot be modified through their adjacency matrix.");
	}

}
