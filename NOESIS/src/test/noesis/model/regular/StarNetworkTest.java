package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.model.regular.RegularNetwork;
import noesis.model.regular.StarNetwork;

import org.junit.Test;
import org.junit.Before;

public class StarNetworkTest extends RegularNetworkTest
{
	private StarNetwork network;

	@Override
	public RegularNetwork network()
	{
		return network;
	}
		
    @Before
	public void setUp() throws Exception 
	{
    	network = new StarNetwork(SIZE);
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
		
		for (int i=1; i<size; i++) {
			assertEquals ( i, (int) network.get(i));
			assertEquals ( i, (int) network.get(network.CENTER,i));
			assertEquals ( network.CENTER, (int) network.get(i,network.CENTER));
		}
	}
	
	@Test
	public void testDegrees()
	{
		assertEquals (SIZE-1, network.outDegree(network.CENTER));
		assertEquals (SIZE-1, network.inDegree(network.CENTER));
	
		for (int i=0; i<network.size(); i++) {
			if (i!=network.CENTER) {
				assertEquals ( 1, network.outDegree(i));
				assertEquals ( 1, network.inDegree(i));
			}
		}
		
	}
}
