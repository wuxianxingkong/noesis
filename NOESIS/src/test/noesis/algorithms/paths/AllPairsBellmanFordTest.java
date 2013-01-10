package test.noesis.algorithms.paths;

import static org.junit.Assert.*;

import ikor.collection.Evaluator;

import org.junit.Test;

import test.noesis.algorithms.TestNetworks;

import noesis.Network;
import noesis.algorithms.paths.*;

public class AllPairsBellmanFordTest extends AllPairsShortestPathFinderTest
{
	// Path finder
	
	@Override
	public AllPairsShortestPathFinder pathFinder (Network net, Evaluator linkEvaluator)
	{		
		return new AllPairsBellmanFord(net,linkEvaluator);
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
		
		network = TestNetworks.negativeWeightsGraph();
		finder  = new AllPairsBellmanFord(network,linkEvaluator);
		
		finder.run();
		
		for (int i=0; i<network.size(); i++)
			for (int j=0; j<network.size(); j++)
				checkDistance(i,j, TestNetworks.NEGATIVE_DISTANCE[i][j]);

		assertFalse( finder.negativeCycleDetected() );
	}
	
	@Test
	public void testNegativeCycle ()
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		Network<String,Integer> cycle = TestNetworks.negativeCycleGraph();
		
		finder = new AllPairsBellmanFord(cycle,linkEvaluator);
		
		finder.run();

		assertTrue( finder.negativeCycleDetected() );
	}
	
}
