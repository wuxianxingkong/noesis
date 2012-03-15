package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.analysis.structure.*;

import noesis.model.regular.RegularNetwork;

import org.junit.Test;

public abstract class RegularNetworkTest 
{
	public final int    SIZE = 32;
	public final double EPSILON = 1e-9;
	
	public abstract RegularNetwork network();
		
	@Test
	public final void testSize() 
	{
		assertEquals( SIZE, network().size());
	}
	
	@Test
	public abstract void testLinks();
	
	@Test
	public abstract void testTopology();
	
	@Test
	public abstract void testDegrees();

	@Test
	public final void testAverageDegree() 
	{
		RegularNetwork network = network();
		OutDegree outDegrees = new OutDegree(network);
		InDegree  inDegrees  = new InDegree(network);
		
		outDegrees.compute();
		inDegrees.compute();

		assertEquals(network.minDegree(), (int) outDegrees.min());
		assertEquals(network.maxDegree(), (int) outDegrees.max());

		assertEquals(network.minDegree(), (int) inDegrees.min());
		assertEquals(network.maxDegree(), (int) inDegrees.max());
		
		assertEquals(network.averageDegree(), outDegrees.average(), EPSILON);
		assertEquals(network.averageDegree(), inDegrees.average(), EPSILON);
	}
	
	@Test
	public final void testPathLength()
	{
		int            source;
		PathLength     paths;
		RegularNetwork network = network();
		
		
		for (source=0; source<network.size(); source++) {
			
			paths = new PathLength(network,source);
			paths.compute();
		
			for (int i=0; i<network.size(); i++) {
				assertEquals ( network.distance(source,i), (int) paths.get(i));
			}
		}
	}
	
	
	@Test
	public final void testAveragePathLength()
	{
		RegularNetwork    network = network();
		AveragePathLength apl = new AveragePathLength(network);
		
		apl.compute();
		
		assertEquals( network.averagePathLength(), apl.averagePathLength(), EPSILON );
		assertEquals( network.diameter(), apl.diameter() );
			
		for (int i=0; i<network.size(); i++) {
			assertEquals ( network.averagePathLength(i), apl.get(i), EPSILON);
		}
	}
	
	@Test
	public final void testRadius()
	{
		RegularNetwork network = network();
		Radius radius = new Radius(network);
		
		radius.compute();
		
		assertEquals( network.diameter(), radius.diameter() );
			
		for (int i=0; i<network.size(); i++) {
			assertEquals ( network.radius(i), radius.get(i), EPSILON);
		}
	}
	
	@Test
	public final void testCloseness()
	{
		RegularNetwork network   = network();	
		Closeness      closeness = new Closeness(network);
		
		closeness.compute();
			
		for (int i=0; i<network.size(); i++) {
			assertEquals ( network.closeness(i), closeness.get(i), EPSILON);
		}
	}	
	
	@Test
	public final void testClusteringCoefficient()
	{
		RegularNetwork        network = network();		
		ClusteringCoefficient clustering = new ClusteringCoefficient(network);
		
		clustering.compute();
			
		for (int i=0; i<network.size(); i++) {
			assertEquals ( network.clusteringCoefficient(i), clustering.get(i), EPSILON);
		}
	}		
	
	
	@Test
	public final void testBetweenness()
	{
		RegularNetwork  network = network();		
		Betweenness     betweenness = new Betweenness(network);
		
		betweenness.compute();
			
		for (int i=0; i<network.size(); i++) {
			assertEquals ( network.betweenness(i), betweenness.get(i), EPSILON);
		}
	}		
}
