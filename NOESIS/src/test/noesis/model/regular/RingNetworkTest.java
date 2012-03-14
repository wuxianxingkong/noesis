package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.analysis.structure.InDegree;
import noesis.analysis.structure.OutDegree;
import noesis.model.regular.RingNetwork;

import org.junit.Test;
import org.junit.Before;

public class RingNetworkTest 
{

	public final int SIZE = 64;
	public final double EPSILON = 0;
	
	private RingNetwork ring;
	
    @Before
	public void setUp() throws Exception 
	{
    	ring = new RingNetwork(SIZE);
	}
	
	@Test
	public void testSize() 
	{
		assertEquals( SIZE,   ring.size());
		assertEquals( 2*SIZE, ring.links());
	}
	
	@Test
	public void testTopology()
	{
		int next;
		int previous;
		int size = ring.size();
		
		for (int i=0; i<size; i++) {
			next = (i+1) % size;
			previous = (i+size-1) % size;
			assertEquals ( i, (int) ring.get(i));
			assertEquals ( next, (int) ring.get(i,next));
			assertEquals ( previous, (int) ring.get(i,previous));
		}
	}
	
	@Test
	public void testDegree()
	{
		OutDegree outDegrees = new OutDegree(ring);
		InDegree  inDegrees = new InDegree(ring);
		
		outDegrees.compute();
		inDegrees.compute();
		
		assertEquals(2, (int) outDegrees.getVector().min());
		assertEquals(2, (int) outDegrees.getVector().max());
		assertEquals(2, (int) outDegrees.getVector().average());
		assertEquals(0.0, outDegrees.getVector().deviation(), EPSILON);

		assertEquals(2, (int) inDegrees.getVector().min());
		assertEquals(2, (int) inDegrees.getVector().max());
		assertEquals(2, (int) inDegrees.getVector().average());
		assertEquals(0.0, outDegrees.getVector().deviation(), EPSILON);

		for (int i=0; i<ring.size(); i++) {
			assertEquals ( 2, (int) outDegrees.get(i));
			assertEquals ( 2, (int) inDegrees.get(i));
		}
		
	}

}
