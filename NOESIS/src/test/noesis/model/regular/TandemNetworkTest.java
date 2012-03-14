package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.model.regular.RegularNetwork;
import noesis.model.regular.TandemNetwork;

import org.junit.Test;
import org.junit.Before;

public class TandemNetworkTest extends RegularNetworkTest
{
	private TandemNetwork network;
	
	@Override
	public RegularNetwork network()
	{
		return network;
	}
		
    @Before
	public void setUp() throws Exception 
	{
    	network = new TandemNetwork(SIZE);
	}
	
	@Test
	public void testLinks() 
	{
		assertEquals( 2*SIZE-2, network.links());
	}
	
	@Test
	public void testTopology()
	{
		int size = network.size();
		
		for (int i=0; i<size; i++) {
			assertEquals ( i, (int) network.get(i));
			
			if (i<size-1)
				assertEquals ( i+1, (int) network.get(i,i+1));
			
			if (i>0)
				assertEquals ( i-1, (int) network.get(i,i-1));
		}
	}
	
	@Test
	public void testDegrees()
	{
		assertEquals ( 1, network.outDegree(0));
		assertEquals ( 1, network.inDegree(0));
		assertEquals ( 1, network.outDegree(network.size()-1));
		assertEquals ( 1, network.inDegree(network.size()-1));
		
		for (int i=1; i<network.size()-1; i++) {
			assertEquals ( 2, network.outDegree(i));
			assertEquals ( 2, network.inDegree(i));
		}
		
	}
}
