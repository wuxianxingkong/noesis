package ikor.math.equation;

// Title:       Levinson-Durbin algorithm for solving a system of linear equations
// Version:     2.0
// Copyright:   1998-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.Matrix;
import ikor.math.MatrixFactory;
import ikor.math.Vector;

/**
 * Levinson-Durbin algorithm for solving systems of linear equations involving a Toeplitz matrix in O(n^2).
 * 
 * ref. http://en.wikipedia.org/wiki/Levinson_recursion
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class LevinsonDurbinSolver implements SystemSolver
{
	/**
	 * Solves a linear system of the form
	 * 
	 * | v0 v1 v2 .. vn-1 | | x1 |   | v1 |
	 * | v1 v0 v1 .. vn-2 | | x2 |   | v2 |
	 * | v2 v1 v0 .. vn-3 | | x3 | = | .. |
	 * |        ...       | | .. |   | .. |
	 * | vn-1 vn-2 ..  v0 | | xn |   | vn |
	 * 
	 * @param v Toeplitz coefficients (v0, v1, ... vn)
	 * @return nx1 solution vector (i.e. x satisfying Ax=B)
	 */
	public Vector solve (double v[]) 
	{
		int i, i1, j, ji, p;
		double A[][];
		double W[], E[], K[];
		Vector X;

		p = v.length - 1;
		W = new double[p+2];
		E = new double[p+2];
		K = new double[p+2];
		A = new double[p+2][p+2];

		W[0] = v[1];
		E[0] = v[0];

		for (i = 1; i <= p; i++) {
			K[i] = W[i-1] / E[i - 1];
			E[i] = E[i-1] * (1.0 - K[i] * K[i]);
			A[i][i] = -K[i];

			i1 = i - 1;

			if (i1 >= 1)
				for (j = 1; j <= i1; j++) {
					ji = i - j;
					A[j][i] = A[j][i1] - K[i] * A[ji][i1];
				}

			if (i != p) {
				W[i] = v[i+1];
				for (j = 1; j <= i; j++)
					W[i] += A[j][i] * v[i - j + 1];
			}
		}

		X = MatrixFactory.createVector(p);

		for (i = 0; i < p; i++)
			X.set(i, -A[i+1][p]);

		W = null;
		E = null;
		K = null;
		A = null;

		return X;
	}

	/**
	 * Solves a linear system Ax=B of the form
	 * 
	 * | v0 v1 v2 .. vn-1 | | x1 |   | v1 |
	 * | v1 v0 v1 .. vn-2 | | x2 |   | v2 |
	 * | v2 v1 v0 .. vn-3 | | x3 | = | .. |
	 * |        ...       | | .. |   | .. |
	 * | vn-1 vn-2 ..  v0 | | xn |   | vn |
	 * 
	 * @param A Symmetric Toeplitz matrix (nxn)
	 * @param B Column vector (nx1)
	 * @return Solution vector (nx1) satisfying Ax=B
	 */
	
	@Override
	public Vector solve (Matrix A, Vector B) 
	{
		double v[];
		Vector X;
		int i, n;

		n = A.rows();
		v = new double[n+1];

		for (i = 0; i < n; i++)
			v[i] = A.get(i,0);

		v[n] = B.get(n-1,0);
		
		X = solve(v);

		v = null;

		return X;
	}
}
