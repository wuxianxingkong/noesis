package test.ikor.collection.array;

import static org.junit.Assert.*;
import ikor.collection.array.Array;

import org.junit.Before;
import org.junit.Test;

public abstract class ArrayTest 
{
	Array array;

	public abstract Array createArray();

	@Before
	public void setUp()
	{
		this.array = createArray();
	}

	// Constructor
	
	@Test
	public void testConstructor() 
	{	
		assertEquals(0, array.size());
	}

	// Adding elements to the array
	
	private void add (int n)
	{
		int total = array.size();
		
		for (int i=1; i<=n; i++) {
		    array.add(i);
		    assertEquals(total+i, array.size());
		}
		
		for (int i=0; i<n; i++) {
			assertEquals(i+1, array.get(total+i));
		}
	}

	
	@Test
	public void testAdd4()
	{
		add(4);
	}

	@Test
	public void testAdd8()
	{
		add(8);
	}

	@Test
	public void testAdd255()
	{
		add(255);
	}

	// Removing elements from the array
	
	@Test
	public void testRemoveAtLastElement()
	{
		add(4);
		
		array.remove(3);

		assertEquals(3, array.size());
		
		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		assertEquals(3, array.get(2));
		
		array.remove(2);
		
		assertEquals(2, array.size());
		
		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));

		array.remove(1);
		
		assertEquals(1, array.size());
		
		assertEquals(1, array.get(0));

		array.remove(0);
		
		assertEquals(0, array.size());
	}
	

	@Test
	public void testRemoveAtFirstElement()
	{
		add(4);
				
		array.remove(0);
		
		assertEquals(3, array.size());
		
		assertEquals(2, array.get(0));
		assertEquals(3, array.get(1));
		assertEquals(4, array.get(2));
		
		array.remove(0);
		
		assertEquals(2, array.size());
		
		assertEquals(3, array.get(0));
		assertEquals(4, array.get(1));

		array.remove(0);
		
		assertEquals(1, array.size());
		
		assertEquals(4, array.get(0));

		array.remove(0);
		
		assertEquals(0, array.size());
	}


	@Test
	public void testRemoveAtSecondElement()
	{
		add(4);
		
		array.remove(1);

		assertEquals(3, array.size());
		
		assertEquals(1, array.get(0));
		assertEquals(3, array.get(1));
		assertEquals(4, array.get(2));
		
		array.remove(1);
		
		assertEquals(2, array.size());
		
		assertEquals(1, array.get(0));
		assertEquals(4, array.get(1));

		array.remove(1);
		
		assertEquals(1, array.size());
		
		assertEquals(1, array.get(0));

		array.remove(0);
		
		assertEquals(0, array.size());
	}

	@Test
	public void testRemoveAtSecondToLastElement()
	{
		add(4);
		
		array.remove(2);

		assertEquals(3, array.size());
		
		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		assertEquals(4, array.get(2));
		
		array.remove(2);
		
		assertEquals(2, array.size());
		
		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));

		array.remove(1);
		
		assertEquals(1, array.size());
		
		assertEquals(1, array.get(0));

		array.remove(0);
		
		assertEquals(0, array.size());
	}
	
	
	private void remove (int size, int n)
	{
		array = createArray();
		
		add(size);
		
		array.remove(n);
		assertEquals(size-1, array.size());
		
		for (int i=0; i<n; i++)
			assertEquals(i+1, array.get(i));
		
		for (int i=n; i<array.size(); i++)
			assertEquals(i+2, array.get(i));
	}

	private void remove (int size)
	{
		for (int i=0; i<size; i++)
		    remove (size, i);		
	}

	
	private void removeAll (int size, int n)
	{
		array = createArray();
		
		add(size);
		
		for (int k=1; k<=size; k++) {
			array.remove(n);
			assertEquals(size-k, array.size());

			for (int i=0; i<n; i++)
				assertEquals(i+1, array.get(i));

			for (int i=n; i<array.size(); i++)
				assertEquals(i+1+k, array.get(i));
			
			if (array.size()<=n)
				n = array.size()-1;
		}
	}

	private void removeAll (int size)
	{
		for (int i=0; i<size; i++)
		    removeAll (size, i);		
	}

	
	@Test
	public void testRemove4 ()
	{
		remove(4);
	}

	@Test
	public void testRemove8 ()
	{
		remove(8);
	}

	@Test
	public void testRemove255 ()
	{
		remove(255);
	}


	@Test
	public void testRemoveAll4 ()
	{
		removeAll(4);
	}

	@Test
	public void testRemoveAll8 ()
	{
		removeAll(8);
	}

	@Test
	public void testRemoveAll255 ()
	{
		removeAll(255);
	}


	// Ancillary methods: contains & set
	
	@Test
	public void testContains()
	{
		add(4);
		
		assertTrue( array.contains(1) );
		assertTrue( array.contains(2) );
		assertTrue( array.contains(3) );
		assertTrue( array.contains(4) );
		
		assertFalse( array.contains(0) );
		assertFalse( array.contains(5) );
	}
	
	@Test
	public void testSet()
	{
		add(4);
		
		array.set(2, 5);
		
		assertEquals(4, array.size());
		
		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		assertEquals(5, array.get(2));
		assertEquals(4, array.get(3));
		
		array.set(2, 3);

		assertEquals(4, array.size());

		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		assertEquals(3, array.get(2));
		assertEquals(4, array.get(3));

		array.set(2, 0);
		
		assertEquals(4, array.size());
		
		assertEquals(1, array.get(0));
		assertEquals(2, array.get(1));
		assertEquals(0, array.get(2));
		assertEquals(4, array.get(3));
	}
}
