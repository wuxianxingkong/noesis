package test.noesis.analysis.structure;

import static org.junit.Assert.*;

import ikor.parallel.Task;

import noesis.BasicNetwork;
import noesis.Network;
import noesis.analysis.structure.LinkMeasure;
import noesis.analysis.structure.LinkEmbeddedness;
import noesis.analysis.structure.LinkNeighborhoodOverlap;
import noesis.analysis.structure.LinkNeighborhoodSize;

import org.junit.Before;
import org.junit.Test;


public class LinkEmbeddednessTest 
{
	public final double EPSILON = 1e-8;
	
	Network net;

	@Before
	public void setUp() throws Exception 
	{
		//     0
		//   / | \
		// 1 - 2 - 3
		// | /   / |
		// 4 - 5 - 6
		
		net = new BasicNetwork();
		
		net.setSize(7);
		
		net.add2(0,1);
		net.add2(0,2);
		net.add2(0,3);
		net.add2(1,2);
		net.add2(2,3);
		net.add2(1,4);
		net.add2(2,4);
		net.add2(3,5);
		net.add2(4,5);
		net.add2(3,6);
		net.add2(5,6);
	}

	@Test
	public void testEmbeddedness() 
		throws Exception
	{
		Task<LinkMeasure> task = new LinkEmbeddedness(net);
		LinkMeasure embeddedness = task.call();
		//LinkMeasure embeddedness = task.getResult();
		//LinkMeasure embeddedness = task.getFuture().get();
		
		assertEquals(1, embeddedness.get(0,1), EPSILON);
		assertEquals(2, embeddedness.get(0,2), EPSILON);
		assertEquals(1, embeddedness.get(0,3), EPSILON);
		assertEquals(2, embeddedness.get(1,2), EPSILON);
		assertEquals(1, embeddedness.get(2,3), EPSILON);
		assertEquals(1, embeddedness.get(1,4), EPSILON);
		assertEquals(1, embeddedness.get(2,4), EPSILON);
		assertEquals(1, embeddedness.get(3,5), EPSILON);
		assertEquals(0, embeddedness.get(4,5), EPSILON);
		assertEquals(1, embeddedness.get(3,6), EPSILON);
		assertEquals(1, embeddedness.get(5,6), EPSILON);
	}

	@Test
	public void testLinkNeighborhoodOverlap() 
		throws Exception
	{
		Task<LinkMeasure> task = new LinkNeighborhoodOverlap(net);
		LinkMeasure neighborhood = task.call();
		//LinkMeasure neighborhood = task.getResult();
		//LinkMeasure neighborhood = task.getFuture().get();
		
		assertEquals(1/3.0, neighborhood.get(0,1), EPSILON);
		assertEquals(2/3.0, neighborhood.get(0,2), EPSILON);
		assertEquals(1/4.0, neighborhood.get(0,3), EPSILON);
		assertEquals(2/3.0, neighborhood.get(1,2), EPSILON);
		assertEquals(1/5.0, neighborhood.get(2,3), EPSILON);
		assertEquals(1/3.0, neighborhood.get(1,4), EPSILON);
		assertEquals(1/4.0, neighborhood.get(2,4), EPSILON);
		assertEquals(1/4.0, neighborhood.get(3,5), EPSILON);
		assertEquals(0,     neighborhood.get(4,5), EPSILON);
		assertEquals(1/3.0, neighborhood.get(3,6), EPSILON);
		assertEquals(1/2.0, neighborhood.get(5,6), EPSILON);
	}

	@Test
	public void testLinkNeighborhoodSize() 
		throws Exception
	{
		Task<LinkMeasure> task = new LinkNeighborhoodSize(net);
		LinkMeasure neighborhood = task.call();
		//LinkMeasure neighborhood = task.getResult();
		//LinkMeasure neighborhood = task.getFuture().get();
		
		assertEquals(3, neighborhood.get(0,1), EPSILON);
		assertEquals(3, neighborhood.get(0,2), EPSILON);
		assertEquals(4, neighborhood.get(0,3), EPSILON);
		assertEquals(3, neighborhood.get(1,2), EPSILON);
		assertEquals(5, neighborhood.get(2,3), EPSILON);
		assertEquals(3, neighborhood.get(1,4), EPSILON);
		assertEquals(4, neighborhood.get(2,4), EPSILON);
		assertEquals(4, neighborhood.get(3,5), EPSILON);
		assertEquals(4, neighborhood.get(4,5), EPSILON);
		assertEquals(3, neighborhood.get(3,6), EPSILON);
		assertEquals(2, neighborhood.get(5,6), EPSILON);
	}
	
}
