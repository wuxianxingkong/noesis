package ikor.math.equation;

// Title:       Gaussian elimination algorithm for solving a system of linear equations
// Version:     2.0
// Copyright:   1998-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.LUDecomposition;
import ikor.math.Matrix;
import ikor.math.MatrixFactory;
import ikor.math.Vector;

/**
 * Gaussian elimination algorithm for solving systems of linear equations in O(n^3).
 * 
 * ref. http://en.wikipedia.org/wiki/Gauss%E2%80%93Jordan_elimination
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class GaussianEliminationSolver implements SystemSolver
{
	
	/**
	 * Solves a linear system Ax=B in O(n^3).
	 * 
	 * @param A Square matrix (nxn)
	 * @param B Column vector (nx1)
	 * @return Solution vector (nx1) satisfying Ax=B
	 */

	@Override
	public Vector solve (Matrix A, Vector B) 
	{
		LUDecomposition lu;
		Vector Bcopy = MatrixFactory.createVector(B); // Copy original vector into B2
		Vector X  = MatrixFactory.createVector(A.rows()).transpose();

		// 1. LU factorization
		
		lu = new LUDecomposition(A);
		
		// 2. Back substitution
		
		lu.backwardsSubstitution(Bcopy, X, 0);

		return X;
	}

}
