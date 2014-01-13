package test.noesis;

//Title:       Mock visitor
//Version:     1.0
//Copyright:   2011
//Author:      Fernando Berzal
//E-mail:      berzal@acm.org

import ikor.collection.List;
import ikor.collection.Visitor;

import noesis.CollectionFactory;

import static org.junit.Assert.*;

/**
* Mock visitor
*/

public class MockVisitor<T> implements Visitor<T> 
{
	private List<T> expected;
	private int next;
	
	public MockVisitor()
	{
		expected = CollectionFactory.createList();
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
