package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.analysis.structure.*;

import noesis.model.regular.TandemNetwork;

import org.junit.Test;
import org.junit.Before;

public class TandemNetworkTest 
{

	public final int SIZE = 64;
	public final double EPSILON = 1e-9;
	
	private TandemNetwork tandem;
	
    @Before
	public void setUp() throws Exception 
	{
    	tandem = new TandemNetwork(SIZE);
	}
	
	@Test
	public void testSize() 
	{
		assertEquals( SIZE,     tandem.size());
		assertEquals( 2*SIZE-2, tandem.links());
	}
	
	@Test
	public void testTopology()
	{
		int size = tandem.size();
		
		for (int i=0; i<size; i++) {
			assertEquals ( i, (int) tandem.get(i));
			
			if (i<size-1)
				assertEquals ( i+1, (int) tandem.get(i,i+1));
			
			if (i>0)
				assertEquals ( i-1, (int) tandem.get(i,i-1));
		}
	}
	
	@Test
	public void testDegree()
	{
		OutDegree outDegrees = new OutDegree(tandem);
		InDegree  inDegrees = new InDegree(tandem);
		
		outDegrees.compute();
		inDegrees.compute();

		assertEquals(tandem.averageDegree(), outDegrees.average(), EPSILON);
		assertEquals(tandem.averageDegree(), inDegrees.average(), EPSILON);

		assertEquals(1, (int) outDegrees.min());
		assertEquals(2, (int) outDegrees.max());

		assertEquals(1, (int) inDegrees.min());
		assertEquals(2, (int) inDegrees.max());

		assertEquals ( 1, (int) outDegrees.get(0));
		assertEquals ( 1, (int) inDegrees.get(0));
		assertEquals ( 1, (int) outDegrees.get(tandem.size()-1));
		assertEquals ( 1, (int) inDegrees.get(tandem.size()-1));
		
		for (int i=1; i<tandem.size()-1; i++) {
			assertEquals ( 2, (int) outDegrees.get(i));
			assertEquals ( 2, (int) inDegrees.get(i));
		}
		
	}
	
	@Test
	public void testPathLength()
	{
		int        source;
		PathLength paths;
		
		
		for (source=0; source<tandem.size(); source++) {
			
			paths = new PathLength(tandem,source);
			paths.compute();
		
			for (int i=0; i<tandem.size(); i++) {
				assertEquals ( tandem.distance(source,i), (int) paths.get(i));
			}
		}
	}
	
	
	@Test
	public void testAveragePathLength()
	{
		AveragePathLength apl = new AveragePathLength(tandem);
		
		apl.compute();
		
		assertEquals( tandem.averagePathLength(), apl.averagePathLength(), EPSILON );
		assertEquals( tandem.diameter(), apl.diameter() );
			
		for (int i=0; i<tandem.size(); i++) {
			assertEquals ( tandem.averagePathLength(i), apl.get(i), EPSILON);
		}
	}
	
	@Test
	public void testRadius()
	{
		Radius radius = new Radius(tandem);
		
		radius.compute();
		
		assertEquals( tandem.diameter(), radius.diameter() );
			
		for (int i=0; i<tandem.size(); i++) {
			assertEquals ( tandem.radius(i), radius.get(i), EPSILON);
		}
	}
	
	@Test
	public void testCloseness()
	{
		Closeness closeness = new Closeness(tandem);
		
		closeness.compute();
			
		for (int i=0; i<tandem.size(); i++) {
			assertEquals ( 1.0/tandem.averagePathLength(i), closeness.get(i), EPSILON);
		}
	}	
}
