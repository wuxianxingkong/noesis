package test.ikor.collection.graph;

// Title:       Generic Graph ADT
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.graph.*;
import ikor.collection.graph.search.*;

/**
 * Generic Graph Test
 * 
 * @author Fernando Berzal
 */

public class GraphTraversalTest
{
	// TEST

	public static void main (String args[])
	{
		DynamicGraph<String,Integer> map = GraphTest.roadmap();
		DynamicGraph<String,String>  web = GraphTest.web();

		GraphSearch<String,Integer>  mapSearch; 
		GraphSearch<String,String>   webSearch;

 
		System.out.println("ROADMAP");
		// System.out.println(map);

		System.out.println("\nDFS (NODES)");
		mapSearch = new DFS<String,Integer>(map, new NaiveVisitor<Node<String,Integer>>(), null);
		mapSearch.explore();

		System.out.println("\nBFS (NODES)");
		mapSearch = new BFS<String,Integer>(map, new NaiveVisitor<Node<String,Integer>>(), null);
		mapSearch.explore();

		System.out.println("\nDFS (EDGES)");
		mapSearch = new DFS<String,Integer>(map, null, new NaiveVisitor<Link<String,Integer>>());
		mapSearch.explore();

		System.out.println("\nBFS (EDGES)");
		mapSearch = new BFS<String,Integer>(map, null, new NaiveVisitor<Link<String,Integer>>());
		mapSearch.explore();

		System.out.println("WEB");
		//System.out.println(web);


		System.out.println("\nDFS (NODES)");
		webSearch = new DFS<String,String>(web, new NaiveVisitor<Node<String,String>>(), null);
		webSearch.explore();

		System.out.println("\nBFS (NODES)");
		webSearch = new BFS<String,String>(web, new NaiveVisitor<Node<String,String>>(), null);
		webSearch.explore();

		System.out.println("\nDFS (EDGES)");
		webSearch = new DFS<String,String>(web, null, new NaiveVisitor<Link<String,String>>());
		webSearch.explore();

		System.out.println("\nBFS (EDGES)");
		webSearch = new BFS<String,String>(web, null, new NaiveVisitor<Link<String,String>>());
		webSearch.explore();
	}

}

