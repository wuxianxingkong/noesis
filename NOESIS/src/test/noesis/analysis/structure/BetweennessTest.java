package test.noesis.analysis.structure;

import static org.junit.Assert.*;

import noesis.BasicNetwork;
import noesis.Network;

import noesis.analysis.structure.Betweenness;
import noesis.analysis.structure.BetweennessScore;

import org.junit.Before;
import org.junit.Test;

public class BetweennessTest 
{
	public final double EPSILON = 1e-9;

	Network tree;
	Network graph;
	
	BetweennessScore treeBetweenness;
	BetweennessScore graphBetweenness;
	
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
	}
	
	private void addLink (Network net, int a, int b)
	{
		net.add(a,b);
		net.add(b,a);
	}
	
	@Test
	public void testDistance ()
	{
		BetweennessScore treeBetweenness = new BetweennessScore(tree,0);
		BetweennessScore graphBetweenness = new BetweennessScore(graph,0);
		
		treeBetweenness.compute();
		graphBetweenness.compute();
		
		assertEquals ( 0, treeBetweenness.distance(0));
		assertEquals ( 1, treeBetweenness.distance(1));
		assertEquals ( 1, treeBetweenness.distance(2));
		assertEquals ( 2, treeBetweenness.distance(3));
		assertEquals ( 2, treeBetweenness.distance(4));
		assertEquals ( 2, treeBetweenness.distance(5));
		assertEquals ( 3, treeBetweenness.distance(6));

		assertEquals ( 0, graphBetweenness.distance(0));
		assertEquals ( 1, graphBetweenness.distance(1));
		assertEquals ( 1, graphBetweenness.distance(2));
		assertEquals ( 2, graphBetweenness.distance(3));
		assertEquals ( 2, graphBetweenness.distance(4));
		assertEquals ( 3, graphBetweenness.distance(5));
		assertEquals ( 3, graphBetweenness.distance(6));	
	}
	
	@Test
	public void testGeodesics ()
	{	
		BetweennessScore treeBetweenness = new BetweennessScore(tree,0);
		BetweennessScore graphBetweenness = new BetweennessScore(graph,0);
		
		treeBetweenness.compute();
		graphBetweenness.compute();
		
		assertEquals ( 1, treeBetweenness.geodesics(0));
		assertEquals ( 1, treeBetweenness.geodesics(1));
		assertEquals ( 1, treeBetweenness.geodesics(2));
		assertEquals ( 1, treeBetweenness.geodesics(3));
		assertEquals ( 1, treeBetweenness.geodesics(4));
		assertEquals ( 1, treeBetweenness.geodesics(5));
		assertEquals ( 1, treeBetweenness.geodesics(6));

		assertEquals ( 1, graphBetweenness.geodesics(0));
		assertEquals ( 1, graphBetweenness.geodesics(1));
		assertEquals ( 1, graphBetweenness.geodesics(2));
		assertEquals ( 2, graphBetweenness.geodesics(3));
		assertEquals ( 1, graphBetweenness.geodesics(4));
		assertEquals ( 3, graphBetweenness.geodesics(5));
		assertEquals ( 1, graphBetweenness.geodesics(6));	
	}
	
	@Test
	public void testScores ()
	{	
		BetweennessScore treeBetweenness = new BetweennessScore(tree,0);
		BetweennessScore graphBetweenness = new BetweennessScore(graph,0);
		
		treeBetweenness.compute();
		graphBetweenness.compute();
		
		assertEquals ( 7, treeBetweenness.get(0), EPSILON);
		assertEquals ( 2, treeBetweenness.get(1), EPSILON);
		assertEquals ( 4, treeBetweenness.get(2), EPSILON);
		assertEquals ( 1, treeBetweenness.get(3), EPSILON);
		assertEquals ( 1, treeBetweenness.get(4), EPSILON);
		assertEquals ( 2, treeBetweenness.get(5), EPSILON);
		assertEquals ( 1, treeBetweenness.get(6), EPSILON);

		assertEquals (  7.0,     graphBetweenness.get(0), EPSILON);
		assertEquals ( 11.0/6.0, graphBetweenness.get(1), EPSILON);
		assertEquals ( 25.0/6.0, graphBetweenness.get(2), EPSILON);
		assertEquals (  5.0/3.0, graphBetweenness.get(3), EPSILON);
		assertEquals (  7.0/3.0, graphBetweenness.get(4), EPSILON);
		assertEquals (  1.0,     graphBetweenness.get(5), EPSILON);
		assertEquals (  1.0,     graphBetweenness.get(6), EPSILON);	
	}	
	
	
	@Test
	public void testBetweenness ()
	{	
		Betweenness treeBetweenness = new Betweenness(tree);
		Betweenness graphBetweenness = new Betweenness(graph);
		
		treeBetweenness.compute();
		graphBetweenness.compute();
		
		assertEquals ( 7+5+3+5+3+3+3, treeBetweenness.get(0), EPSILON);
		assertEquals ( 2+7+2+6+2+2+2, treeBetweenness.get(1), EPSILON);
		assertEquals ( 4+4+7+4+6+5+5, treeBetweenness.get(2), EPSILON);
		assertEquals ( 1+1+1+7+1+1+1, treeBetweenness.get(3), EPSILON);
		assertEquals ( 1+1+1+1+7+1+1, treeBetweenness.get(4), EPSILON);
		assertEquals ( 2+2+2+2+2+7+6, treeBetweenness.get(5), EPSILON);
		assertEquals ( 1+1+1+1+1+1+7, treeBetweenness.get(6), EPSILON);

		assertEquals ( 46.0/3.0, graphBetweenness.get(0), EPSILON);
		assertEquals ( 44.0/3.0, graphBetweenness.get(1), EPSILON);
		assertEquals ( 72.0/3.0, graphBetweenness.get(2), EPSILON);
		assertEquals ( 63.0/3.0, graphBetweenness.get(3), EPSILON);
		assertEquals ( 74.0/3.0, graphBetweenness.get(4), EPSILON);
		assertEquals ( 49.0/3.0, graphBetweenness.get(5), EPSILON);
		assertEquals ( 39.0/3.0, graphBetweenness.get(6), EPSILON);	
	}		
}
