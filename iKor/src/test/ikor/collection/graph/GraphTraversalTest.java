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
import ikor.collection.graph.search.*;

public class GraphTraversalTest
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
	public void testBFSundirected() {
		
		GraphSearch<String,Integer>    mapSearch; 
		MockVisitor<GraphNode<String>> cityVisitor;
		
		cityVisitor = MockObjects.roadmapBFSVisitor(roadmap);
		mapSearch = new BFS<String,Integer>(roadmap, cityVisitor, null);
		
		mapSearch.explore();
		cityVisitor.checkFinished();
	}

	@Test
	public void testDFSundirected() {
		
		GraphSearch<String,Integer>    mapSearch; 
		MockVisitor<GraphNode<String>> cityVisitor;
		
		cityVisitor = MockObjects.roadmapDFSVisitor(roadmap);
		mapSearch = new DFS<String,Integer>(roadmap, cityVisitor, null);
		
		mapSearch.explore();
		cityVisitor.checkFinished();
	}

	@Test
	public void testBFSundirectedLinks() {
		
		GraphSearch<String,Integer>     mapSearch; 
		MockVisitor<GraphLink<Integer>> roadVisitor;
		
		roadVisitor = MockObjects.roadmapBFSLinkVisitor(roadmap);
		mapSearch = new BFS<String,Integer>(roadmap, null, roadVisitor);
		
		mapSearch.explore();
		roadVisitor.checkFinished();
	}	
	
	@Test
	public void testDFSundirectedLinks() {
		
		GraphSearch<String,Integer>     mapSearch; 
		MockVisitor<GraphLink<Integer>> roadVisitor;
		
		roadVisitor = MockObjects.roadmapDFSLinkVisitor(roadmap);
		mapSearch = new DFS<String,Integer>(roadmap, null, roadVisitor);
		
		mapSearch.explore();
		roadVisitor.checkFinished();
	}		
	
	// Directed graph
	
	@Test
	public void testBFSdirected() {
		
		GraphSearch<String,String>     webSearch; 
		MockVisitor<GraphNode<String>> pageVisitor;
		
		pageVisitor = MockObjects.webBFSVisitor(web);
		webSearch = new BFS<String,String>(web, pageVisitor, null);
		
		webSearch.explore();	
		pageVisitor.checkFinished();
	}

	
	@Test
	public void testDFSdirected() {
		
		GraphSearch<String,String>     webSearch; 
		MockVisitor<GraphNode<String>> pageVisitor;
		
		pageVisitor = MockObjects.webDFSVisitor(web);
		webSearch = new DFS<String,String>(web, pageVisitor, null);
		
		webSearch.explore();
		pageVisitor.checkFinished();
	}
	
	@Test
	public void testBFSdirectedLinks() {
		
		GraphSearch<String,String>     webSearch; 
		MockVisitor<GraphLink<String>> linkVisitor;
		
		linkVisitor = MockObjects.webBFSLinkVisitor(web);
		webSearch = new BFS<String,String>(web, null, linkVisitor);
		
		webSearch.explore();
		linkVisitor.checkFinished();
	}	

	@Test
	public void testDFSdirectedLinks() {
		
		GraphSearch<String,String>     webSearch; 
		MockVisitor<GraphLink<String>> linkVisitor;
		
		linkVisitor = MockObjects.webDFSLinkVisitor(web);
		webSearch = new DFS<String,String>(web, null, linkVisitor);
		
		webSearch.explore();
		linkVisitor.checkFinished();
	}		
	
	// TEST

	public static void main (String args[])
	{
		DynamicGraph<String,Integer> map = MockObjects.roadmap();
		DynamicGraph<String,String>  web = MockObjects.web();

		GraphSearch<String,Integer>  mapSearch; 
		GraphSearch<String,String>   webSearch;

		System.out.println("ROADMAP");
		// System.out.println(map);

		System.out.println("\nDFS (NODES)");
		mapSearch = new DFS<String,Integer>(map, new MockVisitor<GraphNode<String>>(), null);
		mapSearch.explore();

		System.out.println("\nBFS (NODES)");
		mapSearch = new BFS<String,Integer>(map, new MockVisitor<GraphNode<String>>(), null);
		mapSearch.explore();

		System.out.println("\nDFS (EDGES)");
		mapSearch = new DFS<String,Integer>(map, null, new MockVisitor<GraphLink<Integer>>());
		mapSearch.explore();

		System.out.println("\nBFS (EDGES)");
		mapSearch = new BFS<String,Integer>(map, null, new MockVisitor<GraphLink<Integer>>());
		mapSearch.explore();

		System.out.println("WEB");
		//System.out.println(web);


		System.out.println("\nDFS (NODES)");
		webSearch = new DFS<String,String>(web, new MockVisitor<GraphNode<String>>(), null);
		webSearch.explore();

		System.out.println("\nBFS (NODES)");
		webSearch = new BFS<String,String>(web, new MockVisitor<GraphNode<String>>(), null);
		webSearch.explore();

		System.out.println("\nDFS (EDGES)");
		webSearch = new DFS<String,String>(web, null, new MockVisitor<GraphLink<String>>());
		webSearch.explore();

		System.out.println("\nBFS (EDGES)");
		webSearch = new BFS<String,String>(web, null, new MockVisitor<GraphLink<String>>());
		webSearch.explore();
	}

}

