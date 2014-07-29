package test.ikor.math;

import static org.junit.Assert.*;
import ikor.math.DenseVector;
import ikor.math.SparseVector;

import org.junit.Test;
import org.junit.Before;

public class SparseVectorTest 
{
	public static final double EPSILON = 1e-15;
	
	private SparseVector vector;
	
	
	@Before
	public void setUp ()
	{
		vector = new SparseVector(32);
	}
	
	@Test
	public void testConstructor() 
	{
		for (int i=0; i<vector.size(); i++)
			assertEquals( 0.0, vector.get(i), EPSILON );
	}
	
	@Test
	public void testSet()
	{
		vector.set(2,4);
		
		assertEquals(1, vector.nonzero());
		
		for (int i=0; i<vector.size(); i++)
			if (i==2)
				assertEquals( 4.0, vector.get(i), EPSILON);
			else
				assertEquals( 0.0, vector.get(i), EPSILON );
	}

	
	// 2-element vector
	
	public void check2 (SparseVector vector)
	{
		assertEquals(2, vector.nonzero());
		
		for (int i=0; i<vector.size(); i++)
			if (i==2)
				assertEquals( 4.0, vector.get(i), EPSILON);
			else if (i==4)
				assertEquals( 8.0, vector.get(i), EPSILON);			
			else
				assertEquals( 0.0, vector.get(i), EPSILON );
	}
	
	@Test
	public void testSet12()
	{
		vector.set(2,4);
		vector.set(4,8);
		
		check2(vector);
	}

	@Test
	public void testSet21()
	{
		vector.set(4,8);		
		vector.set(2,4);
		
		check2(vector);
	}


	// 3-element vector
	
	public void check3 (SparseVector vector)
	{
		assertEquals(3, vector.nonzero());
		
		for (int i=0; i<vector.size(); i++)
			if (i==2)
				assertEquals( 4.0, vector.get(i), EPSILON);
			else if (i==4)
				assertEquals( 8.0, vector.get(i), EPSILON);			
			else if (i==6)
				assertEquals( 9.0, vector.get(i), EPSILON);			
			else
				assertEquals( 0.0, vector.get(i), EPSILON );		
	}

	@Test
	public void testSet123()
	{
		vector.set(2,4);
		vector.set(4,8);		
		vector.set(6,9);

		check3(vector);
	}

	@Test
	public void testSet132()
	{
		vector.set(2,4);
		vector.set(6,9);
		vector.set(4,8);		

		check3(vector);
	}
	
	@Test
	public void testSet213()
	{
		vector.set(4,8);		
		vector.set(2,4);
		vector.set(6,9);

		check3(vector);
	}
	
	@Test
	public void testSet231()
	{
		vector.set(4,8);		
		vector.set(6,9);
		vector.set(2,4);

		check3(vector);
	}

	@Test
	public void testSet312()
	{
		vector.set(6,9);
		vector.set(2,4);
		vector.set(4,8);
		
		check3(vector);
	}

	@Test
	public void testSet321()
	{
		vector.set(6,9);
		vector.set(4,8);		
		vector.set(2,4);

		check3(vector);
	}

	// 8-element vector
	
	@Test
	public void test8ascending()
	{
		for (int i=1; i<=8; i++) {
			vector.set(i,2*i);
		}
		
		assertEquals(32, vector.size());
		assertEquals( 8, vector.nonzero());
		
		for (int i=1; i<=8; i++) {
			assertEquals(2*i, vector.get(i), EPSILON);
		}
	}
	
	@Test
	public void test8descending()
	{
		for (int i=8; i>0; i--) {
			vector.set(i,2*i);
		}

		assertEquals(32, vector.size());
		assertEquals( 8, vector.nonzero());
		
		for (int i=1; i<=8; i++) {
			assertEquals(2*i, vector.get(i), EPSILON);
		}
	}

	@Test
	public void testEven8ascending()
	{
		for (int i=1; i<=8; i++) {
			vector.set(2*i,3*i);
		}
		
		assertEquals(32, vector.size());
		assertEquals( 8, vector.nonzero());
		
		for (int i=1; i<=8; i++) {
			assertEquals(3*i, vector.get(2*i), EPSILON);
		}
	}
	
	@Test
	public void testEven8descending()
	{
		for (int i=8; i>0; i--) {
			vector.set(2*i,3*i);
		}

		assertEquals(32, vector.size());
		assertEquals( 8, vector.nonzero());
		
		for (int i=1; i<=8; i++) {
			assertEquals(3*i, vector.get(2*i), EPSILON);
		}
	}

	@Test
	public void testOdd8ascending()
	{
		for (int i=1; i<=8; i++) {
			vector.set(2*i-1,3*i);
		}
		
		assertEquals(32, vector.size());
		assertEquals( 8, vector.nonzero());
		
		for (int i=1; i<=8; i++) {
			assertEquals(3*i, vector.get(2*i-1), EPSILON);
		}
	}
	
	@Test
	public void testOdd8descending()
	{
		for (int i=8; i>0; i--) {
			vector.set(2*i-1,3*i);
		}

		assertEquals(32, vector.size());
		assertEquals( 8, vector.nonzero());
		
		for (int i=1; i<=8; i++) {
			assertEquals(3*i, vector.get(2*i-1), EPSILON);
		}
	}
	
	// Large dynamically-extended vectors
	
	@Test
	public void test32ascending()
	{
		for (int i=1; i<=32; i++) {
			vector.set(i,2*i);
		}

		assertEquals(33, vector.size());
		assertEquals(32, vector.nonzero());
		
		for (int i=1; i<=32; i++) {
			assertEquals(2*i, vector.get(i), EPSILON);
		}
	}
	
	@Test
	public void test32descending()
	{
		for (int i=32; i>0; i--) {
			vector.set(i,2*i);
		}
		
		assertEquals(33, vector.size());
		assertEquals(32, vector.nonzero());
		
		for (int i=1; i<=32; i++) {
			assertEquals(2*i, vector.get(i), EPSILON);
		}
	}	
	
	
	@Test
	public void test256ascending()
	{
		for (int i=1; i<=256; i++) {
			vector.set(i,2*i);
		}

		assertEquals(257, vector.size());
		assertEquals(256, vector.nonzero());
		
		for (int i=1; i<=256; i++) {
			assertEquals(2*i, vector.get(i), EPSILON);
		}
	}
	
	@Test
	public void test256descending()
	{
		for (int i=256; i>0; i--) {
			vector.set(i,2*i);
		}
		
		assertEquals(257, vector.size());
		assertEquals(256, vector.nonzero());
		
		for (int i=1; i<=256; i++) {
			assertEquals(2*i, vector.get(i), EPSILON);
		}
	}		
	
	// Vector operations
	// -----------------
	
	// Dot product
	
	private SparseVector createSparseVector(int n)
	{
		SparseVector vector = new SparseVector(n);
		
		vector.set( 2, 1);
		vector.set( 4, 2);
		vector.set( 8, 3);
		vector.set(16, 4);
		vector.set(32, 5);
		vector.set(64, 6);

		return vector;
	}
	
	@Test
	public void testSparseDotProduct() 
	{
		SparseVector vector = createSparseVector(256);
		
		assertEquals(256, vector.size());
		assertEquals(  6, vector.nonzero());
		
		// 1*1 + 2*2 + 3*3 + 4*4 + 5*5 + 6*6 = 91
		assertEquals( 91, vector.dotProduct(vector), EPSILON);
	}

	@Test
	public void testDenseDotProduct() 
	{
		SparseVector vector = createSparseVector(256);
		DenseVector  vector2 = new DenseVector(vector);
		
		assertEquals(256, vector.size());
		assertEquals(  6, vector.nonzero());
		
		// 1*1 + 2*2 + 3*3 + 4*4 + 5*5 + 6*6 = 91
		assertEquals( 91, vector.dotProduct(vector2), EPSILON);
		assertEquals( 91, vector2.dotProduct(vector), EPSILON);
	}

	@Test
	public void testSparseDotProduct2 () 
	{
		SparseVector vector = createSparseVector(256);		
		SparseVector vector2 = new SparseVector(128);
		
		vector2.set(64,1);
		
		assertEquals(256, vector.size());
		assertEquals(  6, vector.nonzero());
		assertEquals(128, vector2.size());
		assertEquals(  1, vector2.nonzero());
		
		assertEquals( 6, vector.dotProduct(vector2), EPSILON);
		assertEquals( 6, vector2.dotProduct(vector), EPSILON);
	}

	
	
	@Test
	public void testSparseMagnitude() 
	{
		SparseVector vector = createSparseVector(256);		

		// 1*1+2*2+3*3+4*4+5*5+6*6 = 91
		assertEquals( 91, vector.magnitude2(), EPSILON);
		assertEquals( Math.sqrt(91), vector.magnitude(), EPSILON);
	}

	@Test
	public void testSparseProjection() 
	{
		SparseVector vector = createSparseVector(256);		
		
		assertEquals( vector.magnitude(), vector.projection(vector), EPSILON);
	}

	@Test
	public void testSparseDistance() 
	{
		SparseVector vector = createSparseVector(256);		
		
		assertEquals( 0, vector.distance(vector), EPSILON);
		assertEquals( 0, vector.distance2(vector), EPSILON);		
	}

	@Test
	public void testSparseAngle() 
	{
		SparseVector vector = createSparseVector(256);		
				
		assertEquals( 0, vector.angle(vector), EPSILON);
	}

	@Test
	public void testSparseMin() 
	{
		SparseVector vector = createSparseVector(256);		
		
		assertEquals( 0, vector.min(), EPSILON);
	}

	@Test
	public void testSparseMax() 
	{
		SparseVector vector = createSparseVector(256);		
		
		assertEquals( 6, vector.max(), EPSILON);
	}

	@Test
	public void testSparseSum() 
	{
		SparseVector vector = createSparseVector(256);		
		
		// 1+2+3+4+5+6 = 21
		assertEquals( 21, vector.sum(), EPSILON);
		// 1*1+2*2+3*3+4*4+5*5+6*6 = 91
		assertEquals( 91, vector.sum2(), EPSILON);
	}

	@Test
	public void testSparseAverage() 
	{
		SparseVector vector = createSparseVector(100);		

		assertEquals( 0.21, vector.average(), EPSILON);
	}

	@Test
	public void testVariance() 
	{
		SparseVector vector = createSparseVector(100);		

		assertEquals( ( 94*0.21*.21 
				      + (1-0.21)*(1-0.21)
				      + (2-0.21)*(2-0.21)
				      + (3-0.21)*(3-0.21)
				      + (4-0.21)*(4-0.21)
				      + (5-0.21)*(5-0.21)
				      + (6-0.21)*(6-0.21) )/100, vector.variance(), EPSILON);
	}

	@Test
	public void testDeviation() 
	{
		SparseVector vector = createSparseVector(100);		

		assertEquals( Math.sqrt(vector.variance()), vector.deviation(), EPSILON);
	}
	
	@Test
	public void testAbsoluteDeviation() 
	{
		SparseVector vector = createSparseVector(100);		

		assertEquals( ( 94*0.21 + (1-0.21) + (2-0.21) + (3-0.21) + (4-0.21) + (5-0.21) + (6-0.21) )/100, vector.absoluteDeviation(), EPSILON);
	}	
	
}
