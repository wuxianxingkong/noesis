package test.noesis.network;

import static org.junit.Assert.*;

import noesis.BasicNetwork;
import noesis.network.LinkIndex;

import org.junit.Before;
import org.junit.Test;

public class LinkIndexTest 
{
	BasicNetwork base;
	
	@Before
	public void setUp() throws Exception 
	{
		base = new BasicNetwork();
		
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
	public void testIndex() 
	{
		LinkIndex index = new LinkIndex(base);
		
		assertEquals(base, index.network());
		assertEquals(4, index.nodes());
		assertEquals(6, index.links());
				
		// Links
		
		assertEquals(0, index.index(0,1));
		assertEquals(1, index.index(0,2));
		assertEquals(2, index.index(0,3));
		assertEquals(3, index.index(1,2));
		assertEquals(4, index.index(1,3));
		assertEquals(5, index.index(2,3));

		assertEquals(-1, index.index(0,0));
		assertEquals(-1, index.index(1,0));
		assertEquals(-1, index.index(1,1));
		assertEquals(-1, index.index(2,0));
		assertEquals(-1, index.index(2,1));
		assertEquals(-1, index.index(2,2));
	}

	@Test
	public void testSource() 
	{
		LinkIndex index = new LinkIndex(base);
		
		assertEquals(base, index.network());
		assertEquals(4, index.nodes());
		assertEquals(6, index.links());
				
		// Links
		
		assertEquals(0, index.source(0));
		assertEquals(0, index.source(1));
		assertEquals(0, index.source(2));
		assertEquals(1, index.source(3));
		assertEquals(1, index.source(4));
		assertEquals(2, index.source(5));

		assertEquals(-1, index.source(6));
	}

	@Test
	public void testDestination() 
	{
		LinkIndex index = new LinkIndex(base);
		
		assertEquals(base, index.network());
		assertEquals(4, index.nodes());
		assertEquals(6, index.links());
				
		// Links
		
		assertEquals(1, index.destination(0));
		assertEquals(2, index.destination(1));
		assertEquals(3, index.destination(2));
		assertEquals(2, index.destination(3));
		assertEquals(3, index.destination(4));
		assertEquals(3, index.destination(5));

		assertEquals(-1, index.destination(6));
		
	}
	
	
	
	public static final int LARGE_NETWORK_SIZE = 100;
	
	@Test
	public void testLargeNetwork ()
	{
		BasicNetwork large = new BasicNetwork();

		large.setSize(LARGE_NETWORK_SIZE);
		
		for (int i=0; i<LARGE_NETWORK_SIZE-1; i++)
			for (int j=i+1; j<LARGE_NETWORK_SIZE; j++)
				large.add(i,j);
		
		LinkIndex index = new LinkIndex(large);
		
		assertEquals(large, index.network());
		assertEquals(LARGE_NETWORK_SIZE, index.nodes());
		assertEquals(LARGE_NETWORK_SIZE*(LARGE_NETWORK_SIZE-1)/2, index.links());
				
		// Links
		
		int link = 0;
		
		for (int i=0; i<LARGE_NETWORK_SIZE-1; i++) {
			for (int j=i+1; j<LARGE_NETWORK_SIZE; j++) {
				assertEquals(link, index.index(i,j));
				assertEquals(i, index.source(link));
				assertEquals(j, index.destination(link));
				link++;
			}
		}
	}
}
