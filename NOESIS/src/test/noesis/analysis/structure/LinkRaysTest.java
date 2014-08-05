package test.noesis.analysis.structure;

import static org.junit.Assert.*;
import ikor.parallel.Task;
import noesis.BasicNetwork;
import noesis.Network;
import noesis.analysis.LinkScore;
import noesis.analysis.structure.LinkRays;

import org.junit.Before;
import org.junit.Test;


public class LinkRaysTest 
{
	public final double EPSILON = 1e-8;
	
	Network net;

	@Before
	public void setUp() throws Exception 
	{
		net = new BasicNetwork();
		
		net.setSize(7);
		
		net.add(0,1);  // Fully-connected triangle
		net.add(0,2);
		net.add(1,0);
		net.add(1,2);
		net.add(2,0);
		net.add(2,1);
		
		net.add(2,3);  // Bridge
		net.add(3,4);
		
		net.add(4,5);  // Directed triangle
		net.add(4,6);
		net.add(5,6);
	}

	@Test
	public void test() 
		throws Exception
	{
		Task<LinkScore> task = new LinkRays(net);
		LinkScore rays = task.call();
		//LinkMeasure rays = task.getResult();
		//LinkMeasure rays = task.getFuture().get();
		
		assertEquals(4, rays.get(0,1), EPSILON);
		assertEquals(6, rays.get(0,2), EPSILON);
		assertEquals(4, rays.get(1,0), EPSILON);
		assertEquals(6, rays.get(1,2), EPSILON);
		assertEquals(4, rays.get(2,0), EPSILON);
		assertEquals(4, rays.get(2,1), EPSILON);

		assertEquals(2, rays.get(2,3), EPSILON);
		assertEquals(2, rays.get(3,4), EPSILON);

		assertEquals(1, rays.get(4,5), EPSILON);
		assertEquals(0, rays.get(4,6), EPSILON);
		assertEquals(0, rays.get(5,6), EPSILON);
		
		assertTrue( Double.isNaN(rays.get(6,5)) );
		assertTrue( Double.isNaN(rays.get(6,4)) );
		assertTrue( Double.isNaN(rays.get(5,4)) );
		assertTrue( Double.isNaN(rays.get(4,3)) );
		assertTrue( Double.isNaN(rays.get(3,2)) );

		assertTrue( Double.isNaN(rays.get(0,0)) );
		assertTrue( Double.isNaN(rays.get(0,3)) );
		assertTrue( Double.isNaN(rays.get(0,4)) );
		assertTrue( Double.isNaN(rays.get(0,5)) );
		assertTrue( Double.isNaN(rays.get(0,6)) );
		
	}

}
