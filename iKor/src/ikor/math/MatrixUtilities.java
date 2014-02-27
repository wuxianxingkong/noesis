package ikor.math;

// Title:       Matrix utilities
// Version:     2.0
// Copyright:   1998-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

/**
 * Matrix utilities
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */
public class MatrixUtilities 
{

	/**
	 * LU decomposition  (where 'LU' stands for 'Lower Upper'), a.k.a. LU factorization.
	 * 
	 * ref. http://en.wikipedia.org/wiki/LU_decomposition
	 * 
	 * @param A Square matrix (n x n)
	 * @param P Permutation vector (n x 1)
	 * @return Number of row exchanges (-1 when the matrix is singular) + A is now an LU matrix
	 */
	public static int LU (Matrix A, Vector P) 
	{
		int i, j, k, n;
		int maxi, pi, pk;
		double c, c1;
		int p;

		n = A.columns();

		for (i = 0; i < n; i++)
			P.set(i,i);

		p = 0;

		for (k = 0; k < n; k++) {

			// Pivoting

			for (i = k, maxi = k, c = 0; i < n; i++) {

				pi = (int) P.get(i);
				c1 = Math.abs(A.get(pi,k));

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

			if (A.get(pk,k) == 0.0)  // Singular matrix ?
				return -1;

			for (i = k + 1; i < n; i++) {
				
				pi = (int) P.get(i);

				// A[P[i],k] /= A[P[k],k]
				
				A.set(pi,k, A.get(pi,k) / A.get(pk,k) );

				// Elimination

				for (j = k + 1; j < n; j++) {
					// A[P[i],j] -= A[P[i],k]*A[P[k],j]
					A.set(pi,j, A.get(pi,j) - A.get(pi,k)*A.get(pk,j) );
				}
			}
		}

		return p;
	}
	
	/**
	 * Backwards substitution.
	 * 
	 * ref. http://en.wikipedia.org/wiki/Triangular_matrix#Forward_and_back_substitution
	 * 
	 * @param A Square LU matrix (n x n)
	 * @param B Column vector (n x 1)
	 * @param X Solution to AX=B
	 * @param P Permutation (obtained by the LU decomposition)
	 * @param xcol Column index in the X matrix
	 */

	public static void backwardsSubstitution (Matrix A, Vector B, Matrix X, Vector P, int xcol) 
	{
		int i, j, k, n;
		double sum;

		n = A.columns();

		for (k = 0; k < n; k++) {
			int pk = (int) P.get(k);
			for (i = k + 1; i < n; i++) {
				// B[P[i]] = B[P[i]] - A[P[i],k]*B[P[k]]
				int pi = (int) P.get(i);
				B.set ( pi, B.get(pi) - A.get(pi,k)*B.get(pk) );
			}
		}

		// X[n-1] = B[P[n-1]]/A[P[n-1],n-1]
		int plast = (int) P.get(n-1);		
		X.set(n-1, xcol, B.get(plast) / A.get(plast, n-1) );
	
		
		for (k = n - 2; k >= 0; k--) {

			int pk = (int) P.get(k);

			// sum ( A[P[k],j]*X[j] )
			
			sum = 0;
			
			for (j = k + 1; j < n; j++)
				sum += A.get(pk,j) * X.get(j,xcol);
				
			// X[k] = ( B[P[k]] -sum ) / A[P[k],k]
				
			X.set (k, xcol, ( B.get(pk) - sum ) / A.get(pk,k) );

		}
	}
	
}
