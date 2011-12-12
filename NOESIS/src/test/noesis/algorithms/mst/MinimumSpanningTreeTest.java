package test.noesis.algorithms.mst;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import test.noesis.algorithms.TestNetworks;

import ikor.collection.Evaluator;

import noesis.Network;
import noesis.algorithms.mst.*;

public class MinimumSpanningTreeTest
{
	Network<String,Integer> roadmap;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		roadmap = TestNetworks.roadmap();
	}
	
	// Undirected graph

	@Test
	public void testKruskal() 
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		KruskalMinimumSpanningTree kruskal = new KruskalMinimumSpanningTree(roadmap,linkEvaluator);
		
		kruskal.run();
		
		Network<String,Integer> mst = kruskal.MST();
		
		assertEquals( roadmap.size(), mst.size());
		assertEquals( roadmap.size()-1, mst.links());
		assertEquals( 1+1+2+20+55+70+94, kruskal.weight(), 0.001 );
	}

	@Test
	public void testPrim() 
	{
		Evaluator<Integer> linkEvaluator = new LinkEvaluator();
		PrimMinimumSpanningTree prim = new PrimMinimumSpanningTree(roadmap,linkEvaluator);
		
		prim.run();
		
		Network<String,Integer> mst = prim.MST(); 
		
		assertEquals( roadmap.size(), mst.size());
		assertEquals( roadmap.size()-1, mst.links());
		assertEquals( 1+1+2+20+55+70+94, prim.weight(), 0.001 );
	}	
	
	class LinkEvaluator implements Evaluator<Integer>
	{
		@Override
		public double evaluate(Integer object) 
		{
			return object;
		}
	}
	

}

