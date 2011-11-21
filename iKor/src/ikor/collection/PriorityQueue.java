package ikor.collection;

//Title:       iKor Collection Framework
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal Galiano
//E-mail:      berzal@acm.org

import java.util.Comparator;

/**
 * Generic priority queue interface.
 * 
 * @author Fernando Berzal
 */
public interface PriorityQueue<T> extends MutableCollection<T> 
{
	/**
	 * Retrieves, but does not remove, the head of this queue.
	 * 
	 * @return head of this queue, null if this queue is empty.
	 */	
	public T peek ();
	
	/**
	 * Retrieves and removes the head of this queue.
	 *   
	 * @return head of this queue, null if this queue is empty.
	 */	
	public T get ();
	
	/**
	 * Returns the comparator used to order this collection,
	 * or null if this collection is sorted according to its elements natural ordering (using Comparable).
	 */	
	public Comparator comparator ();

}
