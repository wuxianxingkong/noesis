package sandbox.adt;

// Title:       Array collection ADT
// Version:     2.0
// Copyright:   2006
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.ArrayList; 
import java.util.Set;


// Collection ADT
// --------------

// Version history
// 1.0 - May'2004 - Set of values
// 2.0 - Dec'2006 - Generics

/**
 * Collection ADT
 *
 * @author Fernando Berzal
 */
public class Array<T> implements MutableCollection<T>
{
    /**
     * Collection. ArrayList is roughly equivalent to Vector, except that it is unsynchronized
     */
    ArrayList<T> items = new ArrayList<T>();
    
    /**
     * Constructor
     */
    public Array ()
    {   
    }

    /**
     * Copy constructor
     * @param collection An existing collection
     */
    public Array (Collection<T> collection)
    {
    	int i;
   	
    	for (i=0; i<collection.size(); i++)
    		this.add(collection.get(i));   			
    }
    
    /**
     * "Copy" constructor
     * @param set An existing set
     */
    
    public Array (Set<T> set)
    {
    	for (T t: set) {
    		this.add(t);
    	}
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
     * Checks if the collection contains a given object
     * @param object Object
     */
    
    public int indexOf (T object)
    {
        return items.indexOf(object);
    }   

    /**
     * Adds an object to the collection
     * @param object Object to be added
     */
    
    public void add (T object)
    {
        items.add(object);
    }
    
    /**
     * Removes an element from the collection
     * @param object Object to be removed
     */
    
    public void remove (T object)
    {
        items.remove(object);
    }
    
    /**
     * Removes an element from the collection 
     * @param i Element index
     */
    
    public void remove (int i)
    {
        items.remove(i);
    }
    
    
    /**
     * Gets a collection element
     * @param i Item index
     * @return Collection item
     */
    public T get (int i)
    {
        return items.get(i);
    }
    
    /**
     * Sets a collection element
     * @param i Element index
     * @param item Collection element
     */
    public void set (int i, T item)
    {
        items.set(i, item);
    }
    
    /**
     * Returns a collection with all the elements of a given type
     * @param type The type of the elements to be retrieved
     * @return The collection formed by the elements of the specified type
     */
    
    public Collection<T> get (Class type)
    {
    	int i;
    	T   element;
    	Array<T> result = new Array<T>();
    	
    	for (i=0; i<size(); i++) {
    		element = get(i);
    		
    		if (type.isInstance(element))
    			result.add(element);
    	}
    	
    	return result;   	
    }
   
    /** 
     * Standard output
     */
    
    public String toString()
    {
        String str = ""; // "[Collection]\n"; 
        
        for (int i = 0; i < size(); i++)
            str += "  " + get(i) + "\n";
        
        return str;
    }
    
}
