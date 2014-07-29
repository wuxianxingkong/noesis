package test.ikor.math.equation;

import static org.junit.Assert.*;
import ikor.math.DenseMatrix;
import ikor.math.DenseVector;
import ikor.math.Matrix;
import ikor.math.Vector;
import ikor.math.equation.GaussianEliminationSolver;
import ikor.math.equation.SystemSolver;

import org.junit.Test;

public class GaussianEliminationSolverTest 
{
	public final static double EPSILON = 1e-15;
	
	@Test
	public void test1() 
	{
		Matrix A = new DenseMatrix( new double[][]{ {3} } );
		Vector B = new DenseVector ( new double[]{ 6 } );
		
		SystemSolver solver = new GaussianEliminationSolver();
		
		Vector X = solver.solve(A, B);
		
		assertEquals(1, X.size());
		assertEquals(2.0, X.get(0), EPSILON);
	}

	@Test
	public void test2() 
	{
		Matrix A = new DenseMatrix( new double[][]{ {1,2}, {3,4}} );
		Vector B = new DenseVector ( new double[]{ 5, 11 } ).transpose();
		
		SystemSolver solver = new GaussianEliminationSolver();
		
		Vector X = solver.solve(A, B);
		
		assertEquals(2, X.size());
		assertEquals(1.0, X.get(0), EPSILON);
		assertEquals(2.0, X.get(1), EPSILON);
	}

	@Test
	public void test3() 
	{
		Matrix A = new DenseMatrix( new double[][]{ {1,1,1}, {1,2,3}, {1,3,6} }); // pascal(3)
		Vector B = new DenseVector ( new double[]{ 6, 14, 25 } ).transpose();
		
		SystemSolver solver = new GaussianEliminationSolver();
		
		Vector X = solver.solve(A, B);
		
		assertEquals(3, X.size());
		assertEquals(1.0, X.get(0), EPSILON);
		assertEquals(2.0, X.get(1), EPSILON);
		assertEquals(3.0, X.get(2), EPSILON);
	}
	
	@Test
	public void test3x3x3() 
	{
		Matrix A = new DenseMatrix( new double[][]{ {1,1,1}, {1,2,3}, {1,3,6} }); // pascal(3)
		Matrix B = new DenseMatrix( new double[][]{ {8,1,6}, {3,5,7}, {4,9,2} }); // magic(3)
		
		GaussianEliminationSolver solver = new GaussianEliminationSolver();
		
		Matrix X = solver.solve(A, B);
		
		assertEquals( 9, X.size());
		assertEquals( 19.0, X.get(0,0), EPSILON);
		assertEquals( -3.0, X.get(0,1), EPSILON);
		assertEquals( -1.0, X.get(0,2), EPSILON);
		assertEquals(-17.0, X.get(1,0), EPSILON);
		assertEquals(  4.0, X.get(1,1), EPSILON);
		assertEquals( 13.0, X.get(1,2), EPSILON);
		assertEquals(  6.0, X.get(2,0), EPSILON);
		assertEquals(  0.0, X.get(2,1), EPSILON);
		assertEquals( -6.0, X.get(2,2), EPSILON);
	}

	
	@Test(expected=RuntimeException.class) // Singular matrix
	public void testSingular1() 
	{
		Matrix A = new DenseMatrix( new double[][]{ {1,2,1}, {2,1,2}, {3,3,3} });
		Vector B = new DenseVector ( new double[]{ 8, 10, 6 } ).transpose();
		
		SystemSolver solver = new GaussianEliminationSolver();
		
		Vector X = solver.solve(A, B);
		
		assertEquals(3, X.size());
		assertEquals(1.0, X.get(0), EPSILON);
		assertEquals(2.0, X.get(1), EPSILON);
		assertEquals(3.0, X.get(2), EPSILON);
	}
	
	@Test(expected=RuntimeException.class) // Singular matrix
	public void testSingular2() 
	{
		Matrix A = new DenseMatrix( new double[][]{ {1,2,3}, {4,5,6}, {7,8,9} });
		Vector B = new DenseVector ( new double[]{ 14, 32, 50 } ).transpose();
		
		SystemSolver solver = new GaussianEliminationSolver();
		
		Vector X = solver.solve(A, B);
		
		assertEquals(3, X.size());
		assertEquals(1.0, X.get(0), EPSILON);
		assertEquals(2.0, X.get(1), EPSILON);
		assertEquals(3.0, X.get(2), EPSILON);
	}
	
}
