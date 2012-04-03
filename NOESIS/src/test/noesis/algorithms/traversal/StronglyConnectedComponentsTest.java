package test.noesis.algorithms.traversal;

import static org.junit.Assert.*;

import noesis.ArrayNetwork;
import noesis.Network;

import noesis.algorithms.traversal.StronglyConnectedComponents;

import org.junit.Test;

public class StronglyConnectedComponentsTest 
{
	
	private void checkConnectedness (Network net, StronglyConnectedComponents scc)
	{
		int[] sizes = scc.componentSizes();
		int[] index = scc.componentIndex();
		
		assertEquals( 1, scc.components());
		assertEquals( net.size(), sizes[0]);
		
		for (int i=0; i<net.size(); i++)
			assertEquals(1, index[i]);		
	}
	
	private void checkIsolatedness (Network net, StronglyConnectedComponents scc)
	{
		int[] sizes = scc.componentSizes();
		
		assertEquals( net.size(), scc.components());
		
		for (int i=0; i<net.size(); i++) {
			assertEquals(1, sizes[i]);
		}		
	}
	
	@Test
	public void testUndirectedLineNetwork ()
	{
		Network net = new ArrayNetwork<String,Object>();
		
		net.add("s");
		net.add("a");
		net.add("b");
		net.add("c");
		net.add("t");
		
		net.add("s","a"); net.add("a","s");
		net.add("a","b"); net.add("b","a");
		net.add("b","c"); net.add("c","b");
		net.add("c","t"); net.add("t","c");
		
		StronglyConnectedComponents scc = new StronglyConnectedComponents(net);
		
		scc.compute();
		
		checkConnectedness(net,scc);
	}

	@Test
	public void testDirectedLineNetwork ()
	{
		Network net = new ArrayNetwork<String,Object>();
		
		net.add("s");
		net.add("a");
		net.add("b");
		net.add("c");
		net.add("t");
		
		net.add("s","a");
		net.add("a","b");
		net.add("b","c");
		net.add("c","t");
		
		StronglyConnectedComponents scc = new StronglyConnectedComponents(net);
		
		scc.compute();
		
		checkIsolatedness(net,scc);
	}
	
	@Test
	public void testUndirectedRingNetwork ()
	{
		Network net = new ArrayNetwork<String,Object>();
		
		net.add("s");
		net.add("a");
		net.add("b");
		net.add("c");
		net.add("t");
		
		net.add("s","a"); net.add("a","s");
		net.add("a","b"); net.add("b","a");
		net.add("b","c"); net.add("c","b");
		net.add("c","t"); net.add("t","c");
		net.add("t","s"); net.add("s","t");
		
		StronglyConnectedComponents scc = new StronglyConnectedComponents(net);
		
		scc.compute();
		
		checkConnectedness(net,scc);
	}

	@Test
	public void testDirectedRingNetwork ()
	{
		Network net = new ArrayNetwork<String,Object>();
		
		net.add("s");
		net.add("a");
		net.add("b");
		net.add("c");
		net.add("t");
		
		net.add("s","a");
		net.add("a","b");
		net.add("b","c");
		net.add("c","t");
		net.add("t","s");
		
		StronglyConnectedComponents scc = new StronglyConnectedComponents(net);
		
		scc.compute();
		
		checkConnectedness(net,scc);
	}

	@Test
	public void testUndirectedBranchNetwork ()
	{
		Network net = new ArrayNetwork<String,Object>();
		
		net.add("s");
		net.add("a");
		net.add("b");
		net.add("c");
		net.add("t");
		
		net.add("s","a"); net.add("a","s");
		net.add("a","b"); net.add("b","a");
		net.add("b","t"); net.add("t","b");
		net.add("s","c"); net.add("c","s");
		net.add("c","t"); net.add("t","c");
		
		StronglyConnectedComponents scc = new StronglyConnectedComponents(net);
		
		scc.compute();
		
		checkConnectedness(net,scc);
	}

	@Test
	public void testDirectedBranchNetwork ()
	{
		Network net = new ArrayNetwork<String,Object>();
		
		net.add("s");
		net.add("a");
		net.add("b");
		net.add("c");
		net.add("t");
		
		net.add("s","a"); 
		net.add("a","b"); 
		net.add("b","t"); 
		net.add("s","c"); 
		net.add("c","t"); 
		
		StronglyConnectedComponents scc = new StronglyConnectedComponents(net);
		
		scc.compute();
		
		checkIsolatedness(net,scc);
	}
	
	
	@Test
	public void testDisconnectedNetwork ()
	{
		Network net = new ArrayNetwork<String,Object>();
		
		net.add("s");
		net.add("a");
		net.add("b");
		net.add("c");
		net.add("t");
		
		net.add("s","a"); net.add("a","s");
		net.add("a","b"); net.add("b","a");

		net.add("c","t"); net.add("t","c");
		
		StronglyConnectedComponents scc = new StronglyConnectedComponents(net);
		
		scc.compute();
		
		int[] sizes = scc.componentSizes();
		int[] index = scc.componentIndex();
		int[] expected = new int[]{ 2, 2, 2, 1, 1};
		
		assertEquals( 2, scc.components());
		assertEquals( 2, sizes[0]);
		assertEquals( 3, sizes[1]);
		
		for (int i=0; i<net.size(); i++)
			assertEquals(expected[i], index[i]);
	}	

	@Test
	public void testIsolateNetwork ()
	{
		Network net = new ArrayNetwork<String,Object>();
		
		net.add("s");
		net.add("a");
		net.add("b");
		net.add("c");
		net.add("t");

		StronglyConnectedComponents scc = new StronglyConnectedComponents(net);
		
		scc.compute();
		
		checkIsolatedness(net,scc);
	}	

	@Test
	public void testStanfordTriangleNetwork ()
	{
		Network net = new ArrayNetwork<String,Object>();
		
		for (int i=1; i<10; i++)
			net.add(""+i);

		net.add("1","7");
		net.add("7","4");
		net.add("4","1");
		
		net.add("7","9");

		net.add("9","6");
		net.add("6","3");
		net.add("3","9");

		net.add("6","8");

		net.add("8","5");
		net.add("5","2");
		net.add("2","8");
		
		StronglyConnectedComponents scc = new StronglyConnectedComponents(net);
		
		scc.compute();
		
		int[] sizes = scc.componentSizes();
		int[] index = scc.componentIndex();
		int[] expected = new int[]{ 3, 1, 2, 3, 1, 2, 3, 1, 2};
		
		assertEquals( 3, scc.components());
		assertEquals( 3, sizes[0]);
		assertEquals( 3, sizes[1]);
		assertEquals( 3, sizes[2]);
		
		for (int i=0; i<net.size(); i++)
			assertEquals(expected[i], index[i]);
	}	
	
}
