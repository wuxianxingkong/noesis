package test.ikor.math;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ikor.math.MatrixFactory;
import ikor.math.Vector;

public class VectorTest 
{
	private Vector die;
	
	private static double d[] = new double[] { 1, 2, 3, 4, 5, 6 };
	public  static final double EPSILON = 1e-6;

	@Before
	public void setUp() throws Exception 
	{
		die = MatrixFactory.createVector(d.length);
		
		for (int i=0; i<d.length; i++)
			die.set(i, d[i]);
	}
	
	@Test
	public void testVectorOperations ()
	{
		Vector v;
		
		v = die.add(die);

		for (int i=0; i<die.size(); i++)
			assertEquals ( 2*d[i], v.get(i), EPSILON);
			
		v = v.subtract(die);
		
		assertEquals(v, die);
		
		for (int i=0; i<die.size(); i++)
			assertEquals ( d[i], v.get(i), EPSILON);
		
		v = die.subtract(die);
		
		for (int i=0; i<die.size(); i++)
			assertEquals ( 0.0, v.get(i), EPSILON);		
	}
	
	@Test
	public void testScalarOperations ()
	{
		Vector v;
		
		v = die.add(2.0);

		for (int i=0; i<die.size(); i++)
			assertEquals ( 2.0+d[i], v.get(i), EPSILON);

		v = die.add(0.0);

		assertEquals(v, die);
			
		v = die.multiply(2.0);
		
		for (int i=0; i<die.size(); i++)
			assertEquals ( 2.0*d[i], v.get(i), EPSILON);
		
		v = die.multiply(1.0);
		
		assertEquals(v, die);
		
		v = die.multiply(0.0);
		
		for (int i=0; i<die.size(); i++)
			assertEquals ( 0.0, v.get(i), EPSILON);		
	}
	
	

	@Test
	public void testMagnitude() 
	{
		assertEquals(d.length, die.size());

		// 1*1+2*2+3*3+4*4+5*5+6*6 = 91
		
		assertEquals( 91, die.magnitude2(), EPSILON);
		assertEquals( Math.sqrt(91), die.magnitude(), EPSILON);
	}

	@Test
	public void testDotProduct() 
	{
		assertEquals( 91, die.dotProduct(die), EPSILON);
	}

	@Test
	public void testProjection() 
	{
		assertEquals( die.magnitude(), die.projection(die), EPSILON);
	}

	@Test
	public void testDistance() 
	{
		assertEquals( 0, die.distance(die), EPSILON);
		assertEquals( 0, die.distance2(die), EPSILON);		
	}

	@Test
	public void testAngle() 
	{
		assertEquals( 0, die.angle(die), EPSILON);
	}

	@Test
	public void testMin() 
	{
		assertEquals( 1, die.min(), EPSILON);
	}

	@Test
	public void testMax() 
	{
		assertEquals( 6, die.max(), EPSILON);
	}

	@Test
	public void testSum() 
	{
		// 1+2+3+4+5+6 = 21
		assertEquals( 21, die.sum(), EPSILON);
	}

	@Test
	public void testSum2() 
	{
		assertEquals( 91, die.sum2(), EPSILON);
	}

	@Test
	public void testAverage() 
	{
		assertEquals( 3.5, die.average(), EPSILON);
	}

	@Test
	public void testVariance() 
	{
		assertEquals( 17.5/6, die.variance(), EPSILON);
	}

	@Test
	public void testDeviation() 
	{
		assertEquals( Math.sqrt(17.5/6), die.deviation(), EPSILON);
	}
	
	@Test
	public void testAbsoluteDeviation() 
	{
		assertEquals( 1.5, die.absoluteDeviation(), EPSILON);
	}	

}
