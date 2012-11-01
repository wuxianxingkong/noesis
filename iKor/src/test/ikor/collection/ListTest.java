package test.ikor.collection;

import static org.junit.Assert.*;

import java.util.Iterator;

import ikor.collection.DynamicList;

import org.junit.Before;
import org.junit.Test;

public class ListTest {

	DynamicList<String> list;
	
	@Before
	public void setUp() throws Exception {
		list = new DynamicList<String>();
		
		list.add("one");
		list.add("two");
		list.add("three");
	}


	@Test
	public void testGet() {
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
		assertEquals("three", list.get(2));
		
		assertEquals(null, list.get(3));
		//try {
		//	list.get(3);
		//	fail("list.get() should have thrown an exception!");
		//} catch (Exception error) {
		//}		
	}

	@Test
	public void testIndex() {
		
		assertEquals(0, list.index("one"));
		assertEquals(1, list.index("two"));
		assertEquals(2, list.index("three"));
		
		assertEquals(-1, list.index("X"));
		assertEquals(-1, list.index(null));
	}
	
	
	@Test
	public void testSize() {
		
		DynamicList<String> newList = new DynamicList<String>();
		
		assertEquals(3, list.size());
		assertEquals(0, newList.size());
	}
	
	@Test
	public void testNullItem() {
		
		assertEquals(-1, list.index(null));
		list.add(null);
		assertEquals(3, list.index(null));
	}	

	@Test
	public void testIterator() {
		
		Iterator<String> iterator;
		
		iterator = list.iterator();

		assertEquals("one", iterator.next());
		assertEquals("two", iterator.next());
		assertEquals("three", iterator.next());
		assertEquals(null, iterator.next());
	}

	@Test
	public void testAdd() {
		
		list.add("four");
		
		assertEquals(4, list.size());
		assertEquals("four", list.get(3));
		assertEquals(3, list.index("four"));
	}

	@Test
	public void testRemoveFirst() {
		
		list.remove(0);
		
		assertEquals(2,list.size());
		assertEquals("two", list.get(0));
		assertEquals("three", list.get(1));
	}

	@Test
	public void testRemoveSecond() {
		
		list.remove(1);
		
		assertEquals(2,list.size());
		assertEquals("one", list.get(0));
		assertEquals("three", list.get(1));
	}

	@Test
	public void testRemoveLast() {
		
		list.remove(list.size()-1);
		
		assertEquals(2,list.size());
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
	}
	
	@Test
	public void testRemoveFirstElement() {
		
		list.remove("one");
		
		assertEquals(2,list.size());
		assertEquals("two", list.get(0));
		assertEquals("three", list.get(1));
	}

	@Test
	public void testRemoveSecondElement() {
		
		list.remove("two");
		
		assertEquals(2,list.size());
		assertEquals("one", list.get(0));
		assertEquals("three", list.get(1));
	}

	@Test
	public void testRemoveLastElement() {
		
		list.remove("three");
		
		assertEquals(2,list.size());
		assertEquals("one", list.get(0));
		assertEquals("two", list.get(1));
	}
	
	@Test
	public void testClear() {
		list.clear();
		assertEquals(0,list.size());
		assertEquals(-1, list.index("one"));
		assertEquals(-1, list.index("two"));
		assertEquals(-1, list.index("three"));
	}
		

	@Test
	public void testSet() {
		
		assertEquals("one", list.get(0));
		list.set(0, "one+");
		assertEquals("one+", list.get(0));
		
		assertEquals("two", list.get(1));
		list.set(1, "two+");
		assertEquals("two+", list.get(1));

		assertEquals("three", list.get(2));
		list.set(2, "three+");
		assertEquals("three+", list.get(2));
		
		try {
			// Automatic addition of new nodes
			list.set(3, "out");
			assertEquals("out",list.get(3));
			// fail("list.set() should have thrown an exception!");
		} catch (Exception error) {
		}
		
	}


	@Test
	public void testContains() {
		
		assertTrue(list.contains("one"));
		assertTrue(list.contains("two"));
		assertTrue(list.contains("three"));
		
		assertFalse(list.contains("x"));
		assertFalse(list.contains(null));
	}
}
