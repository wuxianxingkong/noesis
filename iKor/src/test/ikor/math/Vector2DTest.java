package test.ikor.math;

import static org.junit.Assert.*;

import org.junit.*;

import ikor.math.Vector2D;

/**
 * JUnit test for {@link ikor.math.Vector3D}.
 * @author Fernando Berzal
 */
public class Vector2DTest {

	public final static double EPSILON = 1e-12;
	
	Vector2D vector00;
	Vector2D vector01;
	Vector2D vector10;
	Vector2D vector11;
	Vector2D vector20;
	Vector2D vector21;
	Vector2D vector22;
	
	@Before
	public void setUp() throws Exception {
		vector00 = new Vector2D(0,0);
		vector01 = new Vector2D(0,1);
		vector10 = new Vector2D(1,0);
		vector11 = new Vector2D(1,1);
		vector20 = new Vector2D(2,0);
		vector21 = new Vector2D(2,1);
		vector22 = new Vector2D(2,2);
		
	}
	
	@Test
	public void testArithmetic()
	{	
		assertEquals( vector00, vector00.add(vector00));
		assertEquals( vector01, vector01.add(vector00));
		assertEquals( vector10, vector10.add(vector00));
		assertEquals( vector11, vector10.add(vector01));
		assertEquals( vector21, vector10.add(vector11));
		assertEquals( vector22, vector11.add(vector11));
		
		assertEquals( vector00, vector00.substract(vector00));
		assertEquals( vector10, vector10.substract(vector00));
		assertEquals( vector10, vector21.substract(vector11));
		assertEquals( vector11, vector21.substract(vector10));
		assertEquals( vector11, vector22.substract(vector11));
		assertEquals( vector21, vector21.substract(vector00));
		
		assertEquals( vector00, vector00.multiply(0));
		assertEquals( vector00, vector00.multiply(2));
		assertEquals( vector22, vector11.multiply(2));

		assertEquals( vector00, vector00.divide(2));
		assertEquals( vector11, vector22.divide(2));
	}
	
	@Test
	public void testLength ()
	{
		assertEquals( 0, vector00.length(), EPSILON);
		assertEquals( 1, vector01.length(), EPSILON);
		assertEquals( 1, vector10.length(), EPSILON);
		assertEquals( Math.sqrt(1*1+1*1), vector11.length(), EPSILON);
		assertEquals( Math.sqrt(2*2+2*2), vector22.length(), EPSILON);
		assertEquals( Math.sqrt(2*2+1*1), vector21.length(), EPSILON);
	}
	
	@Test
	public void testNormalize ()
	{
		assertEquals (vector01, vector01.normalize());
		assertEquals (vector10, vector10.normalize());
		assertEquals (vector11.divide(vector11.length()), vector11.normalize());
		assertEquals (1, vector11.normalize().length(), EPSILON);
		assertEquals (vector21.divide(vector21.length()), vector21.normalize());
		assertEquals (1, vector21.normalize().length(), EPSILON);
		assertEquals (vector22.divide(vector22.length()), vector22.normalize());
		assertEquals (1, vector22.normalize().length(), EPSILON);
	}
	
	@Test
	public void testReverse()
	{
		assertEquals (vector00, vector00.reverse());
		assertEquals (vector01, vector01.reverse().reverse());
		assertEquals (vector10, vector10.reverse().reverse());
		assertEquals (vector11, vector11.reverse().reverse());
		assertEquals (vector21, vector21.reverse().reverse());
		assertEquals (vector22, vector22.reverse().reverse());
		
		assertEquals (-2, vector21.reverse().x(), EPSILON);
		assertEquals (-1, vector21.reverse().y(), EPSILON);
	}
	
	@Test
	public void testDotProduct()
	{
		assertEquals (0, vector00.dotProduct(vector00), EPSILON);
		assertEquals (0, vector00.dotProduct(vector01), EPSILON);
		assertEquals (0, vector00.dotProduct(vector10), EPSILON);
		assertEquals (0, vector00.dotProduct(vector11), EPSILON);
		assertEquals (0, vector00.dotProduct(vector21), EPSILON);
		assertEquals (0, vector00.dotProduct(vector22), EPSILON);

		assertEquals (1, vector01.dotProduct(vector01), EPSILON);
		assertEquals (0, vector01.dotProduct(vector10), EPSILON);
		assertEquals (1, vector01.dotProduct(vector11), EPSILON);
		assertEquals (1, vector01.dotProduct(vector21), EPSILON);
		assertEquals (2, vector01.dotProduct(vector22), EPSILON);

		assertEquals (1, vector10.dotProduct(vector10), EPSILON);
		assertEquals (1, vector10.dotProduct(vector11), EPSILON);
		assertEquals (2, vector10.dotProduct(vector21), EPSILON);
		assertEquals (2, vector10.dotProduct(vector22), EPSILON);

		assertEquals (2, vector11.dotProduct(vector11), EPSILON);
		assertEquals (3, vector11.dotProduct(vector21), EPSILON);
		assertEquals (4, vector11.dotProduct(vector22), EPSILON);

		assertEquals (5, vector21.dotProduct(vector21), EPSILON);
		assertEquals (6, vector21.dotProduct(vector22), EPSILON);

		assertEquals (8, vector22.dotProduct(vector22), EPSILON);
	}
	
	@Test
	public void testDistance()
	{
		assertEquals (0, vector00.distance(vector00), EPSILON);
		assertEquals (1, vector00.distance(vector01), EPSILON);
		assertEquals (1, vector00.distance(vector10), EPSILON);
		assertEquals (Math.sqrt(2), vector00.distance(vector11), EPSILON);
		assertEquals (Math.sqrt(5), vector00.distance(vector21), EPSILON);
		assertEquals (Math.sqrt(8), vector00.distance(vector22), EPSILON);

		assertEquals (0, vector01.distance(vector01), EPSILON);
		assertEquals (Math.sqrt(2), vector01.distance(vector10), EPSILON);
		assertEquals (1, vector01.distance(vector11), EPSILON);
		assertEquals (2, vector01.distance(vector21), EPSILON);
		assertEquals (Math.sqrt(5), vector01.distance(vector22), EPSILON);

		assertEquals (0, vector10.distance(vector10), EPSILON);
		assertEquals (1, vector10.distance(vector11), EPSILON);
		assertEquals (Math.sqrt(2), vector10.distance(vector21), EPSILON);
		assertEquals (Math.sqrt(5), vector10.distance(vector22), EPSILON);

		assertEquals (0, vector11.distance(vector11), EPSILON);
		assertEquals (1, vector11.distance(vector21), EPSILON);
		assertEquals (Math.sqrt(2), vector11.distance(vector22), EPSILON);

		assertEquals (0, vector21.distance(vector21), EPSILON);
		assertEquals (1, vector21.distance(vector22), EPSILON);

		assertEquals (0, vector22.distance(vector22), EPSILON);
	}	
	
	@Test
	public void testReflect ()
	{
		assertEquals ( vector00, vector00.reflect(vector01));
		assertEquals ( vector01.reverse(), vector01.reflect(vector01));	
		assertEquals ( vector10, vector10.reflect(vector01));
		assertEquals ( vector20, vector20.reflect(vector01));
		assertEquals ( new Vector2D(1,-1), vector11.reflect(vector01));
		assertEquals ( new Vector2D(2,-1), vector21.reflect(vector01));
		assertEquals ( new Vector2D(2,-2), vector22.reflect(vector01));

		assertEquals ( vector00, vector00.reflect(vector10));
		assertEquals ( vector01, vector01.reflect(vector10));	
		assertEquals ( vector10.reverse(), vector10.reflect(vector10));
		assertEquals ( vector20.reverse(), vector20.reflect(vector10));
		assertEquals ( new Vector2D(-1,1), vector11.reflect(vector10));
		assertEquals ( new Vector2D(-2,1), vector21.reflect(vector10));
		assertEquals ( new Vector2D(-2,2), vector22.reflect(vector10));

		
		assertEquals ( vector00, vector00.reflect(vector11.normalize()));
		assertEquals ( new Vector2D(-1,-1), vector11.reflect(vector11.normalize()));	
		assertEquals ( new Vector2D(-1,0), vector01.reflect(vector11.normalize()));
		assertEquals ( new Vector2D(0,-1), vector10.reflect(vector11.normalize()));
		assertEquals ( new Vector2D(0,-2), vector20.reflect(vector11.normalize()));
		assertEquals ( new Vector2D(-1,-2), vector21.reflect(vector11.normalize()));
		assertEquals ( vector22.reverse(), vector22.reflect(vector11.normalize()));
		
	}

}
