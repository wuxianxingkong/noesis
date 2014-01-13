package test.ikor.collection.index;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ikor.collection.index.Index;
import ikor.collection.index.ArrayIndex;


public class ArrayIndexTest extends IndexTest
{
	@Override
	public Index createIndex() 
	{
		return new ArrayIndex();
	}

	// Removing elements from the array
	
	@Test
	public void testRemoveAtLastElement()
	{
		add(4);
		
		index.remove(3);

		assertEquals(3, index.size());
		
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(3, index.get(2));
		
		index.remove(2);
		
		assertEquals(2, index.size());
		
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));

		index.remove(1);
		
		assertEquals(1, index.size());
		
		assertEquals(1, index.get(0));

		index.remove(0);
		
		assertEquals(0, index.size());
	}
	

	@Test
	public void testRemoveAtFirstElement()
	{
		add(4);
				
		index.remove(0);
		
		assertEquals(3, index.size());
		
		assertEquals(2, index.get(0));
		assertEquals(3, index.get(1));
		assertEquals(4, index.get(2));
		
		index.remove(0);
		
		assertEquals(2, index.size());
		
		assertEquals(3, index.get(0));
		assertEquals(4, index.get(1));

		index.remove(0);
		
		assertEquals(1, index.size());
		
		assertEquals(4, index.get(0));

		index.remove(0);
		
		assertEquals(0, index.size());
	}


	@Test
	public void testRemoveAtSecondElement()
	{
		add(4);
		
		index.remove(1);

		assertEquals(3, index.size());
		
		assertEquals(1, index.get(0));
		assertEquals(3, index.get(1));
		assertEquals(4, index.get(2));
		
		index.remove(1);
		
		assertEquals(2, index.size());
		
		assertEquals(1, index.get(0));
		assertEquals(4, index.get(1));

		index.remove(1);
		
		assertEquals(1, index.size());
		
		assertEquals(1, index.get(0));

		index.remove(0);
		
		assertEquals(0, index.size());
	}

	@Test
	public void testRemoveAtSecondToLastElement()
	{
		add(4);
		
		index.remove(2);

		assertEquals(3, index.size());
		
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(4, index.get(2));
		
		index.remove(2);
		
		assertEquals(2, index.size());
		
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));

		index.remove(1);
		
		assertEquals(1, index.size());
		
		assertEquals(1, index.get(0));

		index.remove(0);
		
		assertEquals(0, index.size());
	}
	
    // Remove all elements from the index
	
	private void removeAllArray (int size, int n)
	{	
		index = createIndex();

		add(size);

		for (int k=1; k<=size; k++) {
			index.remove(n);
			assertEquals(size-k, index.size());

			for (int i=0; i<n; i++)
				assertEquals(i+1, index.get(i));

			for (int i=n; i<index.size(); i++)
				assertEquals(i+1+k, index.get(i));

			if (index.size()<=n)
				n = index.size()-1;
		}
	}

	private void removeAllArray (int size)
	{
		for (int i=0; i<size; i++)
		    removeAllArray (size, i);		
	}
	
	@Test
	public void testRemoveAllArray4 ()
	{
		removeAllArray(4);
	}

	@Test
	public void testRemoveAllArray8 ()
	{
		removeAllArray(8);
	}

	@Test
	public void testRemoveAllArray255 ()
	{
		removeAllArray(255);
	}
	
	
	// Changing elements in the array
	
	@Test
	public void testSetArray()
	{
		add(4);
		
		index.set(2, 5);
		
		assertEquals(4, index.size());
		
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(5, index.get(2));
		assertEquals(4, index.get(3));
		
		index.set(2, 3);

		assertEquals(4, index.size());

		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(3, index.get(2));
		assertEquals(4, index.get(3));

		index.set(2, 0);
		
		assertEquals(4, index.size());
		
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(0, index.get(2));
		assertEquals(4, index.get(3));
	}
	
	
}
