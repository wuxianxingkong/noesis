package test.ikor.math;

import static org.junit.Assert.*;

import ikor.math.DenseMatrix;
import ikor.math.LUDecomposition;
import ikor.math.Matrix;
import ikor.math.Vector;

import org.junit.Before;
import org.junit.Test;

/**
 * LU decomposition tests 
 * (numerical results from MATLAB, http://www.mathworks.es/es/help/matlab/ref/lu.html)
 * 
 * @author Fernando Berzal (berzal@acm.org) *
 */
public class LUDecompositionTest 
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
		
		LUDecomposition lu = new LUDecomposition(A);

		checkVector( new double[]{ 0, 1, 2, 3}, lu.getPermutation());

		checkMatrix( new double[][]{ 
				{ 1.0000, 0.5000, 0.3333, 0.2500},
				{ 0.5000, 0.7500, 0.5000, 0.3750},
				{ 0.3333, 0.6667, 0.5556, 0.4167},
				{ 0.2500, 0.5000, 0.7500, 0.4375}}, lu.getLU());

		checkMatrix( new double[][]{ 
				{ 1.0000, 0.0000, 0.0000, 0.0000},
				{ 0.5000, 1.0000, 0.0000, 0.0000},
				{ 0.3333, 0.6667, 1.0000, 0.0000},
				{ 0.2500, 0.5000, 0.7500, 1.0000}}, lu.getL());

		checkMatrix( new double[][]{ 
				{ 1.0000, 0.5000, 0.3333, 0.2500},
				{ 0.0000, 0.7500, 0.5000, 0.3750},
				{ 0.0000, 0.0000, 0.5556, 0.4167},
				{ 0.0000, 0.0000, 0.0000, 0.4375}}, lu.getU());
	}

	@Test
	public void testNonSymmetricMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 1,  7,  3 },
				                                     { 2,  9, 12 },
				                                     { 5, 22,  7 } } );
		
		LUDecomposition lu = new LUDecomposition(A);

		checkVector( new double[]{ 2, 0, 1}, lu.getPermutation());

		checkMatrix( new double[][]{ 
				{ 0.2000,  2.6000, 1.6000},
				{ 0.4000,  0.0769, 9.0769},
				{ 5.0000, 22.0000, 7.0000}}, lu.getLU());

		checkMatrix( new double[][]{ 
				{ 1.0000,  0.0000, 0.0000},
				{ 0.2000,  1.0000, 0.0000},
				{ 0.4000,  0.0769, 1.0000}}, lu.getL());
		
		checkMatrix( new double[][]{ 
				{ 5.0000, 22.0000, 7.0000},
				{ 0.0000,  2.6000, 1.6000},
				{ 0.0000,  0.0000, 9.0769}}, lu.getU());
	}	
	
	@Test
	public void testUpperTriangularMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 1, 2, 0 },
				                                     { 0, 2, 0 },
				                                     { 0, 0, 3 } } );
		
		LUDecomposition lu = new LUDecomposition(A);

		checkVector( new double[]{ 0, 1, 2}, lu.getPermutation());

		checkMatrix( A.getArray(), lu.getLU());

		checkMatrix( new double[][]{ 
				{ 1.0000, 0.0000, 0.0000},
				{ 0.0000, 1.0000, 0.0000},
				{ 0.0000, 0.0000, 1.0000}}, lu.getL());

		checkMatrix( A.getArray(), lu.getU());
	}


	@Test
	public void testNonDiagonalizableMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 3, 1, 0 },
				                                     { 0, 3, 1 },
				                                     { 0, 0, 3 } } );
		
		LUDecomposition lu = new LUDecomposition(A);
		
		checkVector( new double[]{ 0, 1, 2 }, lu.getPermutation());

		checkMatrix( A.getArray(), lu.getLU());

		checkMatrix( new double[][]{ 
				{ 1.0000, 0.0000, 0.0000},
				{ 0.0000, 1.0000, 0.0000},
				{ 0.0000, 0.0000, 1.0000}}, lu.getL());

		checkMatrix( A.getArray(), lu.getU());
	}

	@Test
	public void testLowerTriangularMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 3, 0, 0 },
				                                     { 1, 3, 0 },
				                                     { 0, 1, 3 } } );
		
		LUDecomposition lu = new LUDecomposition(A);
		
		checkVector( new double[]{ 0, 1, 2 }, lu.getPermutation());

		checkMatrix( new double[][]{ 
				{ 3.0000, 0.0000, 0.0000},
				{ 0.3333, 3.0000, 0.0000},
				{ 0.0000, 0.3333, 3.0000}}, lu.getLU());

		checkMatrix( new double[][]{ 
				{ 1.0000, 0.0000, 0.0000},
				{ 0.3333, 1.0000, 0.0000},
				{ 0.0000, 0.3333, 1.0000}}, lu.getL());

		checkMatrix( new double[][]{ 
				{ 3.0000, 0.0000, 0.0000},
				{ 0.0000, 3.0000, 0.0000},
				{ 0.0000, 0.0000, 3.0000}}, lu.getU());
	}
	
	@Test
	public void testNonSymmetricCircularMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 1, 2, 3 },
				                                     { 3, 1, 2 },
				                                     { 2, 3, 1 } } );

		LUDecomposition lu = new LUDecomposition(A);
		
		checkVector( new double[]{ 1, 2, 0}, lu.getPermutation());

		checkMatrix( new double[][]{ 
				{ 0.3333, 0.7143, 2.5714},
				{ 3.0000, 1.0000, 2.0000},
				{ 0.6667, 2.3333,-0.3333}}, lu.getLU());

		checkMatrix( new double[][]{ 
				{ 1.0000, 0.0000, 0.0000},
				{ 0.6667, 1.0000, 0.0000},
				{ 0.3333, 0.7143, 1.0000}}, lu.getL());

		checkMatrix( new double[][]{ 
				{ 3.0000, 1.0000, 2.0000},
				{ 0.0000, 2.3333,-0.3333},
				{ 0.0000, 0.0000, 2.5714}}, lu.getU());
	}	
}
