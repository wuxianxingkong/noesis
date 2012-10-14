package ikor.collection;

// Title:       Queue collection ADT
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.ArrayDeque;
import java.util.Iterator;


// Collection ADT
// --------------

// Version history
// 1.0 - Jun'2008 - Generics

/**
 * Collection ADT
 *
 * @author Fernando Berzal
 */
public class Queue<T> implements ReadOnlyCollection<T>
{
    /**
     * Collection. ArrayList is roughly equivalent to Vector, except that it is unsynchronized
     */
    ArrayDeque<T> items = new ArrayDeque<T>();
    
    /**
     * Constructor
     */
    public Queue ()
    {   
    }

    /**
     * Collection size
     * @return Number of dictionary entries in the dictionary
     */
    
    public int size ()
    {
        return items.size();
    }
    
    
    /**
     * Checks if the collection contains a given object
     * @param object Object
     */
    
    public boolean contains (T object)
    {
        return items.contains(object);
    }    
    

    /**
     * Adds an object to the collection
     * @param object Object to be added
     */
    
    public void enqueue (T object)
    {
        items.addLast(object);
    }
    
    /**
     * Removes an element from the collection
     * @param object Object to be removed
     */
    
    public T dequeue ()
    {
        return items.removeFirst();
    }

    /**
     * Retrieves, but does not remove, the element from the head of the queue
     */
    
    public T peek ()
    {
        return items.peekFirst();
    }
    
     
    /**
     * Gets a collection element
     * @param i Item index
     * @return Collection item
     */
    public T get (int i)
    {
        throw new UnsupportedOperationException("Queues do not support random access methods");
    }
   
    /** 
     * Standard output
     */
    
    public String toString()
    {
		Object[] array =  items.toArray();
        String   str = ""; // "[Collection]\n"; 
        
        for (int i = 0; i <  array.length; i++)
            str += "  " + array[i] + "\n";
        
        return str;
    }

	@Override
	public Iterator<T> iterator() {
		return items.iterator();
	}
    
}
