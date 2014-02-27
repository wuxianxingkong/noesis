package ikor.math;

// Title:       Matrix ADT
// Version:     2.0
// Copyright:   1998-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Matrix ADT
 *
 * References:
 * - Mary L.Boas: "Mathematical Methods in the Physical Science," John Wiley & Sons, 2nd edition, Chapter 3, 1983.
 * - Kendall E.Atkinson: "An Introduction to Numerical Analysis," John Wiley & Sons, 1978.
 * - Alfred V.Aho, John E.Hopcroft & Jeffrey D.Ullman: "The Design and Analysis of Computer Algorithms," 1974.
 * - Shuzo Saito & Kazuo Nakata: "Fundamentals of Speech Signal Processing," Academic Press, New York, 1985.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public abstract class Matrix implements java.io.Serializable
{	
	// Factory methods
	
	public static final Matrix createMatrix (int rows, int columns)
	{
		return new DenseMatrix(rows, columns);
	}

	public static final Matrix createMatrix (int rows, int columns, double value)
	{
		Matrix matrix = new DenseMatrix(rows, columns);

		for (int i=0; i<rows; i++)
			for (int j=0; j<columns; j++)
				matrix.set(i,j, value);
		
		return matrix;
	}	
	
	public static final Matrix createMatrix (Matrix origen) 
	{
		int filas = origen.rows();
		int columnas = origen.columns();
		
		Matrix datos = new DenseMatrix(filas, columnas);

		for (int i=0; i<filas; i++)
			for (int j=0; j<columnas; j++)
				datos.set(i,j, origen.get(i,j));
		
		return datos;
	}	

	public static final Matrix createMatrix (double data[][]) 
	{
		int rows = data.length;
		int columns = data[0].length;
		
		Matrix matrix = new DenseMatrix(rows, columns);

		for (int i=0; i<rows; i++)
			for (int j=0; j<columns; j++)
				matrix.set(i,j, data[i][j]);
		
		return matrix;
	}	
	
	public static final Matrix createIdentity (int n)
	{
		Matrix identity = createMatrix(n,n);

		for (int i=0; i<n; i++)
			identity.set(i,i,1);

		return identity;
	}
	
		
	// Matrix dimensions

	public final int size() 
	{
		return rows() * columns();
	}
	
	public abstract int rows(); 

	public abstract int columns(); 
	
	// Accessors & mutators

	public abstract double get(int i, int j); 

	public abstract void set(int i, int j, double v);
	
	public void set (int i, double v[])
	{
		setRow(i,v);
	}

	
	public final void setRow (int i, double v[])
	{
		if (v.length==columns()) {
			for (int j=0; j<v.length; j++) {
				set (i, j, v[j]);
			}
		}
	}

	public final void setRow (int i, double v)
	{
		for (int j=0; j<columns(); j++) {
			set (i, j, v);
		}
	}

	public final void setColumn (int j, double v[])
	{
		if (v.length==rows()) {
			for (int i=0; i<v.length; i++) {
				set (i, j, v[i]);
			}
		}		
	}

	public final void setColumn (int j, double v)
	{
		for (int i=0; i<rows(); i++) {
			set (i, j, v);
		}
	}

	public void set (double v[][])
	{
		if (v.length==rows()) {
			for (int i=0; i<v.length; i++) {
				setRow(i, v[i]);
			}
		}		
	}
	
	
	/* ----------------- */
	/* MATRIX OPERATIONS */
	/* ----------------- */

	// Traspuesta de una matriz

	public Matrix transpose() 
	{
		int i, j;
		int filas = rows();
		int columnas = columns();
		Matrix t = createMatrix(columnas, filas);

		for (i = 0; i < columnas; i++)
			for (j = 0; j < filas; j++)
				t.set(i,j, this.get(j,i));

		return t;
	}

	// Devuelve el resultado de eliminar de A la fila i y la columna j

	/**
	 * @pre (filas>1) && (i>=0) && (i<filas) 
	 *   && (columnas>1) && (j>=0) && (j<columnas)
	 */
	public Matrix submatrix(int i, int j) 
	{
		int x, y, xS, yS;
		int filas = rows();
		int columnas = columns();
		Matrix S = createMatrix(filas - 1, columnas - 1);

		for (x = xS = 0; x < filas; x++)
			if (x != i) {
				for (y = yS = 0; y < columnas; y++)
					if (y != j) {
						S.set(xS,yS, this.get(x,y));
						yS++;
					}
				xS++;
			}

		return S;
	}

	// Suma de matrices: A+B
	
	public Matrix add (Matrix other) 
	{
		int i, j;
		int filas = rows();
		int columnas = columns();
		Matrix suma = null;

		if (this.rows() == other.rows() && this.columns() == other.columns()) {

			suma = createMatrix(filas, columnas);

			for (i = 0; i < filas; i++)
				for (j = 0; j < columnas; j++)
					suma.set(i,j, this.get(i,j)+other.get(i,j));
		}

		return suma;
	}

	public Matrix add (double constant) 
	{
		int filas = rows();
		int columnas = columns();
		
		Matrix suma = createMatrix(filas,columnas);
		
		for (int i=0; i<filas; i++)
			for (int j=0; j<columnas; j++)
				suma.set(i,j, this.get(i,j)+constant);

		return suma;
	}

	// Resta de matrices: A-B

	public Matrix subtract (Matrix other)
	{
		int filas = rows();
		int columnas = columns();
		Matrix result = null;

		if (this.rows() == other.rows() && this.columns() == other.columns()) {

			result = createMatrix(filas, columnas);
			
			for (int i=0; i < filas; i++)
				for (int j=0; j < columnas; j++)
					result.set(i,j, this.get(i,j)-other.get(i,j));
		}

		return this;
	}

	// Multiplicación de matrices: A*B

	public Matrix multiply(Matrix other) 
	{
		int i, j, k;
		double sum;
		Matrix result = null;

		if (this.columns() == other.rows()) {

			result = createMatrix(this.rows(), other.columns());

			for (i = 0; i < this.rows(); i++)
				for (j = 0; j < other.columns(); j++) {

					sum = 0;

					for (k = 0; k < this.columns(); k++)
						sum += this.get(i,k) * other.get(k,j);
					
					result.set(i,j,sum);
				}
		}

		return result;
	}

	public Matrix multiply(double constant) 
	{
		int i, j;
		int filas = rows();
		int columnas = columns();
		
		Matrix result = createMatrix(filas, columnas);

		for (i = 0; i < filas; i++)
			for (j = 0; j < columnas; j++)
				result.set(i,j, constant * this.get(i,j));

		return result;
	}

	public Matrix divide(double constant) 
	{
		int i, j;
		int filas = rows();
		int columnas = columns();
		
		Matrix result = createMatrix(filas, columnas);

		for (i = 0; i < filas; i++)
			for (j = 0; j < columnas; j++)
				result.set(i,j, this.get(i,j)/constant );

		return result;
	}
	
	// Exponenciación
	
	public Matrix power (int n)
	{
		Matrix result = null;
		
		if (rows()==columns()) {
			
			if (n>1) {
				
				Matrix half = this.power(n/2);
				
				if (n%2==1)
					result = half.multiply(half).multiply(this);
				else
					result = half.multiply(half);
				
			} else if (n==1) {
				result = this;
			} else if (n==0) {
				result = createIdentity(rows());
			} else if (n<0) {
				result = this.power(-n).inverse();
			}
		}
		
		
		return result;
	}

	// Suma de los coeficientes de la diagonal (Traza)

	public double trace() 
	{
		int i;
		int filas = rows();
		int columnas = columns();
		double result = 0;

		if (filas == columnas)
			for (i = 0; i < filas; i++)
				result += this.get(i,i);

		return result;
	}

	// Producto de los coeficientes de la diagonal

	public double diagonalProduct()
	{
		int i;
		int filas = rows();
		int columnas = columns();
		double result = 1;

		if (filas == columnas)
			for (i = 0; i < filas; i++)
				result *= this.get(i,i);

		return result;
	}

	
	/**
	 * LU decomposition
	 * @param A Square matrix (n x n)
	 * @param P Permutation vector (n x 1)
	 * @return Number of row exchanges (-1 when the matrix is singular) + A is now an LU matrix
	 */
	private int LU (Matrix A, Vector P) 
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

	/* ----------------------------------------------------------------------- */
	/* Substitución hacia atrás */
	/* Entrada: A = Matriz LU cuadrada n x n */
	/* B = Matriz n x 1 ( se sobreescribe) */
	/* X = Resultado de AX=B */
	/* P = Permutación (tras llamar a Descomposicion LU) */

	private void backwardsSubstitution (Matrix A, Vector B, Matrix X, Vector P, int xcol) 
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

	/* ----------------------------------------------------------------------- */
	/* Sistema de ecuaciones lineales AX = c                                   */
	/* ----------------------------------------------------------------------- */

	public Vector system (Vector c)
	{
		Matrix A = createMatrix(this);
		Vector B = new Vector(c);
		Vector X = (new Vector(rows())).transpose();
		Vector P = new Vector(rows());

		LU(A, P);
		backwardsSubstitution(A, B, X, P, 0);

		A = null;
		B = null;
		P = null;

		return X;
	}

	/* ----------------------------------------------------------------------- */
	/* Inversa de una matriz                                                   */
	/* ----------------------------------------------------------------------- */

	public Matrix inverse()
	{
		int i;
		int n = rows();
		int p;
		Matrix A = createMatrix(this);
		Vector B = new Vector(n);
		Vector P = new Vector(n);
		Matrix C = null;

		p = LU(A, P); // LU decomposition

		if (p != -1) {

			C = createMatrix(n, n);

			for (i = 0; i < n; i++) {
				B.zero();
				B.set(i,1);
				backwardsSubstitution(A, B, C, P, i);
			}
		}

		A = null;
		B = null;
		P = null;

		return C;
	}

	// Anula todos los coeficientes de una matriz

	public void zero() 
	{
		int i, j;
		int filas = rows();
		int columnas = columns();

		for (i = 0; i < filas; i++)
			for (j = 0; j < columnas; j++)
				set(i,j,0);
	}

	/* ----------------------------------------------------------------------- */
	/* Determinante de una matriz A                                            */
	/* ----------------------------------------------------------------------- */

	private static final double sign[] = { 1.0, -1.0 };

	public double determinant() 
	{
		Matrix A;
		Vector P;
		int i, j, n;
		double result = 0.0;

		n = rows();
		A = createMatrix(this);
		P = new Vector(n);

		i = LU(A, P); // LU decomposition

		if (i!=-1) {  // non-singular matrix
			
			// |A| = |L||U||P|
			// |L| = 1,
			// |U| = multiplication of the diagonal
			// |P| = +-1

			result = 1.0;

			for (j = 0; j < n; j++)
				result *= A.get( (int)P.get(j), j);

			result *= sign[i % 2];
		}

		A = null;
		P = null;

		return result;
	}

	/* ----------------------------------------------------------------------- */
	/* Menor de Aij                                                            */

	public double minor(int i, int j) 
	{
		Matrix S = this.submatrix(i, j);

		return S.determinant();
	}

	/* ----------------------------------------------------------------------- */
	/* Cofactor de Aij                                                         */

	public double cofactor(int i, int j) 
	{
		return sign[(i + j) % 2] * get(i,j) * minor(i, j);
	}

	/* ----------------------------------------------------------------------- */
	/* Matriz simétrica n x n de Toeplitz a partir de un vector n x 1 */

	public Matrix toeplitz(Matrix R) 
	{
		int i, j, n;
		Matrix T;

		n = R.rows();
		T = createMatrix(n, n);

		for (i = 0; i < n; i++)
			for (j = 0; j < n; j++)
				T.set (i,j, R.get(Math.abs(i - j),0));

		return T;
	}

	/* ----------------------------------------------------------------------- */
	/* Algoritmo de Levinson-Durbin                                            */
	/*                                                                         */
	/* Resolución de un sistema de ecuaciones lineales de la forma:            */
	/*                                                                         */
	/* | v0 v1 v2 .. vn-1 | | a1 |   | v1 |                                    */
	/* | v1 v0 v1 .. vn-2 | | a2 |   | v2 |                                    */
	/* | v2 v1 v0 .. vn-3 | | a3 | = | .. |                                    */
	/* |        ...       | | .. |   | .. |                                    */
	/* | vn-1 vn-2 ..  v0 | | an |   | vn |                                    */
	/*                                                                         */
	/* - A es una matriz simétrica de Toeplitz                                 */
	/* - R = Matriz (v0, v1, ... vn) (dim (n+1) x 1)                           */
	/* - Devuelve x (de Ax = B)                                                */
	/* ----------------------------------------------------------------------- */

	public Vector LevinsonDurbin (double v[]) 
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

		X = new Vector(p);

		for (i = 0; i < p; i++)
			X.set(i, -A[i+1][p]);

		A = null;
		W = null;
		K = null;
		E = null;

		return X;
	}

	/* ----------------------------------------------------------------------- */
	/* Algoritmo de Levinson-Durbin:                                           */
	/* Resuelve el sistema de ecuaciones Ax=B de la forma                      */
	/*                                                                         */
	/* | v0 v1 v2 .. vn-1 | | x1 |   | v1 |                                    */
	/* | v1 v0 v1 .. vn-2 | | x2 |   | v2 |                                    */
	/* | v2 v1 v0 .. vn-3 | | x3 | = | .. |                                    */
	/* |        ...       | | .. |   | .. |                                    */
	/* | vn-1 vn-2 ..  v0 | | xn |   | vn |                                    */
	/*                                                                         */
	/* ----------------------------------------------------------------------- */

	public Matrix LevinsonDurbinSystem (Matrix A, Vector B) 
	{
		double v[];
		Vector X;
		int i, n;

		n = A.rows();
		v = new double[n+1];

		for (i = 0; i < n; i++)
			v[i] = A.get(i,0);

		v[n] = B.get(n-1,0);
		
		X = LevinsonDurbin(v);

		v = null;

		return X;
	}
	
	
	// Matrix equality
	
	@Override
	public boolean equals (Object obj)
	{
		Matrix other;
		
		if (this==obj) {
			
			return true;
			
		} else if (obj instanceof Matrix) {
			
			other = (Matrix) obj;
			
			if ( this.columns()!=other.columns() )
				return false;
			
			if ( this.rows()!=other.rows() )
				return false;
			
			for (int i=0; i<rows(); i++)
				for (int j=0; j<rows(); j++)
					if ( this.get(i,j) != other.get(i,j) )
						return false;
			
			return true;
		
		} else {
			return false;
		}
	}
	
	@Override 
	public int hashCode ()
	{
		return this.toString().hashCode(); 
	}	

	/* --------------- */
	/* Salida estándar */
	/* --------------- */

	@Override
	public String toString() 
	{
		return toString( "[", "]\n", " " );
	}

	public String toString ( String rowPrefix, String rowSuffix, String delimiter) 
	{
		int i,j;
		StringBuffer buffer = new StringBuffer();
		
		for (i = 0; i < rows(); i++) {

			buffer.append(rowPrefix);
			buffer.append(get(i,0));

			for (j = 1; j < columns(); j++) {
				buffer.append(delimiter);
				buffer.append(get(i,j));
			}

			buffer.append(rowSuffix);
		}
			
		return buffer.toString();
	}	
	
	
}
