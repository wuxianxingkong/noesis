package sandbox.adt;

import static org.junit.Assert.*;

import org.junit.Test;

public class SortedSkipListTest 
{
	SortedSkipList index = new SortedSkipList();

	@Test
	public void testConstructor() 
	{	
		assertEquals(0, index.size());
	}
	
	
	private void add (int n)
	{
		int total = index.size();
		
		for (int i=1; i<=n; i++) {
		    index.add(i);
		    assertEquals(total+i, index.size());
		}
		
		for (int i=0; i<n; i++) {
			assertEquals(i+1, index.get(total+i));
		}
	}
	
	@Test
	public void testAddition4()
	{
		add(4);
	}

	@Test
	public void testAddition8()
	{
		add(8);
	}

	@Test
	public void testAddition255()
	{
		add(255);
	}

	@Test
	public void testAddFirst7()
	{
		add(7);
		
		index.add(0);
		
		assertEquals(8, index.size());
		assertEquals(0, index.get(0));
		assertEquals(1, index.get(1));
		assertEquals(2, index.get(2));
		assertEquals(3, index.get(3));
		assertEquals(4, index.get(4));
		assertEquals(5, index.get(5));
		assertEquals(6, index.get(6));
		assertEquals(7, index.get(7));
	}

	@Test
	public void testAddFirst6()
	{
		add(6);
		
		index.add(0);
		
		assertEquals(7, index.size());
		assertEquals(0, index.get(0));
		assertEquals(1, index.get(1));
		assertEquals(2, index.get(2));
		assertEquals(3, index.get(3));
		assertEquals(4, index.get(4));
		assertEquals(5, index.get(5));
		assertEquals(6, index.get(6));
	}

	@Test
	public void testAddFirst5()
	{
		add(5);
		
		index.add(0);
		
		assertEquals(6, index.size());
		assertEquals(0, index.get(0));
		assertEquals(1, index.get(1));
		assertEquals(2, index.get(2));
		assertEquals(3, index.get(3));
		assertEquals(4, index.get(4));
		assertEquals(5, index.get(5));
	}

	@Test
	public void testAddFirst4()
	{
		add(4);
		
		index.add(0);
		
		assertEquals(5, index.size());
		assertEquals(0, index.get(0));
		assertEquals(1, index.get(1));
		assertEquals(2, index.get(2));
		assertEquals(3, index.get(3));
		assertEquals(4, index.get(4));
	}

	@Test
	public void testAddFirst3()
	{
		add(3);
		
		index.add(0);
		
		assertEquals(4, index.size());
		assertEquals(0, index.get(0));
		assertEquals(1, index.get(1));
		assertEquals(2, index.get(2));
		assertEquals(3, index.get(3));
	}

	@Test
	public void testAddSecond7()
	{
		add(7);
		
		index.add(2);
		
		assertEquals(8, index.size());
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(2, index.get(2));
		assertEquals(3, index.get(3));
		assertEquals(4, index.get(4));
		assertEquals(5, index.get(5));
		assertEquals(6, index.get(6));
		assertEquals(7, index.get(7));
	}

	@Test
	public void testAddSecond6()
	{
		add(6);
		
		index.add(2);
		
		assertEquals(7, index.size());
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(2, index.get(2));
		assertEquals(3, index.get(3));
		assertEquals(4, index.get(4));
		assertEquals(5, index.get(5));
		assertEquals(6, index.get(6));
	}

	@Test
	public void testAddSecond5()
	{
		add(5);
		
		index.add(2);
		
		assertEquals(6, index.size());
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(2, index.get(2));
		assertEquals(3, index.get(3));
		assertEquals(4, index.get(4));
		assertEquals(5, index.get(5));
	}

	@Test
	public void testAddSecond4()
	{
		add(4);
		
		index.add(2);
		
		assertEquals(5, index.size());
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(2, index.get(2));
		assertEquals(3, index.get(3));
		assertEquals(4, index.get(4));
	}

	@Test
	public void testAddSecond3()
	{
		add(3);
		
		index.add(2);
		
		assertEquals(4, index.size());
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(2, index.get(2));
		assertEquals(3, index.get(3));
	}

	@Test
	public void testRemoveAtLastElement()
	{
		add(4);
		
		index.removeAt(3);
		assertEquals(3, index.size());
		
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(3, index.get(2));
	}

	@Test
	public void testRemoveAtFirstElement()
	{
		add(4);
				
		index.removeAt(0);
		assertEquals(3, index.size());
		
		assertEquals(2, index.get(0));
		assertEquals(3, index.get(1));
		assertEquals(4, index.get(2));
	}

	@Test
	public void testRemoveAtSecondElement()
	{
		add(4);
		
		index.removeAt(1);
		assertEquals(3, index.size());
		
		assertEquals(1, index.get(0));
		assertEquals(3, index.get(1));
		assertEquals(4, index.get(2));
	}

	@Test
	public void testRemoveAtSecondToLastElement()
	{
		add(4);
		
		index.removeAt(2);
		assertEquals(3, index.size());
		
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(4, index.get(2));
	}
	
	private void remove (int size, int n)
	{
		index = new SortedSkipList();
		
		add(size);
		
		index.removeAt(n);
		assertEquals(size-1, index.size());
		
		// System.err.println(index);
		
		for (int i=0; i<n; i++)
			assertEquals(i+1, index.get(i));
		
		for (int i=n; i<index.size(); i++)
			assertEquals(i+2, index.get(i));
	}

	@Test
	public void testRemove8 ()
	{
		for (int i=0; i<1; i++)
		    remove (8, i);
	}

	@Test
	public void testRemove255 ()
	{
		for (int i=0; i<1; i++)
		    remove (255, i);
	}

	@Test
	public void testRemoveAt2()
	{
		add(4);
		
		index.removeAt(0);
		index.removeAt(0);
		assertEquals(2, index.size());
		
		assertEquals(3, index.get(0));
		assertEquals(4, index.get(1));
	}

	@Test
	public void testRemoveAt3()
	{
		add(4);
		
		index.removeAt(0);
		index.removeAt(0);
		index.removeAt(0);
		assertEquals(1, index.size());
		
		assertEquals(4, index.get(0));
	}

	@Test
	public void testRemoveAt4()
	{
		add(4);
		
		index.removeAt(0);
		index.removeAt(0);
		index.removeAt(0);
		index.removeAt(0);
		assertEquals(0, index.size());
	}

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
		
		index.set(2, 5);

		assertEquals(4, index.size());
		
		assertEquals(1, index.get(0));
		assertEquals(2, index.get(1));
		assertEquals(4, index.get(2));
		assertEquals(5, index.get(3));
	}
}
