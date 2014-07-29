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
	private SparseVector rows[];
	private SparseVector columns[];
	
	public SparseMatrix (int rows, int columns)
	{
		this.rows = new SparseVector[rows];
		this.columns = new SparseVector[columns];
	}

	@Override
	public int rows() 
	{
		return rows.length;
	}

	@Override
	public int columns() 
	{
		return columns.length;
	}

	@Override
	public double get(int i, int j) 
	{
		if (rows[i]!=null)
			return rows[i].get(j);
		else
			return 0.0;
	}

	@Override
	public void set(int i, int j, double v) 
	{
		if (rows[i]==null)
			rows[i] = new SparseVector(columns());

		if (columns[j]==null)
			columns[j] = new SparseVector(rows());
		
		rows[i].set(j, v);
		columns[j].set(i, v);
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
		if (rows[i]!=null) {
		
			return rows[i];

		} else {

			if (emptyRow==null)
				emptyRow = new SparseVector(columns());

			return emptyRow;
		}
	}
	

	// Column vector

	private SparseVector emptyColumn;
	
	/**
	 * Get a complete column in the matrix
	 * 
	 * @param j Column index
	 * @return Column vector
	 */
	@Override
	public Vector getColumn (int j)
	{
		if (columns[j]!=null) {
		
			return columns[j];

		} else {

			if (emptyColumn==null)
				emptyColumn = new SparseVector(rows());

			return emptyColumn;
		}
	}	
	
}
