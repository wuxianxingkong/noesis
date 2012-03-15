package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.model.regular.RegularNetwork;
import noesis.model.regular.HypercubeNetwork;

import org.junit.Test;
import org.junit.Before;

public class HypercubeNetworkTest extends RegularNetworkTest
{
	private HypercubeNetwork network;
	
	@Override
	public RegularNetwork network()
	{
		return network;
	}
	
	private int dimension ()
	{
		return (int) (Math.log(SIZE)/Math.log(2));
	}
		
    @Before
	public void setUp() throws Exception 
	{    	
    	network = new HypercubeNetwork(dimension());
	}
	
	@Test
	public void testLinks() 
	{
		assertEquals( SIZE*dimension(), network.links());
	}
	
	@Test
	public void testTopology()
	{
		int bit;
		int size = network.size();
		int dimension = dimension();
		
		for (int i=0; i<size; i++) {
			assertEquals ( i, (int) network.get(i));
			
			for (int d=0; d<dimension; d++) {
				bit = 1<<d;
				assertEquals ( i^bit, (int) network.get(i,i^bit));
			}
		}
	}
	
	@Test
	public void testDegrees()
	{
		int D = dimension();
		
		for (int i=0; i<network.size(); i++) {
			assertEquals ( D, network.outDegree(i));
			assertEquals ( D, network.inDegree(i));
		}
	}
}
