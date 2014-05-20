package test.ikor.math;

import static org.junit.Assert.*;
import ikor.math.DenseMatrix;
import ikor.math.EigenvectorDecomposition;
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
public class EigenvectorDecompositionTest 
{

	@Before
	public void setUp() throws Exception {
	}

	private void checkEigenvector ( double expected[], Vector eigenvector)
	{
		for (int i=0; i<expected.length; i++)
			assertEquals(expected[i], eigenvector.get(i), 0.0001);
	}
	
	@Test
	public void testRealSymmetricMatrix()
	{
		// Positive definite matrix
		
		Matrix A = new DenseMatrix( new double[][] { { 1.0,     1.0/2.0, 1.0/3.0, 1.0/4.0 },
				                                     { 1.0/2.0, 1.0,     2.0/3.0, 2.0/4.0 },
				                                     { 1.0/3.0, 2.0/3.0, 1.0,     3.0/4.0 },
				                                     { 1.0/4.0, 2.0/4.0, 3.0/4.0, 1.0     } } );
		
		EigenvectorDecomposition eig = new EigenvectorDecomposition(A);
		
		assertEquals( 0.2078, eig.getRealEigenvalue(0), 0.0001 );
		assertEquals( 0.4078, eig.getRealEigenvalue(1), 0.0001 );
		assertEquals( 0.8482, eig.getRealEigenvalue(2), 0.0001 );
		assertEquals( 2.5362, eig.getRealEigenvalue(3), 0.0001 );		

		assertEquals( 0.0000, eig.getImagEigenvalue(0), 0.0001 );
		assertEquals( 0.0000, eig.getImagEigenvalue(1), 0.0001 );
		assertEquals( 0.0000, eig.getImagEigenvalue(2), 0.0001 );
		assertEquals( 0.0000, eig.getImagEigenvalue(3), 0.0001 );
		
		//    0.0693   -0.4422   -0.8105    0.3778
		//   -0.3618    0.7420   -0.1877    0.5322
		//    0.7694    0.0486    0.3010    0.5614
		//   -0.5219   -0.5014    0.4662    0.5088
		
		checkEigenvector ( new double[]{ 0.0693, -0.3618, 0.7694, -0.5219}, eig.getEigenvector(0) );
		checkEigenvector ( new double[]{-0.4422,  0.7420, 0.0486, -0.5014}, eig.getEigenvector(1) );
		checkEigenvector ( new double[]{-0.8105, -0.1877, 0.3010,  0.4662}, eig.getEigenvector(2) );
		checkEigenvector ( new double[]{ 0.3778,  0.5322, 0.5614,  0.5088}, eig.getEigenvector(3) );
	}
	
	@Test
	public void testNonSymmetricMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 1,  7,  3 },
				                                     { 2,  9, 12 },
				                                     { 5, 22,  7 } } );
		
		EigenvectorDecomposition eig = new EigenvectorDecomposition(A);
		
		assertEquals( 25.5548, eig.getRealEigenvalue(1), 0.0001 );
		assertEquals(  0.0000, eig.getImagEigenvalue(1), 0.0001 );

		assertEquals( -0.5789, eig.getRealEigenvalue(0), 0.0001 );
		assertEquals(  0.0000, eig.getImagEigenvalue(0), 0.0001 );

		assertEquals( -7.9759, eig.getRealEigenvalue(2), 0.0001 );
		assertEquals(  0.0000, eig.getImagEigenvalue(2), 0.0001 );

		//   -0.2610   -0.9734    0.1891
		//   -0.5870    0.2281   -0.5816
		//   -0.7663   -0.0198    0.7912
		
		checkEigenvector ( new double[]{  0.2610,  0.5870,  0.7663}, eig.getEigenvector(1) ); // x -1
		checkEigenvector ( new double[]{ -0.9734,  0.2281, -0.0198}, eig.getEigenvector(0) ); 
		checkEigenvector ( new double[]{  0.1891, -0.5816,  0.7912}, eig.getEigenvector(2) );
	}	
	
	@Test
	public void testRealNonSymmetricMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 1, 2, 0 },
				                                     { 0, 2, 0 },
				                                     { 0, 0, 3 } } );
		
		EigenvectorDecomposition eig = new EigenvectorDecomposition(A);
		
		assertEquals( 1.0000, eig.getRealEigenvalue(0), 0.0001 );
		assertEquals( 0.0000, eig.getImagEigenvalue(0), 0.0001 );

		assertEquals( 2.0000, eig.getRealEigenvalue(1), 0.0001 );
		assertEquals( 0.0000, eig.getImagEigenvalue(1), 0.0001 );

		assertEquals( 3.0000, eig.getRealEigenvalue(2), 0.0001 );
		assertEquals( 0.0000, eig.getImagEigenvalue(2), 0.0001 );

		// 1.0000    0.8944         0
        //      0    0.4472         0
        //      0         0    1.0000
		
		checkEigenvector ( new double[]{ 1.0000, 0.0000, 0.0000}, eig.getEigenvector(0) );
		checkEigenvector ( new double[]{ 0.8944, 0.4472, 0.0000}, eig.getEigenvector(1) ); // (2,1,0)
		checkEigenvector ( new double[]{ 0.0000, 0.0000, 1.0000}, eig.getEigenvector(2) );		
	}
	
		
	@Test
	public void testNonSymmetricCircularMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 1, 2, 3 },
				                                     { 3, 1, 2 },
				                                     { 2, 3, 1 } } );
		
		EigenvectorDecomposition eig = new EigenvectorDecomposition(A);
		
		assertEquals( 6.0000, eig.getRealEigenvalue(0), 0.0001 );
		assertEquals( 0.0000, eig.getImagEigenvalue(0), 0.0001 );

		assertEquals( -1.5000, eig.getRealEigenvalue(1), 0.0001 );
		assertEquals(  0.8660, eig.getImagEigenvalue(1), 0.0001 );

		assertEquals( -1.5000, eig.getRealEigenvalue(2), 0.0001 );
		assertEquals( -0.8660, eig.getImagEigenvalue(2), 0.0001 );
		
		//  -0.5774             0.2887 - 0.5000i   0.2887 + 0.5000i
		//  -0.5774            -0.5774            -0.5774          
		//  -0.5774             0.2887 + 0.5000i   0.2887 - 0.5000i
		
		checkEigenvector ( new double[]{ 0.5774, 0.5774, 0.5774}, eig.getEigenvector(0) ); // x -1
		checkEigenvector ( new double[]{ 0.3624, 0.4525,-0.8148}, eig.getEigenvector(1) ); // vs. complex eigenvectors !!!
		checkEigenvector ( new double[]{ 0.7317,-0.6797,-0.0520}, eig.getEigenvector(2) ); // vs. complex eigenvectors !!!		
		
	}	

	@Test
	public void testNonDiagonizableMatrix()
	{
		Matrix A = new DenseMatrix( new double[][] { { 3, 1, 0 },
				                                     { 0, 3, 1 },
				                                     { 0, 0, 3 } } );
		
		EigenvectorDecomposition eig = new EigenvectorDecomposition(A);
		
		assertEquals( 3.0000, eig.getRealEigenvalue(0), 0.0001 );
		assertEquals( 0.0000, eig.getImagEigenvalue(0), 0.0001 );

		assertEquals( 3.0000, eig.getRealEigenvalue(1), 0.0001 );
		assertEquals( 0.0000, eig.getImagEigenvalue(1), 0.0001 );

		assertEquals( 3.0000, eig.getRealEigenvalue(2), 0.0001 );
		assertEquals( 0.0000, eig.getImagEigenvalue(2), 0.0001 );

		// 1.0000   -1.0000    1.0000
        //      0    0.0000   -0.0000
        //      0         0    0.0000
		
		checkEigenvector ( new double[]{ 1.0000, 0.0000, 0.0000}, eig.getEigenvector(0) );
		checkEigenvector ( new double[]{-1.0000, 0.0000, 0.0000}, eig.getEigenvector(1) );
		checkEigenvector ( new double[]{ 1.0000, 0.0000, 0.0000}, eig.getEigenvector(2) );		
	}
	
}
