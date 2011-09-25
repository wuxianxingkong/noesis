/**
 * JUnit test for {@link ikor.collection.graph.DynamicGraph}.
 */
package test.ikor.collection.graph;

import static org.junit.Assert.*;

import java.util.Iterator;

import ikor.collection.List;
import ikor.collection.graph.DynamicGraph;
import ikor.collection.graph.Link;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Fernan
 *
 */
public class GraphTest {

	DynamicGraph<String,Integer> roadmap;
	DynamicGraph<String,String>  web;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		roadmap = Mock.roadmap();
		web     = Mock.web();		
	}


	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#isDirected()}.
	 */
	@Test
	public void testIsDirected() {
		assertTrue(web.isDirected());
		assertFalse(roadmap.isDirected());
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#get(int)}, 
	 * which relies on {@link ikor.collection.graph.DynamicGraph#getNode(int)}.
	 */
	
	@Test
	public void testNodes() {
		
		assertEquals("Granada", roadmap.get(0));
		assertEquals("Motril", roadmap.get(1));
		assertEquals("La Zubia", roadmap.get(2));
		assertEquals("Cájar", roadmap.get(3));
		assertEquals("Huétor Vega", roadmap.get(4));
		assertEquals("Castell de Ferro", roadmap.get(5));
		assertEquals("Almería", roadmap.get(6));
		assertEquals("Guadix", roadmap.get(7));
		
		assertEquals("home", web.get(0));
		assertEquals("C", web.get(1));
		assertEquals("C#", web.get(2));
		assertEquals("C++Builder", web.get(3));
		assertEquals("Java", web.get(4));
		assertEquals("DB", web.get(5));
		assertEquals("Data Mining", web.get(6));
		assertEquals("Internet", web.get(7));
		assertEquals("ASP.NET", web.get(8));		
	}


	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#index(java.lang.Object)}.
	 */
	@Test
	public void testIndex() {

		assertEquals(0, roadmap.index("Granada"));
		assertEquals(1, roadmap.index("Motril"));
		assertEquals(2, roadmap.index("La Zubia"));
		assertEquals(3, roadmap.index("Cájar"));
		assertEquals(4, roadmap.index("Huétor Vega"));
		assertEquals(5, roadmap.index("Castell de Ferro"));
		assertEquals(6, roadmap.index("Almería"));
		assertEquals(7, roadmap.index("Guadix"));
		
		assertEquals(0, web.index("home"));
		assertEquals(1, web.index("C"));
		assertEquals(2, web.index("C#"));
		assertEquals(3, web.index("C++Builder"));
		assertEquals(4, web.index("Java"));
		assertEquals(5, web.index("DB"));
		assertEquals(6, web.index("Data Mining"));
		assertEquals(7, web.index("Internet"));
		assertEquals(8, web.index("ASP.NET"));			
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#index(ikor.collection.graph.Node)}
	 * and {@link ikor.collection.graph.DynamicGraph#getNode(int)}.
	 */
	@Test
	public void testIndexNode() {
		
		for (int i=0; i<roadmap.size(); i++)
			assertEquals ( i, roadmap.index(roadmap.getNode(i)) );

		for (int i=0; i<web.size(); i++)
			assertEquals ( i, web.index(web.getNode(i)) );
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#size()}.
	 */
	@Test
	public void testSize() {
		assertEquals(8, roadmap.size());
		assertEquals(9, web.size());
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#links()}.
	 */
	@Test
	public void testLinks() {
		assertEquals(10, roadmap.links());
		assertEquals(17, web.links());
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#degree(int)}.
	 */
	
	@Test
	public void testDegree() {
		
		// Undirected graph
		
		assertEquals(5, roadmap.degree("Granada") );
		assertEquals(2, roadmap.degree("Motril") );
		assertEquals(2, roadmap.degree("La Zubia") );
		assertEquals(3, roadmap.degree("Cájar") );
		assertEquals(2, roadmap.degree("Huétor Vega") );
		assertEquals(2, roadmap.degree("Castell de Ferro") );
		assertEquals(2, roadmap.degree("Almería") );
		assertEquals(2, roadmap.degree("Guadix") );
		
		// Directed graph (out degree)
		
		assertEquals(8, web.degree("home") );
		assertEquals(3, web.degree("C") );
		assertEquals(1, web.degree("C#") );
		assertEquals(0, web.degree("C++Builder") );
		assertEquals(3, web.degree("Java") );
		assertEquals(1, web.degree("DB") );
		assertEquals(0, web.degree("Data Mining") );
		assertEquals(1, web.degree("Internet") );
		assertEquals(0, web.degree("ASP.NET") );			
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#inDegree(int)}.
	 */
	@Test
	public void testInDegree() {
		
		// Undirected graph (in-degree == out-degree)
		
		assertEquals(5, roadmap.inDegree("Granada") );
		assertEquals(2, roadmap.inDegree("Motril") );
		assertEquals(2, roadmap.inDegree("La Zubia") );
		assertEquals(3, roadmap.inDegree("Cájar") );
		assertEquals(2, roadmap.inDegree("Huétor Vega") );
		assertEquals(2, roadmap.inDegree("Castell de Ferro") );
		assertEquals(2, roadmap.inDegree("Almería") );
		assertEquals(2, roadmap.inDegree("Guadix") );
		
		// Directed graph (out degree)
		
		assertEquals(0, web.inDegree("home") );
		assertEquals(2, web.inDegree("C") );
		assertEquals(3, web.inDegree("C#") );
		assertEquals(3, web.inDegree("C++Builder") );
		assertEquals(2, web.inDegree("Java") );
		assertEquals(2, web.inDegree("DB") );
		assertEquals(2, web.inDegree("Data Mining") );
		assertEquals(1, web.inDegree("Internet") );
		assertEquals(2, web.inDegree("ASP.NET") );	
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#outDegree(int)}.
	 */
	@Test
	public void testOutDegree() {
		
		// Undirected graph (in-degree == out-degree)
		
		assertEquals(5, roadmap.outDegree("Granada") );
		assertEquals(2, roadmap.outDegree("Motril") );
		assertEquals(2, roadmap.outDegree("La Zubia") );
		assertEquals(3, roadmap.outDegree("Cájar") );
		assertEquals(2, roadmap.outDegree("Huétor Vega") );
		assertEquals(2, roadmap.outDegree("Castell de Ferro") );
		assertEquals(2, roadmap.outDegree("Almería") );
		assertEquals(2, roadmap.outDegree("Guadix") );
		
		// Directed graph (out degree)
		
		assertEquals(8, web.outDegree("home") );
		assertEquals(3, web.outDegree("C") );
		assertEquals(1, web.outDegree("C#") );
		assertEquals(0, web.outDegree("C++Builder") );
		assertEquals(3, web.outDegree("Java") );
		assertEquals(1, web.outDegree("DB") );
		assertEquals(0, web.outDegree("Data Mining") );
		assertEquals(1, web.outDegree("Internet") );
		assertEquals(0, web.outDegree("ASP.NET") );		
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#outLinks(int)}.
	 */
	@Test
	public void testOutLinks() {
			
		// Undirected graph (in-links == out-links)
		
		assertEquals(5, roadmap.outLinks("Granada").size() );
		assertEquals(2, roadmap.outLinks("Motril").size() );
		assertEquals(2, roadmap.outLinks("La Zubia").size() );
		assertEquals(3, roadmap.outLinks("Cájar").size() );
		assertEquals(2, roadmap.outLinks("Huétor Vega").size() );
		assertEquals(2, roadmap.outLinks("Castell de Ferro").size() );
		assertEquals(2, roadmap.outLinks("Almería").size() );
		assertEquals(2, roadmap.outLinks("Guadix").size() );
		
		List<Link<String,Integer>> list = roadmap.outLinks(roadmap.index("Granada"));
				
		assertEquals ( "Granada", list.get(0).getSource().getContent() );
		assertEquals ( "Motril", list.get(0).getDestination().getContent() );
		assertEquals ( 70, list.get(0).getContent().intValue() );

		assertEquals ( "Granada", list.get(1).getSource().getContent() );
		assertEquals ( "La Zubia", list.get(1).getDestination().getContent() );
		assertEquals ( 3, list.get(1).getContent().intValue() );

		assertEquals ( "Granada", list.get(2).getSource().getContent() );
		assertEquals ( "Cájar", list.get(2).getDestination().getContent() );
		assertEquals ( 3, list.get(2).getContent().intValue() );

		assertEquals ( "Granada", list.get(3).getSource().getContent() );
		assertEquals ( "Huétor Vega", list.get(3).getDestination().getContent() );
		assertEquals ( 2, list.get(3).getContent().intValue() );

		assertEquals ( "Granada", list.get(4).getSource().getContent() );
		assertEquals ( "Guadix", list.get(4).getDestination().getContent() );
		assertEquals ( 55, list.get(4).getContent().intValue() );
		
		
		// Directed graph (out degree)
		
		assertEquals(8, web.outLinks("home").size() );
		assertEquals(3, web.outLinks("C").size() );
		assertEquals(1, web.outLinks("C#").size() );
		assertEquals(0, web.outLinks("C++Builder").size() );
		assertEquals(3, web.outLinks("Java").size() );
		assertEquals(1, web.outLinks("DB").size() );
		assertEquals(0, web.outLinks("Data Mining").size() );
		assertEquals(1, web.outLinks("Internet").size() );
		assertEquals(0, web.outLinks("ASP.NET").size() );		

		List<Link<String,String>> links = web.outLinks(web.index("Java"));

		assertEquals ( "Java", links.get(0).getSource().getContent() );
		assertEquals ( "C", links.get(0).getDestination().getContent() );
		
		assertEquals ( "Java", links.get(1).getSource().getContent() );
		assertEquals ( "C#", links.get(1).getDestination().getContent() );
		
		assertEquals ( "Java", links.get(2).getSource().getContent() );
		assertEquals ( "C++Builder", links.get(2).getDestination().getContent() );
		
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#inLinks(int)}.
	 */
	@Test
	public void testInLinks() {
				
		// Undirected graph (in-links == out-links)
		
		assertEquals(5, roadmap.inLinks("Granada").size() );
		assertEquals(2, roadmap.inLinks("Motril").size() );
		assertEquals(2, roadmap.inLinks("La Zubia").size() );
		assertEquals(3, roadmap.inLinks("Cájar").size() );
		assertEquals(2, roadmap.inLinks("Huétor Vega").size() );
		assertEquals(2, roadmap.inLinks("Castell de Ferro").size() );
		assertEquals(2, roadmap.inLinks("Almería").size() );
		assertEquals(2, roadmap.inLinks("Guadix").size() );
		
		List<Link<String,Integer>> list = roadmap.inLinks(roadmap.index("Granada"));
				
		assertEquals ( "Granada", list.get(0).getSource().getContent() );
		assertEquals ( "Motril", list.get(0).getDestination().getContent() );
		assertEquals ( 70, list.get(0).getContent().intValue() );

		assertEquals ( "Granada", list.get(1).getSource().getContent() );
		assertEquals ( "La Zubia", list.get(1).getDestination().getContent() );
		assertEquals ( 3, list.get(1).getContent().intValue() );

		assertEquals ( "Granada", list.get(2).getSource().getContent() );
		assertEquals ( "Cájar", list.get(2).getDestination().getContent() );
		assertEquals ( 3, list.get(2).getContent().intValue() );

		assertEquals ( "Granada", list.get(3).getSource().getContent() );
		assertEquals ( "Huétor Vega", list.get(3).getDestination().getContent() );
		assertEquals ( 2, list.get(3).getContent().intValue() );

		assertEquals ( "Granada", list.get(4).getSource().getContent() );
		assertEquals ( "Guadix", list.get(4).getDestination().getContent() );
		assertEquals ( 55, list.get(4).getContent().intValue() );
		
		
		// Directed graph (out degree)
		
		assertEquals(0, web.inLinks("home").size() );
		assertEquals(2, web.inLinks("C").size() );
		assertEquals(3, web.inLinks("C#").size() );
		assertEquals(3, web.inLinks("C++Builder").size() );
		assertEquals(2, web.inLinks("Java").size() );
		assertEquals(2, web.inLinks("DB").size() );
		assertEquals(2, web.inLinks("Data Mining").size() );
		assertEquals(1, web.inLinks("Internet").size() );
		assertEquals(2, web.inLinks("ASP.NET").size() );		

		List<Link<String,String>> links = web.inLinks(web.index("Java"));

		assertEquals ( "home", links.get(0).getSource().getContent() );
		assertEquals ( "Java", links.get(0).getDestination().getContent() );
		
		assertEquals ( "C", links.get(1).getSource().getContent() );
		assertEquals ( "Java", links.get(1).getDestination().getContent() );
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#add(java.lang.Object)}.
	 */
	@Test
	public void testAddNode() {
		
		roadmap.add("Jaén");
		
		assertEquals(8+1, roadmap.size());
		assertEquals(10+0, roadmap.links());
		
		assertEquals("Jaén", roadmap.get(8));
		assertEquals(8, roadmap.index("Jaén"));
		assertEquals(0, roadmap.degree("Jaén"));
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#add(java.lang.Object, java.lang.Object, java.lang.Object)},
	 * which relies on {@link ikor.collection.graph.DynamicGraph#add(int, int, java.lang.Object)}.
	 */
	@Test
	public void testAddEdge() {

		// Undirected graph
		
		assertTrue(roadmap.add("La Zubia", "Castell de Ferro", 100));

		assertEquals(8+0, roadmap.size());
		assertEquals(10+1, roadmap.links());
		
		assertEquals(2+1, roadmap.degree("La Zubia"));
		assertEquals(2+1, roadmap.degree("Castell de Ferro"));

		assertFalse(roadmap.add("La Zubia", "XXX", 100));
		assertFalse(roadmap.add("XXX", "Castell de Ferro", 100));
		assertFalse(roadmap.add("XXX", "XXX", 100));
		
		// Directed graph
		
		assertTrue(web.add("C#", "Java", null));
		
		assertEquals(9+0, web.size());
		assertEquals(17+1, web.links());
		
		assertEquals(3+0, web.inDegree("C#"));
		assertEquals(2+1, web.inDegree("Java"));
		
		assertEquals(1+1, web.outDegree("C#"));
		assertEquals(3+0, web.outDegree("Java"));	
		
		assertFalse(web.add("C#",  "XXX", null));
		assertFalse(web.add("XXX", "Java", null));
		assertFalse(web.add("XXX", "XXX", null));		
	}


	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#remove(java.lang.Object)}
	 * and {@link ikor.collection.graph.DynamicGraph#remove(ikor.collection.graph.Node)},
	 * which rely on {@link ikor.collection.graph.DynamicGraph#remove(int)}.
	 */
	@Test
	public void testRemoveNode() {
				
		// Undirected graph
		
		assertTrue(roadmap.remove("Almería"));
		
		assertEquals("Granada", roadmap.get(0));
		assertEquals("Motril", roadmap.get(1));
		assertEquals("La Zubia", roadmap.get(2));
		assertEquals("Cájar", roadmap.get(3));
		assertEquals("Huétor Vega", roadmap.get(4));
		assertEquals("Castell de Ferro", roadmap.get(5));
		// assertEquals("Almería", roadmap.get(6)); == removed node
		assertEquals("Guadix", roadmap.get(7-1));
		
		assertEquals(8-1, roadmap.size());
		assertEquals(10-2, roadmap.links());
		
		assertEquals(2-1, roadmap.degree("Castell de Ferro"));
		assertEquals(2-1, roadmap.degree("Guadix"));
		assertEquals(0, roadmap.degree("Almería"));
		
		assertEquals("Motril",  roadmap.inLinks("Castell de Ferro").get(0).getSource().getContent() );
		assertEquals("Granada", roadmap.inLinks("Guadix").get(0).getSource().getContent() );
		
		assertFalse(roadmap.remove("Jaén"));               // Remove non-existing node
		assertEquals(8-1, roadmap.size());
		assertEquals(10-2, roadmap.links());
		
		
		// Directed graph
		
		assertTrue(web.remove("Java"));
		
		assertEquals("home", web.get(0));
		assertEquals("C", web.get(1));
		assertEquals("C#", web.get(2));
		assertEquals("C++Builder", web.get(3));
		// assertEquals("Java", web.get(4));
		assertEquals("DB", web.get(5-1));
		assertEquals("Data Mining", web.get(6-1));
		assertEquals("Internet", web.get(7-1));
		assertEquals("ASP.NET", web.get(8-1));	
		
		assertEquals(9-1, web.size());
		assertEquals(17-5, web.links());
		
		assertEquals(0, web.inDegree("home") );
		assertEquals(2-1, web.inDegree("C") );
		assertEquals(3-1, web.inDegree("C#") );
		assertEquals(3-1, web.inDegree("C++Builder") );
		assertEquals(0, web.inDegree("Java") );
		assertEquals(2, web.inDegree("DB") );
		assertEquals(2, web.inDegree("Data Mining") );
		assertEquals(1, web.inDegree("Internet") );
		assertEquals(2, web.inDegree("ASP.NET") );	
		
		assertEquals(8-1, web.outDegree("home") );
		assertEquals(3-1, web.outDegree("C") );
		assertEquals(1, web.outDegree("C#") );
		assertEquals(0, web.outDegree("C++Builder") );
		assertEquals(0, web.outDegree("Java") );
		assertEquals(1, web.outDegree("DB") );
		assertEquals(0, web.outDegree("Data Mining") );
		assertEquals(1, web.outDegree("Internet") );
		assertEquals(0, web.outDegree("ASP.NET") );		
		
		assertFalse(web.remove("CV"));				// Remove non-existing node
		assertEquals(9-1, web.size());
		assertEquals(17-5, web.links());
		
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#remove(java.lang.Object, java.lang.Object, java.lang.Object)}
	 * and {@link ikor.collection.graph.DynamicGraph#remove(ikor.collection.graph.Link)},
	 * which rely on {@link ikor.collection.graph.DynamicGraph#remove(int, int, java.lang.Object)}..
	 */
	@Test
	public void testRemoveEdge() {
		
		// Undirected graph
		// ----------------
		
		// Edge with different content -> FAILURE
		
		assertFalse(roadmap.remove("Granada", "La Zubia", 3+2)); 

		assertEquals(8-0, roadmap.size());
		assertEquals(10-0, roadmap.links());
		
		assertEquals(5-0, roadmap.degree("Granada"));
		assertEquals(2-0, roadmap.degree("La Zubia"));
		
		// Edge with proper content -> SUCCESS
		
		assertTrue(roadmap.remove("Granada", "La Zubia", 3));
		
		assertEquals(8-0, roadmap.size());
		assertEquals(10-1, roadmap.links());
		
		assertEquals(5-1, roadmap.degree("Granada"));
		assertEquals(2-1, roadmap.degree("La Zubia"));
		
		// Duplicated deletion
		
		assertFalse(roadmap.remove("Granada", "La Zubia", 3));
		assertEquals(8-0, roadmap.size());
		assertEquals(10-1, roadmap.links());
		
		// Unexisting edges

		assertFalse(roadmap.remove("Granada", "Almería", 180));
		assertEquals(8-0, roadmap.size());
		assertEquals(10-1, roadmap.links());

		assertFalse(roadmap.remove("Granada", "Jaén", 90));
		assertEquals(8-0, roadmap.size());
		assertEquals(10-1, roadmap.links());

		
		// Directed graph
		// --------------
		
		// Wrong content -> Removal error
		
		assertFalse(web.remove("DB", "Data Mining", "Really?"));
		
		assertEquals(9-0, web.size());
		assertEquals(17-0, web.links());
		
		// Wrong direction -> Removal error

		assertFalse(web.remove("Data Mining", "DB", "Really?"));
		
		assertEquals(9-0, web.size());
		assertEquals(17-0, web.links());
		
		// Right content -> Removal OK
		
		assertTrue(web.remove("DB", "Data Mining", null));
		
		assertEquals(9-0, web.size());
		assertEquals(17-1, web.links());
		
		assertEquals(0, web.inDegree("home") );
		assertEquals(2, web.inDegree("C") );
		assertEquals(3, web.inDegree("C#") );
		assertEquals(3, web.inDegree("C++Builder") );
		assertEquals(2, web.inDegree("Java") );
		assertEquals(2, web.inDegree("DB") );
		assertEquals(2-1, web.inDegree("Data Mining") );
		assertEquals(1, web.inDegree("Internet") );
		assertEquals(2, web.inDegree("ASP.NET") );	
		
		assertEquals(8, web.outDegree("home") );
		assertEquals(3, web.outDegree("C") );
		assertEquals(1, web.outDegree("C#") );
		assertEquals(0, web.outDegree("C++Builder") );
		assertEquals(3, web.outDegree("Java") );
		assertEquals(1-1, web.outDegree("DB") );
		assertEquals(0, web.outDegree("Data Mining") );
		assertEquals(1, web.outDegree("Internet") );
		assertEquals(0, web.outDegree("ASP.NET") );				

		// Duplicated deletion
		
		assertFalse(web.remove("DB", "Data Mining", null));	
		assertEquals(9-0, web.size());
		assertEquals(17-1, web.links());
		
		// Unexisting arcs
		
		assertFalse(web.remove("DB", "home", null));
		assertEquals(9-0, web.size());
		assertEquals(17-1, web.links());
		
		assertFalse(web.remove("DB", "CV", null));
		assertEquals(9-0, web.size());
		assertEquals(17-1, web.links());

		assertFalse(web.remove("CV", "DB", null));
		assertEquals(9-0, web.size());
		assertEquals(17-1, web.links());

		assertFalse(web.remove("CV", "CV", null));
		assertEquals(9-0, web.size());
		assertEquals(17-1, web.links());
	}

	
	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#contains(java.lang.Object)}.
	 */
	@Test
	public void testContains() {
		
		assertTrue(roadmap.contains("Granada"));
		assertTrue(roadmap.contains("Motril"));
		assertTrue(roadmap.contains("La Zubia"));
		assertTrue(roadmap.contains("Cájar"));
		assertTrue(roadmap.contains("Huétor Vega"));
		assertTrue(roadmap.contains("Castell de Ferro"));
		assertTrue(roadmap.contains("Almería"));
		assertTrue(roadmap.contains("Guadix"));
		assertFalse(roadmap.contains("Jaén"));
		assertFalse(roadmap.contains("Málaga"));
		
		assertTrue(web.contains("home"));
		assertTrue(web.contains("C"));
		assertTrue(web.contains("C#"));
		assertTrue(web.contains("C++Builder"));
		assertTrue(web.contains("Java"));
		assertTrue(web.contains("DB"));
		assertTrue(web.contains("Data Mining"));
		assertTrue(web.contains("Internet"));
		assertTrue(web.contains("ASP.NET"));			
		assertFalse(web.contains("CV"));			
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#iterator()}.
	 */
	@Test
	public void testIterator() {
				
		Iterator<String> iterator;
		
		// Undirected graph
		
		iterator = roadmap.iterator();

		assertEquals("Granada", iterator.next());
		assertEquals("Motril", iterator.next());
		assertEquals("La Zubia", iterator.next());
		assertEquals("Cájar", iterator.next());
		assertEquals("Huétor Vega", iterator.next());
		assertEquals("Castell de Ferro", iterator.next());
		assertEquals("Almería", iterator.next());
		assertEquals("Guadix", iterator.next());
		assertEquals(null, iterator.next());
		
		iterator = web.iterator();
		
		assertEquals("home", iterator.next());
		assertEquals("C", iterator.next());
		assertEquals("C#", iterator.next());
		assertEquals("C++Builder", iterator.next());
		assertEquals("Java", iterator.next());
		assertEquals("DB", iterator.next());
		assertEquals("Data Mining", iterator.next());
		assertEquals("Internet", iterator.next());
		assertEquals("ASP.NET", iterator.next());				
	}

	/**
	 * Test method for {@link ikor.collection.graph.DynamicGraph#clear()}.
	 */
	@Test
	public void testClear() {
		
		roadmap.clear();
		assertEquals(0, roadmap.size());
		assertEquals(0, roadmap.links());
		
		web.clear();
		assertEquals(0, web.size());
		assertEquals(0, web.links());
	}

}
