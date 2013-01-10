package test.noesis.algorithms.paths;

import org.junit.Test;

import noesis.Network;
import noesis.LinkEvaluator;
import noesis.algorithms.paths.*;

public class DijkstraTest extends SingleSourceShortestPathFinderTest
{
	// Path finder
	
	@Override
	public SingleSourceShortestPathFinder pathFinder (Network net, int source, LinkEvaluator linkEvaluator)
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
