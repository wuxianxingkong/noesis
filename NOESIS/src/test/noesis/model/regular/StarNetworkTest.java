package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.analysis.structure.*;

import noesis.model.regular.StarNetwork;

import org.junit.Test;
import org.junit.Before;

public class StarNetworkTest 
{

	public final int SIZE = 64;
	public final double EPSILON = 1e-9;
	
	private StarNetwork star;
	
    @Before
	public void setUp() throws Exception 
	{
    	star = new StarNetwork(SIZE);
	}
	
	@Test
	public void testSize() 
	{
		assertEquals( SIZE, star.size());
		assertEquals( 2*(SIZE-1), star.links());
	}
	
	@Test
	public void testTopology()
	{
		int size = star.size();
		
		for (int i=1; i<size; i++) {
			assertEquals ( i, (int) star.get(i));
			assertEquals ( i, (int) star.get(star.CENTER,i));
			assertEquals ( star.CENTER, (int) star.get(i,star.CENTER));
		}
	}
	
	@Test
	public void testDegree()
	{
		OutDegree outDegrees = new OutDegree(star);
		InDegree  inDegrees = new InDegree(star);
		
		outDegrees.compute();
		inDegrees.compute();

		assertEquals(star.averageDegree(), outDegrees.average(), EPSILON);
		assertEquals(star.averageDegree(), inDegrees.average(), EPSILON);

		assertEquals(1, (int) outDegrees.min());
		assertEquals(SIZE-1, (int) outDegrees.max());

		assertEquals(1, (int) inDegrees.min());
		assertEquals(SIZE-1, (int) inDegrees.max());

		assertEquals (SIZE-1, (int) outDegrees.get(star.CENTER));
		assertEquals (SIZE-1, (int) inDegrees.get(star.CENTER));
	
		for (int i=1; i<star.size(); i++) {
			assertEquals ( 1, (int) outDegrees.get(i));
			assertEquals ( 1, (int) inDegrees.get(i));
		}
		
	}
	
	@Test
	public void testPathLength()
	{
		int        source;
		PathLength paths;
		
		
		for (source=0; source<star.size(); source++) {
			
			paths = new PathLength(star,source);
			paths.compute();
		
			for (int i=0; i<star.size(); i++) {
				assertEquals ( star.distance(source,i), (int) paths.get(i));
			}
		}
	}
	
	
	@Test
	public void testAveragePathLength()
	{
		AveragePathLength apl = new AveragePathLength(star);
		
		apl.compute();
		
		assertEquals( star.averagePathLength(), apl.averagePathLength(), EPSILON );
		assertEquals( star.diameter(), apl.diameter() );
			
		for (int i=0; i<star.size(); i++) {
			assertEquals ( star.averagePathLength(i), apl.get(i), EPSILON);
		}
	}
	
	@Test
	public void testRadius()
	{
		Radius radius = new Radius(star);
		
		radius.compute();
		
		assertEquals( star.diameter(), radius.diameter() );
			
		for (int i=0; i<star.size(); i++) {
			assertEquals ( star.radius(i), radius.get(i), EPSILON);
		}
	}
	
	@Test
	public void testCloseness()
	{
		Closeness closeness = new Closeness(star);
		
		closeness.compute();
			
		for (int i=0; i<star.size(); i++) {
			assertEquals ( 1.0/star.averagePathLength(i), closeness.get(i), EPSILON);
		}
	}	
	
	@Test
	public void testClusteringCoefficient()
	{
		ClusteringCoefficient clustering = new ClusteringCoefficient(star);
		
		clustering.compute();
			
		for (int i=0; i<star.size(); i++) {
			assertEquals ( 0.0, clustering.get(i), EPSILON);
		}
	}		
}
