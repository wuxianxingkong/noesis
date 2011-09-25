package test.ikor.collection.graph;

// Title:       Generic Graph ADT
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.util.Visitor;

/**
 * Naive visitor
 */

class NaiveVisitor<T> implements Visitor<T> 
{
	public void visit (T object)
	{
		System.out.println(object.toString());
	}
}
