package ikor.math;

// Title:       Matrix ADT
// Version:     2.0
// Copyright:   1998-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Matrix ADT. Abstract base class for matrices.
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
	// Matrix dimensions

	/**
	 * Matrix size
	 * 
	 * @return Number of rows times number of columns
	 */
	public int size() 
	{
		return rows() * columns();
	}
	
	/**
	 * Matrix rows
	 * 
	 * @return Number of rows
	 */
	public abstract int rows(); 

	/**
	 * Matrix columns
	 * 
	 * @return Number of columns
	 */
	public abstract int columns(); 
	
	
	// Accessors & mutators
	
	/**
	 * Access to a matrix coefficient
	 * 
	 * @param i Row index
	 * @param j Column index
	 * @return Value at (i,j)
	 */
	public abstract double get(int i, int j); 

	/**
	 * Set a matrix coefficient
	 * 
	 * @param i Row index
	 * @param j Column index
	 * @param v Value
	 */
	public abstract void set(int i, int j, double v);
	
	
	/**
	 * Get matrix coefficient array
	 * 
	 * @return Bidimensional array containing the matrix coefficients.
	 */
	
	public double[][] getArray ()
	{
		int m = rows();
		int n = columns();
		double[][] array = new double[m][n];
		
		for (int i=0; i<m; i++)
			for (int j=0; j<n; j++)
				array[i][j] = get(i, j);
		
		return array;
	}

	/**
	 * Set matrix coefficients
	 * 
	 * @param v Coefficient values
	 */
	public void set (double v[][])
	{
		if (v.length==rows()) {
			for (int i=0; i<v.length; i++) {
				setRow(i, v[i]);
			}
		}		
	}
	
	/**
	 * Clear all the matrix coefficients (i.e. sets them to 0).
	 */
	public void zero() 
	{
		int i, j;
		int filas = rows();
		int columnas = columns();

		for (i = 0; i < filas; i++)
			for (j = 0; j < columnas; j++)
				set(i,j,0);
	}

	
	// Matrix rows
	
	/**
	 * Set a complete row in the matrix
	 * 
	 * @param i Row index
	 * @param v Row values
	 */
	public final void set (int i, double v[])
	{
		setRow(i,v);
	}

	/**
	 * Get a complete row in the matrix
	 * 
	 * @param i Row index
	 * @return Row vector
	 */
	public final Vector getRow (int i)
	{
		return new RowVector(i);
	}
	
	public class RowVector extends Vector
	{
		private int row;
		
		public RowVector(int row)
		{
			this.row = row;
		}
		
		@Override
		public int size() 
		{
			return columns();
		}

		@Override
		public double get(int i) 
		{
			return Matrix.this.get(row,i);
		}

		@Override
		public void set(int i, double value) 
		{
			Matrix.this.set(row,i,value);
		}		
	}
	
	/**
	 * Set a complete row in the matrix
	 * 
	 * @param i Row index
	 * @param v Row values
	 */	
	public final void setRow (int i, double v[])
	{
		if (v.length==columns()) {
			for (int j=0; j<v.length; j++) {
				Matrix.this.set (i, j, v[j]);
			}
		}
	}

	/**
	 * Set a complete row in the matrix with a given value
	 * 
	 * @param i Row index
	 * @param v Value for every row coefficient
	 */
	public final void setRow (int i, double v)
	{
		for (int j=0; j<columns(); j++) {
			Matrix.this.set (i, j, v);
		}
	}

	// Matrix columns
	
	/**
	 * Get a complete column in the matrix
	 * 
	 * @param i Column index
	 * @return Column vector
	 */
	public final Vector getColumn (int j)
	{
		return new ColumnVector(j);
	}
	
	public class ColumnVector extends Vector
	{
		private int column;
		
		public ColumnVector(int column)
		{
			this.column = column;
		}
		
		@Override
		public int size() 
		{
			return rows();
		}

		@Override
		public double get(int i) 
		{
			return Matrix.this.get(i,column);
		}

		@Override
		public void set(int i, double value) 
		{
			Matrix.this.set(i,column,value);
		}		
	}

	/**
	 * Set a complete column in the matrix
	 * 
	 * @param i Column index
	 * @param v Column values
	 */	
	public final void setColumn (int j, double v[])
	{
		if (v.length==rows()) {
			for (int i=0; i<v.length; i++) {
				Matrix.this.set (i, j, v[i]);
			}
		}		
	}

	/**
	 * Set a complete column in the matrix with a given value
	 * 
	 * @param i Column index
	 * @param v Value for every column coefficient
	 */
	public final void setColumn (int j, double v)
	{
		for (int i=0; i<rows(); i++) {
			Matrix.this.set (i, j, v);
		}
	}

	
	
	// Matrix operations

	/**
	 * Transposed matrix
	 * 
	 * ref. http://en.wikipedia.org/wiki/Transposed_matrix
	 * 
	 * @return Transposed matrix
	 */
	public Matrix transpose() 
	{
		int i, j;
		int filas = rows();
		int columnas = columns();
		Matrix t = MatrixFactory.create(columnas, filas);

		for (i = 0; i < columnas; i++)
			for (j = 0; j < filas; j++)
				t.set(i,j, this.get(j,i));

		return t;
	}
	
	/**
	 * Submatrix obtained by deleting the i-th row and the j-th column
	 * 
	 * @param i Row index
	 * @param j Column index
	 * @return Submatrix
	 * @pre (filas>1) && (i>=0) && (i<filas) && (columnas>1) && (j>=0) && (j<columnas)
	 */
	public Matrix submatrix(int i, int j) 
	{
		int x, y, xS, yS;
		int filas = rows();
		int columnas = columns();
		Matrix S = MatrixFactory.create(filas - 1, columnas - 1);

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

	
	/**
	 * Matrix addition
	 * @param other Matrix to be added
	 * @return Result (this+other)
	 */
	public Matrix add (Matrix other) 
	{
		int i, j;
		int filas = rows();
		int columnas = columns();
		Matrix suma = null;

		if (this.rows() == other.rows() && this.columns() == other.columns()) {

			suma = MatrixFactory.create(filas, columnas);

			for (i = 0; i < filas; i++)
				for (j = 0; j < columnas; j++)
					suma.set(i,j, this.get(i,j)+other.get(i,j));
		}

		return suma;
	}

	
	/**
	 * Add a constant to a matrix A
	 * 
	 * @param constant c
	 * @return Result (A+c)
	 */
	public Matrix add (double constant) 
	{
		int filas = rows();
		int columnas = columns();
		
		Matrix suma = MatrixFactory.create(filas,columnas);
		
		for (int i=0; i<filas; i++)
			for (int j=0; j<columnas; j++)
				suma.set(i,j, this.get(i,j)+constant);

		return suma;
	}


	/**
	 * Matrix subtraction
	 * 
	 * @param other Matrix to be subtracted
	 * @return Result (this-other)
	 */
	public Matrix subtract (Matrix other)
	{
		int filas = rows();
		int columnas = columns();
		Matrix result = null;

		if (this.rows() == other.rows() && this.columns() == other.columns()) {

			result = MatrixFactory.create(filas, columnas);
			
			for (int i=0; i < filas; i++)
				for (int j=0; j < columnas; j++)
					result.set(i,j, this.get(i,j)-other.get(i,j));
		}

		return result;
	}


	/**
	 * Matrix multiplication
	 * 
	 * @param other Matrix to be multiplied with
	 * @return Multiplication result (this*other)
	 */
	public Matrix multiply(Matrix other) 
	{
		int i, j, k;
		double sum;
		Matrix result = null;

		if (this.columns() == other.rows()) {

			result = MatrixFactory.create(this.rows(), other.columns());

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

	/**
	 * Multiply a matrix A by a constant
	 * 
	 * @param constant c
	 * @return Result (A*c)
	 */
	public Matrix multiply(double constant) 
	{
		int i, j;
		int filas = rows();
		int columnas = columns();
		
		Matrix result = MatrixFactory.create(filas, columnas);

		for (i = 0; i < filas; i++)
			for (j = 0; j < columnas; j++)
				result.set(i,j, constant * this.get(i,j));

		return result;
	}
	
	/**
	 * Divide a matrix A by a constant
	 *  
	 * @param constant c
	 * @return Result (A/c)
	 */
	public Matrix divide(double constant) 
	{
		int i, j;
		int filas = rows();
		int columnas = columns();
		
		Matrix result = MatrixFactory.create(filas, columnas);

		for (i = 0; i < filas; i++)
			for (j = 0; j < columnas; j++)
				result.set(i,j, this.get(i,j)/constant );

		return result;
	}
	
	
	/**
	 * Power of the matrix using log(n) matrix multiplications
	 * 
	 * ref. http://en.wikipedia.org/wiki/Matrix_multiplication#Operations_derived_from_the_matrix_product
	 * 
	 * @param n Power (n>0) or n-th root (n<0)
	 * 
	 * @return Power of the matrix
	 */
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
				result = MatrixFactory.createIdentity(rows());
			} else if (n<0) {
				result = this.power(-n).inverse();
			}
		}
		
		
		return result;
	}

	/**
	 * Matrix diagonal
	 * 
	 * @return Vector corresponding to the diagonal
	 */
	public Vector getDiagonal ()
	{
		int n;
		Vector diagonal=null;
		
		if (rows()==columns()) {
			n = rows();
			diagonal = MatrixFactory.createVector(n);
			
			for (int i=0; i<n; i++)
				diagonal.set(i, get(i,i));
		}
		
		return diagonal;
	}
	
	/**
	 * Matrix trace, i.e. the sum of the elements on the main diagonal.
	 * 
	 * ref. http://en.wikipedia.org/wiki/Matrix_trace
	 * 
	 * @return Trace
	 */
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

	/**
	 * Diagonal product.
	 * 
	 * @return Product of the elements on the main diagonal.
	 */
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
	 * Inverse matrix.
	 * 
	 * ref. http://en.wikipedia.org/wiki/Inverse_matrix
	 * 
	 * @return Inverse of a square matrix (null if the matrix is singular)
	 */
	
	public Matrix inverse()
	{
		int i;
		int n = rows();
		int p;
		Matrix A = MatrixFactory.create(this);
		Vector B = MatrixFactory.createVector(n);
		Vector P = MatrixFactory.createVector(n);
		Matrix C = null;

		p = MatrixUtilities.LU(A, P);  // LU decomposition

		if (p != -1) {

			C = MatrixFactory.create(n, n);

			for (i = 0; i < n; i++) {
				B.zero();
				B.set(i,1);
				MatrixUtilities.backwardsSubstitution(A, B, C, P, i);
			}
		}

		A = null;
		B = null;
		P = null;

		return C;
	}

	
	
	// Matrix determinant

	private static final double sign[] = { 1.0, -1.0 };

	/**
	 * Determinant of a square matrix, denoted as det(A) or |A|.
	 * 
	 * ref. http://en.wikipedia.org/wiki/Determinant
	 * 
	 * @return Determinant
	 */
	
	public double determinant() 
	{
		Matrix A;
		Vector P;
		int i, j, n;
		double result = 0.0;

		n = rows();
		A = MatrixFactory.create(this);
		P = MatrixFactory.createVector(n);

		i = MatrixUtilities.LU(A, P); // LU decomposition

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

	
	/**
	 * Minor: The minor of the entry in the i-th row and j-th column 
	 * (also known as the (i,j) minor, or a first minor[1]) is the determinant 
	 * of the submatrix formed by deleting the i-th row and j-th column. 
	 * This number is often denoted Mi,j.
	 * 
	 * ref. http://en.wikipedia.org/wiki/Minor_(linear_algebra)
	 * 
	 * @param i Row index
	 * @param j Column index
	 * @return (i,j) minor
	 */

	public double minor(int i, int j) 
	{
		Matrix S = this.submatrix(i, j);

		return S.determinant();
	}

	
	/**
	 * Cofactor: The (i,j) cofactor is obtained by multiplying the minor by (-1)^{i+j}.
	 * 
	 * ref. http://en.wikipedia.org/wiki/Cofactor_(linear_algebra)
	 * 
	 * @param i Row index
	 * @param j Column index
	 * @return (i,j) cofactor
	 */
	
	public double cofactor(int i, int j) 
	{
		return sign[(i + j) % 2] * minor(i, j);
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

	// Hash code
	
	@Override 
	public int hashCode ()
	{
		return this.toString().hashCode(); 
	}	

	// Standard output
	
	@Override
	public String toString() 
	{
		return toString( "[", "]\n", " " );
	}
	
	/**
	 * Standard output
	 * @param rowPrefix Row preffix, e.g. "["
	 * @param rowSuffix Row suffix, e.g. "]\n"
	 * @param delimiter Delimiter, e.g. " "
	 * @return
	 */

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
