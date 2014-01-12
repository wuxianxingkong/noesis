package ikor.collection.index;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org


/**
 * Dynamic array.
 * 
 * - Insertion: Amortized O(1)
 * - Deletion: O(n)
 * - Access by index: O(1)
 * - Access by content: O(n)
 * - Space: O(n)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 */

public class ArrayIndex implements Index
{
    private int     currentSize; // actual number of elements in the array
    private int[]   values;      // element values 

    private final static int INITIAL_CAPACITY = 4;  // 16
	
    // Constructor
    
    public ArrayIndex ()
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


	public void set(int index, int value) 
	{
		if ((index>=0) && (index<values.length))
			values[index] = value;
	}
	

	// Add at the end of the array...
	
	public boolean add (int value) 
	{
		if (values.length<=currentSize)
			resizeArray (2*values.length);
		
		values[currentSize] = value;
				
		currentSize++;

		return true;
	}
	

	public int remove (int index) 
	{
		int value = 0;
		
		if ((index>=0) && (index<currentSize)) {
			value = get(index);
			System.arraycopy(values,index+1,values,index,currentSize-1-index);
			currentSize--;
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

	public String toString ()
	{
		String str = "";
		
		for (int i=0; i<currentSize; i++)
			str += " "+values[i]; 
		
		return str;
	}
}
