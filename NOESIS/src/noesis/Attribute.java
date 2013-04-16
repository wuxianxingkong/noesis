package noesis;

// Title:       Network ADT
// Version:     1.0
// Copyright:   2012
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.DynamicList;
import ikor.model.data.DataModel;

/**
 * Network node attribute
 * 
 * @author Fernando Berzal
 *
 * @param <T> Base type
 */
public class Attribute<T> extends DynamicList<T> 
{
	private String id;
	private DataModel type;
	
	public Attribute (String id)
	{
		this.id = id;
	}

	public Attribute (String id, DataModel type)
	{
		this.id = id;
		this.type = type;
	}

	public final String getID()
	{
		return id;
	}

	public final void setID(String id)
	{
		this.id = id;
	}	
	
	
	public final void set (int index, String value)
	{
		if (type!=null)
			super.set( index, (T) type.fromString(value) );
		else
			super.set( index, (T) value );
	}
	
	
	private final static DataModel DEFAULT_MODEL = new ikor.model.data.TextModel();
	
	public final DataModel getModel ()
	{
		if (type!=null)
			return type;
		else
			return DEFAULT_MODEL;
	}
	
	public final void setModel (DataModel model)
	{
		this.type = model;
	}

}
