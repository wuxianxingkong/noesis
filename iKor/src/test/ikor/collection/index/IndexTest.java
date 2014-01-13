package test.ikor.collection.index;

import static org.junit.Assert.*;

import java.util.Arrays;

import ikor.collection.index.Index;

import org.junit.Before;
import org.junit.Test;

public abstract class IndexTest 
{
	protected Index index;

	public abstract Index createIndex();

	@Before
	public void setUp()
	{
		this.index = createIndex();
	}

	// Constructor
	
	@Test
	public void testConstructor() 
	{	
		assertEquals(0, index.size());
	}

	// Check index values 1..n
	
	protected void checkAll ()
	{
		int values[] = index.values();
		
		Arrays.sort(values);
		
		for (int i=0; i<values.length; i++)
			assertEquals ( i+1, values[i]);
	}
	
	// Add elements to the index
	
	protected void add (int n)
	{
		for (int i=1; i<=n; i++) {
		    index.add(i);
		}
		
	}

	@Test
	public void testAdd4()
	{
		add(4);
	    checkAll();
	}

	@Test
	public void testAdd8()
	{
		add(8);
	    checkAll();
	}

	@Test
	public void testAdd255()
	{
		add(255);
	    checkAll();
	}

	// Remove individual elements from the index
	
	private void removeValue (int size, int n)
	{
		index = createIndex();
		
		add(size);
		
		index.removeValue(n+1);
		
		assertEquals(size-1, index.size());
		
		int values[] = index.values();
		
		Arrays.sort(values);
		
		for (int i=0; i<n; i++)
			assertEquals(i+1, values[i]);
		
		for (int i=n; i<index.size(); i++)
			assertEquals(i+2, values[i]);
	}

	private void removeValue (int size)
	{
		for (int i=0; i<size; i++)
		    removeValue (size, i);		
	}
	
	@Test
	public void testRemoveValue4 ()
	{
		removeValue(4);
	}

	@Test
	public void testRemoveValue8 ()
	{
		removeValue(8);
	}

	@Test
	public void testRemoveValue255 ()
	{
		removeValue(255);
	}

    // Remove all elements from the index
	
	private void removeAll (int size)
	{
		add(size);
		
		for (int k=1; k<=size; k++) {
			
			index.removeValue(k);
			
			assertEquals(size-k, index.size());
			
			int values[] = index.values();
			
			Arrays.sort(values);

			for (int i=0; i<values.length; i++)
				assertEquals(i+k+1, values[i]);
		}
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
		
		assertTrue( index.contains(1) );
		assertTrue( index.contains(2) );
		assertTrue( index.contains(3) );
		assertTrue( index.contains(4) );
		
		assertFalse( index.contains(0) );
		assertFalse( index.contains(5) );
	}
	
	@Test
	public void testSet()
	{
		add(4);
		
		index.set( index.index(3), 5);

		assertEquals(4, index.size());
		
		assertTrue( index.contains(1) );
		assertTrue( index.contains(2) );
		assertFalse( index.contains(3) );
		assertTrue( index.contains(4) );
		
		assertFalse( index.contains(0) );
		assertTrue( index.contains(5) );
		
		index.set( index.index(5), 3);

		assertEquals(4, index.size());

		assertTrue( index.contains(1) );
		assertTrue( index.contains(2) );
		assertTrue( index.contains(3) );
		assertTrue( index.contains(4) );
		
		assertFalse( index.contains(0) );
		assertFalse( index.contains(5) );

		index.set( index.index(3), 0);
		
		assertEquals(4, index.size());
		
		assertTrue( index.contains(1) );
		assertTrue( index.contains(2) );
		assertFalse( index.contains(3) );
		assertTrue( index.contains(4) );
		
		assertTrue( index.contains(0) );
		assertFalse( index.contains(5) );
	}
}
