package test.noesis.algorithms.traversal;

import org.junit.Test;
import org.junit.Before;

import test.noesis.MockVisitor;
import test.noesis.SampleNetworks;

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
		roadmap = SampleNetworks.roadmap();
		web     = SampleNetworks.web();		
	}
	
	// Undirected graph

	@Test
	public void testBFSundirected() {
		
		NetworkTraversal<String,Integer>   mapSearch; 
		MockVisitor<String>                cityVisitor;
		
		cityVisitor = SampleNetworks.roadmapBFSVisitor(roadmap);
		mapSearch = new NetworkBFS<String,Integer>(roadmap, cityVisitor, null);
		
		mapSearch.traverse();
		cityVisitor.checkFinished();
	}

	@Test
	public void testDFSundirected() {
		
		NetworkTraversal<String,Integer>   mapSearch; 
		MockVisitor<String>                cityVisitor;
		
		cityVisitor = SampleNetworks.roadmapDFSVisitor(roadmap);
		mapSearch = new NetworkDFS<String,Integer>(roadmap, cityVisitor, null);
		
		mapSearch.traverse();
		cityVisitor.checkFinished();
	}

	@Test
	public void testBFSundirectedLinks() {
		
		NetworkTraversal<String,Integer>   mapSearch; 
		MockVisitor<Integer>               roadVisitor;
		
		roadVisitor = SampleNetworks.roadmapBFSLinkVisitor(roadmap);
		mapSearch = new NetworkBFS<String,Integer>(roadmap, null, roadVisitor);
		
		mapSearch.traverse();
		roadVisitor.checkFinished();
	}	
	
	@Test
	public void testDFSundirectedLinks() {
		
		NetworkTraversal<String,Integer>   mapSearch; 
		MockVisitor<Integer>               roadVisitor;
		
		roadVisitor = SampleNetworks.roadmapDFSLinkVisitor(roadmap);
		mapSearch = new NetworkDFS<String,Integer>(roadmap, null, roadVisitor);
		
		mapSearch.traverse();
		roadVisitor.checkFinished();
	}		
	
	// Directed graph
	
	@Test
	public void testBFSdirected() {
		
		NetworkTraversal<String,String> webSearch; 
		MockVisitor<String>             pageVisitor;
		
		pageVisitor = SampleNetworks.webBFSVisitor(web);
		webSearch = new NetworkBFS<String,String>(web, pageVisitor, null);
		
		webSearch.traverse();	
		pageVisitor.checkFinished();
	}

	
	@Test
	public void testDFSdirected() {
		
		NetworkTraversal<String,String>   webSearch; 
		MockVisitor<String>               pageVisitor;
		
		pageVisitor = SampleNetworks.webDFSVisitor(web);
		webSearch = new NetworkDFS<String,String>(web, pageVisitor, null);
		
		webSearch.traverse();
		pageVisitor.checkFinished();
	}
	
	@Test
	public void testBFSdirectedLinks() {
		
		NetworkTraversal<String,String>   webSearch; 
		MockVisitor<String>               linkVisitor;
		
		linkVisitor = SampleNetworks.webBFSLinkVisitor(web);
		webSearch = new NetworkBFS<String,String>(web, null, linkVisitor);
		
		webSearch.traverse();
		linkVisitor.checkFinished();
	}	

	@Test
	public void testDFSdirectedLinks() {
		
		NetworkTraversal<String,String>   webSearch; 
		MockVisitor<String>               linkVisitor;
		
		linkVisitor = SampleNetworks.webDFSLinkVisitor(web);
		webSearch = new NetworkDFS<String,String>(web, null, linkVisitor);
		
		webSearch.traverse();
		linkVisitor.checkFinished();
	}		
}

