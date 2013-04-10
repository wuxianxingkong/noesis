package ikor.model.ui;

/**
 * Generic interface for user selection actions.
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 * @param <T> selectable object class
 */

public interface SelectionListener<T>
{
	public void setSelection (T object);
	
	public void addSelection (T object);
	
	public void clearSelection ();
}
