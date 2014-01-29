package noesis;

// Title:       Network ADT
// Version:     1.1
// Copyright:   2014
// Author:      Fernando Berzal
// E-mail:      berzal@acm.org

import ikor.collection.Dictionary;
import ikor.collection.List;

/**
 * Attribute collection
 * 
 * @author Fernando Berzal (berzal@acm.org)
 *
 * @param <T> Attribute type, e.g. Attribute or LinkAttribute
 */
public class AttributeSet<T extends Attribute> 
{
	private List<String> names;
	private Dictionary<String, T> attributes;

	public AttributeSet ()
	{
		attributes = CollectionFactory.createDictionary();
		names = CollectionFactory.createList();
	}

	
	public int size()
	{
		return attributes.size();
	}
	
	public T get (int index)
	{
		return attributes.get( names.get(index) );
	}
	
	public T get (String id)
	{
		return attributes.get(id);
	}
	
	public void add (T attribute)
	{
		attributes.set(attribute.getID(), attribute);
		names.add(attribute.getID());
	}
	
	public void remove (String id)
	{
		names.remove(id);
		attributes.remove(id);
	}	
}
