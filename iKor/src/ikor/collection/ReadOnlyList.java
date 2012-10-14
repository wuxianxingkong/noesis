package ikor.collection;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2011
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

/**
 * Generic read-only list interface. 
 * 
 * Lists are ordered collections that allow indexed access to their contents.
 * 
 * @author Fernando Berzal
 */
public interface ReadOnlyList<T> extends ReadOnlyCollection<T>
{
  /**
   * Returns the element at the specified position in this list.
   * @param index Element position
   * @return Element in the collection
   */
  public T get (int index);

  /**
   * Returns the index of the specified element.
   * @param index Element
   * @return Index of the specified element within the list (-1 if it is not in the list)
   */
  public int index (T object);
}
