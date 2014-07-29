package ikor.math;

// Title:       Dense matrix ADT
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Sparse matrix implementation.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class SparseMatrix extends Matrix 
{
	private int rows;
	private int columns;
	private SparseVector vectors[];
	
	public SparseMatrix (int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		this.vectors = new SparseVector[rows];
	}

	@Override
	public int rows() 
	{
		return rows;
	}

	@Override
	public int columns() 
	{
		return columns;
	}

	@Override
	public double get(int i, int j) 
	{
		if (vectors[i]!=null)
			return vectors[i].get(j);
		else
			return 0.0;
	}

	@Override
	public void set(int i, int j, double v) 
	{
		if (vectors[i]==null)
			vectors[i] = new SparseVector(columns);
		
		vectors[i].set(j, v);
	}

	// Row vector

	private SparseVector emptyRow;
	
	/**
	 * Get a complete row in the matrix
	 * 
	 * @param i Row index
	 * @return Row vector
	 */
	@Override
	public Vector getRow (int i)
	{
		if (vectors[i]!=null) {
		
			return vectors[i];

		} else {

			if (emptyRow==null)
				emptyRow = new SparseVector(columns);

			return emptyRow;
		}
	}
	
}
