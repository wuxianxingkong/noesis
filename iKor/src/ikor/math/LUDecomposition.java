package ikor.math;

// Title:       LU matrix factorization
// Version:     2.0
// Copyright:   1998-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * LU decomposition  (where 'LU' stands for 'Lower Upper'), a.k.a. LU factorization.
 *   
 * <p>
 *    For an n-by-n matrix A, the LU decomposition is an n-by-n
 *    unit lower triangular matrix L, an n-by-n upper triangular matrix U,
 *    and a permutation vector P of length n so that A(P,:) = L*U.
 * <p>
 *    The LU decomposition with pivoting always exists, even if the matrix is singular.
 * <p>   
 *    The primary use of the LU decomposition is in the solution of square
 *    systems of simultaneous linear equations, {@link ikor.math.equation.SystemSolver}
 *
 * ref. http://en.wikipedia.org/wiki/LU_decomposition
 *
 * @author Fernando Berzal (berzal@acm.org)
 */

public class LUDecomposition extends MatrixDecomposition 
{
	// Instance variables
			
	/** 
	 * Array for internal storage of the LU decomposition. 
	 */
	private double[][] LU;
	
	/**
	 * Row dimension
	 */
	private int n;
	
	/**
	 * Column dimension
	 */
	private int m;
	
	/**
	 * Pivot sign
	 */
	private int pivsign; 

	/** 
	 * Pivot vector
	 */
	private int[] piv;

	/**
	 * Row exchange count
	 */
	private int exchanges;
		
    /**
     * Constructor
     * 
     * @param matrix Matrix to be decomposed
     */
	public LUDecomposition (Matrix matrix)
	{
		LU = matrix.getArray();
		n = matrix.rows();
		m = matrix.columns();
		
		// Use a "left-looking", dot-product, Crout/Doolittle algorithm.

		piv = new int[m];

		for (int i = 0; i < m; i++) {
			piv[i] = i;
		}
		pivsign = 1;

		double[] LUrowi;
		double[] LUcolj = new double[m];

		// Outer loop.

		for (int j = 0; j < n; j++) {

			// Make a copy of the j-th column to localize references.

			for (int i = 0; i < m; i++) {
				LUcolj[i] = LU[i][j];
			}

			// Apply previous transformations.

			for (int i = 0; i < m; i++) {
				LUrowi = LU[i];

				// Most of the time is spent in the following dot product.

				int kmax = Math.min(i,j);
				double s = 0.0;
				for (int k = 0; k < kmax; k++) {
					s += LUrowi[k]*LUcolj[k];
				}

				LUrowi[j] = LUcolj[i] -= s;
			}

			// Find pivot and exchange if necessary.

			int p = j;
			for (int i = j+1; i < m; i++) {
				if (Math.abs(LUcolj[i]) > Math.abs(LUcolj[p])) {
					p = i;
				}
			}
			if (p != j) {
				for (int k = 0; k < n; k++) {
					double t = LU[p][k]; 
					LU[p][k] = LU[j][k]; 
					LU[j][k] = t;
				}
				int k = piv[p]; 
				piv[p] = piv[j]; 
				piv[j] = k;
				pivsign = -pivsign;
				exchanges++;
			}

			// Compute multipliers.

			if (j < m & LU[j][j] != 0.0) {
				for (int i = j+1; i < m; i++) {
					LU[i][j] /= LU[j][j];
				}
			}
		}
	}
		
	// Accessors
	
	/**
	 * LU decomposition matrix
	 * 
	 * @return LU matrix (n x n)
	 */
	public Matrix getLU ()
	{
		return new DenseMatrix(LU);
	}
	
	/**
	 * Column permutation vector
	 * 
	 * @return Permutation vector (m x 1)
	 */
	public Vector getPermutation ()
	{
		Vector permutation = new DenseVector(m);
		
		for (int i=0; i<m; i++)
			permutation.set(i, piv[i]);
		
		return permutation;
	}

	/**
	 * Unit lower triangular matrix L
	 * @return L
	 */
	public Matrix getL ()
	{
		Matrix L = new DenseMatrix(n,n);
		
		for (int i=0; i<n; i++) {
			L.set(i,i, 1.0);
			for (int j=0; j<i; j++) {
				L.set(i,j, LU[i][j]);
			}
		}
		
		return L;
	}

	/**
	 * Upper triangular matrix U
	 * @return U
	 */
	public Matrix getU ()
	{
		Matrix U = new DenseMatrix(n,n);
		
		for (int i=0; i<n; i++) {
			for (int j=i; j<n; j++) {
				U.set(i,j, LU[i][j]);
			}
		}
		
		return U;
	}
	
	/**
	 * Number of row exchanges (-1 when the matrix is singular)
	 * 
	 * @return Number of row exchanges
	 */
	public int getRowExchangeCount ()
	{
		return exchanges;
	}
	
	/**
	 * Is the matrix singular?
	 * 
	 * @return true when the matrix is singular
	 */
	public boolean isSingular ()
	{
		for (int j = 0; j < n; j++) {
			if (LU[j][j] == 0)
				return true;
		}

		return false;
	}
	
	/**
	 * Determinant
	 */
	public double determinant () 
	{
		if (m != n)
			throw new UnsupportedOperationException("Matrix must be square.");
		
		// |A| = |L||U||P|
		// |L| = 1,
		// |U| = multiplication of the diagonal
		// |P| = +-1

		double d = (double) pivsign;
		
		for (int j = 0; j < n; j++) {
			d *= LU[j][j];
		}
		
		return d;
	}
	
	
	/**
	 * Solve A*X=B by backwards substitution.
	 * 
	 * ref. http://en.wikipedia.org/wiki/Triangular_matrix#Forward_and_back_substitution
	 * 
	 * @param B A Matrix with as many rows as A and any number of columns.
	 * @return X so that L*U*X = B(piv,:)
	 * @exception  IllegalArgumentException Matrix row dimensions must agree.
	 * @exception  RuntimeException  Matrix is singular.
	 */

	public Matrix solve (Matrix B) 
	{
		if (B.rows() != m) 
			throw new IllegalArgumentException("Matrix row dimensions must agree.");
		
		if (this.isSingular()) 
			throw new RuntimeException("Matrix is singular.");

		// Copy right hand side with pivoting
		int nx = B.columns();
		double[][] X = getDataArray(B);

		// Solve L*Y = B(piv,:)
		for (int k = 0; k < n; k++) {
			for (int i = k+1; i < n; i++) {
				for (int j = 0; j < nx; j++) {
					X[i][j] -= X[k][j]*LU[i][k];
				}
			}
		}
		// Solve U*X = Y;
		for (int k = n-1; k >= 0; k--) {
			for (int j = 0; j < nx; j++) {
				X[k][j] /= LU[k][k];
			}
			for (int i = 0; i < k; i++) {
				for (int j = 0; j < nx; j++) {
					X[i][j] -= X[k][j]*LU[i][k];
				}
			}
		}
		
		return new DenseMatrix(X);
	}
	
	private double[][] getDataArray(Matrix B)
	{
		double data[][] = new double[B.rows()][B.columns()];
		
		for (int i=0; i<B.rows(); i++)
			for (int j=0; j<B.columns(); j++)
				data[i][j] = B.get(piv[i], j);
		
		return data;
	}
	
	/**
	 * Matrix inverse, by solving A* A^{-1} = I
	 * 
	 * @return Matrix inverse: A^{-1} 
	 */
	public Matrix inverse ()
	{
		return solve ( MatrixFactory.createIdentity(n) );
		
		// B = MatrixFactory.createVector(n);
		// C = MatrixFactory.create(n, n);

		// for (i = 0; i < n; i++) {
		//	 B.zero();
		//	 B.set(i,1);
		//	 lu.backwardsSubstitution(B, C, i);
		// }
	}
	   
}
