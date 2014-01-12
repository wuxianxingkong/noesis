package ikor.collection.index;

// Title:       iKor Collection Framework
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal Galiano
// E-mail:      berzal@acm.org

/**
* Generic [integer] index interface.
* 
* @author Fernando Berzal
*/

public interface Index 
{	  
	/**
	 * Array size.
	 * @return Number of items in the array
	 */
	public int size ();

	/**
	 * Returns the element at the specified position in this array.
	 * @param index Element position
	 * @return Element in the collection
	 */
	public int get (int index);


	/**
	 * Replaces the element at the specified position in this array with the specified element.
	 * @param index Index of the element to replace
	 * @param value Element to be stored at the specified position
	 * @return The element previously at the specified position
	 */
	public void set (int index, int value);


	/**
	 * Adds an element to the array.	
	 * @param value Element to be added.
	 * @return true if the collection has changed
	 */
	public boolean add (int value);

	/**
	 * Removes an element from the array.
	 * @param index Element index
	 * @return element removed from this array, if present
	 */
	public int remove (int index);

	/**
	 * Removes an element from the collection.
	 * @param value Value to be removed
	 * @return true if the object was actually removed
	 */
	public boolean removeValue (int value);


	/**
	 * Returns the index of the specified element.
	 * @param value Element
	 * @return Index of the specified element within the array (-1 if it is not in the array)
	 */
	public int index (int value);

	/**
	 * Returns whether the specified element is within the array.
	 * @param value Element value
	 * @return true if the element is in the array, false otherwise
	 */
	public boolean contains (int value);

}
