package test.ikor.math;

import static org.junit.Assert.*;
import ikor.math.DenseMatrix;
import ikor.math.SingularValueDecomposition;
import ikor.math.Matrix;
import ikor.math.Vector;

import org.junit.Before;
import org.junit.Test;

/**
 * Eigenvector decomposition tests 
 * (numerical results from MATLAB, http://www.mathworks.es/es/help/matlab/ref/eig.html)
 * 
 * @author Fernando Berzal (berzal@acm.org) *
 */
public class SingularValueDecompositionTest 
{

	@Before
	public void setUp() throws Exception {
	}

	private void checkVector ( double expected[], Vector vector)
	{
		for (int i=0; i<expected.length; i++)
			assertEquals(expected[i], vector.get(i), 0.0001);
	}

	private void checkMatrix ( double expected[][], Matrix matrix)
	{
		for (int i=0; i<expected.length; i++)
			for (int j=0; j<expected[i].length; j++)
				assertEquals(expected[i][j], matrix.get(i,j), 0.0001);
	}
	
	@Test
	public void testRealSymmetricMatrix()
	{
		// Positive definite matrix
		
		Matrix A = new DenseMatrix( new double[][] { { 1.0,     1.0/2.0, 1.0/3.0, 1.0/4.0 },
				                                     { 1.0/2.0, 1.0,     2.0/3.0, 2.0/4.0 },
				                                     { 1.0/3.0, 2.0/3.0, 1.0,     3.0/4.0 },
				                                     { 1.0/4.0, 2.0/4.0, 3.0/4.0, 1.0     } } );
		
		SingularValueDecomposition svd = new SingularValueDecomposition(A);

		checkMatrix ( A.getArray(), svd.getU().multiply( svd.getS().multiply( svd.getV().transpose() ) ) );

		checkVector( new double[]{ 2.5362, 0.8482, 0.4078, 0.2078}, svd.getSingularValues());
	}

	@Test
	public void testNonSymmetricMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 1,  7,  3 },
				                                     { 2,  9, 12 },
				                                     { 5, 22,  7 } } );
		
		SingularValueDecomposition svd = new SingularValueDecomposition(A);

		checkMatrix ( A.getArray(), svd.getU().multiply( svd.getS().multiply( svd.getV().transpose() ) ) );
		
		checkVector( new double[]{ 28.0560, 7.6524, 0.5496}, svd.getSingularValues());
	}	
	
	@Test
	public void testRealNonSymmetricMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 1, 2, 0 },
				                                     { 0, 2, 0 },
				                                     { 0, 0, 3 } } );
		
		SingularValueDecomposition svd = new SingularValueDecomposition(A);

		checkMatrix ( A.getArray(), svd.getU().multiply( svd.getS().multiply( svd.getV().transpose() ) ) );
		
		checkVector( new double[]{ 3.0000, 2.9208, 0.6847}, svd.getSingularValues());
		
		checkMatrix( new double[][]{ 
				{ 0.0000, 0.7497, -0.6618},
				{ 0.0000, 0.6618,  0.7497},
				{ 1.0000, 0.0000,  0.0000}}, svd.getU());
		checkMatrix( new double[][]{ 
				{ 3.0000, 0.0000, 0.0000},
				{ 0.0000, 2.9208, 0.0000},
				{ 0.0000, 0.0000, 0.6847}}, svd.getS());
		checkMatrix( new double[][]{ 
				{ 0.0000, 0.2567, -0.9665},
				{ 0.0000, 0.9665,  0.2567},
				{ 1.0000, 0.0000,  0.0000}}, svd.getV());
	}
	
	@Test
	public void testNonSymmetricCircularMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 1, 2, 3 },
				                                     { 3, 1, 2 },
				                                     { 2, 3, 1 } } );
		
		SingularValueDecomposition svd = new SingularValueDecomposition(A);
		
		checkMatrix ( A.getArray(), svd.getU().multiply( svd.getS().multiply( svd.getV().transpose() ) ) );

		checkVector( new double[]{ 6.0000, 1.7321, 1.7321}, svd.getSingularValues());
		
		checkMatrix( new double[][]{ 
				{ 0.5774,-0.7071,-0.4082},
				{ 0.5774, 0.7071,-0.4082},
				{ 0.5774, 0.0000, 0.8165}}, svd.getU());
		checkMatrix( new double[][]{ 
				{ 6.0000, 0.0000, 0.0000},
				{ 0.0000, 1.7321, 0.0000},
				{ 0.0000, 0.0000, 1.7321}}, svd.getS());
		checkMatrix( new double[][]{ 
				{ 0.5774, 0.8165, 0.0000},
				{ 0.5774,-0.4082, 0.7071},
				{ 0.5774,-0.4082,-0.7071}}, svd.getV());
	}	

	@Test
	public void testNonDiagonizableMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 3, 1, 0 },
				                                     { 0, 3, 1 },
				                                     { 0, 0, 3 } } );
		
		SingularValueDecomposition svd = new SingularValueDecomposition(A);

		checkMatrix ( A.getArray(), svd.getU().multiply( svd.getS().multiply( svd.getV().transpose() ) ) );
		
		// vs. 3.0000
		checkVector( new double[]{ 3.7451, 3.0833, 2.3382}, svd.getSingularValues());
		
		checkMatrix( new double[][]{ 
				{ 0.5390,-0.7118, 0.4504},
				{ 0.7233, 0.1170,-0.6806},
				{ 0.4317, 0.6926, 0.5779}}, svd.getU());
		checkMatrix( new double[][]{ 
				{ 3.7451, 0.0000, 0.0000},
				{ 0.0000, 3.0833, 0.0000},
				{ 0.0000, 0.0000, 2.3382}}, svd.getS());
		checkMatrix( new double[][]{ 
				{ 0.4317,-0.6926, 0.5779},
				{ 0.7233,-0.1170,-0.6806},
				{ 0.5390, 0.7118, 0.4504}}, svd.getV());
	}

	@Test
	public void testRectangularMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 1, 2 },
				                                     { 3, 4 },
				                                     { 5, 6 },
				                                     { 7, 8 } } );
		
		SingularValueDecomposition svd = new SingularValueDecomposition(A);

		checkMatrix ( A.getArray(), svd.getU().multiply( svd.getS().multiply( svd.getV().transpose() ) ) );
		
		checkVector( new double[]{ 14.2691, 0.6268 }, svd.getSingularValues());
		
		assertEquals (4, svd.getU().rows());
		assertEquals (2, svd.getU().columns());
		
		checkMatrix( new double[][]{ 
				{  0.1525,  0.8226},
				{  0.3499,  0.4214},
				{  0.5474,  0.0201},
				{  0.7448, -0.3812} }, svd.getU());

		assertEquals (2, svd.getS().rows());
		assertEquals (2, svd.getS().columns());
		
		checkMatrix( new double[][]{ 
				{ 14.2691,  0.0000 },
				{  0.0000,  0.6268 } }, svd.getS());
		
		assertEquals (2, svd.getV().rows());
		assertEquals (2, svd.getV().columns());
		
		checkMatrix( new double[][]{ 
				{  0.6414, -0.7672 },
				{  0.7672,  0.6414 } }, svd.getV());
	}
	
}
