package test.noesis.algorithms.paths;

import ikor.collection.Evaluator;

import org.junit.Test;

import noesis.Network;
import noesis.algorithms.paths.*;

public class AllPairsDijkstraTest extends AllPairsShortestPathFinderTest
{
	// Path finder
	
	@Override
	public AllPairsShortestPathFinder pathFinder (Network net, Evaluator linkEvaluator)
	{		
		return new AllPairsDijkstra(net,linkEvaluator);
	}
	
	
	// Unit tests
	
	@Test
	public void testConnected() 
	{
		checkConnected();
	}	

	@Test
	public void testUnreachable() 
	{
		checkUnreachable();
	}	
	
	@Test
	public void testDisconnected() 
	{
		checkDisconnected();
	}		

}
