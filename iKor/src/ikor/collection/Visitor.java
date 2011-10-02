package ikor.collection;

// Title:       Visitor
// Version:     1.0
// Copyright:   2008
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org



/**
 * Visitor
 * 
 * @author Fernando Berzal
 */

public interface Visitor<T>
{
   public void visit (T object);
}