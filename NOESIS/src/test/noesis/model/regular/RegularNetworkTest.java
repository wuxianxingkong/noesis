package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.analysis.structure.*;

import noesis.model.regular.RegularNetwork;

import org.junit.Test;

public abstract class RegularNetworkTest extends RegularNetworkBasicTest
{
	public abstract RegularNetwork network();
		
	@Test
	public abstract void testLinks();
	
	@Test
	public abstract void testTopology();
	
	@Test
	public abstract void testDegrees();

	
	@Test
	public final void testAveragePathLength()
	{
		RegularNetwork    network = network();
		AveragePathLength apl = new AveragePathLength(network);
		
		apl.compute();
		
		assertEquals( network.averagePathLength(), apl.averagePathLength(), EPSILON );
		assertEquals( network.diameter(), apl.diameter() );
			
		for (int i=0; i<network.size(); i++) {
			assertEquals ( network.averagePathLength(i), apl.getResult().get(i), EPSILON);
		}
	}
	
	@Test
	public final void testCloseness()
	{
		RegularNetwork network   = network();	
		Closeness      closeness = new Closeness(network);
			
		for (int i=0; i<network.size(); i++) {
			assertEquals ( network.closeness(i), closeness.getResult().get(i), EPSILON);
		}
	}	
	
	@Test
	public final void testBetweenness()
	{
		RegularNetwork  network = network();		
		Betweenness     betweenness = new Betweenness(network);
			
		for (int i=0; i<network.size(); i++) {
			assertEquals ( network.betweenness(i), betweenness.getResult().get(i), EPSILON);
		}
	}		
}
