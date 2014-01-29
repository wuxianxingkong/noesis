package test.noesis.model.regular;

import static org.junit.Assert.*;

import noesis.analysis.structure.*;

import noesis.model.regular.RegularNetwork;

import org.junit.Test;

public abstract class RegularNetworkBasicTest 
{
    // Perfect square + Power of 2: 4, 16, 64...
	public final int    SIZE = 16;
	
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

		assertEquals(network.minDegree(), (int) outDegrees.getResult().min());
		assertEquals(network.maxDegree(), (int) outDegrees.getResult().max());

		assertEquals(network.minDegree(), (int) inDegrees.getResult().min());
		assertEquals(network.maxDegree(), (int) inDegrees.getResult().max());
		
		assertEquals(network.averageDegree(), outDegrees.getResult().average(), EPSILON);
		assertEquals(network.averageDegree(), inDegrees.getResult().average(), EPSILON);
	}
	
	@Test
	public final void testPathLength()
	{
		int            source;
		PathLength     paths;
		RegularNetwork network = network();
		
		
		for (source=0; source<network.size(); source++) {
			
			paths = new PathLength(network,source);
		
			for (int i=0; i<network.size(); i++) {
				assertEquals ( network.distance(source,i), (int) paths.getResult().get(i));
			}
		}
	}
		
	@Test
	public final void testRadius()
	{
		RegularNetwork network = network();
		Eccentricity radius = new Eccentricity(network);
		
		assertEquals( network.diameter(), radius.diameter() );
			
		for (int i=0; i<network.size(); i++) {
			assertEquals ( network.radius(i), radius.getResult().get(i), EPSILON);
		}
	}
		
	@Test
	public final void testClusteringCoefficient()
	{
		RegularNetwork        network = network();		
		ClusteringCoefficient clustering = new ClusteringCoefficient(network);
			
		for (int i=0; i<network.size(); i++) {
			assertEquals ( network.clusteringCoefficient(i), clustering.getResult().get(i), EPSILON);
		}
	}		
}
