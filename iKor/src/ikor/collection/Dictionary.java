package ikor.collection;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

/**
* Generic dictionary interface.
* 
* In contrast to the standard Java Collections Framework, mutable collections
* are separated from the standard immutable collection interface (as in COCOA
* for Mac OS X and iOS). 
* 
* @author Fernando Berzal (berzal@acm.org)
*/
public interface Dictionary<K,V> extends ReadOnlyDictionary<K,V>, Collection<K>
{
	/**
	 * Sets a dictionary entry.
	 * @param key key with which the specified value is to be associated
	 * @param value value to be associated with the specified key
	 * @return the previous value associated with key, or null if there was no mapping for key. (A null return can also indicate that the map previously associated null with key.)
	 */
	public V set (K key, V value);
}
