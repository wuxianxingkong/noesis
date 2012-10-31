package sandbox.adt;

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

public class SortedSkipList extends SkipList 
{	
    // Constructor
    
    public SortedSkipList ()
    {
    	super();
    }
    

	// O(log n) operations through skip lists
	
	private void addSkip (int position)
	{
		int diff = (totalSize-1)-position;
		
		if (position!=currentSize-1)  // Update index if not at the current end
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


	public int set (int index, int value) 
	{
		System.out.println(this);
		removeAt(index);
		
		System.out.println(this);
		add(value);
		
		System.out.println(this);
		return totalSize-1;
	}
	

	// Add at the end of the array...
	
	public boolean add (int value) 
	{
		int position = search(value,0,currentSize-1);
				
		super.add(value);
		
		addSkip(position);

		return true;
	}
	

	// O(log n) implementation
	
	public boolean remove (int value) 
	{
		int position = search(value,0,currentSize-1);
		
		if ((position<currentSize) && (value==get(position)))
			return removeAt(position);
		else
			return false;
	}

}
