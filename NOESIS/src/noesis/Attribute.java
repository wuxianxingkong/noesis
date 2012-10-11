package noesis;

// Title:       Network ADT
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.DynamicList;

/**
 * Network node attribute
 * 
 * @author Fernando Berzal
 *
 * @param <T> Base type
 */
public class Attribute<T> extends DynamicList<T> 
{
	String id;
	
	public Attribute (String id)
	{
		this.id = id;
	}
	
	public String getID()
	{
		return id;
	}

	public void setID(String id)
	{
		this.id = id;
	}	

}
