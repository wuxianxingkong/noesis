package test.noesis.algorithms.paths;

import org.junit.Test;
import static org.junit.Assert.*;

import test.noesis.SampleNetworks;

import noesis.Network;
import noesis.algorithms.paths.DepthFirstPathFinder;


public class DFSPathFinderTest extends SingleSourcePathFinderTest
{
	
	@Test
	public void testConnected() 
	{
		Network<String,Integer> graph = SampleNetworks.weightedDirectedGraph();
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
	public void testUnreachable() 
	{
		Network<String,Integer> graph = SampleNetworks.weightedUnreachableGraph();;
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
	public void testDisconnected() 
	{
		Network<String,Integer> graph = SampleNetworks.weightedDisconnectedGraph();;
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

}
