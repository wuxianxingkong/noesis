package ikor.math;

// Title:       Matrix factory
// Version:     2.0
// Copyright:   1998-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

public class MatrixFactory 
{
	// Factory methods
	
	public static final Matrix create (int rows, int columns)
	{
		return new DenseMatrix(rows, columns);
	}

	public static final Matrix create (int rows, int columns, double value)
	{
		Matrix matrix = new DenseMatrix(rows, columns);

		for (int i=0; i<rows; i++)
			for (int j=0; j<columns; j++)
				matrix.set(i,j, value);
		
		return matrix;
	}	
	
	public static final Matrix create (Matrix original) 
	{
		int filas = original.rows();
		int columnas = original.columns();
		
		Matrix matrix;
		
		if (original instanceof DenseMatrix)
			matrix = new DenseMatrix(filas, columnas);
		else // original instanceof SparseMatrix
			matrix = new SparseMatrix(filas,columnas);
			

		for (int i=0; i<filas; i++)
			for (int j=0; j<columnas; j++)
				matrix.set(i,j, original.get(i,j));
		
		return matrix;
	}	

	public static final Matrix create (double data[][]) 
	{
		int rows = data.length;
		int columns = data[0].length;
		
		Matrix matrix = new DenseMatrix(rows, columns);

		for (int i=0; i<rows; i++)
			for (int j=0; j<columns; j++)
				matrix.set(i,j, data[i][j]);
		
		return matrix;
	}	
	
	// Vectors
	
	public static final Vector createVector (int n)
	{
		return new DenseVector(n);
	}
	
	public static final Vector createVector (Vector v)
	{
		return new DenseVector(v);
	}
	
	public static final Vector createVector (double v[])
	{
		Vector vector = new DenseVector(v.length);
		
		for (int i=0; i<v.length; i++)
			vector.set(i, v[i]);
		
		return vector;
	}	
	
	// Special matrices

	/**
	 * NxN identity matrix
	 * @param n Matrix size
	 * @return Identity matrix
	 */
	public static final Matrix createIdentity (int n)
	{
		Matrix identity = new SparseMatrix(n,n);

		for (int i=0; i<n; i++)
			identity.set(i,i,1);

		return identity;
	}

	/**
	 * Symmetric NxN Toeplitz matrix given an Nx1 vector
	 * @param v Vector
	 * @return Toeplitz matrix
	 */
	public Matrix createToeplitz (Vector v) 
	{
		int i, j, n;
		Matrix T;

		n = v.size();
		T = MatrixFactory.create(n, n);

		for (i = 0; i < n; i++)
			for (j = 0; j < n; j++)
				T.set (i,j, v.get(Math.abs(i - j)));

		return T;
	}
	

}
