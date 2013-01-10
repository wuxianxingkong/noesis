package test.noesis.algorithms.paths;

import ikor.collection.Evaluator;

import org.junit.Test;

import noesis.Network;
import noesis.algorithms.paths.*;

public class DijkstraTest extends SingleSourceShortestPathFinderTest
{
	// Path finder
	
	@Override
	public SingleSourceShortestPathFinder pathFinder (Network net, int source, Evaluator linkEvaluator)
	{		
		return new DijkstraShortestPathFinder(net,source,linkEvaluator);
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
