package ikor.math.equation;

// Title:       Algorithms for solving systems of linear equations
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.math.Matrix;
import ikor.math.Vector;

/**
 * Generic algorithms for solving systems of linear equations
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public interface SystemSolver 
{
	/**
	 * Solve a system of linear equations Ax=B
	 * 
	 * @param A Square matrix (nxn)
	 * @param B Column vector (nx1)
	 * @return x Solution (nx1)
	 */
	public Vector solve (Matrix A, Vector B);
}
