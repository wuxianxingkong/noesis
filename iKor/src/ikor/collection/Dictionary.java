package ikor.collection;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

/**
 * Generic dictionary interface.
 * 
 * "One striking design choice of the Java language designers was the exclusion 
 * of map from the collections hierarchy." (Brad Long: "Towards the design of a 
 * set-based Java collections framework," ACM SIGSOFT SEN, Volume 35 Issue 6, 
 * November 2010). In the iKor Collection Framework, dictionaries (a.k.a. maps)
 * are also collections. 
 * 
 * @author Fernando Berzal
 */
public interface Dictionary<K,V> extends Collection<K>
{
	/**
	 * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
	 * @param key the key whose associated value is to be returned
	 * @return the value to which the specified key is mapped, or null if this map contains no mapping for the key
	 */
	public V get (K key);
	
    // Collection views
	
	/**
	 * Returns the CURRENT set of keys in the dictionary.
	 */
    public Collection<K> keys(); 
    
    /**
     * Returns the CURRENT set of values in the dictionary.
     * @return
     */
    public Collection<V> values();
    
    /**
     * Returns the CURRENT set of dictionary entries.
     * @return
     */
    public Collection<Dictionary.Entry<K,V>> entries();

    
    /**
     * Generic interface for dictionary entries.
     */
    public interface Entry<K,V> 
    {
        K getKey();
        V getValue();
    }
}
