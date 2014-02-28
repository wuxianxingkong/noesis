package test.ikor.math;

import static org.junit.Assert.*;
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
		
		for (int i=0; i<vector.size(); i++)
			if (i==2)
				assertEquals( 4.0, vector.get(i), EPSILON);
			else
				assertEquals( 0.0, vector.get(i), EPSILON );
	}

	
	// 2-element vector
	
	public void check2 (SparseVector vector)
	{
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
		
		assertEquals(32,vector.size());
		
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

		assertEquals(32,vector.size());
		
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
		
		assertEquals(32,vector.size());
		
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

		assertEquals(32,vector.size());
		
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
		
		assertEquals(32,vector.size());
		
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

		assertEquals(32,vector.size());
		
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

		assertEquals(33,vector.size());
		
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
		
		assertEquals(33,vector.size());
		
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

		assertEquals(257,vector.size());
		
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
		
		assertEquals(257,vector.size());
		
		for (int i=1; i<=256; i++) {
			assertEquals(2*i, vector.get(i), EPSILON);
		}
	}		
}
