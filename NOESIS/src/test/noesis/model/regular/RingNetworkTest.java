package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.analysis.structure.*;

import noesis.model.regular.RingNetwork;

import org.junit.Test;
import org.junit.Before;

public class RingNetworkTest 
{

	public final int SIZE = 64;
	public final double EPSILON = 1e-9;
	
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

		assertEquals( ring.averageDegree(), outDegrees.average(), EPSILON );
		assertEquals( ring.averageDegree(), inDegrees.average(), EPSILON );
		
		assertEquals(2, (int) outDegrees.min());
		assertEquals(2, (int) outDegrees.max());
		assertEquals(0.0, outDegrees.deviation(), EPSILON);

		assertEquals(2, (int) inDegrees.min());
		assertEquals(2, (int) inDegrees.max());
		assertEquals(0.0, outDegrees.deviation(), EPSILON);

		for (int i=0; i<ring.size(); i++) {
			assertEquals ( 2, (int) outDegrees.get(i));
			assertEquals ( 2, (int) inDegrees.get(i));
		}
		
	}
	
	@Test
	public void testPathLength()
	{
		int        source;
		PathLength paths;
		
		
		for (source=0; source<ring.size(); source++) {
			
			paths = new PathLength(ring,source);
			paths.compute();
		
			for (int i=0; i<ring.size(); i++) {
				assertEquals ( ring.distance(source,i), (int) paths.get(i));
			}
		}
	}
	
	
	@Test
	public void testAveragePathLength()
	{
		AveragePathLength apl = new AveragePathLength(ring);
		
		apl.compute();
		
		assertEquals( ring.averagePathLength(), apl.averagePathLength(), EPSILON );
		assertEquals( ring.diameter(), apl.diameter() );
		
			
		for (int i=0; i<ring.size(); i++) {
			assertEquals ( ring.averagePathLength(), apl.get(i), EPSILON);
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
			assertEquals ( oddRing.averagePathLength(), apl.get(i), EPSILON);
		}
	}

	
	@Test
	public void testRadius()
	{
		Radius radius = new Radius(ring);
		
		radius.compute();
		
		assertEquals( ring.diameter(), radius.min(), EPSILON );
		assertEquals( ring.diameter(), radius.max(), EPSILON );
		assertEquals( ring.diameter(), radius.average(), EPSILON );
		
		assertEquals( ring.diameter(), radius.diameter() );
		
			
		for (int i=0; i<ring.size(); i++) {
			assertEquals ( ring.diameter(), (int) radius.get(i) );
		}
	}	
	
	@Test
	public void testCloseness()
	{
		Closeness closeness = new Closeness(ring);
		
		closeness.compute();
		
		for (int i=0; i<ring.size(); i++) {
			assertEquals ( 1.0/ring.averagePathLength(), closeness.get(i), EPSILON);
		}
	}
	
	@Test
	public void testClusteringCoefficient()
	{
		ClusteringCoefficient clustering = new ClusteringCoefficient(ring);
		
		clustering.compute();
		
		for (int i=0; i<ring.size(); i++) {
			assertEquals ( 0.0, clustering.get(i), EPSILON);
		}
	}	
	
}
