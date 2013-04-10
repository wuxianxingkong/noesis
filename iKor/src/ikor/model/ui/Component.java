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
	 * Standard output
	 */
	public String toString ()
	{
		return id;
	}
}
