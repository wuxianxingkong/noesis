package test.ikor.collection.graph;

// Title:       Generic Graph ADT
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import org.junit.Before;
import org.junit.Test;

import test.ikor.collection.MockVisitor;

import ikor.collection.graph.*;

public class GraphIteratorTest
{
	
	DynamicGraph<String,Integer> roadmap;
	DynamicGraph<String,String>  web;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		roadmap = MockObjects.roadmap();
		web     = MockObjects.web();		
	}

	
	// Undirected graph

	@Test
	public void testNodeIteratorUndirected()
	{
		GraphNodeIterator<String> iterator = new GraphNodeIterator<String>(roadmap);
		MockVisitor<String>       visitor  = createNodeVisitor(roadmap);
		
		while (iterator.hasNext())
			visitor.visit(iterator.next());

		visitor.checkFinished();
	}
	
	// Directed graph
	
	@Test
	public void testNodeIteratorDirected() 
	{
		GraphNodeIterator<String> iterator = new GraphNodeIterator<String>(web);
		MockVisitor<String>       visitor  = createNodeVisitor(web);
		
		while (iterator.hasNext())
			visitor.visit(iterator.next());

		visitor.checkFinished();
	}

	// Link iterator

	@Test
	public void testLinkIteratorUndirected() 
	{
		GraphLinkIterator<Integer> iterator = new GraphLinkIterator<Integer>(roadmap);
		MockVisitor<Integer>       visitor  = createLinkVisitor(roadmap);
		
		while (iterator.hasNext())
			visitor.visit(iterator.next());

		visitor.checkFinished();		
	}

	


	private MockVisitor<String> createNodeVisitor (Graph<String,?> graph)
	{
		MockVisitor<String> visitor = new MockVisitor<String>();
		
		for (int i=0; i<graph.size(); i++)
			visitor.addVisit(graph.get(i));

		return visitor;
	}
	
	private MockVisitor<Integer> createLinkVisitor (Graph<?,Integer> graph)
	{
		MockVisitor<Integer> visitor = new MockVisitor<Integer>();
		
		for (int i=0; i<graph.size(); i++) {
			
			int[] links = graph.outLinks(i);
			
			for (int j=0; j<links.length; j++)
				visitor.addVisit(graph.get(i,links[j]));
		}
		
		return visitor;
	}	
	
}

