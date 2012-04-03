package test.noesis.algorithms.traversal;

import org.junit.Test;
import static org.junit.Assert.*;

import noesis.Network;
import noesis.ArrayNetwork;
import noesis.algorithms.traversal.TopologicalSort;

public class TopologicalSortTest 
{
	@Test
	public void testLine ()
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
		
		TopologicalSort ts = new TopologicalSort(net);
		
		int result[] = ts.sort();
		
		assertEquals(0, result[net.index("s")]);
		assertEquals(1, result[net.index("a")]);
		assertEquals(2, result[net.index("b")]);
		assertEquals(3, result[net.index("c")]);
		assertEquals(4, result[net.index("t")]);	
	}

	@Test
	public void testCycle ()
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
		
		TopologicalSort ts = new TopologicalSort(net);
		
		int result[] = ts.sort();
		
		assertEquals(0, result[net.index("s")]);
		assertEquals(1, result[net.index("a")]);
		assertEquals(2, result[net.index("b")]);
		assertEquals(3, result[net.index("c")]);
		assertEquals(4, result[net.index("t")]);	
	}

	@Test
	public void testBranch ()
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
		net.add("c","a");
		net.add("c","t");
		
		TopologicalSort ts = new TopologicalSort(net);
		
		int result[] = ts.sort();
		
		assertEquals(0, result[net.index("s")]);
		assertEquals(1, result[net.index("c")]);
		assertEquals(2, result[net.index("a")]);
		assertEquals(3, result[net.index("b")]);
		assertEquals(4, result[net.index("t")]);	
	}

	@Test
	public void testWeaklyConnected ()
	{
		Network net = new ArrayNetwork<String,Object>();
		
		net.add("s");
		net.add("a");
		net.add("b");
		net.add("c");
		net.add("t");
		
		net.add("s","a");
		net.add("a","b");
		net.add("c","a");
		net.add("c","t");
		
		TopologicalSort ts = new TopologicalSort(net);
		
		int result[] = ts.sort();
		
		assertEquals(2, result[net.index("s")]);
		assertEquals(3, result[net.index("a")]);
		assertEquals(4, result[net.index("b")]);
		
		assertEquals(0, result[net.index("c")]);	
		assertEquals(1, result[net.index("t")]);	
	}

	@Test
	public void testDisconnected ()
	{
		Network net = new ArrayNetwork<String,Object>();
		
		net.add("s");
		net.add("a");
		net.add("b");
		net.add("c");
		net.add("t");
		
		net.add("s","a");
		net.add("a","b");
		
		net.add("c","t");
		
		TopologicalSort ts = new TopologicalSort(net);
		
		int result[] = ts.sort();
		
		assertEquals(2, result[net.index("s")]);
		assertEquals(3, result[net.index("a")]);
		assertEquals(4, result[net.index("b")]);
		
		assertEquals(0, result[net.index("c")]);	
		assertEquals(1, result[net.index("t")]);	
	}
	
}
