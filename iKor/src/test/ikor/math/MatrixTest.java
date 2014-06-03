package test.ikor.math;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import ikor.math.Matrix;
import ikor.math.MatrixFactory;

public class MatrixTest 
{
	Matrix original;
	Matrix identity;
	Matrix matrix;
	Matrix zeros;
	Matrix ones;
	
	double[][] data = new double[][] { {3,2,1}, {4,5,6}, {8,7,9} };
	
	public static final double EPSILON = 1e-6;
	

	@Before
	public void setUp() throws Exception 
	{
		original = MatrixFactory.create(data);
		matrix   = MatrixFactory.create(data);
		identity = MatrixFactory.createIdentity(3);
		
		ones  = MatrixFactory.create(3,3,1);
		zeros = MatrixFactory.create(3,3,0);
	}
	
	@Test
	public void testValues ()
	{
		int i,j;
		
		for (i=0; i<3; i++)
			for (j=0; j<3; j++)
				assertEquals(data[i][j], matrix.get(i,j), 0);

		// Identity
		
		for (i=0; i<3; i++)
			for (j=0; j<3; j++)
				if (i==j)
					assertEquals(1, identity.get(i,j), 0);
				else
					assertEquals(0, identity.get(i,j), 0);
		
		// Constants
		
		for (i=0; i<3; i++)
			for (j=0; j<3; j++) {
				assertEquals(0, zeros.get(i,j), 0);
				assertEquals(1, ones.get(i,j), 0);				
			}
		
	}
	
	@Test
	public void testRowVectors ()
	{
		for (int j=0; j<original.rows(); j++) {
			
			assertEquals ( original.columns(), original.getRow(j).size() );
		
			for (int i=0; i<original.columns(); i++)
				assertEquals ( data[j][i], original.getRow(j).get(i), EPSILON); 
		}
		
	}

	@Test
	public void testColumnVectors ()
	{		
		for (int j=0; j<original.columns(); j++) {
			
			assertEquals ( original.columns(), original.getColumn(j).size() );
		
			for (int i=0; i<original.rows(); i++)
				assertEquals ( data[i][j], original.getColumn(j).get(i), EPSILON); 
		}
		
	}
	
	@Test
	public void testEqualities ()
	{
		assertEquals(matrix, matrix);
		assertEquals(identity, identity);
		assertEquals(zeros, zeros);
		assertEquals(ones, ones);
	}
	
	@Test
	public void testIdentities ()
	{
		assertEquals(original, matrix.multiply(identity));		
		assertEquals(original, identity.multiply(matrix));		
	}

	@Test
	public void testZeroMultiplication ()
	{
		assertEquals(zeros, zeros.multiply(matrix));
		assertEquals(zeros, matrix.multiply(zeros));		
	}
		
	@Test
	public void testZeroAddition ()
	{
		assertEquals(original, matrix.add(zeros));		
		assertEquals(original, zeros.add(matrix));		
	
		assertEquals(original, matrix.subtract(zeros));
	}
	
	@Test
	public void testScalarOperations ()
	{
		assertEquals(original, matrix.add(0));		
		assertEquals(original, matrix.multiply(1));		
		assertEquals(original, matrix.divide(1));

		assertEquals(ones, zeros.add(1));
	}


	@Test
	public void testSize ()
	{
		assertEquals(9, matrix.size());
		assertEquals(3, matrix.rows());
		assertEquals(3, matrix.columns());
	}
	
	@Test
	public void testTranspose ()
	{
		assertEquals (matrix,   matrix.transpose().transpose() );
		assertEquals (identity, identity.transpose() );
		assertEquals (zeros,    zeros.transpose() );
		assertEquals (ones,     ones.transpose() );
	}

	@Test
	public void testTrace ()
	{
		assertEquals( 3+5+9, matrix.trace(), EPSILON);
		assertEquals( 3, identity.trace(), EPSILON);
		assertEquals( 0, zeros.trace(), EPSILON);
		assertEquals( 3, ones.trace(), EPSILON);
	}

	@Test
	public void testDiagonalProduct ()
	{
		assertEquals( 3*5*9, matrix.diagonalProduct(), EPSILON);
		assertEquals( 1, identity.diagonalProduct(), EPSILON);
		assertEquals( 0, zeros.diagonalProduct(), EPSILON);
		assertEquals( 1, ones.diagonalProduct(), EPSILON);
	}

	
	@Test
	public void testDeterminant ()
	{
		assertEquals( 21, matrix.determinant(), EPSILON);
		assertEquals( 1, identity.determinant(), EPSILON);
		assertEquals( 0, zeros.determinant(), EPSILON);
		assertEquals( 0, ones.determinant(), EPSILON);
	}
	
	@Test
	public void testMinor ()
	{
		// 3 2 1
		// 4 5 6
		// 8 7 9
		
		assertEquals( 5*9-6*7, matrix.minor(0,0), EPSILON);
		assertEquals( 4*9-6*8, matrix.minor(0,1), EPSILON);
		assertEquals( 4*7-5*8, matrix.minor(0,2), EPSILON);
		assertEquals( 2*9-1*7, matrix.minor(1,0), EPSILON);
		assertEquals( 3*9-1*8, matrix.minor(1,1), EPSILON);
		assertEquals( 3*7-2*8, matrix.minor(1,2), EPSILON);
		assertEquals( 2*6-1*5, matrix.minor(2,0), EPSILON);
		assertEquals( 3*6-1*4, matrix.minor(2,1), EPSILON);
		assertEquals( 3*5-2*4, matrix.minor(2,2), EPSILON);
		
		assertEquals( 1, identity.minor(0,0), EPSILON);
		assertEquals( 0, zeros.minor(0,0), EPSILON);
		assertEquals( 0, ones.minor(0,0), EPSILON);
	}	

	@Test
	public void testCofactor ()
	{
		// 3 2 1
		// 4 5 6
		// 8 7 9
		
		assertEquals( +(5*9-6*7), matrix.cofactor(0,0), EPSILON);
		assertEquals( -(4*9-6*8), matrix.cofactor(0,1), EPSILON);
		assertEquals( +(4*7-5*8), matrix.cofactor(0,2), EPSILON);
		assertEquals( -(2*9-1*7), matrix.cofactor(1,0), EPSILON);
		assertEquals( +(3*9-1*8), matrix.cofactor(1,1), EPSILON);
		assertEquals( -(3*7-2*8), matrix.cofactor(1,2), EPSILON);
		assertEquals( +(2*6-1*5), matrix.cofactor(2,0), EPSILON);
		assertEquals( -(3*6-1*4), matrix.cofactor(2,1), EPSILON);
		assertEquals( +(3*5-2*4), matrix.cofactor(2,2), EPSILON);
		
		assertEquals( 1, identity.cofactor(0,0), EPSILON);
		assertEquals( 0, zeros.cofactor(0,0), EPSILON);
		assertEquals( 0, ones.cofactor(0,0), EPSILON);
	}	
	
	@Test
	public void testPower ()
	{
		Matrix A  = MatrixFactory.create ( new double[][] { {0,1,1,0}, {1,0,1,0}, {1,1,0,1}, {0,0,1,0} } );
		Matrix A0 = MatrixFactory.create ( new double[][] { {1,0,0,0}, {0,1,0,0}, {0,0,1,0}, {0,0,0,1} } );
		Matrix A2 = MatrixFactory.create ( new double[][] { {2,1,1,1}, {1,2,1,1}, {1,1,3,0}, {1,1,0,1} } );
		Matrix A3 = MatrixFactory.create ( new double[][] { {2,3,4,1}, {3,2,4,1}, {4,4,2,3}, {1,1,3,0} } );
		Matrix A4 = MatrixFactory.create ( new double[][] { { 7, 6, 6, 4}, { 6, 7, 6, 4}, { 6, 6,11, 2}, { 4, 4, 2, 3} } );
		Matrix A5 = MatrixFactory.create ( new double[][] { {12,13,17, 6}, {13,12,17, 6}, {17,17,14,11}, { 6, 6,11, 2} } );
		Matrix A6 = MatrixFactory.create ( new double[][] { {30,29,31,17}, {29,30,31,17}, {31,31,45,14}, {17,17,14,11} } );
		Matrix A7 = MatrixFactory.create ( new double[][] { {60,61,76,31}, {61,60,76,31}, {76,76,76,45}, {31,31,45,14} } );
		
		assertEquals ( A,  A.power(1) );
		assertEquals ( A2, A.power(2) );
		assertEquals ( A3, A.power(3) );
		assertEquals ( A4, A.power(4) );
		assertEquals ( A5, A.power(5) );
		assertEquals ( A6, A.power(6) );
		assertEquals ( A7, A.power(7) );

		assertEquals ( A0, A.power(0) );
		
		assertEquals ( A.inverse(),  A.power(-1) );
		assertEquals ( A2.inverse(), A.power(-2) );
		assertEquals ( A3.inverse(), A.power(-3) );
		
	}
}
