package test.noesis.algorithms.paths;

import static org.junit.Assert.*;
import ikor.collection.Evaluator;

import org.junit.Test;

import test.noesis.algorithms.TestNetworks;

import noesis.Network;
import noesis.algorithms.paths.*;


public class BellmanFordTest extends SingleSourceShortestPathFinderTest
{
	private BellmanFordShortestPathFinder finder;
	
	// Path finder factory method
	
	@Override
	public SingleSourceShortestPathFinder pathFinder (Network net, int source, Evaluator linkEvaluator)
	{		
		finder = new BellmanFordShortestPathFinder(net,source,linkEvaluator);
		
		return finder;
	}
	
	
	// Unit tests
	
	@Test
	public void testConnected() 
	{
		checkConnected();
		assertFalse(finder.negativeCycleDetected());
	}	

	@Test
	public void testUnreachable() 
	{
		checkUnreachable();
		assertFalse(finder.negativeCycleDetected());
	}	
	
	@Test
	public void testDisconnected() 
	{
		checkDisconnected();
		assertFalse(finder.negativeCycleDetected());
	}
	
	@Test
	public void testNegativeWeights ()
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		Network<String,Integer> graph = TestNetworks.negativeWeightsGraph();
		
		finder = new BellmanFordShortestPathFinder(graph,0,linkEvaluator);
		
		finder.run();
		
		assertEquals(  0, finder.distance(graph.index("s")), EPSILON );
		assertEquals( -4, finder.distance(graph.index("a")), EPSILON );
		assertEquals( -1, finder.distance(graph.index("b")), EPSILON );
		assertEquals( -6, finder.distance(graph.index("t")), EPSILON );
		
		Network<String,Integer> paths = finder.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-1, paths.links());
		
		assertEquals( -4, (int) paths.get("s","a") );
		assertEquals( -1, (int) paths.get("s","b") );
		assertEquals( -2, (int) paths.get("a","t") );
		assertEquals( null,     paths.get("b","t") );

		testPath ( finder, new String[]{"s"} );
		testPath ( finder, new String[]{"s","a"} );
		testPath ( finder, new String[]{"s","b"} );
		testPath ( finder, new String[]{"s","a","t"} );
		
		assertFalse( finder.negativeCycleDetected() );
	}
	
	@Test
	public void testNegativeCycle ()
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		Network<String,Integer> graph = TestNetworks.negativeCycleGraph();
		
		finder = new BellmanFordShortestPathFinder(graph,0,linkEvaluator);
		
		finder.run();

		assertTrue( finder.negativeCycleDetected() );
	}
	

}
