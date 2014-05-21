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
	 * Matrix dimension
	 */
	private int n;
	
	/**
	 * LU matrix (n x n)
	 */
	private Matrix LU;
	
	/**
	 * Permutation vector (n x 1)
	 */
	private Vector P;

	/**
	 * Number of row exchanges (-1 when the matrix is singular)
	 */
	private int p;
	
	// Constructor
	
    /**
     * Constructor
     * 
     * @param matrix Square matrix (n x n)
     */
	public LUDecomposition (Matrix matrix)
	{
		if (!matrix.isSquare())
			throw new UnsupportedOperationException("LU decomposition only available for square matrices.");

		n = matrix.rows(); // == matrix.columns();
		
		LU = MatrixFactory.create(matrix);
		P = MatrixFactory.createVector(n);
	
		int i, j, k;
		int maxi, pi, pk;
		double c, c1;

		for (i = 0; i < n; i++)
			P.set(i,i);

		p = 0;

		for (k = 0; (k < n) && (p != -1); k++) {

			// Pivoting

			for (i = k, maxi = k, c = 0; i < n; i++) {

				pi = (int) P.get(i);
				c1 = Math.abs(LU.get(pi,k));

				if (c1 > c) {
					c = c1;
					maxi = i;
				}
			}

			// Row exchange

			if (k != maxi) {
				P.swap(k,maxi);
				p++;
			}

			pk = (int) P.get(k);

			if (LU.get(pk,k) == 0.0) { // Singular matrix ?
				
				p = -1;

			} else {
				
				for (i = k + 1; i < n; i++) {

					pi = (int) P.get(i);

					// A[P[i],k] /= A[P[k],k]

					LU.set(pi,k, LU.get(pi,k) / LU.get(pk,k) );

					// Elimination

					for (j = k + 1; j < n; j++) {
						// A[P[i],j] -= A[P[i],k]*A[P[k],j]
						LU.set(pi,j, LU.get(pi,j) - LU.get(pi,k)*LU.get(pk,j) );
					}
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
		return LU;
	}
	
	/**
	 * Row permutation vector
	 * 
	 * @return Permutation vector (n x 1)
	 */
	public Vector getPermutation ()
	{
		return P;
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
				L.set(i,j, LU.get((int)P.get(i), j));
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
				U.set(i,j, LU.get((int)P.get(i), j));
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
		return p;
	}
	
	/**
	 * Is the matrix singular?
	 * 
	 * @return true when the matrix is singular
	 */
	public boolean isSingular ()
	{
		return (p==-1);
	}
	
	
	/**
	 * Backwards substitution.
	 * 
	 * ref. http://en.wikipedia.org/wiki/Triangular_matrix#Forward_and_back_substitution
	 * 
	 * @param B Column vector (n x 1)
	 * @param X Solution to AX=B, where A is the LU matrix
	 * @param xcol Column index in the X matrix
	 */

	public void backwardsSubstitution (Vector B, Matrix X, int xcol) 
	{
		int i, j, k;
		double sum;

		for (k = 0; k < n; k++) {
			int pk = (int) P.get(k);
			for (i = k + 1; i < n; i++) {
				// B[P[i]] = B[P[i]] - A[P[i],k]*B[P[k]]
				int pi = (int) P.get(i);
				B.set ( pi, B.get(pi) - LU.get(pi,k)*B.get(pk) );
			}
		}

		// X[n-1] = B[P[n-1]]/A[P[n-1],n-1]
		int plast = (int) P.get(n-1);		
		X.set(n-1, xcol, B.get(plast) / LU.get(plast, n-1) );
	
		
		for (k = n - 2; k >= 0; k--) {

			int pk = (int) P.get(k);

			// sum ( A[P[k],j]*X[j] )
			
			sum = 0;
			
			for (j = k + 1; j < n; j++)
				sum += LU.get(pk,j) * X.get(j,xcol);
				
			// X[k] = ( B[P[k]] -sum ) / A[P[k],k]
				
			X.set (k, xcol, ( B.get(pk) - sum ) / LU.get(pk,k) );

		}
	}
	
}
