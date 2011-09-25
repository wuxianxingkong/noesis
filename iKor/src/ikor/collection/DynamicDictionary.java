package ikor.collection;

// Title:       iKor Collection Framework
// Version:     3.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.Iterator;
import java.util.HashMap;

import ikor.collection.Collection;
import ikor.collection.Dictionary;
import ikor.collection.MutableDictionary;

// Dictionary
// ----------

// Version history
// 1.0 - May'2004 - Set of (id,value) pairs
// 2.0 - Dec'2006 - Generics
// 3.0 - Mar'2011 - iKor Collection Framework

/**
 * Standard dictionary implementation, based on java.util.HashMap (not-synchronized
 * hash table based implementation of the Map interface).
 * 
 * @author Fernando Berzal
 */

public class DynamicDictionary<K,V> implements MutableDictionary<K,V>
{
	private HashMap<K,V> map = new HashMap<K,V>();
	
	// Access
	
	@Override
	public V get(K key) {
		return map.get(key);
	}
	
	// Views

	@Override
	public Collection<K> keys() 
	{
		DynamicList<K> list = new DynamicList<K>();
		
		for (K key: map.keySet()) {
			list.add(key);
		}
		
		return list;
	}

	@Override
	public Collection<V> values() 
	{
		DynamicList<V> list = new DynamicList<V>();
		
		for (V value: map.values()) {
			list.add(value);
		}

		return list;
	}

	@Override
	public Collection<Dictionary.Entry<K, V>> entries() 
	{
		DynamicList<Dictionary.Entry<K, V>> list = new DynamicList<Dictionary.Entry<K, V>>();
		
		for (java.util.Map.Entry<K,V> entry: map.entrySet()) {
			list.add(new ImmutableEntry(entry.getKey(), entry.getValue()));
		}

		return list;
	}
	
	public class ImmutableEntry implements Dictionary.Entry<K,V> 
	{
		private K key;
		private V value;
		
		protected ImmutableEntry (K key, V value)
		{
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}
	}
	
	// Collection interface

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean contains(K key) {
		return map.containsKey(key);
	}

	@Override
	public Iterator<K> iterator() {
		return map.keySet().iterator();
	}
	
	// Modification

	@Override
	public boolean add(K key) {
		map.put(key,null);
		return true;
	}

	@Override
	public boolean remove(K key) {
		map.remove(key);
		return true;
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public V set(K key, V value) {
		return map.put(key, value);
	}

    /** 
     * Standard output
     */
    
    public String toString()
    {
        String str = ""; // "[Dictionary]\n"; 
        
        Collection<Dictionary.Entry<K,V>> entries = this.entries();
        
        for (Dictionary.Entry<K,V> entry: entries) {
        	str += "" + entry.getKey() + " = " + entry.getValue() + "\n";       	
        }
        
        return str;
    }	
}
