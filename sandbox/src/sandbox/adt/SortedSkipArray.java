package sandbox.adt;

import ikor.collection.array.SkipArray;

// TODO O(log n) removal & addition using skip lists for a sorted array... asumming it is not impossible !

/**
 * Sorted dynamic array with logical deletion, implemented as a skip-list-like data structure...
 * 
 * - Insertion: Amortized O(log n)
 * - Deletion: O(log n)
 * - Access by index: O(log n)
 * - Access by content: O(log n)
 * - Space: O(n)
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 */

public class SortedSkipArray extends SkipArray 
{	
    // Constructor
    
    public SortedSkipArray ()
    {
    	super();
    }
    

	// O(log n) operations through skip lists
	
	private void addSkip (int position)
	{
		// TODO Generic update of skip indexes
		
		int diff = (totalSize-1)-skip(position);
		
		skip[0][position] += diff; 
		
		int target = position;
		int bits = (capacity-1)-position;
		int pos;
		
		for (int level=0; level<skip.length; level++) {
			
			if ( (bits & (1<<level)) != 0)  {  // level-th LSB
				pos = target|1;
				
				if ((pos<<level)<totalSize)
			       skip[level][pos]--;
			}
			
			target>>=1;
		}		
	}

	protected void removeSkip (int n)
	{
		// TODO Generic update of skip indexes

		int target = n;
		int pos;
		
		if (target==0) {
			skip[0][0] = skip[0][1];
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
	
	// O(log n) implementation

	private int search (int value, int left, int right) 
	{
		if (left<=right) {
			
			int middle = (left+right)/2;
			int pivot = get(middle);
			
			if (pivot==value)
				return middle;
			else if (pivot>value)
				return search(value, left, middle-1);
			else
				return search(value, middle+1, right);
		
		} else {
			
			return left; // Where it should be...
		}
	}

	public boolean contains (int value) 
	{
		int position = search(value,0,currentSize-1);
		
		if (position<currentSize)
			return get(position)==value;
		else
			return false;
	}


	public void set (int index, int value) 
	{
		System.err.println(this);
		remove(index);
		System.err.println(this);
		add(value);
		System.err.println(this);
	}
	

	// Add a new element to the sorted array...
	
	public boolean add (int value) 
	{
		int position = search(value,0,currentSize-1);
				
		super.add(value);
		
		addSkip(position);

		return true;
	}
	

	// O(log n) implementation
	
	public boolean removeValue (int value) 
	{
		int position = search(value,0,currentSize-1);
		
		if ((position<currentSize) && (value==get(position))) {
			remove(position);
			return true;
		} else {
			return false;
		}
	}

}
