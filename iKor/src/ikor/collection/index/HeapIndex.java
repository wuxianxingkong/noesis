package ikor.collection.index;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2014
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org


/**
 * Dynamic index using heaps.
 * 
 * CPU requirements:
 * - Insertion: Amortized O(log n)
 * - Deletion by index: O(log n)
 * - Deletion by content: O(n)
 * - Access by index: O(1)
 * - Access by content: O(n)
 * 
 * Memory requirements:
 * - Space: O(n)
 * - Fragmentation: O(1)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 */

public class HeapIndex implements Index
{
    private int     currentSize; // actual number of elements in the array
    private int[]   values;      // element values 

    private final static int INITIAL_CAPACITY = 4;  // 16
	
    // Constructor
    
    public HeapIndex ()
    {
    	this.currentSize = 0;
    	
    	resizeArray(INITIAL_CAPACITY);
    }
    
    // Dynamic size
    
	private final void resizeArray (int capacity) 
	{
		int[] oldValues = this.values;

		this.values = new int[capacity]; 

		if (oldValues!=null)
			System.arraycopy(oldValues,0,values,0,oldValues.length);
	}


	// Accessors & Mutators
    
	public int get (int position) 
	{
		return values[position];
	}


	// Collection size
    
	public int size() 
	{
		return currentSize;
	}
	
	// O(n) implementation

	public int index (int value) 
	{
		for (int i=0; i<size(); i++) 
			if (get(i)==value)
				return i;

		return -1;
	}

	public boolean contains (int value) 
	{
		return index(value)!=-1;
	}

	
	// Set = remove + add

	public void set(int index, int value) 
	{
		if ((index>=0) && (index<values.length)) {
			remove(index);
			add(value);
		}
	}
	
	// Heap
	//
	// index  0 1 2 3 4 5 6 7 8...
	// parent - 0 0 1 1 2 2 3 3...
	
	protected int parent (int k)
	{
		return (k-1)/2; 
	}
	
	protected int leftChild (int k)
	{
		return 2*k+1;
	}
	
	protected int rightChild (int k)
	{
		return 2*k+2;
	}
	
	// Bottom-up heapify
	
	private void swim(int k) 
	{
		int tmp;
		int parent = parent(k);
		
		while ( (k>1) && values[parent]>values[k]) {
			tmp = values[k];
			values[k] = values[parent];
			values[parent] = tmp;
			k = parent;
			parent = parent(k);
		}
	}	
	
	// Up-down
	
	private void sink(int k) 
	{
		int tmp;
		boolean finished = false;
	
		while ( leftChild(k)<currentSize && !finished)  {
			int j = leftChild(k);
			
			if ( (j<currentSize-1) && (values[j]>values[j+1])) 
				j++;
			
			if (values[k]>values[j]) {
				tmp = values[k];
				values[k] = values[j];
				values[j] = tmp;
			} else {
				finished = true;
			}

			k = j;
		}
	}
	
	// Add at the end of the array...
	
	public boolean add (int value) 
	{
		int last = currentSize;
		
		if (values.length<=currentSize)
			resizeArray (2*values.length);
		
		values[last] = value;
		currentSize++;
		
		swim(last);

		return true;
	}
	

	public int remove (int index) 
	{
		int value = 0;
		
		if ((index>=0) && (index<currentSize)) {
			value = values[index];
			values[index] = values[currentSize-1];
			currentSize--;
			
			sink(index);
		}
		
		return value;
	}

	// WARNING! O(n) implementation
	
	public boolean removeValue (int value) 
	{
		int position = index(value);
		
		if (position!=-1) {
		   remove(position);
		   return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int[] values() 
	{
		int v[] = new int[currentSize];
		
		for (int i=0; i<v.length; i++)
			v[i] = values[i];
		
		return v;
	}	

	public String toString ()
	{
		String str = "";
		
		for (int i=0; i<currentSize; i++)
			str += " "+values[i]; 
		
		return str;
	}
}
