package ikor.model.ui;

import ikor.model.Subject;

import java.util.UUID;


/**
 * Base class for user interface components.
 * 
 * @author Fernando Berzal
 */
public class Component<T> extends Subject<T>
{
	private String id;
	private boolean visible = true;
	
	/**
	 * Default constructor
	 */
	public Component ()
	{
		this.id = UUID.randomUUID().toString();
	}
	
	/**
	 * Component constructor
	 * 
	 * @param id Component ID
	 */
	public Component (String id)
	{
		this.id = id;
	}

	/**
	 * Get component ID.
	 * 
	 * @return Component ID
	 */
	public String getId ()
	{
		return id;
	}
	
	/**
	 * Set component ID.
	 * 
	 * @param id ID to be set
	 */
	public void setId (String id)
	{
		this.id = id;
	}


	/**
	 * Is the component visible?
	 * 
	 * @return true when the component is visible.
	 */
	public boolean isVisible() 
	{
		return visible;
	}

	/**
	 * Set component visibility.
	 * 
	 * @param visible true to show, false to hide.
	 */
	public void setVisible(boolean visible) 
	{
		this.visible = visible;
		notifyObservers();
	}
	
	/** 
	 * Show component
	 */
	public void show ()
	{
		setVisible(true);
	}

	/**
	 * Hide component
	 */
	
	public void hide ()
	{
		setVisible(false);
	}
	
	/**
	 * Standard output
	 */
	public String toString ()
	{
		return id;
	}
}
