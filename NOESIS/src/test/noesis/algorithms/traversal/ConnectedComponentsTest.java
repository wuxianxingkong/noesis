package test.noesis.algorithms.traversal;

import org.junit.Test;
import static org.junit.Assert.*;

import noesis.Network;
import noesis.ArrayNetwork;
import noesis.algorithms.traversal.ConnectedComponents;


public class ConnectedComponentsTest 
{
	
	private void checkConnectedness (Network net, ConnectedComponents cc)
	{
		int[] sizes = cc.componentSizes();
		int[] index = cc.componentIndex();
		
		assertEquals( 1, cc.components());
		assertEquals( net.size(), sizes[0]);
		
		for (int i=0; i<net.size(); i++)
			assertEquals(1, index[i]);		
	}
	
	@Test
	public void testConnectedLineNetwork ()
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
		
		ConnectedComponents cc = new ConnectedComponents(net);
		
		cc.compute();
		
		checkConnectedness(net,cc);
	}

	@Test
	public void testConnectedRingNetwork ()
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
		
		ConnectedComponents cc = new ConnectedComponents(net);
		
		cc.compute();
		
		checkConnectedness(net,cc);
	}
	
	@Test
	public void testConnectedBranchNetwork ()
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
		
		ConnectedComponents cc = new ConnectedComponents(net);
		
		cc.compute();
		
		checkConnectedness(net,cc);
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
		
		ConnectedComponents cc = new ConnectedComponents(net);
		
		cc.compute();
		
		int[] sizes = cc.componentSizes();
		int[] index = cc.componentIndex();
		int[] expected = new int[]{ 1, 1, 1, 2, 2};
		
		assertEquals( 2, cc.components());
		assertEquals( 3, sizes[0]);
		assertEquals( 2, sizes[1]);
		
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

		ConnectedComponents cc = new ConnectedComponents(net);
		
		cc.compute();
		
		int[] sizes = cc.componentSizes();
		int[] index = cc.componentIndex();
		int[] expected = new int[]{ 1, 2, 3, 4, 5};
		
		assertEquals( net.size(), cc.components());
		
		for (int i=0; i<net.size(); i++) {
			assertEquals(1, sizes[i]);
			assertEquals(expected[i], index[i]);
		}
	}	
	
}
