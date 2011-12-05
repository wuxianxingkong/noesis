package test.noesis.algorithms.traversal;

import org.junit.Test;
import org.junit.Before;

import test.noesis.MockVisitor;
import test.noesis.algorithms.MockObjects;

import noesis.Network;
import noesis.algorithms.traversal.*;

public class NetworkTraversalTest
{
	
	Network<String,Integer> roadmap;
	Network<String,String>  web;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		roadmap = MockObjects.roadmap();
		web     = MockObjects.web();		
	}
	
	// Undirected graph

	@Test
	public void testBFSundirected() {
		
		NetworkTraversal<String,Integer>   mapSearch; 
		MockVisitor<String>                cityVisitor;
		
		cityVisitor = MockObjects.roadmapBFSVisitor(roadmap);
		mapSearch = new NetworkBFS<String,Integer>(roadmap, cityVisitor, null);
		
		mapSearch.traverse();
		cityVisitor.checkFinished();
	}

	@Test
	public void testDFSundirected() {
		
		NetworkTraversal<String,Integer>   mapSearch; 
		MockVisitor<String>                cityVisitor;
		
		cityVisitor = MockObjects.roadmapDFSVisitor(roadmap);
		mapSearch = new NetworkDFS<String,Integer>(roadmap, cityVisitor, null);
		
		mapSearch.traverse();
		cityVisitor.checkFinished();
	}

	@Test
	public void testBFSundirectedLinks() {
		
		NetworkTraversal<String,Integer>   mapSearch; 
		MockVisitor<Integer>               roadVisitor;
		
		roadVisitor = MockObjects.roadmapBFSLinkVisitor(roadmap);
		mapSearch = new NetworkBFS<String,Integer>(roadmap, null, roadVisitor);
		
		mapSearch.traverse();
		roadVisitor.checkFinished();
	}	
	
	@Test
	public void testDFSundirectedLinks() {
		
		NetworkTraversal<String,Integer>   mapSearch; 
		MockVisitor<Integer>               roadVisitor;
		
		roadVisitor = MockObjects.roadmapDFSLinkVisitor(roadmap);
		mapSearch = new NetworkDFS<String,Integer>(roadmap, null, roadVisitor);
		
		mapSearch.traverse();
		roadVisitor.checkFinished();
	}		
	
	// Directed graph
	
	@Test
	public void testBFSdirected() {
		
		NetworkTraversal<String,String> webSearch; 
		MockVisitor<String>             pageVisitor;
		
		pageVisitor = MockObjects.webBFSVisitor(web);
		webSearch = new NetworkBFS<String,String>(web, pageVisitor, null);
		
		webSearch.traverse();	
		pageVisitor.checkFinished();
	}

	
	@Test
	public void testDFSdirected() {
		
		NetworkTraversal<String,String>   webSearch; 
		MockVisitor<String>               pageVisitor;
		
		pageVisitor = MockObjects.webDFSVisitor(web);
		webSearch = new NetworkDFS<String,String>(web, pageVisitor, null);
		
		webSearch.traverse();
		pageVisitor.checkFinished();
	}
	
	@Test
	public void testBFSdirectedLinks() {
		
		NetworkTraversal<String,String>   webSearch; 
		MockVisitor<String>               linkVisitor;
		
		linkVisitor = MockObjects.webBFSLinkVisitor(web);
		webSearch = new NetworkBFS<String,String>(web, null, linkVisitor);
		
		webSearch.traverse();
		linkVisitor.checkFinished();
	}	

	@Test
	public void testDFSdirectedLinks() {
		
		NetworkTraversal<String,String>   webSearch; 
		MockVisitor<String>               linkVisitor;
		
		linkVisitor = MockObjects.webDFSLinkVisitor(web);
		webSearch = new NetworkDFS<String,String>(web, null, linkVisitor);
		
		webSearch.traverse();
		linkVisitor.checkFinished();
	}		
}

