package test.noesis.algorithms.paths;

import static org.junit.Assert.*;

import noesis.Network;
import noesis.algorithms.paths.SingleSourcePathFinder;


public abstract class SingleSourcePathFinderTest 
{
	
	// Ancillary routine
	
	protected void testPath ( SingleSourcePathFinder dijkstra, String[] nodes)
	{
		Network<String,Integer> graph = dijkstra.network();
		int   length = nodes.length;
		int[] path = dijkstra.pathTo(graph.index(nodes[length-1]));
		
		assertEquals (length, path.length);
		
		for (int i=0; i<nodes.length; i++)
			assertEquals( graph.index(nodes[i]), path[i] );
	}	
}
