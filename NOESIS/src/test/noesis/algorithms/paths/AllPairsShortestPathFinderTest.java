package test.noesis.algorithms.paths;

import static org.junit.Assert.*;

import ikor.collection.Evaluator;

import test.noesis.algorithms.TestNetworks;

import noesis.Network;
import noesis.algorithms.paths.*;


public abstract class AllPairsShortestPathFinderTest
{
	protected final double EPSILON = 0.000001;
	protected Network<String,Integer> network;
	protected AllPairsShortestPathFinder finder;	
	
	// Abstract factory method
	
	public abstract AllPairsShortestPathFinder pathFinder (Network net, Evaluator linkEvaluator);

	
	// Check distance
	
	public void checkDistance (String source, String destination, double expected)
	{
		assertEquals( expected, finder.distance(network.index(source),network.index(destination)), EPSILON );
	}
	
	public void checkDistance (int source, int destination, double expected)
	{
		String message = "Expected distance from "+network.get(source)+" to "+network.get(destination)+" = "+expected;
		
		assertEquals( "ERROR: "+message, expected, finder.distance(source,destination), EPSILON);
	}
	
	// Common tests

	public void checkConnected () 
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		
		network = TestNetworks.weightedDirectedGraph();
		
		finder = pathFinder(network,linkEvaluator);
			
		finder.run();
		
		for (int i=0; i<network.size(); i++)
			for (int j=0; j<network.size(); j++)
				checkDistance(i,j, TestNetworks.DIRECTED_DISTANCE[i][j]);
	}	

	
	public void checkUnreachable() 
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		
		network = TestNetworks.weightedUnreachableGraph();
		
		finder = pathFinder(network,linkEvaluator);
		
		finder.run();
		
		for (int i=0; i<network.size(); i++)
			for (int j=0; j<network.size(); j++)
				checkDistance(i,j, TestNetworks.UNREACHABLE_DISTANCE[i][j]);
	}	
	
	
	public void checkDisconnected() 
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();

		network = TestNetworks.weightedDisconnectedGraph();;
				
		finder = pathFinder(network,linkEvaluator);
		
		finder.run();
		
		for (int i=0; i<network.size(); i++)
			for (int j=0; j<network.size(); j++)
				checkDistance(i,j, TestNetworks.DISCONNECTED_DISTANCE[i][j]);
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
