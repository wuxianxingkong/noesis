package test.noesis.algorithms.paths;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import test.noesis.algorithms.TestNetworks;

import ikor.collection.Evaluator;

import noesis.Network;
import noesis.algorithms.paths.BreadthFirstPathFinder;
import noesis.algorithms.paths.DepthFirstPathFinder;
import noesis.algorithms.paths.DijkstraShortestPathFinder;
import noesis.algorithms.paths.PathFinder;


public class PathFinderTest 
{
	private final double EPSILON = 0.000001;
		
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
	}

	// DepthFirstPathFinder
	
	@Test
	public void testDepthFirstConnected() 
	{
		Network<String,Integer> graph = TestNetworks.weightedDirectedGraph();
		DepthFirstPathFinder pathFinder = new DepthFirstPathFinder(graph,0);
		
		pathFinder.run();
		
		Network<String,Integer> paths = pathFinder.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-1, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 23, (int) paths.get("2","3") );
		assertEquals(  2, (int) paths.get("3","5") );
		assertEquals( 11, (int) paths.get("5","4") );
		assertEquals(  6, (int) paths.get("4","t") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals(  5, (int) paths.get("6","7") );

		testPath ( pathFinder, new String[]{"s"} );
		testPath ( pathFinder, new String[]{"s","2"} );
		testPath ( pathFinder, new String[]{"s","2","3"} );
		testPath ( pathFinder, new String[]{"s","2","3","5","4"} );
		testPath ( pathFinder, new String[]{"s","2","3","5"} );
		testPath ( pathFinder, new String[]{"s","6"} );
		testPath ( pathFinder, new String[]{"s","6","7"} );
		testPath ( pathFinder, new String[]{"s","2","3","5","4","t"} );	
	}	

	@Test
	public void testDepthFirstUnreachable() 
	{
		Network<String,Integer> graph = TestNetworks.weightedUnreachableGraph();;
		DepthFirstPathFinder pathFinder = new DepthFirstPathFinder(graph,0);
		
		pathFinder.run();
			
		Network<String,Integer> paths = pathFinder.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-3, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 23, (int) paths.get("2","3") );
		assertEquals( 19, (int) paths.get("3","t") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals(  5, (int) paths.get("6","7") );

		testPath ( pathFinder, new String[]{"s"} );
		testPath ( pathFinder, new String[]{"s","2"} );
		testPath ( pathFinder, new String[]{"s","2","3"} );
		testPath ( pathFinder, new String[]{"s","6"} );
		testPath ( pathFinder, new String[]{"s","6","7"} );
		testPath ( pathFinder, new String[]{"s","2","3","t"} );
		
		assertNull( pathFinder.pathTo(graph.index("4")));
		assertNull( pathFinder.pathTo(graph.index("5")));
	}	
	
	@Test
	public void testDepthFirstDisconnected() 
	{
		Network<String,Integer> graph = TestNetworks.weightedDisconnectedGraph();;
		DepthFirstPathFinder pathFinder = new DepthFirstPathFinder(graph,0);
		
		pathFinder.run();
			
		Network<String,Integer> paths = pathFinder.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-5, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals(  5, (int) paths.get("6","7") );

		testPath ( pathFinder, new String[]{"s"} );
		testPath ( pathFinder, new String[]{"s","2"} );
		testPath ( pathFinder, new String[]{"s","6"} );
		testPath ( pathFinder, new String[]{"s","6","7"} );
		
		assertNull( pathFinder.pathTo(graph.index("3")));
		assertNull( pathFinder.pathTo(graph.index("4")));
		assertNull( pathFinder.pathTo(graph.index("5")));
		assertNull( pathFinder.pathTo(graph.index("t")));
	}		

	// BreadthFirstPathFinder
	
	@Test
	public void testBreadthFirstConnected() 
	{
		Network<String,Integer> graph = TestNetworks.weightedDirectedGraph();
		BreadthFirstPathFinder pathFinder = new BreadthFirstPathFinder(graph,0);
		
		pathFinder.run();
		
		Network<String,Integer> paths = pathFinder.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-1, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals( 15, (int) paths.get("s","7") );
		assertEquals( 23, (int) paths.get("2","3") );
		assertEquals( 30, (int) paths.get("6","5") );
		assertEquals( 44, (int) paths.get("7","t") );
		assertEquals( 11, (int) paths.get("5","4") );

		testPath ( pathFinder, new String[]{"s"} );
		testPath ( pathFinder, new String[]{"s","2"} );
		testPath ( pathFinder, new String[]{"s","2","3"} );
		testPath ( pathFinder, new String[]{"s","6","5","4"} );
		testPath ( pathFinder, new String[]{"s","6","5"} );
		testPath ( pathFinder, new String[]{"s","6"} );
		testPath ( pathFinder, new String[]{"s","7"} );
		testPath ( pathFinder, new String[]{"s","7","t"} );	
	}	

	@Test
	public void testBreadthFirstUnreachable() 
	{
		Network<String,Integer> graph = TestNetworks.weightedUnreachableGraph();;
		BreadthFirstPathFinder pathFinder = new BreadthFirstPathFinder(graph,0);
		
		pathFinder.run();
			
		Network<String,Integer> paths = pathFinder.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-3, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals( 15, (int) paths.get("s","7") );
		assertEquals( 23, (int) paths.get("2","3") );
		assertEquals( 23, (int) paths.get("2","3") );
		assertEquals( 44, (int) paths.get("7","t") );
		
		testPath ( pathFinder, new String[]{"s"} );
		testPath ( pathFinder, new String[]{"s","2"} );
		testPath ( pathFinder, new String[]{"s","2","3"} );
		testPath ( pathFinder, new String[]{"s","6"} );
		testPath ( pathFinder, new String[]{"s","7"} );
		testPath ( pathFinder, new String[]{"s","7","t"} );
		
		assertNull( pathFinder.pathTo(graph.index("4")));
		assertNull( pathFinder.pathTo(graph.index("5")));
	}	
	
	@Test
	public void testBreadthFirstDisconnected() 
	{
		Network<String,Integer> graph = TestNetworks.weightedDisconnectedGraph();;
		BreadthFirstPathFinder pathFinder = new BreadthFirstPathFinder(graph,0);
		
		pathFinder.run();
			
		Network<String,Integer> paths = pathFinder.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-5, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals( 15, (int) paths.get("s","7") );

		testPath ( pathFinder, new String[]{"s"} );
		testPath ( pathFinder, new String[]{"s","2"} );
		testPath ( pathFinder, new String[]{"s","6"} );
		testPath ( pathFinder, new String[]{"s","7"} );
		
		assertNull( pathFinder.pathTo(graph.index("3")));
		assertNull( pathFinder.pathTo(graph.index("4")));
		assertNull( pathFinder.pathTo(graph.index("5")));
		assertNull( pathFinder.pathTo(graph.index("t")));
	}		
	
	// DijkstraShortestPathFinder
	
	@Test
	public void testDijkstraConnected() 
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		Network<String,Integer> graph = TestNetworks.weightedDirectedGraph();
		DijkstraShortestPathFinder dijkstra = new DijkstraShortestPathFinder(graph,0,linkEvaluator);
		
		dijkstra.run();
		
		assertEquals(  0, dijkstra.distance(graph.index("s")), EPSILON );
		assertEquals(  9, dijkstra.distance(graph.index("2")), EPSILON );
		assertEquals( 32, dijkstra.distance(graph.index("3")), EPSILON );
		assertEquals( 45, dijkstra.distance(graph.index("4")), EPSILON );
		assertEquals( 34, dijkstra.distance(graph.index("5")), EPSILON );
		assertEquals( 14, dijkstra.distance(graph.index("6")), EPSILON );
		assertEquals( 15, dijkstra.distance(graph.index("7")), EPSILON );
		assertEquals( 50, dijkstra.distance(graph.index("t")), EPSILON );
		
		Network<String,Integer> paths = dijkstra.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-1, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals( 15, (int) paths.get("s","7") );
		assertEquals( 23, (int) paths.get("2","3") );
		assertEquals(  2, (int) paths.get("3","5") );
		assertEquals( 11, (int) paths.get("5","4") );
		assertEquals( 16, (int) paths.get("5","t") );

		testPath ( dijkstra, new String[]{"s"} );
		testPath ( dijkstra, new String[]{"s","2"} );
		testPath ( dijkstra, new String[]{"s","2","3"} );
		testPath ( dijkstra, new String[]{"s","2","3","5","4"} );
		testPath ( dijkstra, new String[]{"s","2","3","5"} );
		testPath ( dijkstra, new String[]{"s","6"} );
		testPath ( dijkstra, new String[]{"s","7"} );
		testPath ( dijkstra, new String[]{"s","2","3","5","t"} );	
	}	

	@Test
	public void testDijkstraUnreachable() 
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		Network<String,Integer> graph = TestNetworks.weightedUnreachableGraph();;
		DijkstraShortestPathFinder dijkstra = new DijkstraShortestPathFinder(graph,0,linkEvaluator);
		
		dijkstra.run();
		
		assertEquals(  0, dijkstra.distance(graph.index("s")), EPSILON );
		assertEquals(  9, dijkstra.distance(graph.index("2")), EPSILON );
		assertEquals( 32, dijkstra.distance(graph.index("3")), EPSILON );
		assertEquals( Double.POSITIVE_INFINITY, dijkstra.distance(graph.index("4")), EPSILON );
		assertEquals( Double.POSITIVE_INFINITY, dijkstra.distance(graph.index("5")), EPSILON );
		assertEquals( 14, dijkstra.distance(graph.index("6")), EPSILON );
		assertEquals( 15, dijkstra.distance(graph.index("7")), EPSILON );
		assertEquals( 51, dijkstra.distance(graph.index("t")), EPSILON );
		
		Network<String,Integer> paths = dijkstra.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-3, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals( 15, (int) paths.get("s","7") );
		assertEquals( 23, (int) paths.get("2","3") );
		assertEquals( 19, (int) paths.get("3","t") );

		testPath ( dijkstra, new String[]{"s"} );
		testPath ( dijkstra, new String[]{"s","2"} );
		testPath ( dijkstra, new String[]{"s","2","3"} );
		testPath ( dijkstra, new String[]{"s","6"} );
		testPath ( dijkstra, new String[]{"s","7"} );
		testPath ( dijkstra, new String[]{"s","2","3","t"} );
		
		assertNull( dijkstra.pathTo(graph.index("4")));
		assertNull( dijkstra.pathTo(graph.index("5")));
	}	
	
	@Test
	public void testDijkstraDisconnected() 
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		Network<String,Integer> graph = TestNetworks.weightedDisconnectedGraph();;
		DijkstraShortestPathFinder dijkstra = new DijkstraShortestPathFinder(graph,0,linkEvaluator);
		
		dijkstra.run();
		
		assertEquals(  0, dijkstra.distance(graph.index("s")), EPSILON );
		assertEquals(  9, dijkstra.distance(graph.index("2")), EPSILON );
		assertEquals( 14, dijkstra.distance(graph.index("6")), EPSILON );
		assertEquals( 15, dijkstra.distance(graph.index("7")), EPSILON );

		assertEquals( Double.POSITIVE_INFINITY, dijkstra.distance(graph.index("3")), EPSILON );
		assertEquals( Double.POSITIVE_INFINITY, dijkstra.distance(graph.index("4")), EPSILON );
		assertEquals( Double.POSITIVE_INFINITY, dijkstra.distance(graph.index("5")), EPSILON );
		assertEquals( Double.POSITIVE_INFINITY, dijkstra.distance(graph.index("t")), EPSILON );
		
		Network<String,Integer> paths = dijkstra.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-5, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals( 15, (int) paths.get("s","7") );

		testPath ( dijkstra, new String[]{"s"} );
		testPath ( dijkstra, new String[]{"s","2"} );
		testPath ( dijkstra, new String[]{"s","6"} );
		testPath ( dijkstra, new String[]{"s","7"} );
		
		assertNull( dijkstra.pathTo(graph.index("3")));
		assertNull( dijkstra.pathTo(graph.index("4")));
		assertNull( dijkstra.pathTo(graph.index("5")));
		assertNull( dijkstra.pathTo(graph.index("t")));
	}		
	
	// Ancillary routine
	
	private void testPath ( PathFinder dijkstra, String[] nodes)
	{
		Network<String,Integer> graph = dijkstra.network();
		int   length = nodes.length;
		int[] path = dijkstra.pathTo(graph.index(nodes[length-1]));
		
		assertEquals (length, path.length);
		
		for (int i=0; i<nodes.length; i++)
			assertEquals( graph.index(nodes[i]), path[i] );
	}
	
	// Ancillary class
	
	class LinkEvaluator implements Evaluator<Integer>
	{
		@Override
		public double evaluate(Integer object) 
		{
			return object;
		}
	}	
}
