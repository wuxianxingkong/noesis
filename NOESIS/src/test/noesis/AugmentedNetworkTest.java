package test.noesis;

import static org.junit.Assert.*;

import noesis.AttributeNetwork;
import noesis.AugmentedNetwork;

import org.junit.Before;
import org.junit.Test;

public class AugmentedNetworkTest {

	AugmentedNetwork augmented;
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
		
		augmented = new AugmentedNetwork(base);
	}

	@Test
	public void testBase() 
	{
		assertEquals(4, augmented.size());
		assertEquals(6, augmented.links());
		
		assertEquals(3, augmented.outDegree(0));
		assertEquals(2, augmented.outDegree(1));
		assertEquals(1, augmented.outDegree(2));
		assertEquals(0, augmented.outDegree(3));

		assertEquals(0, augmented.inDegree(0));
		assertEquals(1, augmented.inDegree(1));
		assertEquals(2, augmented.inDegree(2));
		assertEquals(3, augmented.inDegree(3));
	}
	
	@Test
	public void testNewNode()
	{
		augmented.add(4);
		
		assertEquals(5, augmented.size());
		assertEquals(6, augmented.links());
		
		assertEquals(3, augmented.outDegree(0));
		assertEquals(2, augmented.outDegree(1));
		assertEquals(1, augmented.outDegree(2));
		assertEquals(0, augmented.outDegree(3));
		assertEquals(0, augmented.outDegree(4));

		assertEquals(0, augmented.inDegree(0));
		assertEquals(1, augmented.inDegree(1));
		assertEquals(2, augmented.inDegree(2));
		assertEquals(3, augmented.inDegree(3));
		assertEquals(0, augmented.inDegree(4));
	}

	
	@Test
	public void testNewLink()
	{
		augmented.add(3,0);
		
		assertEquals(4, augmented.size());
		assertEquals(7, augmented.links());
		
		assertEquals(3, augmented.outDegree(0));
		assertEquals(2, augmented.outDegree(1));
		assertEquals(1, augmented.outDegree(2));
		assertEquals(1, augmented.outDegree(3));

		assertEquals(1, augmented.inDegree(0));
		assertEquals(1, augmented.inDegree(1));
		assertEquals(2, augmented.inDegree(2));
		assertEquals(3, augmented.inDegree(3));
		
		assertEquals(0, augmented.outLink(3,0));
		
		assertEquals(3, augmented.inLink(0,0));		
	}	
	
	@Test
	public void testNewLinkToNewNode()
	{
		augmented.add(4);
		
		augmented.add(0,4);
		
		assertEquals(5, augmented.size());
		assertEquals(7, augmented.links());
		
		assertEquals(4, augmented.outDegree(0));
		assertEquals(2, augmented.outDegree(1));
		assertEquals(1, augmented.outDegree(2));
		assertEquals(0, augmented.outDegree(3));
		assertEquals(0, augmented.outDegree(4));

		assertEquals(0, augmented.inDegree(0));
		assertEquals(1, augmented.inDegree(1));
		assertEquals(2, augmented.inDegree(2));
		assertEquals(3, augmented.inDegree(3));	
		assertEquals(1, augmented.inDegree(4));
		
		assertEquals(1, augmented.outLink(0,0));
		assertEquals(2, augmented.outLink(0,1));
		assertEquals(3, augmented.outLink(0,2));
		assertEquals(4, augmented.outLink(0,3));

		assertEquals(0, augmented.inLink(4,0));
		
		augmented.add(3,4);
		
		assertEquals(5, augmented.size());
		assertEquals(8, augmented.links());
		
		assertEquals(4, augmented.outDegree(0));
		assertEquals(2, augmented.outDegree(1));
		assertEquals(1, augmented.outDegree(2));
		assertEquals(1, augmented.outDegree(3));
		assertEquals(0, augmented.outDegree(4));

		assertEquals(0, augmented.inDegree(0));
		assertEquals(1, augmented.inDegree(1));
		assertEquals(2, augmented.inDegree(2));
		assertEquals(3, augmented.inDegree(3));	
		assertEquals(2, augmented.inDegree(4));
		
		assertEquals(4, augmented.outLink(3,0));

		assertEquals(0, augmented.inLink(4,0));		
		assertEquals(3, augmented.inLink(4,1));		
	}
	
	@Test
	public void testNewLinkFromNewNode()
	{
		augmented.add(4);
		
		augmented.add(4,0);
		
		assertEquals(5, augmented.size());
		assertEquals(7, augmented.links());
		
		assertEquals(3, augmented.outDegree(0));
		assertEquals(2, augmented.outDegree(1));
		assertEquals(1, augmented.outDegree(2));
		assertEquals(0, augmented.outDegree(3));
		assertEquals(1, augmented.outDegree(4));

		assertEquals(1, augmented.inDegree(0));
		assertEquals(1, augmented.inDegree(1));
		assertEquals(2, augmented.inDegree(2));
		assertEquals(3, augmented.inDegree(3));	
		assertEquals(0, augmented.inDegree(4));
		
		assertEquals(0, augmented.outLink(4,0));

		assertEquals(4, augmented.inLink(0,0));

		
		augmented.add(4,3);
		
		assertEquals(5, augmented.size());
		assertEquals(8, augmented.links());
		
		assertEquals(3, augmented.outDegree(0));
		assertEquals(2, augmented.outDegree(1));
		assertEquals(1, augmented.outDegree(2));
		assertEquals(0, augmented.outDegree(3));
		assertEquals(2, augmented.outDegree(4));

		assertEquals(1, augmented.inDegree(0));
		assertEquals(1, augmented.inDegree(1));
		assertEquals(2, augmented.inDegree(2));
		assertEquals(4, augmented.inDegree(3));	
		assertEquals(0, augmented.inDegree(4));
		
		assertEquals(0, augmented.outLink(4,0));
		assertEquals(3, augmented.outLink(4,1));

		assertEquals(0, augmented.inLink(3,0));
		assertEquals(1, augmented.inLink(3,1));
		assertEquals(2, augmented.inLink(3,2));
		assertEquals(4, augmented.inLink(3,3));
	}		
	
}
