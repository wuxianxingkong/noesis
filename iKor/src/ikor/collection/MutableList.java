package ikor.collection;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

/**
* Mutable list interface.
* 
* In contrast to the standard Java Collections Framework, mutable collections
* are separated from the standard immutable collection interface (as in COCOA
* for Mac OS X and iOS). 
* 
* @author Fernando Berzal
*/

public interface MutableList<T> extends List<T>, MutableCollection<T> 
{	  
  /**
   * Replaces the element at the specified position in this list with the specified element.
   * @param index index of the element to replace
   * @param object element to be stored at the specified position
   * @return the element previously at the specified position
   */
  public T set (int index, T object);
    
  /**
   * Removes an element from the collection.
   * @param index Element position
   * @return element removed from this list, if present
   */
  public T remove (int index);
    
}
