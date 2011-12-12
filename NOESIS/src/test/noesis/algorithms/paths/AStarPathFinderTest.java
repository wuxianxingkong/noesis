package test.noesis.algorithms.paths;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import ikor.collection.Evaluator;

import noesis.ArrayNetwork;
import noesis.Network;
import noesis.algorithms.paths.AStarPathFinder;


public class AStarPathFinderTest 
{
	private final double EPSILON = 0.000001;
		
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
	}

	// DepthFirstPathFinder
	
	@Test
	public void testTree() 
	{
		// Simple tree-like network (from ai-class.org midterm exam)
		
		Network<Integer,Integer> net = new ArrayNetwork<Integer,Integer>();

		net.add(15);  
		net.add(11);
		net.add(8);
		net.add(7);
		net.add(6);
		net.add(10);
		net.add(2);
		net.add(3);
		net.add(9);
		net.add(5);
		net.add(20);
		net.add(0);

		net.add( net.index(15), net.index(11) );
		net.add( net.index(15), net.index(8)  );
		net.add( net.index(15), net.index(7)  );
		net.add( net.index(15), net.index(6)  );
		net.add( net.index(15), net.index(10) );

		net.add( net.index(11), net.index(2) );
		net.add( net.index(8),  net.index(3) );
		net.add( net.index(8),  net.index(9) );
		net.add( net.index(6),  net.index(5) );
		net.add( net.index(6),  net.index(20));
		net.add( net.index(20), net.index(0) );
		net.add( net.index(10), net.index(0) );		
	
		// A* search
		
		AStarPathFinder pathFinder = new AStarPathFinder(net, net.index(15), new ConstantLinkEvaluator(10), new IntegerEvaluator() );
		
		pathFinder.run();  // A* expansion order: 15, 6, 7, 8, 10, 0, 11, 2, 3, 5, 9, 20
		
		// Costs
		
		double cost[] = pathFinder.cost();
		
		assertEquals ( 0, cost[net.index(15)], EPSILON );

		assertEquals ( 10, cost[net.index(11)], EPSILON );
		assertEquals ( 10, cost[net.index(8)], EPSILON );
		assertEquals ( 10, cost[net.index(7)], EPSILON );
		assertEquals ( 10, cost[net.index(6)], EPSILON );
		assertEquals ( 10, cost[net.index(10)], EPSILON );
		
		assertEquals ( 20, cost[net.index(2)], EPSILON );
		assertEquals ( 20, cost[net.index(3)], EPSILON );
		assertEquals ( 20, cost[net.index(9)], EPSILON );
		assertEquals ( 20, cost[net.index(5)], EPSILON );
		assertEquals ( 20, cost[net.index(20)], EPSILON );
		assertEquals ( 20, cost[net.index(0)], EPSILON );	
		
		// Paths

		assertEquals ( -1, pathFinder.predecessor(net.index(15)) );
		
		assertEquals ( net.index(15), pathFinder.predecessor(net.index(11)) );
		assertEquals ( net.index(15), pathFinder.predecessor(net.index(8)) );
		assertEquals ( net.index(15), pathFinder.predecessor(net.index(7)) );
		assertEquals ( net.index(15), pathFinder.predecessor(net.index(6)) );
		assertEquals ( net.index(15), pathFinder.predecessor(net.index(10)) );
		
		assertEquals ( net.index(11), pathFinder.predecessor(net.index(2)) );
		assertEquals ( net.index(8),  pathFinder.predecessor(net.index(3)) );
		assertEquals ( net.index(8),  pathFinder.predecessor(net.index(9)) );
		assertEquals ( net.index(6),  pathFinder.predecessor(net.index(5)) );
		assertEquals ( net.index(6),  pathFinder.predecessor(net.index(20)) );
		assertEquals ( net.index(10), pathFinder.predecessor(net.index(0)) );
	}
	
	// Ancillary classes

	class ConstantLinkEvaluator implements Evaluator
	{
		double constant;
		
		public ConstantLinkEvaluator (double constant)
		{
			this.constant = constant;
		}

		@Override
		public double evaluate(Object object) 
		{
			return constant;
		}
	}
	
	class IntegerEvaluator implements Evaluator<Integer>
	{
		@Override
		public double evaluate(Integer object)
		{
			return (int) object;
		}
	}
}