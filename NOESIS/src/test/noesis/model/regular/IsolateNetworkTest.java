package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.model.regular.IsolateNetwork;
import noesis.model.regular.RegularNetwork;

import org.junit.Test;
import org.junit.Before;

public class IsolateNetworkTest extends RegularNetworkTest
{
	private IsolateNetwork network;
	
	@Override
	public RegularNetwork network()
	{
		return network;
	}
	
    @Before
	public void setUp() throws Exception 
	{
    	network = new IsolateNetwork(SIZE);
	}
	
	@Test
	public void testLinks() 
	{
		assertEquals( 0, network.links());
	}
	
	@Test
	public void testTopology()
	{
		int size = network.size();
		
		for (int i=0; i<size; i++) {
			assertEquals ( i, (int) network.get(i));
			
			for (int j=0; j<size; j++)
				assertNull(network.get(i,j));
		}
	}
	
	@Test
	public void testDegrees()
	{
		for (int i=0; i<network.size(); i++) {
			assertEquals ( 0, network.outDegree(i));
			assertEquals ( 0, network.inDegree(i));
		}
	}	
}
