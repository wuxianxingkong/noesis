package test.noesis.analysis.structure;

import static org.junit.Assert.*;

import noesis.BasicNetwork;
import noesis.Network;

import noesis.analysis.structure.LinkBetweenness;
import noesis.analysis.structure.LinkBetweennessScore;
import noesis.network.LinkIndex;

import org.junit.Before;
import org.junit.Test;

public class LinkBetweennessTest 
{
	public final double EPSILON = 1e-9;

	Network tree;
	Network graph;
	
	LinkIndex treeLinkIndex;
	LinkIndex graphLinkIndex;
	
	@Before
	public void setUp() throws Exception 
	{
		// From Newman's "Introduction to Networks," 10.3.6 Betweenness centrality,  page 326
		tree = new BasicNetwork(); 
		tree.setSize(7);
		
		addLink(tree,0,1);
		addLink(tree,0,2);
		addLink(tree,1,3);
		addLink(tree,2,4);
		addLink(tree,2,5);
		addLink(tree,5,6);
		
		treeLinkIndex = new LinkIndex(tree);
		
		graph = new BasicNetwork();
		graph.setSize(7);
		
		addLink(graph,0,1);
		addLink(graph,0,2);
		addLink(graph,1,3);
		addLink(graph,2,3);
		addLink(graph,2,4);
		addLink(graph,3,5);
		addLink(graph,4,5);
		addLink(graph,4,6);
		
		graphLinkIndex = new LinkIndex(graph);
	}
	
	private void addLink (Network net, int a, int b)
	{
		net.add(a,b);
		net.add(b,a);
	}
	
	@Test
	public void testDistance ()
	{
		LinkBetweennessScore treeScore = new LinkBetweennessScore(tree,treeLinkIndex,0);
		LinkBetweennessScore graphScore = new LinkBetweennessScore(graph,graphLinkIndex,0);
		
		treeScore.compute();
		graphScore.compute();
		
		assertEquals ( 0, treeScore.distance(0));
		assertEquals ( 1, treeScore.distance(1));
		assertEquals ( 1, treeScore.distance(2));
		assertEquals ( 2, treeScore.distance(3));
		assertEquals ( 2, treeScore.distance(4));
		assertEquals ( 2, treeScore.distance(5));
		assertEquals ( 3, treeScore.distance(6));

		assertEquals ( 0, graphScore.distance(0));
		assertEquals ( 1, graphScore.distance(1));
		assertEquals ( 1, graphScore.distance(2));
		assertEquals ( 2, graphScore.distance(3));
		assertEquals ( 2, graphScore.distance(4));
		assertEquals ( 3, graphScore.distance(5));
		assertEquals ( 3, graphScore.distance(6));	
	}
	
	@Test
	public void testGeodesics ()
	{	
		LinkBetweennessScore treeScore = new LinkBetweennessScore(tree,treeLinkIndex,0);
		LinkBetweennessScore graphScore = new LinkBetweennessScore(graph,graphLinkIndex,0);
		
		treeScore.compute();
		graphScore.compute();
		
		assertEquals ( 1, treeScore.geodesics(0));
		assertEquals ( 1, treeScore.geodesics(1));
		assertEquals ( 1, treeScore.geodesics(2));
		assertEquals ( 1, treeScore.geodesics(3));
		assertEquals ( 1, treeScore.geodesics(4));
		assertEquals ( 1, treeScore.geodesics(5));
		assertEquals ( 1, treeScore.geodesics(6));

		assertEquals ( 1, graphScore.geodesics(0));
		assertEquals ( 1, graphScore.geodesics(1));
		assertEquals ( 1, graphScore.geodesics(2));
		assertEquals ( 2, graphScore.geodesics(3));
		assertEquals ( 1, graphScore.geodesics(4));
		assertEquals ( 3, graphScore.geodesics(5));
		assertEquals ( 1, graphScore.geodesics(6));	
	}
	
	@Test
	public void testNodeBetweenessScores ()
	{	
		LinkBetweennessScore treeScore = new LinkBetweennessScore(tree,treeLinkIndex,0);
		LinkBetweennessScore graphScore = new LinkBetweennessScore(graph,graphLinkIndex,0);
		
		treeScore.compute();
		graphScore.compute();
		
		assertEquals ( 7, treeScore.betweenness(0), EPSILON);
		assertEquals ( 2, treeScore.betweenness(1), EPSILON);
		assertEquals ( 4, treeScore.betweenness(2), EPSILON);
		assertEquals ( 1, treeScore.betweenness(3), EPSILON);
		assertEquals ( 1, treeScore.betweenness(4), EPSILON);
		assertEquals ( 2, treeScore.betweenness(5), EPSILON);
		assertEquals ( 1, treeScore.betweenness(6), EPSILON);

		assertEquals (  7.0,     graphScore.betweenness(0), EPSILON);
		assertEquals ( 11.0/6.0, graphScore.betweenness(1), EPSILON);
		assertEquals ( 25.0/6.0, graphScore.betweenness(2), EPSILON);
		assertEquals (  5.0/3.0, graphScore.betweenness(3), EPSILON);
		assertEquals (  7.0/3.0, graphScore.betweenness(4), EPSILON);
		assertEquals (  1.0,     graphScore.betweenness(5), EPSILON);
		assertEquals (  1.0,     graphScore.betweenness(6), EPSILON);	
	}	

	
	@Test
	public void testScores ()
	{	
		LinkBetweennessScore treeScore = new LinkBetweennessScore(tree,treeLinkIndex,0);
		LinkBetweennessScore graphScore = new LinkBetweennessScore(graph,graphLinkIndex,0);
				
		assertEquals ( 2, treeScore.getResult().get(0,1), EPSILON);
		assertEquals ( 4, treeScore.getResult().get(0,2), EPSILON);
		assertEquals ( 1, treeScore.getResult().get(1,3), EPSILON);
		assertEquals ( 1, treeScore.getResult().get(2,4), EPSILON);
		assertEquals ( 2, treeScore.getResult().get(2,5), EPSILON);
		assertEquals ( 1, treeScore.getResult().get(5,6), EPSILON);
		
		assertEquals ( 0, treeScore.getResult().get(6,5), EPSILON);
		assertEquals ( 0, treeScore.getResult().get(5,2), EPSILON);
		assertEquals ( 0, treeScore.getResult().get(4,2), EPSILON);
		assertEquals ( 0, treeScore.getResult().get(3,1), EPSILON);
		assertEquals ( 0, treeScore.getResult().get(2,0), EPSILON);
		assertEquals ( 0, treeScore.getResult().get(1,0), EPSILON);
		

		assertEquals ( 11.0/6.0, graphScore.getResult().get(0,1), EPSILON);
		assertEquals ( 25.0/6.0, graphScore.getResult().get(0,2), EPSILON);
		assertEquals (  5.0/6.0, graphScore.getResult().get(1,3), EPSILON);
		assertEquals (  5.0/6.0, graphScore.getResult().get(2,3), EPSILON);
		assertEquals (  7.0/3.0, graphScore.getResult().get(2,4), EPSILON);
		assertEquals (  2.0/3.0, graphScore.getResult().get(3,5), EPSILON);
		assertEquals (  1.0/3.0, graphScore.getResult().get(4,5), EPSILON);
		assertEquals (  1.0,     graphScore.getResult().get(4,6), EPSILON);

		assertEquals ( 0, graphScore.getResult().get(1,0), EPSILON);
		assertEquals ( 0, graphScore.getResult().get(2,0), EPSILON);
		assertEquals ( 0, graphScore.getResult().get(3,1), EPSILON);
		assertEquals ( 0, graphScore.getResult().get(3,2), EPSILON);
		assertEquals ( 0, graphScore.getResult().get(4,2), EPSILON);
		assertEquals ( 0, graphScore.getResult().get(5,3), EPSILON);
		assertEquals ( 0, graphScore.getResult().get(5,4), EPSILON);
		assertEquals ( 0, graphScore.getResult().get(6,4), EPSILON);		
	}	
	
	
	@Test
	public void testBetweenness ()
	{	
		LinkBetweenness treeBetweenness = new LinkBetweenness(tree);
		LinkBetweenness graphBetweenness = new LinkBetweenness(graph);
		
		assertEquals ( 2+0+2+0+2+2+2, treeBetweenness.getResult().get(0,1), EPSILON);
		assertEquals ( 0+5+0+5+0+0+0, treeBetweenness.getResult().get(1,0), EPSILON);
		assertEquals ( 4+4+0+4+0+0+0, treeBetweenness.getResult().get(0,2), EPSILON);
		assertEquals ( 0+0+3+0+3+3+3, treeBetweenness.getResult().get(2,0), EPSILON);
		assertEquals ( 1+1+1+0+1+1+1, treeBetweenness.getResult().get(1,3), EPSILON);
		assertEquals ( 0+0+0+6+0+0+0, treeBetweenness.getResult().get(3,1), EPSILON);
		assertEquals ( 1+1+1+1+0+1+1, treeBetweenness.getResult().get(2,4), EPSILON);
		assertEquals ( 0+0+0+0+6+0+0, treeBetweenness.getResult().get(4,2), EPSILON);
		assertEquals ( 2+2+2+2+2+0+0, treeBetweenness.getResult().get(2,5), EPSILON);
		assertEquals ( 0+0+0+0+0+5+5, treeBetweenness.getResult().get(5,2), EPSILON);
		assertEquals ( 1+1+1+1+1+1+0, treeBetweenness.getResult().get(5,6), EPSILON);
		assertEquals ( 0+0+0+0+0+0+6, treeBetweenness.getResult().get(6,5), EPSILON);
		
		assertEquals (  9.0/3.0, graphBetweenness.getResult().get(0,1), EPSILON);
		assertEquals (  9.0/3.0, graphBetweenness.getResult().get(1,0), EPSILON);
		assertEquals ( 16.0/3.0, graphBetweenness.getResult().get(0,2), EPSILON);
		assertEquals ( 16.0/3.0, graphBetweenness.getResult().get(2,0), EPSILON);
		assertEquals ( 14.0/3.0, graphBetweenness.getResult().get(1,3), EPSILON);
		assertEquals ( 14.0/3.0, graphBetweenness.getResult().get(3,1), EPSILON);
		assertEquals ( 27.0/6.0, graphBetweenness.getResult().get(2,3), EPSILON);
		assertEquals ( 27.0/6.0, graphBetweenness.getResult().get(3,2), EPSILON);
		assertEquals ( 43.0/6.0, graphBetweenness.getResult().get(2,4), EPSILON);
		assertEquals ( 43.0/6.0, graphBetweenness.getResult().get(4,2), EPSILON);
		assertEquals ( 29.0/6.0, graphBetweenness.getResult().get(3,5), EPSILON);
		assertEquals ( 29.0/6.0, graphBetweenness.getResult().get(5,3), EPSILON);
		assertEquals ( 27.0/6.0, graphBetweenness.getResult().get(4,5), EPSILON);
		assertEquals ( 27.0/6.0, graphBetweenness.getResult().get(5,4), EPSILON);
		assertEquals ( 18.0/3.0, graphBetweenness.getResult().get(4,6), EPSILON);
		assertEquals ( 18.0/3.0, graphBetweenness.getResult().get(6,4), EPSILON);
	}		
}
