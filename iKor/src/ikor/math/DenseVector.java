package ikor.math;

// Title:       Dense vector ADT
// Version:     2.0
// Copyright:   1998-2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Dense vector implementation.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class DenseVector extends Vector 
{
	private double  data[];
	
	// Constructors
	
	public DenseVector (int size)
	{
		this.data = new double[size];
	}
	
	public DenseVector (Vector original)
	{
		super(original.isTransposed());
		
		this.data = new double[original.size()];
		
		for (int i=0; i<data.length; i++)
			this.data[i] = original.get(i);
	}

	public DenseVector (double[] data)
	{
		this.data = data;
	}

	// Vector dimension

	@Override
	public int size()
	{
		return data.length;
	}
	
	
	// Accessors & mutators
	
	public double get (int i)
	{
		return data[i];
	}
	
	public void set (int i, double value)
	{
		data[i] = value;
	}
}
