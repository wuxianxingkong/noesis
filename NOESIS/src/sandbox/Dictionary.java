package adt;

// Title:       Dictionary ADT
// Version:     2.0
// Copyright:   2006
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.*;

// Dictionary
// ----------

// Version history
// 1.0 - May'2004 - Set of (id,value) pairs
// 2.0 - Dec'2006 - Generics

/**
 * Dictionary ADT
 *
 * @author Fernando Berzal
 */
public class Dictionary<K,V>
{
    /**
     * Collection of dictionary entries
     */
    HashMap<K,V> map = new HashMap<K,V>();
    
    /**
     * Default constructor
     * @param params Properties (from a property file)
     * @param tag Property tag
     */
    public Dictionary ()
    {
    }    
    
    
    /**
     * Dictionary size
     * @return Number of dictionary entries in the dictionary
     */
    public int size ()
    {
        return map.size();
    }
    
    /**
     * Sets a dictionary entry
     * @param key Entry key
     * @param value Entry value
     */
    
    public void set (K key, V value)
    {
        map.put(key, value);
    }
    
    /**
     * Removes a dictionary entry
     * @param key Entry key
     */
    
    public void remove (K key)
    {
    	map.remove(key);
    }    
    
    /**
     * Gets a dictionary entry
     * @param key Entry key
     * @return Dictionary entry
     */
    public V get (K key)
    {
    	return map.get(key);
    }
    
    
    public Collection<K> getKeys ()
    {
    	Set<K>         set = map.keySet();
    	Collection<K>  result = new Array<K>(set);
    	
    	return result;
    }


    /** 
     * Standard output
     */
    
    public String toString()
    {
        String str = ""; // "[Dictionary]\n"; 
        
        Set<Map.Entry<K,V>> entries = map.entrySet();
        
        for (Map.Entry<K,V> entry: entries) {
        	str += "" + entry.getKey() + " = " + entry.getValue() + "\n";       	
        }
        
        return str;
    }
    
}
