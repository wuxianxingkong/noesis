package ikor.math;

// Title:       Sparse vector ADT
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org


/**
 * Sparse vector implementation.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 */

public class SparseVector extends Vector 
{
	private final static int INITIAL_ARRAY_SIZE = 4;
	
	private int size;
	private int used;
	private int index[];
	private double value[];

	
	public SparseVector (int size)
	{
		this.size = size;
		this.index = new int[INITIAL_ARRAY_SIZE];
		this.value = new double[INITIAL_ARRAY_SIZE];
		this.used = 0;
	}
	
	public void extend ()
	{
		int[] newIndex = new int[2*index.length];
		double[] newValue = new double[2*value.length];
		
		System.arraycopy(index,0,newIndex,0,index.length);
		System.arraycopy(value,0,newValue,0,value.length);
		
		this.index = newIndex;
		this.value = newValue;
	}
	
	private void insert (int pos, int ndx, double val)
	{
		if (used==index.length)
			extend();
		
		for (int i=used; i>pos; i--) {
			index[i] = index[i-1];
			value[i] = value[i-1];
		}
		
		index[pos] = ndx;
		value[pos] = val;
		used++;
		
		if (ndx>=size)
			size = ndx+1;
	}

	
	@Override
	public int size() 
	{
		return size;
	}
	
	/**
	 * Binary search, O(log n)
	 * 
	 * @param x Index value
	 * @return Index position
	 */

	private int position (int x)
	{
		int left = 0;
		int right = used-1;
		int middle = (left+right)/2;
		
		while ((left<right) && (index[middle]!=x)) {
			
			if (x<index[middle])
				right = middle - 1;
			else
				left = middle + 1;
			
			middle = (left+right)/2;
		}
		
		if ((middle<used) && (index[middle]<x)) {
			middle++;
		}

		return middle;
	}

	@Override
	public double get(int i) 
	{
		int p = position(i);
		
		if ((p>=0) && (p<used) && (index[p]==i))
			return value[p];
		else
			return 0.0;
	}

	@Override
	public void set(int i, double value) 
	{
		int p = position(i);
		
		if ((p>=0) && (p<used) && index[p]==i) {
			this.value[p] = value;
		} else if (value!=0.0) {
			insert(p,i,value);
		}
	}

}
