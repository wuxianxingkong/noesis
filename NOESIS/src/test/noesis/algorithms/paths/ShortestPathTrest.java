package test.noesis.algorithms.paths;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import test.noesis.algorithms.MockObjects;

import ikor.collection.Evaluator;

import noesis.Network;
import noesis.algorithms.paths.DijkstraShortestPaths;


public class ShortestPathTrest 
{
	Network<String,Integer> graph;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		graph = MockObjects.weightedDirectedGraph();
	}
	
	@Test
	public void testDijkstra() 
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		DijkstraShortestPaths dijkstra = new DijkstraShortestPaths(graph,0,linkEvaluator);
		
		dijkstra.run();
		
		assertEquals(  0, dijkstra.distance(graph.index("s")), 0.001 );
		assertEquals(  9, dijkstra.distance(graph.index("2")), 0.001 );
		assertEquals( 32, dijkstra.distance(graph.index("3")), 0.001 );
		assertEquals( 45, dijkstra.distance(graph.index("4")), 0.001 );
		assertEquals( 34, dijkstra.distance(graph.index("5")), 0.001 );
		assertEquals( 14, dijkstra.distance(graph.index("6")), 0.001 );
		assertEquals( 15, dijkstra.distance(graph.index("7")), 0.001 );
		assertEquals( 50, dijkstra.distance(graph.index("t")), 0.001 );
		
		Network<String,Integer> paths = dijkstra.shortestPaths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-1, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals( 15, (int) paths.get("s","7") );
		assertEquals( 23, (int) paths.get("2","3") );
		assertEquals(  2, (int) paths.get("3","5") );
		assertEquals( 11, (int) paths.get("5","4") );
		assertEquals( 16, (int) paths.get("5","t") );
		
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
