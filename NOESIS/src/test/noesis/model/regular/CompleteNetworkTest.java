package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.analysis.structure.*;

import noesis.model.regular.CompleteNetwork;

import org.junit.Test;
import org.junit.Before;

public class CompleteNetworkTest 
{

	public final int SIZE = 32;
	public final double EPSILON = 1e-9;
	
	private CompleteNetwork complete;
	
    @Before
	public void setUp() throws Exception 
	{
    	complete = new CompleteNetwork(SIZE);
	}
	
	@Test
	public void testSize() 
	{
		assertEquals( SIZE,          complete.size());
		assertEquals( SIZE*(SIZE-1), complete.links());
	}
	
	@Test
	public void testTopology()
	{
		int size = complete.size();
		
		for (int i=0; i<size; i++) {
			assertEquals ( i, (int) complete.get(i));
			
			for (int j=0; j<size; j++)
				if (j!=i)
					assertEquals ( j, (int) complete.get(i,j));
		}
	}
	
	@Test
	public void testDegree()
	{
		OutDegree outDegrees = new OutDegree(complete);
		InDegree  inDegrees = new InDegree(complete);
		
		outDegrees.compute();
		inDegrees.compute();

		assertEquals(complete.averageDegree(), outDegrees.average(), EPSILON);
		assertEquals(complete.averageDegree(), inDegrees.average(), EPSILON);

		assertEquals(SIZE-1, (int) outDegrees.min());
		assertEquals(SIZE-1, (int) outDegrees.max());

		assertEquals(SIZE-1, (int) inDegrees.min());
		assertEquals(SIZE-1, (int) inDegrees.max());
	
		for (int i=0; i<complete.size(); i++) {
			assertEquals ( SIZE-1, (int) outDegrees.get(i));
			assertEquals ( SIZE-1, (int) inDegrees.get(i));
		}
		
	}
	
	@Test
	public void testPathLength()
	{
		int        source;
		PathLength paths;
		
		
		for (source=0; source<complete.size(); source++) {
			
			paths = new PathLength(complete,source);
			paths.compute();
		
			for (int i=0; i<complete.size(); i++) {
				assertEquals ( complete.distance(source,i), (int) paths.get(i));
			}
		}
	}
	
	
	@Test
	public void testAveragePathLength()
	{
		AveragePathLength apl = new AveragePathLength(complete);
		
		apl.compute();
		
		assertEquals( complete.averagePathLength(), apl.averagePathLength(), EPSILON );
		assertEquals( complete.diameter(), apl.diameter() );
			
		for (int i=0; i<complete.size(); i++) {
			assertEquals ( complete.averagePathLength(i), apl.get(i), EPSILON);
		}
	}
	
	@Test
	public void testRadius()
	{
		Radius radius = new Radius(complete);
		
		radius.compute();
		
		assertEquals( complete.diameter(), radius.diameter() );
			
		for (int i=0; i<complete.size(); i++) {
			assertEquals ( complete.radius(i), radius.get(i), EPSILON);
		}
	}
	
	@Test
	public void testCloseness()
	{
		Closeness closeness = new Closeness(complete);
		
		closeness.compute();
			
		for (int i=0; i<complete.size(); i++) {
			assertEquals ( 1.0/complete.averagePathLength(i), closeness.get(i), EPSILON);
		}
	}	
	
	@Test
	public void testClusteringCoefficient()
	{
		ClusteringCoefficient clustering = new ClusteringCoefficient(complete);
		
		clustering.compute();
			
		for (int i=0; i<complete.size(); i++) {
			assertEquals ( 1.0, clustering.get(i), EPSILON);
		}
	}		
}
