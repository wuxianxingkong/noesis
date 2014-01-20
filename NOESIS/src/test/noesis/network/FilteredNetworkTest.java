package test.noesis.network;

import static org.junit.Assert.*;

import noesis.AttributeNetwork;
import noesis.network.FilteredNetwork;
import noesis.network.filter.DefaultFilter;
import noesis.network.filter.LinkFilter;
import noesis.network.filter.MaskFilter;
import noesis.network.filter.NodeFilter;

import org.junit.Before;
import org.junit.Test;

public class FilteredNetworkTest 
{
	FilteredNetwork filtered;
	AttributeNetwork base;
	
	@Before
	public void setUp() throws Exception 
	{
		base = new AttributeNetwork();
		
		base.add(0);
		base.add(1);
		base.add(2);
		base.add(3);
		
		base.add(0,1);
		base.add(0,2);
		base.add(0,3);
		base.add(1,2);
		base.add(1,3);
		base.add(2,3);
	}

	@Test
	public void testBase() 
	{
		filtered = new FilteredNetwork(base, new DefaultFilter());
		
		assertEquals(4, filtered.size());
		assertEquals(6, filtered.links());
		
		// Nodes
		
		assertTrue (filtered.contains(0));
		assertTrue (filtered.contains(1));
		assertTrue (filtered.contains(2));
		assertTrue (filtered.contains(3));
		
		
		// Degrees
		
		assertEquals(3, filtered.outDegree(0));
		assertEquals(2, filtered.outDegree(1));
		assertEquals(1, filtered.outDegree(2));
		assertEquals(0, filtered.outDegree(3));

		assertEquals(0, filtered.inDegree(0));
		assertEquals(1, filtered.inDegree(1));
		assertEquals(2, filtered.inDegree(2));
		assertEquals(3, filtered.inDegree(3));
		
		// Links
		
		assertEquals(1, filtered.outLink(0,0));
		assertEquals(2, filtered.outLink(0,1));
		assertEquals(3, filtered.outLink(0,2));
		assertEquals(2, filtered.outLink(1,0));
		assertEquals(3, filtered.outLink(1,1));
		assertEquals(3, filtered.outLink(2,0));
		
		assertEquals(0, filtered.inLink(1,0));
		assertEquals(0, filtered.inLink(2,0));
		assertEquals(1, filtered.inLink(2,1));
		assertEquals(0, filtered.inLink(3,0));
		assertEquals(1, filtered.inLink(3,1));
		assertEquals(2, filtered.inLink(3,2));		
	}
	
	
	@Test
	public void testNodeFilter1() 
	{
		filtered = new FilteredNetwork(base, new NodeFilter(1));
		
		assertEquals(3, filtered.size());
		assertEquals(3, filtered.links());
		
		// Nodes
		
		assertTrue (filtered.contains(0));
		assertFalse(filtered.contains(1));
		assertTrue (filtered.contains(2));
		assertTrue (filtered.contains(3));
		
		assertEquals (0, filtered.index(0));
		assertEquals (-1, filtered.index(1));
		assertEquals (1, filtered.index(2));
		assertEquals (2, filtered.index(3));
		
		assertEquals (0, (int)filtered.get(0));
		assertEquals (2, (int)filtered.get(1));
		assertEquals (3, (int)filtered.get(2));
		
		// Degrees
		
		assertEquals(2, filtered.outDegree(0));
		assertEquals(1, filtered.outDegree(1));
		assertEquals(0, filtered.outDegree(3));

		assertEquals(0, filtered.inDegree(0));
		assertEquals(1, filtered.inDegree(1));
		assertEquals(2, filtered.inDegree(2));
		
		// Links
		
		assertEquals(1, filtered.outLink(0,0));
		assertEquals(2, filtered.outLink(0,1));
		assertEquals(2, filtered.outLink(1,0));
		
		assertEquals(0, filtered.inLink(1,0));
		assertEquals(0, filtered.inLink(2,0));
		assertEquals(1, filtered.inLink(2,1));
		
	}
	
	@Test
	public void testNodeFilter3()
	{
		filtered = new FilteredNetwork(base, new NodeFilter(3));
		
		assertEquals(3, filtered.size());
		assertEquals(3, filtered.links());
		
		// Nodes
		
		assertTrue (filtered.contains(0));
		assertTrue (filtered.contains(1));
		assertTrue (filtered.contains(2));
		assertFalse(filtered.contains(3));
		
		assertEquals (0, filtered.index(0));
		assertEquals (1, filtered.index(1));
		assertEquals (2, filtered.index(2));
		assertEquals (-1, filtered.index(3));
		
		assertEquals (0, (int)filtered.get(0));
		assertEquals (1, (int)filtered.get(1));
		assertEquals (2, (int)filtered.get(2));

		// Degrees
		
		assertEquals(2, filtered.outDegree(0));
		assertEquals(1, filtered.outDegree(1));
		assertEquals(0, filtered.outDegree(2));
		assertEquals(0, filtered.outDegree(3));
		assertEquals(0, filtered.outDegree(4));

		assertEquals(0, filtered.inDegree(0));
		assertEquals(1, filtered.inDegree(1));
		assertEquals(2, filtered.inDegree(2));
		assertEquals(0, filtered.inDegree(3));
		assertEquals(0, filtered.inDegree(4));
		
		// Links
		
		assertEquals(1, filtered.outLink(0,0));
		assertEquals(2, filtered.outLink(0,1));
		assertEquals(2, filtered.outLink(1,0));
		
		assertEquals(0, filtered.inLink(1,0));
		assertEquals(0, filtered.inLink(2,0));
		assertEquals(1, filtered.inLink(2,1));
	}
	
	@Test
	public void testLinkFilter()
	{
		filtered = new FilteredNetwork(base, new LinkFilter(1,2));
		
		assertEquals(4, filtered.size());
		assertEquals(5, filtered.links());
				
		// Nodes
		
		assertTrue (filtered.contains(0));
		assertTrue (filtered.contains(1));
		assertTrue (filtered.contains(2));
		assertTrue (filtered.contains(3));
		
		// Degrees
		
		assertEquals(3, filtered.outDegree(0));
		assertEquals(2-1, filtered.outDegree(1));
		assertEquals(1, filtered.outDegree(2));
		assertEquals(0, filtered.outDegree(3));

		assertEquals(0, filtered.inDegree(0));
		assertEquals(1, filtered.inDegree(1));
		assertEquals(2-1, filtered.inDegree(2));
		assertEquals(3, filtered.inDegree(3));		
		
		// Links
		
		assertEquals(1, filtered.outLink(0,0));
		assertEquals(2, filtered.outLink(0,1));
		assertEquals(3, filtered.outLink(0,2));
		assertEquals(3, filtered.outLink(1,0));
		assertEquals(3, filtered.outLink(2,0));
		
		assertEquals(0, filtered.inLink(1,0));
		assertEquals(0, filtered.inLink(2,0));
		assertEquals(0, filtered.inLink(3,0));
		assertEquals(1, filtered.inLink(3,1));
		assertEquals(2, filtered.inLink(3,2));
	}
	
	@Test
	public void testMaskFilter()
	{
		filtered = new FilteredNetwork(base, new MaskFilter( new boolean[]{true, true, true, false}));
		
		assertEquals(3, filtered.size());
		assertEquals(3, filtered.links());
		
		// Nodes
		
		assertTrue (filtered.contains(0));
		assertTrue (filtered.contains(1));
		assertTrue (filtered.contains(2));
		assertFalse(filtered.contains(3));
		
		// Degrees
		
		assertEquals(2, filtered.outDegree(0));
		assertEquals(1, filtered.outDegree(1));
		assertEquals(0, filtered.outDegree(2));
		assertEquals(0, filtered.outDegree(3));
		assertEquals(0, filtered.outDegree(4));

		assertEquals(0, filtered.inDegree(0));
		assertEquals(1, filtered.inDegree(1));
		assertEquals(2, filtered.inDegree(2));
		assertEquals(0, filtered.inDegree(3));
		assertEquals(0, filtered.inDegree(4));
		
		// Links
		
		assertEquals(1, filtered.outLink(0,0));
		assertEquals(2, filtered.outLink(0,1));
		assertEquals(2, filtered.outLink(1,0));
		
		assertEquals(0, filtered.inLink(1,0));
		assertEquals(0, filtered.inLink(2,0));
		assertEquals(1, filtered.inLink(2,1));
	}
	
	
	@Test
	public void testMaskFilter2()
	{
		filtered = new FilteredNetwork(base, new MaskFilter( new boolean[]{false, true, true, false}));
		
		assertEquals(2, filtered.size());
		assertEquals(1, filtered.links());
		
		// Nodes
		
		assertFalse (filtered.contains(0));
		assertTrue (filtered.contains(1));
		assertTrue (filtered.contains(2));
		assertFalse(filtered.contains(3));
	
		assertEquals (-1, filtered.index(0));
		assertEquals (0, filtered.index(1));
		assertEquals (1, filtered.index(2));
		assertEquals (-1, filtered.index(3));
		
		assertEquals (1, (int)filtered.get(0));
		assertEquals (2, (int)filtered.get(1));
		
		// Degrees
		
		assertEquals(1, filtered.outDegree(0));
		assertEquals(0, filtered.outDegree(1));

		assertEquals(0, filtered.inDegree(0));
		assertEquals(1, filtered.inDegree(1));
		
		// Link
		
		assertEquals(1, filtered.outLink(0,0));
		assertEquals(0, filtered.inLink(1,0));
	}	
}

