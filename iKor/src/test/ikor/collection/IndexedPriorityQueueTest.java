package test.ikor.collection;

import static org.junit.Assert.*;

import java.util.Iterator;

import ikor.collection.IndexedPriorityQueue;
import ikor.collection.Indexer;

import org.junit.Before;
import org.junit.Test;

public class IndexedPriorityQueueTest 
{

	IndexedPriorityQueue<String> queue;
	Indexer<String>              indexer;
	
	private static final String[] strings = { "it", "was", "the", "best", "of", "times", "it", "was", "the", "worst" };
	private static final int REPETITIONS = 3;
	
	@Before
	public void setUp() throws Exception 
	{
		indexer = new ArrayIndexer<String>(strings);
		queue = new IndexedPriorityQueue<String>(strings.length, String.CASE_INSENSITIVE_ORDER, indexer);
	}


	@Test
	public void testAdd() 
	{
		assertEquals(0, queue.size());
		
		for (int i=0; i<strings.length; i++)
			queue.add(strings[i]);
		
		assertEquals(strings.length-REPETITIONS, queue.size());
	}
	
	
	@Test
	public void testGet() 
	{
		for (int i=0; i<strings.length; i++)
			queue.add(strings[i]);
		
		assertEquals("best", queue.get());
		assertEquals("it", queue.get());
		assertEquals("of", queue.get());
		assertEquals("the", queue.get());
		assertEquals("times", queue.get());
		assertEquals("was", queue.get());
		assertEquals("worst", queue.get());
		assertEquals(null, queue.get());
		
		assertEquals(0, queue.size());
		
	}	
	
	@Test
	public void testIterator() 
	{
		for (int i=0; i<strings.length; i++)
			queue.add(strings[i]);
		
		Iterator<String> iterator = queue.iterator();
		assertEquals("best", iterator.next());
		assertEquals("it", iterator.next());
		assertEquals("of", iterator.next());
		assertEquals("the", iterator.next());
		assertEquals("times", iterator.next());
		assertEquals("was", iterator.next());
		assertEquals("worst", iterator.next());
		assertFalse(iterator.hasNext());
		
		assertEquals(strings.length-REPETITIONS, queue.size());
		
	}		
	
	// Ancillary classes
	
	private class ArrayIndexer<T> implements Indexer<T>
	{
		private T[] array;
		
		public ArrayIndexer(T[] array)
		{
			this.array = array;
		}

		@Override
		public int index(T object) {
			
			for (int i=0; i<array.length; i++)
				if (object.equals(array[i]))
					return i;

			return -1;
		}	
	}
}