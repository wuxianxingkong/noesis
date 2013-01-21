package ikor.collection;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2013
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

/**
 * Generic in-out collection interface for stacks, queues, and priority queues.
 * 
 * @author Fernando Berzal
 */

public interface InOutCollection<T> extends Collection<T>
{
	/**
	 * Retrieves, but does not remove, an element from the collection.
	 * 
	 * @return An element from this collection, null if the collection is empty.
	 */	
	public T peek ();
	
	/**
	 * Retrieves and removes an element from the collection (first element in queues, last element in stacks, minimum element in priority queues).
	 *   
	 * @return An element from this collection, null if the collection is empty.
	 */	
	public T get ();

}
