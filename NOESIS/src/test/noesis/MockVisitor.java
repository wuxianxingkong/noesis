package test.noesis;

//Title:       Mock visitor
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import ikor.collection.DynamicList;
import ikor.collection.Visitor;

import static org.junit.Assert.*;

/**
* Mock visitor
*/

public class MockVisitor<T> implements Visitor<T> 
{
	private DynamicList<T> expected;
	private int next;
	
	public MockVisitor()
	{
		expected = new DynamicList<T>();
		next = 0;
	}
	
	public void addVisit (T element)
	{
		expected.add(element);
	}
	
	public void visit (T object)
	{
		// System.out.println(object);
		assertEquals(expected.get(next), object);
		next++;
	}
	
	public void checkFinished ()
	{
		assertEquals("Incomplete visit", expected.size(), next);
	}
}
