package ikor.math;

// Title:       Dense matrix ADT
// Version:     2.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Dense matrix implementation.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 */

public class DenseMatrix extends Matrix
{
	// Instance variables

	/* ----------------------------------------------------------------------- */
	/*                                                                         */
	/*                        Column                                           */
	/*               Row      0       1       2           n-1                  */
	/*               0    [   a0,0    a0,1    a0,2   ...  a0,n-1   ]           */
	/*               1    [   a1,0    a1,1    a1,2   ...  a1,n-1   ]           */
	/*   A   =       2    [   a2,0    a2,1    a2,2   ...  a2,n-1   ]           */
	/*                    [   ...     ...     ...    ...  ...      ]           */
	/*               m-1  [   am-1,0  am-1,1  am-1,2 ...  am-1,n-1 ]           */
	/*                                                                         */
	/* ----------------------------------------------------------------------- */

	private double datos[][];


	// Constructors
	
	protected DenseMatrix ()
	{
		this.datos = null;
	}

	/** @pre rows>0 && columns>0 */
	public DenseMatrix (int rows, int columns) 
	{
		datos = new double[rows][columns];
		// == this(rows, columns, 0);
	}

	/** @pre rows>0 && columns>0 */
	public DenseMatrix (int rows, int columns, double value) 
	{
		datos = new double[rows][columns];

		for (int i=0; i<rows; i++)
			for (int j=0; j<columns; j++)
				datos[i][j] = value;
	}

	// Constructor de copia

	public DenseMatrix (DenseMatrix origen) 
	{
		int filas = origen.rows();
		int columnas = origen.columns();
		
		datos = new double[filas][columnas];

		for (int i=0; i<filas; i++)
			for (int j=0; j<columnas; j++)
				datos[i][j] = origen.datos[i][j];
	}

	
	public DenseMatrix (double[][] data)
	{
		this.datos = data;
	}
	

	protected DenseMatrix (double[] vector)
	{
		this.datos = new double[1][];
		this.datos[0] = vector;
	}
	
	
	// Matrix dimensions
	
	public int rows() 
	{
		return datos.length;
	}

	public int columns() 
	{
		return datos[0].length;
	}

	
	// Accessors & mutators

	public double get(int i, int j) 
	{
		return datos[i][j];
	}

	public void set(int i, int j, double v) 
	{
		datos[i][j] = v;
	}	
}
