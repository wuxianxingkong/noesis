package test.ikor.math;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ikor.math.Vector3D;

/**
 * JUnit test for {@link ikor.math.Vector3D}.
 * @author Fernando Berzal
 */
public class Vector3DTest 
{
	public final static double EPSILON = 1e-6;
	
	Vector3D vector000;
	Vector3D vector001;
	Vector3D vector010;
	Vector3D vector100;
	Vector3D vector111;
	Vector3D vector200;
	Vector3D vector222;
	
	@Before
	public void setUp() throws Exception {
		vector000 = new Vector3D (0,0,0);
		vector001 = new Vector3D (0,0,1);
		vector010 = new Vector3D (0,1,0);
		vector100 = new Vector3D (1,0,0);
		vector111 = new Vector3D (1,1,1);
		vector200 = new Vector3D (2,0,0);
		vector222 = new Vector3D (2,2,2);
	}

	@Test
	public void testArithmetic()
	{	
		assertEquals( vector000, vector000.add(vector000));
		assertEquals( vector001, vector001.add(vector000));
		assertEquals( vector010, vector010.add(vector000));
		assertEquals( vector100, vector100.add(vector000));
		assertEquals( vector111, vector100.add(vector010).add(vector001));
		assertEquals( vector200, vector100.add(vector100));
		assertEquals( vector222, vector111.add(vector111));
		
		assertEquals( vector000, vector000.substract(vector000));
		assertEquals( vector001, vector001.substract(vector000));
		assertEquals( vector010, vector010.substract(vector000));
		assertEquals( vector100, vector100.substract(vector000));
		assertEquals( vector000, vector111.substract(vector001).substract(vector010).substract(vector100));
		assertEquals( vector100, vector200.substract(vector100));
		assertEquals( vector111, vector222.substract(vector111));
		
		assertEquals( vector000, vector000.multiply(0));
		assertEquals( vector000, vector000.multiply(2));
		assertEquals( vector222, vector111.multiply(2));

		assertEquals( vector000, vector000.divide(2));
		assertEquals( vector111, vector222.divide(2));
	}
	
	@Test
	public void testLength ()
	{
		assertEquals( 0, vector000.length(), EPSILON);
		assertEquals( 1, vector001.length(), EPSILON);
		assertEquals( 1, vector010.length(), EPSILON);
		assertEquals( 1, vector100.length(), EPSILON);
		assertEquals( Math.sqrt(1*1+1*1+1*1), vector111.length(), EPSILON);
		assertEquals( 2, vector200.length(), EPSILON);
		assertEquals( Math.sqrt(2*2+2*2+2*2), vector222.length(), EPSILON);
	}
	
	@Test
	public void testNormalize ()
	{
		assertEquals (vector001, vector001.normalize());
		assertEquals (vector010, vector010.normalize());
		assertEquals (vector100, vector100.normalize());
		assertEquals (vector111.divide(vector111.length()), vector111.normalize());
		assertEquals (1, vector111.normalize().length(), EPSILON);
		assertEquals (vector100, vector200.normalize());
		assertEquals (1, vector200.normalize().length(), EPSILON);
		assertEquals (vector222.divide(vector222.length()), vector222.normalize());
		assertEquals (1, vector222.normalize().length(), EPSILON);
	}
	
	@Test
	public void testReverse()
	{
		assertEquals (vector000, vector000.reverse());
		assertEquals (vector001, vector001.reverse().reverse());
		assertEquals (vector010, vector010.reverse().reverse());
		assertEquals (vector100, vector100.reverse().reverse());
		assertEquals (vector111, vector111.reverse().reverse());
		assertEquals (vector200, vector200.reverse().reverse());
		assertEquals (vector222, vector222.reverse().reverse());
	}
	
	@Test
	public void testDotProduct()
	{
		assertEquals (0, vector000.dotProduct(vector000), EPSILON);
		assertEquals (0, vector000.dotProduct(vector001), EPSILON);
		assertEquals (0, vector000.dotProduct(vector010), EPSILON);
		assertEquals (0, vector000.dotProduct(vector100), EPSILON);
		assertEquals (0, vector000.dotProduct(vector111), EPSILON);
		assertEquals (0, vector000.dotProduct(vector200), EPSILON);
		assertEquals (0, vector000.dotProduct(vector222), EPSILON);

		assertEquals (1, vector001.dotProduct(vector001), EPSILON);
		assertEquals (0, vector001.dotProduct(vector010), EPSILON);
		assertEquals (0, vector001.dotProduct(vector100), EPSILON);
		assertEquals (1, vector001.dotProduct(vector111), EPSILON);
		assertEquals (0, vector001.dotProduct(vector200), EPSILON);
		assertEquals (2, vector001.dotProduct(vector222), EPSILON);

		assertEquals (1, vector010.dotProduct(vector010), EPSILON);
		assertEquals (0, vector010.dotProduct(vector100), EPSILON);
		assertEquals (1, vector010.dotProduct(vector111), EPSILON);
		assertEquals (0, vector010.dotProduct(vector200), EPSILON);
		assertEquals (2, vector010.dotProduct(vector222), EPSILON);
		
		assertEquals (1, vector100.dotProduct(vector100), EPSILON);
		assertEquals (1, vector100.dotProduct(vector111), EPSILON);
		assertEquals (2, vector100.dotProduct(vector200), EPSILON);
		assertEquals (2, vector100.dotProduct(vector222), EPSILON);

		assertEquals (3, vector111.dotProduct(vector111), EPSILON);
		assertEquals (2, vector111.dotProduct(vector200), EPSILON);
		assertEquals (6, vector111.dotProduct(vector222), EPSILON);

		assertEquals (4, vector200.dotProduct(vector200), EPSILON);
		assertEquals (4, vector200.dotProduct(vector222), EPSILON);

		assertEquals (4*3, vector222.dotProduct(vector222), EPSILON);		
	}
	
	@Test
	public void testProjection()
	{
		assertEquals (0, vector000.projection(vector000), EPSILON);
		assertEquals (0, vector000.projection(vector001), EPSILON);
		assertEquals (0, vector000.projection(vector010), EPSILON);
		assertEquals (0, vector000.projection(vector100), EPSILON);
		assertEquals (0, vector000.projection(vector111), EPSILON);
		assertEquals (0, vector000.projection(vector200), EPSILON);
		assertEquals (0, vector000.projection(vector222), EPSILON);

		assertEquals (1, vector001.projection(vector001), EPSILON);
		assertEquals (0, vector001.projection(vector010), EPSILON);
		assertEquals (0, vector001.projection(vector100), EPSILON);
		assertEquals (1/vector111.length(), vector001.projection(vector111), EPSILON);
		assertEquals (0, vector001.projection(vector200), EPSILON);
		assertEquals (2/vector222.length(), vector001.projection(vector222), EPSILON);

		assertEquals (1, vector010.projection(vector010), EPSILON);
		assertEquals (0, vector010.projection(vector100), EPSILON);
		assertEquals (1/vector111.length(), vector010.projection(vector111), EPSILON);
		assertEquals (0, vector010.projection(vector200), EPSILON);
		assertEquals (2/vector222.length(), vector010.projection(vector222), EPSILON);
		
		assertEquals (1, vector100.projection(vector100), EPSILON);
		assertEquals (1/vector111.length(), vector100.projection(vector111), EPSILON);
		assertEquals (1, vector100.projection(vector200), EPSILON);
		assertEquals (2/vector222.length(), vector100.projection(vector222), EPSILON);

		assertEquals (vector111.length(), vector111.projection(vector111), EPSILON);
		assertEquals (1, vector111.projection(vector200), EPSILON);
		assertEquals (vector111.length(), vector111.projection(vector222), EPSILON);

		assertEquals (vector200.length(), vector200.projection(vector200), EPSILON);
		assertEquals (4/vector222.length(), vector200.projection(vector222), EPSILON);

		assertEquals (vector222.length(), vector222.projection(vector222), EPSILON);		
	}
	
	@Test
	public void testAngle()
	{
		assertEquals (0, vector000.angle(vector000), EPSILON);
		assertEquals (0, vector000.angle(vector001), EPSILON);
		assertEquals (0, vector000.angle(vector010), EPSILON);
		assertEquals (0, vector000.angle(vector100), EPSILON);
		assertEquals (0, vector000.angle(vector111), EPSILON);
		assertEquals (0, vector000.angle(vector200), EPSILON);
		assertEquals (0, vector000.angle(vector222), EPSILON);

		assertEquals (0, vector001.angle(vector001), EPSILON);
		assertEquals (Math.PI/2, vector001.angle(vector010), EPSILON);
		assertEquals (Math.PI/2, vector001.angle(vector100), EPSILON);
		assertEquals (Math.acos(1/vector111.length()), vector001.angle(vector111), EPSILON);
		assertEquals (Math.PI/2, vector001.angle(vector200), EPSILON);
		assertEquals (Math.acos(2/vector222.length()), vector001.angle(vector222), EPSILON);

		assertEquals (0, vector010.angle(vector010), EPSILON);
		assertEquals (Math.PI/2, vector010.angle(vector100), EPSILON);
		assertEquals (Math.acos(1/vector111.length()), vector010.angle(vector111), EPSILON);
		assertEquals (Math.PI/2, vector010.angle(vector200), EPSILON);
		assertEquals (Math.acos(2/vector222.length()), vector010.angle(vector222), EPSILON);
		
		assertEquals (0, vector100.angle(vector100), EPSILON);
		assertEquals (Math.acos(1/vector111.length()), vector100.angle(vector111), EPSILON);
		assertEquals (0, vector100.angle(vector200), EPSILON);
		assertEquals (Math.acos(2/vector222.length()), vector100.angle(vector222), EPSILON);

		assertEquals (0, vector111.angle(vector111), EPSILON);
		assertEquals (Math.acos(1/vector111.length()), vector111.angle(vector200), EPSILON);
		assertEquals (0, vector111.angle(vector222), EPSILON);

		assertEquals (0, vector200.angle(vector200), EPSILON);
		assertEquals (Math.acos(2/vector222.length()), vector200.angle(vector222), EPSILON);

		assertEquals (0, vector222.angle(vector222), EPSILON);		
	}
	
	@Test
	public void testDistance()
	{
		assertEquals (0, vector000.distance(vector000), EPSILON);
		assertEquals (1, vector000.distance(vector001), EPSILON);
		assertEquals (1, vector000.distance(vector010), EPSILON);
		assertEquals (1, vector000.distance(vector100), EPSILON);
		assertEquals (vector111.length(), vector000.distance(vector111), EPSILON);
		assertEquals (2, vector000.distance(vector200), EPSILON);
		assertEquals (vector222.length(), vector000.distance(vector222), EPSILON);

		assertEquals (0, vector001.distance(vector001), EPSILON);
		assertEquals (Math.sqrt(2), vector001.distance(vector010), EPSILON);
		assertEquals (Math.sqrt(2), vector001.distance(vector100), EPSILON);
		assertEquals (Math.sqrt(2), vector001.distance(vector111), EPSILON);
		assertEquals (Math.sqrt(5), vector001.distance(vector200), EPSILON);
		assertEquals (3, vector001.distance(vector222), EPSILON);

		assertEquals (0, vector010.distance(vector010), EPSILON);
		assertEquals (Math.sqrt(2), vector010.distance(vector100), EPSILON);
		assertEquals (Math.sqrt(2), vector010.distance(vector111), EPSILON);
		assertEquals (Math.sqrt(5), vector010.distance(vector200), EPSILON);
		assertEquals (3, vector010.distance(vector222), EPSILON);
		
		assertEquals (0, vector100.distance(vector100), EPSILON);
		assertEquals (Math.sqrt(2), vector100.distance(vector111), EPSILON);
		assertEquals (1, vector100.distance(vector200), EPSILON);
		assertEquals (3, vector100.distance(vector222), EPSILON);

		assertEquals (0, vector111.distance(vector111), EPSILON);
		assertEquals (Math.sqrt(3), vector111.distance(vector200), EPSILON);
		assertEquals (vector111.length(), vector111.distance(vector222), EPSILON);

		assertEquals (0, vector200.distance(vector200), EPSILON);
		assertEquals (Math.sqrt(8), vector200.distance(vector222), EPSILON);

		assertEquals (0, vector222.distance(vector222), EPSILON);		
	}	
	
	@Test
	public void testReflect ()
	{
		assertEquals ( new Vector3D(0,0,0),  vector000.reflect(vector001));
		assertEquals ( new Vector3D(0,0,-1), vector001.reflect(vector001));
		assertEquals ( new Vector3D(0,1,0),  vector010.reflect(vector001));
		assertEquals ( new Vector3D(1,0,0),  vector100.reflect(vector001));
		assertEquals ( new Vector3D(1,1,-1), vector111.reflect(vector001));
		assertEquals ( new Vector3D(2,0,0),  vector200.reflect(vector001));
		assertEquals ( new Vector3D(2,2,-2), vector222.reflect(vector001));

		assertEquals ( new Vector3D(0,0,0),  vector000.reflect(vector010));
		assertEquals ( new Vector3D(0,0,1),  vector001.reflect(vector010));
		assertEquals ( new Vector3D(0,-1,0), vector010.reflect(vector010));
		assertEquals ( new Vector3D(1,0,0),  vector100.reflect(vector010));
		assertEquals ( new Vector3D(1,-1,1), vector111.reflect(vector010));
		assertEquals ( new Vector3D(2,0,0),  vector200.reflect(vector010));
		assertEquals ( new Vector3D(2,-2,2), vector222.reflect(vector010));

		assertEquals ( new Vector3D(0,0,0),  vector000.reflect(vector100));
		assertEquals ( new Vector3D(0,0,1),  vector001.reflect(vector100));
		assertEquals ( new Vector3D(0,1,0),  vector010.reflect(vector100));
		assertEquals ( new Vector3D(-1,0,0), vector100.reflect(vector100));
		assertEquals ( new Vector3D(-1,1,1), vector111.reflect(vector100));
		assertEquals ( new Vector3D(-2,0,0), vector200.reflect(vector100));
		assertEquals ( new Vector3D(-2,2,2), vector222.reflect(vector100));

		assertEquals ( new Vector3D(0,0,0),  vector000.reflect(vector111.normalize()));
		assertEquals ( new Vector3D(-2.0/3.0,-2.0/3.0,1.0/3.0), vector001.reflect(vector111.normalize()));
		assertEquals ( new Vector3D(-2.0/3.0,1.0/3.0,-2.0/3.0), vector010.reflect(vector111.normalize()));
		assertEquals ( new Vector3D(1.0/3.0,-2.0/3.0,-2.0/3.0), vector100.reflect(vector111.normalize()));
		assertEquals ( new Vector3D(-1,-1,-1), vector111.reflect(vector111.normalize()));
		assertEquals ( new Vector3D(2.0/3.0,-4.0/3.0,-4.0/3.0), vector200.reflect(vector111.normalize()));
		assertEquals ( new Vector3D(-2,-2,-2), vector222.reflect(vector111.normalize()));
		
	}
	
	@Test
	public void testCrossProduct ()
	{
		assertEquals ( vector100, vector010.crossProduct(vector001));
		assertEquals ( vector100.reverse(), vector001.crossProduct(vector010));

		assertEquals ( vector010, vector001.crossProduct(vector100));
		assertEquals ( vector010.reverse(), vector100.crossProduct(vector001));

		assertEquals ( vector001, vector100.crossProduct(vector010));
		assertEquals ( vector001.reverse(), vector010.crossProduct(vector100));
		
	}
	
}
