package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.model.regular.CompleteNetwork;
import noesis.model.regular.RegularNetwork;

import org.junit.Test;
import org.junit.Before;

public class CompleteNetworkTest extends RegularNetworkTest
{
	private CompleteNetwork network;
	
	@Override
	public RegularNetwork network()
	{
		return network;
	}
	
    @Before
	public void setUp() throws Exception 
	{
    	network = new CompleteNetwork(SIZE);
	}
	
	@Test
	public void testLinks() 
	{
		assertEquals( SIZE*(SIZE-1), network.links());
	}
	
	@Test
	public void testTopology()
	{
		int size = network.size();
		
		for (int i=0; i<size; i++) {
			assertEquals ( i, (int) network.get(i));
			
			for (int j=0; j<size; j++)
				if (j!=i)
					assertEquals ( j, (int) network.get(i,j));
		}
	}
	
	@Test
	public void testDegrees()
	{
		for (int i=0; i<network.size(); i++) {
			assertEquals ( SIZE-1, network.outDegree(i));
			assertEquals ( SIZE-1, network.inDegree(i));
		}	
	}
}
