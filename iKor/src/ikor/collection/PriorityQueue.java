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
	public T peek ();
	
	public T poll ();
	
	public Comparator comparator ();

}
