package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.analysis.structure.*;

import noesis.model.regular.RegularNetwork;
import noesis.model.regular.RingNetwork;

import org.junit.Test;
import org.junit.Before;

public class RingNetworkTest extends RegularNetworkTest
{
	private RingNetwork network;
	
	@Override
	public RegularNetwork network()
	{
		return network;
	}
		
    @Before
	public void setUp() throws Exception 
	{
    	network = new RingNetwork(SIZE);
	}
	
	@Test
	public void testLinks() 
	{
		assertEquals( 2*SIZE, network.links());
	}
	
	@Test
	public void testTopology()
	{
		int next;
		int previous;
		int size = network.size();
		
		for (int i=0; i<size; i++) {
			next = (i+1) % size;
			previous = (i+size-1) % size;
			assertEquals ( i, (int) network.get(i));
			assertEquals ( next, (int) network.get(i,next));
			assertEquals ( previous, (int) network.get(i,previous));
		}
	}
	
	@Test
	public void testDegrees()
	{
		for (int i=0; i<network.size(); i++) {
			assertEquals ( 2, network.outDegree(i));
			assertEquals ( 2, network.inDegree(i));
		}
	}

	@Test
	public void testOddAveragePathLength()
	{
		RingNetwork oddRing = new RingNetwork(SIZE-1);
		AveragePathLength apl = new AveragePathLength(oddRing);
		
		apl.compute();
		
		assertEquals( oddRing.averagePathLength(), apl.averagePathLength(), EPSILON );
		
		for (int i=0; i<oddRing.size(); i++) {
			assertEquals ( oddRing.averagePathLength(), apl.getResult().get(i), EPSILON);
		}
	}	
}
