package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.model.regular.BinaryTreeNetwork;
import noesis.model.regular.RegularNetwork;

import org.junit.Test;
import org.junit.Before;

public class BinaryTreeNetworkTest extends RegularNetworkBasicTest
{
	private BinaryTreeNetwork network;
	
	@Override
	public RegularNetwork network()
	{
		return network;
	}
	
    @Before
	public void setUp() throws Exception 
	{
    	network = new BinaryTreeNetwork(SIZE);
	}
	
	@Test
	public void testLinks() 
	{
		assertEquals( 2*(SIZE-1), network.links());
	}
	
	@Test
	public void testTopology()
	{
		int size = network.size();
		
		assertEquals ( 0, (int) network.get(0));
		
		for (int i=1; i<size; i++) {
			assertEquals ( i, (int) network.get(i));
			assertEquals ( (i-1)/2, (int) network.get(i,network.parent(i)));
			assertEquals ( i, (int) network.get(network.parent(i),i));
		}
	}
	
	@Test
	public void testDegrees()
	{
		for (int i=0; i<network.size(); i++) {
			
			if (i==0) {
				assertEquals ( Math.min(2,SIZE-1), network.outDegree(i));
				assertEquals ( Math.min(2,SIZE-1), network.inDegree(i));
			} else if (2*(i+1)<SIZE) {
				assertEquals ( 3, network.outDegree(i));
				assertEquals ( 3, network.inDegree(i));
			} else if (2*(i+1)==SIZE) {
				assertEquals ( 2, network.outDegree(i));
				assertEquals ( 2, network.inDegree(i));
			} else {
				assertEquals ( 1, network.outDegree(i));
				assertEquals ( 1, network.inDegree(i));
			}
		}	
	}
}
