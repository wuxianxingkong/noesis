package test.noesis.algorithms.paths;

import static org.junit.Assert.*;
import ikor.collection.Evaluator;


import test.noesis.algorithms.TestNetworks;

import noesis.Network;
import noesis.algorithms.paths.*;

public abstract class SingleSourceShortestPathFinderTest extends SingleSourcePathFinderTest
{
	protected final double EPSILON = 0.000001;
	
	// Abstract factory method
	
	public abstract SingleSourceShortestPathFinder pathFinder (Network net, int source, Evaluator linkEvaluator);
	
	// Tests

	public void checkConnected () 
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		Network<String,Integer> graph = TestNetworks.weightedDirectedGraph();
		SingleSourceShortestPathFinder finder = pathFinder(graph,0,linkEvaluator);
			
		finder.run();
		
		assertEquals(  0, finder.distance(graph.index("s")), EPSILON );
		assertEquals(  9, finder.distance(graph.index("2")), EPSILON );
		assertEquals( 32, finder.distance(graph.index("3")), EPSILON );
		assertEquals( 45, finder.distance(graph.index("4")), EPSILON );
		assertEquals( 34, finder.distance(graph.index("5")), EPSILON );
		assertEquals( 14, finder.distance(graph.index("6")), EPSILON );
		assertEquals( 15, finder.distance(graph.index("7")), EPSILON );
		assertEquals( 50, finder.distance(graph.index("t")), EPSILON );
		
		Network<String,Integer> paths = finder.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-1, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals( 15, (int) paths.get("s","7") );
		assertEquals( 23, (int) paths.get("2","3") );
		assertEquals(  2, (int) paths.get("3","5") );
		assertEquals( 11, (int) paths.get("5","4") );
		assertEquals( 16, (int) paths.get("5","t") );

		testPath ( finder, new String[]{"s"} );
		testPath ( finder, new String[]{"s","2"} );
		testPath ( finder, new String[]{"s","2","3"} );
		testPath ( finder, new String[]{"s","2","3","5","4"} );
		testPath ( finder, new String[]{"s","2","3","5"} );
		testPath ( finder, new String[]{"s","6"} );
		testPath ( finder, new String[]{"s","7"} );
		testPath ( finder, new String[]{"s","2","3","5","t"} );	
	}	

	
	public void checkUnreachable() 
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		Network<String,Integer> graph = TestNetworks.weightedUnreachableGraph();;
		SingleSourceShortestPathFinder finder = pathFinder(graph,0,linkEvaluator);
		
		finder.run();
		
		assertEquals(  0, finder.distance(graph.index("s")), EPSILON );
		assertEquals(  9, finder.distance(graph.index("2")), EPSILON );
		assertEquals( 32, finder.distance(graph.index("3")), EPSILON );
		assertEquals( Double.POSITIVE_INFINITY, finder.distance(graph.index("4")), EPSILON );
		assertEquals( Double.POSITIVE_INFINITY, finder.distance(graph.index("5")), EPSILON );
		assertEquals( 14, finder.distance(graph.index("6")), EPSILON );
		assertEquals( 15, finder.distance(graph.index("7")), EPSILON );
		assertEquals( 51, finder.distance(graph.index("t")), EPSILON );
		
		Network<String,Integer> paths = finder.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-3, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals( 15, (int) paths.get("s","7") );
		assertEquals( 23, (int) paths.get("2","3") );
		assertEquals( 19, (int) paths.get("3","t") );

		testPath ( finder, new String[]{"s"} );
		testPath ( finder, new String[]{"s","2"} );
		testPath ( finder, new String[]{"s","2","3"} );
		testPath ( finder, new String[]{"s","6"} );
		testPath ( finder, new String[]{"s","7"} );
		testPath ( finder, new String[]{"s","2","3","t"} );
		
		assertNull( finder.pathTo(graph.index("4")));
		assertNull( finder.pathTo(graph.index("5")));
	}	
	
	
	public void checkDisconnected() 
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		Network<String,Integer> graph = TestNetworks.weightedDisconnectedGraph();;
		SingleSourceShortestPathFinder finder = pathFinder(graph,0,linkEvaluator);
		
		finder.run();
		
		assertEquals(  0, finder.distance(graph.index("s")), EPSILON );
		assertEquals(  9, finder.distance(graph.index("2")), EPSILON );
		assertEquals( 14, finder.distance(graph.index("6")), EPSILON );
		assertEquals( 15, finder.distance(graph.index("7")), EPSILON );

		assertEquals( Double.POSITIVE_INFINITY, finder.distance(graph.index("3")), EPSILON );
		assertEquals( Double.POSITIVE_INFINITY, finder.distance(graph.index("4")), EPSILON );
		assertEquals( Double.POSITIVE_INFINITY, finder.distance(graph.index("5")), EPSILON );
		assertEquals( Double.POSITIVE_INFINITY, finder.distance(graph.index("t")), EPSILON );
		
		Network<String,Integer> paths = finder.paths();
		
		assertEquals( graph.size(), paths.size());
		assertEquals( graph.size()-5, paths.links());
		
		assertEquals(  9, (int) paths.get("s","2") );
		assertEquals( 14, (int) paths.get("s","6") );
		assertEquals( 15, (int) paths.get("s","7") );

		testPath ( finder, new String[]{"s"} );
		testPath ( finder, new String[]{"s","2"} );
		testPath ( finder, new String[]{"s","6"} );
		testPath ( finder, new String[]{"s","7"} );
		
		assertNull( finder.pathTo(graph.index("3")));
		assertNull( finder.pathTo(graph.index("4")));
		assertNull( finder.pathTo(graph.index("5")));
		assertNull( finder.pathTo(graph.index("t")));
	}		

	
	// Ancillary class
	
	protected class LinkEvaluator implements Evaluator<Integer>
	{
		@Override
		public double evaluate(Integer object) 
		{
			return object;
		}
	}	

}
