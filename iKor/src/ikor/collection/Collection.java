package ikor.collection;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

import java.util.Iterator;

import java.lang.Iterable;
import java.io.Serializable;

/**
 * Generic collection interface.
 * 
 * @author Fernando Berzal
 */
public interface Collection<T> extends Iterable<T>, Serializable
{
  /**
   * Collection size.
   * @return Number of items in the collection
   */
  public int size ();
  
  /**
   * Returns true if this collection contains the specified element.
   * @param object element whose presence in this collection is to be tested
   * @return true if this collection contains the specified element
   */
  public boolean contains (T object);
  
  
  /**
   * Creates an iterator over the elements of the collection.
   */
  public Iterator<T> iterator ();
}
