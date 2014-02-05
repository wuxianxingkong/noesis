package test.noesis.analysis.structure;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import noesis.BasicNetwork;
import noesis.Network;

import noesis.analysis.structure.NodeMeasure;
import noesis.analysis.structure.PageRank;


public class PageRankTest 
{
	public final double EPSILON = 1e-4;

	Network  net20Q;
	double[] net20Qrank = { 0.1286, 0.1590, 0.2015, 0.1507, 0.1053, 0.0447, 0.0610, 0.1492};
	
	Network  danglingNode;
	double[] danglingNodeRank = { 0.2222, 0.1666, 0.1666, 0.4444 }; // Theta = 1.0

	Network  disconnected;
	double[] disconnectedRank = { 0.25, 0.25, 0.25, 0.25 }; // Theta = 1.0

	Network  noInDegree;
	double[] noInDegreeRank = { 0.0000, 0.0000, 0.50, 0.50 }; // Theta = 1.0

	@Before
	public void setUp() throws Exception 
	{
		// From Chiang's "Networked Life: 20 Questions..." 3.3, pages 55ff
		
		net20Q = new BasicNetwork(); 
		net20Q.setSize(8);
		
		net20Q.add( 0, 1);
		net20Q.add( 0, 2);
		
		net20Q.add( 1, 0);
		net20Q.add( 1, 4);

		net20Q.add( 2, 1);
		net20Q.add( 2, 7);
		
		net20Q.add( 3, 2);

		net20Q.add( 4, 3);
		net20Q.add( 4, 7);
		
		net20Q.add( 5, 3);
		net20Q.add( 5, 4);

		net20Q.add( 6, 3);
		net20Q.add( 6, 5);

		net20Q.add( 7, 0);
		net20Q.add( 7, 3);
		net20Q.add( 7, 6);
		
		
		// Dangling node
		
		danglingNode = new BasicNetwork(); 
		danglingNode.setSize(4);
		
		danglingNode.add(0,3);

		danglingNode.add(1,0);
		danglingNode.add(1,2);
		danglingNode.add(1,3);

		danglingNode.add(2,0);
		danglingNode.add(2,1);
		danglingNode.add(2,3);
		
		// Disconnected network
		
		disconnected = new BasicNetwork();
		disconnected.setSize(4);
		
		disconnected.add(0,0);
		disconnected.add(0,1);
		disconnected.add(1,0);
		disconnected.add(1,1);
		
		disconnected.add(2,2);
		disconnected.add(2,3);
		disconnected.add(3,2);
		disconnected.add(3,3);

		// No in-degree node

		noInDegree = new BasicNetwork();
		noInDegree.setSize(4);
		
		noInDegree.add(0,1);
		noInDegree.add(0,2);
		noInDegree.add(1,3);
		noInDegree.add(2,3);
		noInDegree.add(3,2);
	}
	
	private void checkPageRank (Network net, double theta, double[] rank)
	{
		PageRank task = new PageRank(net, theta);
		NodeMeasure pagerank = task.getResult();
		
		for (int i=0; i<net.size(); i++)
			assertEquals ( rank[i], pagerank.get(i), EPSILON); 
	}
	
	
	@Test
	public void testPageRank ()
	{
		checkPageRank(net20Q, PageRank.DEFAULT_THETA, net20Qrank);
	}

	@Test
	public void testDanglingNode ()
	{
		checkPageRank(danglingNode, 1.0, danglingNodeRank);
	}
	
	@Test
	public void testDisconnected ()
	{
		checkPageRank(disconnected, 1.0, disconnectedRank);
	}

	@Test
	public void testNoInDegree ()
	{
		checkPageRank(noInDegree, 1.0, noInDegreeRank);
	}
	
}
