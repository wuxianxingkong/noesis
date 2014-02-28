package test.noesis.network;

import static org.junit.Assert.*;
import ikor.math.Matrix;
import ikor.math.MatrixFactory;

import noesis.BasicNetwork;
import noesis.Network;
import noesis.network.AdjacencyMatrix;

import org.junit.Before;
import org.junit.Test;

public class AdjacencyMatrixTest 
{
	Network net;
	Matrix  A, A2, A3, A4, A5, A6, A7;
	
	@Before 
	public void setUp ()
	{
		net = new BasicNetwork();
		
		net.setSize(4);
		
		net.add2(0,1);
		net.add2(0,2);
		net.add2(1,2);
		net.add2(2,3);
		
		A  = MatrixFactory.create ( new double[][] { {0,1,1,0}, {1,0,1,0}, {1,1,0,1}, {0,0,1,0} } );
		A2 = MatrixFactory.create ( new double[][] { {2,1,1,1}, {1,2,1,1}, {1,1,3,0}, {1,1,0,1} } );
		A3 = MatrixFactory.create ( new double[][] { {2,3,4,1}, {3,2,4,1}, {4,4,2,3}, {1,1,3,0} } );
		A4 = MatrixFactory.create ( new double[][] { { 7, 6, 6, 4}, { 6, 7, 6, 4}, { 6, 6,11, 2}, { 4, 4, 2, 3} } );
		A5 = MatrixFactory.create ( new double[][] { {12,13,17, 6}, {13,12,17, 6}, {17,17,14,11}, { 6, 6,11, 2} } );
		A6 = MatrixFactory.create ( new double[][] { {30,29,31,17}, {29,30,31,17}, {31,31,45,14}, {17,17,14,11} } );
		A7 = MatrixFactory.create ( new double[][] { {60,61,76,31}, {61,60,76,31}, {76,76,76,45}, {31,31,45,14} } );
	}
	
	@Test
	public void testNetwork ()
	{
		assertEquals (4, net.size());
		assertEquals (8, net.links());
	}
	
	@Test
	public void testAdjacencyMatrix ()
	{
		assertEquals ( A,  new AdjacencyMatrix(net) );
	}

	@Test
	public void testNumberOfPaths ()
	{
		Matrix adjacency = new AdjacencyMatrix(net);
		
		assertEquals ( "Number of paths of length 1", A,  adjacency.power(1) );
		assertEquals ( "Number of paths of length 2", A2, adjacency.power(2) );
		assertEquals ( "Number of paths of length 3", A3, adjacency.power(3) );
		assertEquals ( "Number of paths of length 4", A4, adjacency.power(4) );
		assertEquals ( "Number of paths of length 5", A5, adjacency.power(5) );
		assertEquals ( "Number of paths of length 6", A6, adjacency.power(6) );
		assertEquals ( "Number of paths of length 7", A7, adjacency.power(7) );
	}

}
