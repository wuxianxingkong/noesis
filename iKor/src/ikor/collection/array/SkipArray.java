package ikor.collection.array;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org


/**
 * Dynamic array with logical deletion, implemented as a skip-list-like data structure...
 * 
 * - Insertion: Amortized O(1)
 * - Deletion: O(log n)
 * - Access by index: O(log n)
 * - Access by content: O(n)
 * - Space: O(3n+d)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 */

public class SkipArray implements Array
{
	protected int     capacity;    // maximum capacity
    protected int     totalSize;   // total number of elements in the array (including deleted entries)
    protected int     currentSize; // actual number of elements in the array
    protected int[][] skip;        // "Skip" lists
    protected int[]   values;      // values[i] 

    private final static int INITIAL_CAPACITY = 4;  // 16
	
    // Constructor
    
    public SkipArray ()
    {
    	this.totalSize   = 0;
    	this.currentSize = 0;
    	
    	resizeArray(INITIAL_CAPACITY);
    	resizeIndex(INITIAL_CAPACITY);
    }
    
    // Dynamic size
    
	private final void resizeArray (int capacity) 
	{
		int[]   oldValues = this.values;

		this.values   = new int[capacity]; 

		if (oldValues!=null)
			System.arraycopy(oldValues,0,values,0,oldValues.length);
	}

	private final void resizeIndex (int capacity) 
	{
		int[][] oldSkip = this.skip;
		
		this.capacity = capacity;
		
		int levels = (int) Math.round(Math.log(capacity)/Math.log(2));
		
		this.skip = new int[levels][];
		
		for (int i=0; i<levels; i++) {
		    this.skip[i] = new int[capacity>>i];
		    
		    for (int j=0; j<this.skip[i].length; j++)
		    	if (j%2 == 1)
		    		this.skip[i][j] = (1<<i);
		    
		    if ((oldSkip!=null) && (i<oldSkip.length) && (oldSkip[i]!=null))
				System.arraycopy(oldSkip[i],0,skip[i],0,oldSkip[i].length);		    	
		}
		
		if (currentSize<totalSize)
		   this.skip[levels-1][1] = totalSize+1; // Starting point for the following elements
	}

	// Accessors & Mutators
    
	public int get (int position) 
	{
		return values[skip(position)];
	}

	// O(log n) operations through skip lists
	
	protected int skip (int position) 
	{
		int target = position;
		int index = 0;
		
		for (int level=0; level<skip.length; level++) {
			index += skip[level][target];
			target >>= 1;
		}
	    
		return index;
	}
    
	
	protected void removeSkip (int n)
	{
		int target = n;
		int pos;
		
		if (target==0) {
			skip[0][0]++;
			target=1;
		}
		
		int bits = capacity - target; 
		
		for (int level=0; level<skip.length; level++) {
			
			if ( (bits & (1<<level)) != 0)  {  // level-th LSB
				pos = target|1;
				skip[level][pos]++;
			}
			
			target>>=1;
		}		
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
		int position = skip(index);
		
		if (position!=-1)
			values[position] = value;
	}
	

	// Add at the end of the array...
	
	public boolean add (int value) 
	{
		if (values.length<=totalSize)
			resizeArray (2*values.length);

		if (capacity<=currentSize)
			resizeIndex (2*capacity);
		
		values[totalSize] = value;
				
		currentSize++;
		totalSize++;

		return true;
	}
	

	public int remove (int index) 
	{
		int value = 0;
		
		if (index!=-1) {
			value = get(index);
			currentSize--;
			removeSkip(index);
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
		
		for (int level=skip.length-1; level>=0; level--) {
			for (int i=0; i<skip[level].length; i++) {
				str += " "+ skip[level][i];
			}
			str += "\n";
		}

		for (int i=0; i<currentSize; i++)
			str += " "+skip(i); 

		str += "\n";

		for (int i=0; i<totalSize; i++)
			str += " "+values[i]; 
		
		str += "\n";
		
		return str;
	}
}
