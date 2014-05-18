package ikor.collection;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Indexed priority queue implementation using a binary heap, 
 * based on Sedgewick's "Algorithms" [4th edition].
 * 
 * The standard implementation provides O(log n) time for the insertion  
 * get(), add(), and remove() methods; and constant time for the 
 * peek(), size(), and contains() retrieval methods.
 * 
 * BUG corrected from original remove() implementation...
 * 
 * @author Fernando Berzal
 */

public class IndexedPriorityQueue<T> implements PriorityQueue<T> 
{
	private int     capacity;  // maximum capacity
    private int     size;      // number of elements on PQ
    private int[]   pq;        // binary heap using 1-based indexing
    private int[]   qp;        // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
    private T[]     keys;      // keys[i] -> priority of i
    private Indexer    indexer;
    private Comparator comparator;


    public IndexedPriorityQueue (int capacity, Evaluator<T> evaluator, Indexer<T> indexer)
    {
    	this(capacity, new EvaluatorComparator<T>(evaluator), indexer);
    }

    
    public IndexedPriorityQueue (int capacity, Comparator<T> comparator, Indexer<T> indexer)
    {
    	this.indexer = indexer;
    	this.comparator = comparator;
    	this.capacity = capacity;
    	clear();
    }
    
	@Override
	public int add(T object) 
	{
		int ndx;
		
        if (!contains(object)) {
        	size++;
        	ndx = indexer.index(object);
        	qp[ndx]   = size;
        	pq[size]  = ndx;
        	keys[ndx] = object;
        	swim(size);	
        	return size-1;
		} else {
			return -1;
		}
	}

	@Override
	public boolean remove(T object) 
	{
		int ndx = indexer.index(object);
		int pos;
		
		if (ndx>=0) {
			
			pos = qp[ndx];
			
			exchange(pos, size--);
			swim(pos);
			sink(pos);
			
			qp[ndx] = -1;        // delete
			keys[ndx] = null;    // to help with garbage collection
			pq[size+1] = -1;     // not really needed
			return true;
			
		} else {
			return false;
		}
	}

	@Override
	public void clear() 
	{
        keys = (T[]) new Object[capacity + 1];
        pq   = new int[capacity + 1];
        qp   = new int[capacity + 1];
        size = 0;
        
        for (int i=0; i<=capacity; i++) 
        	qp[i] = -1;    	
	}

	@Override
	public int size() 
	{
		return size;
	}

	@Override
	public boolean contains(T object)
	{
		int ndx = indexer.index(object);

	    return qp[ndx] != -1;
	}

	@Override
	public T peek() 
	{
		return keys[pq[1]];
	}

	@Override
	public T get()
	{
		T   min;
		int ndx;
		
		if (size>0) {
			ndx = pq[1];
			min = keys[ndx];
			
			exchange(1, size--); 
			sink(1);
			
			qp[ndx] = -1;               // delete
			keys[pq[size+1]] = null;    // to help with garbage collection
			pq[size+1] = -1;            // not needed
			return min; 
        
		} else {
			return null;
		}
	}

	@Override
	public Comparator<T> comparator() 
	{
		return this.comparator;
	}
	
	public Indexer<T> indexer() 
	{
		return this.indexer;
	}
	
	// Iterator
	
	 /**
     * Return an iterator that iterates over all of the elements on the
     * priority queue in ascending order.
     * <p>
     * The iterator doesn't implement <tt>remove()</tt> since it's optional.
     */
	
	@Override
    public Iterator<T> iterator() 
    { 
		return new HeapIterator(); 
	}

    private class HeapIterator extends CollectionIterator<T> 
    {
        // create a new pq
        private IndexedPriorityQueue<T> copy;

        // add all elements to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() 
        {
            copy = new IndexedPriorityQueue<T>(pq.length - 1,comparator,indexer);
            
            for (int i = 1; i <= size; i++)
                copy.add(keys[pq[i]]);
        }

        public boolean hasNext()  
        {
        	return (copy.size()>0);                     
        }

        public T next() 
        {
            if (!hasNext()) 
            	throw new NoSuchElementException();
            
            return copy.get();
        }
    }	
       
	
	// Ancillary routines

    private boolean greater(int i, int j) 
    {
        return comparator.compare( keys[pq[i]], keys[pq[j]]) > 0;
    }
    
    private void exchange(int i, int j) 
    {
        int swap = pq[i]; 
        pq[i] = pq[j]; 
        pq[j] = swap;
        
        qp[pq[i]] = i; 
        qp[pq[j]] = j;
    }	
    
    private void swim(int k)  
    {
        while (k > 1 && greater(k/2, k)) {
            exchange(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) 
    {
        while (2*k <= size) {
            int j = 2*k;
            if (j < size && greater(j, j+1)) j++;
            if (!greater(k, j)) break;
            exchange(k, j);
            k = j;
        }
    }    
}
