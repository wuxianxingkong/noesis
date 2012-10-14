package ikor.collection;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

/**
 * Generic collection interface.
 * 
 * In contrast to the standard Java Collections Framework, mutable collections
 * are separated from the standard immutable ReadOnlyCollection interface (as in COCOA
 * for Mac OS X and iOS). 
 * 
 * @author Fernando Berzal
 */
public interface Collection<T> extends ReadOnlyCollection<T> 
{
  /**
   * Adds an element to the collection.	
   * @param object Element to be added.
   * @return true if the collection has changed
   */
  public boolean add (T object);

  
  /**
   * Removes an element from the collection.
   * @param object Object to be removed
   * @return true if the object was removed
   */
  public boolean remove (T object);

  /**
   * Removes all the elements from the collection.
   */
  public void clear ();
}
