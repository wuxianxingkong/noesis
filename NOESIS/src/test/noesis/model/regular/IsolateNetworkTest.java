package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.analysis.structure.*;

import noesis.model.regular.IsolateNetwork;

import org.junit.Test;
import org.junit.Before;

public class IsolateNetworkTest 
{

	public final int SIZE = 32;
	public final double EPSILON = 1e-9;
	
	private IsolateNetwork isolate;
	
    @Before
	public void setUp() throws Exception 
	{
    	isolate = new IsolateNetwork(SIZE);
	}
	
	@Test
	public void testSize() 
	{
		assertEquals( SIZE, isolate.size());
		assertEquals( 0,    isolate.links());
	}
	
	@Test
	public void testTopology()
	{
		int size = isolate.size();
		
		for (int i=0; i<size; i++) {
			assertEquals ( i, (int) isolate.get(i));
			
			for (int j=0; j<size; j++)
				assertNull(isolate.get(i,j));
		}
	}
	
	@Test
	public void testDegree()
	{
		OutDegree outDegrees = new OutDegree(isolate);
		InDegree  inDegrees = new InDegree(isolate);
		
		outDegrees.compute();
		inDegrees.compute();

		assertEquals(isolate.averageDegree(), outDegrees.average(), EPSILON);
		assertEquals(isolate.averageDegree(), inDegrees.average(), EPSILON);

		assertEquals(0, (int) outDegrees.min());
		assertEquals(0, (int) outDegrees.max());

		assertEquals(0, (int) inDegrees.min());
		assertEquals(0, (int) inDegrees.max());
	
		for (int i=0; i<isolate.size(); i++) {
			assertEquals ( 0, (int) outDegrees.get(i));
			assertEquals ( 0, (int) inDegrees.get(i));
		}
		
	}
	
	@Test
	public void testPathLength()
	{
		int        source;
		PathLength paths;
		
		
		for (source=0; source<isolate.size(); source++) {
			
			paths = new PathLength(isolate,source);
			paths.compute();
		
			for (int i=0; i<isolate.size(); i++) {
				assertEquals ( isolate.distance(source,i), (int) paths.get(i));
			}
		}
	}
	
	
	@Test
	public void testAveragePathLength()
	{
		AveragePathLength apl = new AveragePathLength(isolate);
		
		apl.compute();
		
		assertEquals( isolate.averagePathLength(), apl.averagePathLength(), EPSILON );
		assertEquals( isolate.diameter(), apl.diameter() );
			
		for (int i=0; i<isolate.size(); i++) {
			assertEquals ( isolate.averagePathLength(i), apl.get(i), EPSILON);
		}
	}
	
	@Test
	public void testRadius()
	{
		Radius radius = new Radius(isolate);
		
		radius.compute();
		
		assertEquals( isolate.diameter(), radius.diameter() );
			
		for (int i=0; i<isolate.size(); i++) {
			assertEquals ( isolate.radius(i), radius.get(i), EPSILON);
		}
	}
	
	@Test
	public void testCloseness()
	{
		Closeness closeness = new Closeness(isolate);
		
		closeness.compute();
			
		for (int i=0; i<isolate.size(); i++) {
			assertEquals ( 0.0, closeness.get(i), EPSILON);
		}
	}	
	
	@Test
	public void testClusteringCoefficient()
	{
		ClusteringCoefficient clustering = new ClusteringCoefficient(isolate);
		
		clustering.compute();
			
		for (int i=0; i<isolate.size(); i++) {
			assertEquals ( 0.0, clustering.get(i), EPSILON);
		}
	}		
}
