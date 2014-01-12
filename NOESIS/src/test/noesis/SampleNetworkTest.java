package test.noesis;

import static org.junit.Assert.*;

import noesis.Network;

import org.junit.Test;

public abstract class SampleNetworkTest 
{
	public abstract Class networkClass ();  

	// Undirected network: roadmap
	
	private void testRoadmapNodeDegrees (Network<String,Integer> roadmap) 
	{
		assertEquals(5, roadmap.outDegree("Granada"));
		assertEquals(5, roadmap.inDegree("Granada"));
		
		assertEquals(2, roadmap.outDegree("Motril"));
		assertEquals(2, roadmap.inDegree("Motril"));

		assertEquals(2, roadmap.outDegree("La Zubia"));
		assertEquals(2, roadmap.inDegree("La Zubia"));

		assertEquals(3, roadmap.outDegree("Cájar"));
		assertEquals(3, roadmap.inDegree("Cájar"));

		assertEquals(2, roadmap.outDegree("Huétor Vega"));
		assertEquals(2, roadmap.inDegree("Huétor Vega"));

		assertEquals(2, roadmap.outDegree("Castell de Ferro"));
		assertEquals(2, roadmap.inDegree("Castell de Ferro"));

		assertEquals(2, roadmap.outDegree("Almería"));
		assertEquals(2, roadmap.inDegree("Almería"));

		assertEquals(2, roadmap.outDegree("Guadix"));
		assertEquals(2, roadmap.inDegree("Guadix"));		
	}

	private void testRoadmapLinks (Network<String,Integer> roadmap) 
	{
		assertEquals(3, (int) roadmap.get("Granada", "La Zubia"));
		assertEquals(3, (int) roadmap.get("La Zubia", "Granada"));
		
		assertEquals(70, (int) roadmap.get("Granada", "Motril"));
		assertEquals(70, (int) roadmap.get("Motril", "Granada"));

		assertEquals(20, (int) roadmap.get("Castell de Ferro", "Motril"));
		assertEquals(20, (int) roadmap.get("Motril", "Castell de Ferro"));
	}
	
	@Test
	public void testRoadmap() 
	{
		Network<String,Integer> roadmap = SampleNetworks.roadmap(networkClass());
		
		assertEquals(8, roadmap.size());
		assertEquals(20, roadmap.links());
		
		testRoadmapNodeDegrees(roadmap);
		testRoadmapLinks(roadmap);
	
	}

	// Directed network: web graph

	private void testWebgraphNodeDegrees (Network<String,String> web) 
	{
		assertEquals(8, web.outDegree("home"));
		assertEquals(0, web.inDegree("home"));

		assertEquals(3, web.outDegree("C"));
		assertEquals(2, web.inDegree("C"));

		assertEquals(1, web.outDegree("C#"));
		assertEquals(3, web.inDegree("C#"));

		assertEquals(0, web.outDegree("C++Builder"));
		assertEquals(3, web.inDegree("C++Builder"));

		assertEquals(3, web.outDegree("Java"));
		assertEquals(2, web.inDegree("Java"));

		assertEquals(1, web.outDegree("DB"));
		assertEquals(2, web.inDegree("DB"));

		assertEquals(0, web.outDegree("Data Mining"));
		assertEquals(2, web.inDegree("Data Mining"));

		assertEquals(1, web.outDegree("Internet"));
		assertEquals(1, web.inDegree("Internet"));

		assertEquals(0, web.outDegree("ASP.NET"));
		assertEquals(2, web.inDegree("ASP.NET"));
	}
	
	private void testWebgraphLinks (Network<String,String> web) 
	{
		assertEquals("Programming course", web.get("home","Java"));
		assertEquals("Programming course", web.get("home","C"));
		assertEquals("Programming course", web.get("home","C#"));
		assertEquals("Programming course", web.get("home","C++Builder"));
		assertEquals("Programming course", web.get("home","ASP.NET"));
		assertEquals("Undergraduate course", web.get("home","DB"));
		assertEquals("Undergraduate course", web.get("home","Internet"));
		assertEquals("Graduate course", web.get("home","Data Mining"));
		
		assertEquals("C -> C#", web.get("C","C#"));
		assertEquals("C -> C++Builder", web.get("C","C++Builder"));
		assertEquals("C -> Java", web.get("C","Java"));
		
		assertEquals("Java -> C", web.get("Java","C"));
		assertEquals("Java -> C#", web.get("Java","C#"));
		assertEquals("Java -> C++Builder", web.get("Java","C++Builder"));

		assertEquals("C# -> ASP.NET", web.get("C#","ASP.NET"));
		
		assertEquals("Additional information", web.get("DB","Data Mining"));
		assertEquals("Additional information", web.get("Internet","DB"));
	}
	
	@Test
	public void testWebgraph() 
	{
		Network<String,String> web = SampleNetworks.web(networkClass());
		
		assertEquals(9, web.size());
		assertEquals(17, web.links());

		testWebgraphNodeDegrees(web);
		testWebgraphLinks(web);
	}
	
	@Test
	public void testWebgraphAddNode ()
	{
		Network<String,String> web = SampleNetworks.web(networkClass());
		
		web.add("New page");
		
		assertEquals(9+1, web.size());
		assertEquals(17, web.links());

		testWebgraphNodeDegrees(web);
		testWebgraphLinks(web);
	}

	@Test
	public void testWebgraphAddLink ()
	{
		Network<String,String> web = SampleNetworks.web(networkClass());
		
		web.add("Java","home");
		
		assertEquals(9, web.size());
		assertEquals(17+1, web.links());

		testWebgraphLinks(web);
		
		assertEquals(3+1, web.outDegree("Java"));
		assertEquals(2, web.inDegree("Java"));
		
		assertEquals(8, web.outDegree("home"));
		assertEquals(0+1, web.inDegree("home"));
	}

	@Test
	public void testWebgraphAddNodeAndOutLink ()
	{
		Network<String,String> web = SampleNetworks.web(networkClass());
		
		web.add("New page");
		web.add("home","New page");
		
		assertEquals(9+1, web.size());
		assertEquals(17+1, web.links());

		testWebgraphLinks(web);
		
		assertEquals(0, web.outDegree("New page"));
		assertEquals(1, web.inDegree("New page"));
		
		assertEquals(8+1, web.outDegree("home"));
		assertEquals(0, web.inDegree("home"));
	}

	@Test
	public void testWebgraphAddNodeAndInLink ()
	{
		Network<String,String> web = SampleNetworks.web(networkClass());
		
		web.add("New page");
		web.add("New page","home");
		
		assertEquals(9+1, web.size());
		assertEquals(17+1, web.links());

		testWebgraphLinks(web);
		
		assertEquals(1, web.outDegree("New page"));
		assertEquals(0, web.inDegree("New page"));
		
		assertEquals(8, web.outDegree("home"));
		assertEquals(0+1, web.inDegree("home"));
	}

	@Test
	public void testWebgraphNodeRemoval ()
	{
		Network<String,String> web = SampleNetworks.web(networkClass());
		
		web.remove("Java");
		
		assertEquals(9-1, web.size());
		assertEquals(17-(3+2), web.links());
	}

	@Test
	public void testWebgraphRootNodeRemoval ()
	{
		Network<String,String> web = SampleNetworks.web(networkClass());
		
		web.remove("home");
		
		assertEquals(9-1, web.size());
		assertEquals(17-(8+0), web.links());
	}
	
	@Test
	public void testWebgraphOutNodeRemoval ()
	{
		Network<String,String> web = SampleNetworks.web(networkClass());
		
		web.remove("Data Mining");
		
		assertEquals(9-1, web.size());
		assertEquals(17-(0+2), web.links());
	}

	@Test
	public void testWebgraphClear ()
	{
		Network<String,String> web = SampleNetworks.web(networkClass());
		
		web.clear();
		
		assertEquals(0, web.size());
		assertEquals(0, web.links());
	}
	
}
