package ikor.collection;

//Title:       iKor Collection Framework
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal Galiano
//E-mail:      berzal@acm.org

import java.util.Comparator;
import java.util.Iterator;

/**
 * Priority queue.
 * 
 * @author Fernando Berzal
 */
public class DynamicPriorityQueue<T> implements PriorityQueue<T> 
{
	private static final int INITIAL_CAPACITY = 8;
	
	private java.util.PriorityQueue<T> queue;
	
	public DynamicPriorityQueue ()
	{
		this.queue = new java.util.PriorityQueue<T>(INITIAL_CAPACITY);
	}
	
	public DynamicPriorityQueue (Comparator comparator)
	{
		this.queue = new java.util.PriorityQueue<T>(INITIAL_CAPACITY, comparator);
	}
	
	/* (non-Javadoc)
	 * @see ikor.collection.MutableCollection#add(java.lang.Object)
	 */
	@Override
	public boolean add(T object) 
	{
		return queue.add(object);
	}

	/* (non-Javadoc)
	 * @see ikor.collection.MutableCollection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(T object) 
	{
		return queue.remove(object);
	}

	/* (non-Javadoc)
	 * @see ikor.collection.MutableCollection#clear()
	 */
	@Override
	public void clear() 
	{
		queue.clear();
	}

	/* (non-Javadoc)
	 * @see ikor.collection.Collection#size()
	 */
	@Override
	public int size() 
	{
		return queue.size();
	}

	/* (non-Javadoc)
	 * @see ikor.collection.Collection#contains(java.lang.Object)
	 */
	@Override
	public boolean contains(T object) 
	{
		return queue.contains(object);
	}

	/* (non-Javadoc)
	 * @see ikor.collection.Collection#iterator()
	 */
	@Override
	public Iterator<T> iterator() 
	{
		return queue.iterator();
	}

	/* (non-Javadoc)
	 * @see ikor.collection.PriorityQueue#peek()
	 */
	@Override
	public T peek() 
	{
		return queue.peek();
	}

	/* (non-Javadoc)
	 * @see ikor.collection.PriorityQueue#poll()
	 */
	@Override
	public T poll() 
	{
		return queue.poll();
	}

	/* (non-Javadoc)
	 * @see ikor.collection.PriorityQueue#comparator()
	 */
	@Override
	public Comparator comparator() 
	{
		return queue.comparator();
	}

}
